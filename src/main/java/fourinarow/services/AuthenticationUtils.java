package fourinarow.services;

import java.net.HttpCookie;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import fourinarow.model.GameFromHistory;
import fourinarow.model.Token;
import fourinarow.model.User;

@Service
public class AuthenticationUtils {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private HistoryLogRepository logsRepository;
	
	
	public static class MissingTokenException extends Exception {
		private static final long serialVersionUID = -3057499381260178233L;
		public MissingTokenException() {
			super("No authentication token found in request headers");
		}
	}
	public static class InvalidTokenException extends Exception {
		private static final long serialVersionUID = 7772118219382198080L;
		public InvalidTokenException() {
			super("Invalid authentication token");
		}
	}
	
	private Rfc6265CookieProcessor processor = new Rfc6265CookieProcessor();
	
	/**
	 * Generate a new token for a specific user and save it into the database
	 * @param username
	 * @param userId
	 * @return the generated token
	 */
	private String generateJWT(String username, Long userId) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		Map<String, Object> payloadClaims = new HashMap<>();
		payloadClaims.put("username", username);
		payloadClaims.put("userid", userId);
		String token = JWT.create()
		        .withIssuer("fourinarow")
		        .withPayload(payloadClaims)
		        .withIssuedAt(new Date())
		        .sign(algorithm);
		tokenRepository.save(new Token(token, userId));
		return token;
	}
	
	/**
	 * Create a new user from their username and password and save it into the database<br/>
	 * If a similar user already exists, the method will return null
	 * @param username
	 * @param password
	 * @return The new User object or null
	 */
	private User createUser(String username, String password) {
		if (!userRepository.getFromUsername(username).isEmpty()) {
			return null;
		}
		User user = new User(username, password);
		userRepository.save(user);
		return user;
	}
	
	/**
	 * Check if a user exists from their username and password
	 * @param username
	 * @param password
	 * @return true if this user exists, else false
	 */
	public boolean checkUser(String username, String password) {
		return this.userRepository.existsFromLogin(username, password) == 1;
	}
	
	/**
	 * Check if a user exists, then create a new token for them
	 * @param username
	 * @param password
	 * @param userId
	 * @return The new token or null
	 */
	private String loginUser(String username, String password, Long userId) {
		if (checkUser(username, password)) {
			return generateJWT(username, userId);
		}
		return null;
	}
	
	/**
	 * Get a user object from an authentication token
	 * @param token
	 * @return The attached user
	 * @throws InvalidTokenException if the token cannot be found
	 * @throws MissingTokenException if no token has been provided
	 */
	public User getUserFromToken(String token) throws InvalidTokenException, MissingTokenException {
		if (token == null) {
			throw new MissingTokenException();
		}
		Token tokenObj = tokenRepository.findOne(token);
		if (tokenObj == null) throw new InvalidTokenException();
		return userRepository.findOne(tokenObj.getUserId());
	}
	
	/**
	 * Retrieve a token from the request COOKIE header (Springboot version)
	 * @param headers The springboot headers set
	 * @return The given token, or null
	 */
	private String getTokenFromHeaders(HttpHeaders headers) {
		if (headers.getFirst("Cookie") == null) {
			return null;
		}
		List<HttpCookie> cookies = CookieParser.parseCookies(headers.getFirst("Cookie"));
		String token = null;
		for (HttpCookie cookie: cookies) {
			if (cookie.getName().equals("token")) {
				token = cookie.getValue().trim();
			}
		};
		return token;
	}
	
	/**
	 * Retrieve a token from the request cookie (JavaX version)
	 * @param cookies The request cookies
	 * @return The given token, or null
	 */
	private String getTokenFromJavaXCookies(Cookie[] cookies) {
		if (cookies == null) {
			return null;
		}
		String token = null;
		for (Cookie cookie: cookies) {
			if (cookie.getName().equals("token")) {
				token = cookie.getValue().trim();
			}
		}
		return token;
	}
	
	/**
	 * Get a user object from the request COOKIE header (Springboot version)
	 * @param headers The springboot headers set
	 * @return The authenticated user
	 * @throws InvalidTokenException
	 * @throws MissingTokenException
	 */
	public User getUserFromHeaders(HttpHeaders headers) throws InvalidTokenException, MissingTokenException {
		String token = getTokenFromHeaders(headers);
		return getUserFromToken(token);
	}
	
	/**
	 * Get a user from the request object (JavaX version)
	 * @param req The request object from JavaX
	 * @return The authenticated user
	 * @throws InvalidTokenException
	 * @throws MissingTokenException
	 */
	public User getUserFomServletRequest(HttpServletRequest req) throws InvalidTokenException, MissingTokenException {
		String token = getTokenFromJavaXCookies(req.getCookies());
		return getUserFromToken(token);
	}
	
	// login user
	public ResponseEntity<String> POST_login(JSONObject json) {
		// check if user exists
		List<User> users = userRepository.getFromUsername(json.getString("username"));
		if (users.isEmpty()) {
			JSONObject error = new JSONObject();
			error.put("error","Unknown user");
			return ResponseEntity.badRequest().body(error.toString());
		}
		User user = users.get(0);
		// create a new token for this user, if passwords match
		String token = loginUser(user.getUsername(), json.getString("password"), user.getIdUser());
		if (token == null) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid username or password");
			return ResponseEntity.badRequest().body(error.toString());
		}
		// package it into a header
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(604800); // 7 days
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", processor.generateHeader(cookie));
		// send to client
		JSONObject res = new JSONObject();
		res.put("response","OK");
		return ResponseEntity.ok().headers(headers).body(res.toString());
	}
	
	// create new user
	public ResponseEntity<String> POST_signup(JSONObject json) {
		// create the new user
		User user = this.createUser(json.getString("username"), json.getString("password"));
		if (user == null) {
			// if a user with the same username already exists, we exit
			JSONObject error = new JSONObject();
			error.put("error","Username already exists");
			return ResponseEntity.badRequest().body(error.toString());
		}
		// create a token for the new user
		String token = loginUser(user.getUsername(), user.getPassword(), user.getIdUser());
		if (token == null) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid username or password");
			return ResponseEntity.badRequest().body(error.toString());
		}
		// package it into a header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "token="+token+"; Path=/; Max-Age=604800; HttpOnly"); // 7 days
		// send to client
		JSONObject res = new JSONObject();
		res.put("response","OK");
		return ResponseEntity.ok().headers(headers).body(res.toString());
	}
	
	// logout user
	public ResponseEntity<String> POST_logout(HttpHeaders headers) throws MissingTokenException, InvalidTokenException {
		String auth = getTokenFromHeaders(headers);
		User user = getUserFromToken(auth);
		this.tokenRepository.deleteFromUser(user.getIdUser());
		JSONObject res = new JSONObject();
		res.put("response","Used logged out");
		return ResponseEntity.ok(res.toString());
	}
	
	// send user profile
	public ResponseEntity<String> GET_profile(HttpHeaders headers) throws MissingTokenException, InvalidTokenException {
		String auth = getTokenFromHeaders(headers);
		JSONObject user = getUserFromToken(auth).toJSON();
		JSONArray games = new JSONArray();
		for (GameFromHistory game: logsRepository.getGamesList(user.getLong("id"))) {
			games.put(game.toJSON());
		};
		user.put("games", games);
		return ResponseEntity.ok(user.toString());
	}
	
	// edit user name (must be unique)
	public ResponseEntity<String> POST_username(User user, JSONObject json) {
		String new_username = json.getString("username");
		if (new_username == null || new_username.length() < 4) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid username");
			return ResponseEntity.badRequest().body(error.toString());
		}
		if (!userRepository.getFromUsername(new_username).isEmpty()) {
			JSONObject error = new JSONObject();
			error.put("error","Username already exists");
			return ResponseEntity.badRequest().body(error.toString());
		}
		user.setUsername(new_username);
		userRepository.save(user);
		JSONObject res = new JSONObject();
		res.put("response","Ok");
		return ResponseEntity.ok(res.toString());
	}
	
	// edit user password
	public ResponseEntity<String> POST_password(User user, JSONObject json) {
		String new_password = json.getString("password");
		if (new_password == null || new_password.length() < 6) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid password");
			return ResponseEntity.badRequest().body(error.toString());
		}
		user.setPassword(new_password);
		userRepository.save(user);
		JSONObject res = new JSONObject();
		res.put("response","OK");
		return ResponseEntity.ok(res.toString());
	}
	
	// list of all users
	public ResponseEntity<String> GET_users() {
		Iterable<User> users = userRepository.findAll();
		Iterator<User> it = users.iterator();
		JSONArray res = new JSONArray();
		while(it.hasNext()) {
			res.put(it.next().toJSON());
		}
		return ResponseEntity.ok(res.toString());
	}
	
	// delete a user by his id
		public ResponseEntity<String> DELETE_user(Long id) {
			try {
				User user = userRepository.findOne(id);
			}catch(Exception e) {
				JSONObject error = new JSONObject();
				error.put("error","User doesn't exist.");
				return ResponseEntity.status(400).body(error.toString());
			}
			try {
				userRepository.delete(id);
				tokenRepository.deleteFromUser(id);
				JSONObject res = new JSONObject();
				res.put("response","User "+id+" deleted.");
				return ResponseEntity.ok(res.toString());
			}catch (Exception e) {
				JSONObject error = new JSONObject();
				error.put("error",e.getMessage());
				return ResponseEntity.status(500).body(error.toString());
			}
		}
}
