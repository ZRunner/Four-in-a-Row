package fourinarow.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthenticationUtils {
	
	@Autowired
	private UserRepository userRepository;
	
	protected static String generateJWT(String username) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		Map<String, Object> payloadClaims = new HashMap<>();
		payloadClaims.put("username", username);
		return JWT.create()
		        .withIssuer("fourinarow")
		        .withPayload(payloadClaims)
		        .sign(algorithm);
	}
	
	public boolean checkUser(String username, String password) {
		System.out.println("username: "+username+"   password: "+password);
		System.out.println("   repo: "+userRepository);
		return this.userRepository.existsFromLogin(username, password) == 1;
	}
	
	public String loginUser(String username, String password) {
		if (checkUser(username, password)) {
			return generateJWT(username);
		}
		return null;
	}
	
	public ResponseEntity<String> POST_login(JSONObject json) {
		String token = loginUser((String) json.get("username"), (String) json.get("password"));
		if (token == null) {
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
		JSONObject response = new JSONObject();
		response.put("token", token);
		return ResponseEntity.ok(response.toString());
	}

}
