package fourinarow.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import fourinarow.model.Token;
import fourinarow.model.User;

@Service
public class AuthenticationUtils {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
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
	public String loginUser(String username, String password, Long userId) {
		if (checkUser(username, password)) {
			return generateJWT(username, userId);
		}
		return null;
	}
	
	public ResponseEntity<String> POST_login(JSONObject json) {
		// check if user exists
		List<User> users = userRepository.getFromUsername(json.getString("username"));
		if (users.isEmpty()) {
			return ResponseEntity.badRequest().body("Unknown user");
		}
		User user = users.get(0);
		// create a new token for this user, if passwords match
		String token = loginUser(user.getUsername(), json.getString("password"), user.getIdUser());
		if (token == null) {
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
		// package it into a JSON
		JSONObject response = new JSONObject();
		response.put("token", token);
		// send to client
		return ResponseEntity.ok(response.toString());
	}
	
	public ResponseEntity<String> POST_signup(JSONObject json) {
		// create the new user
		User user = this.createUser(json.getString("username"), json.getString("password"));
		if (user == null) {
			// if a user with the same username already exists, we exit
			return ResponseEntity.badRequest().body("Username already exists");
		}
		// create a token for the new user
		String token = loginUser(user.getUsername(), user.getPassword(), user.getIdUser());
		if (token == null) {
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
		// package it into a JSON
		JSONObject response = new JSONObject();
		response.put("token", token);
		// send to client
		return ResponseEntity.ok(response.toString());
	}

}
