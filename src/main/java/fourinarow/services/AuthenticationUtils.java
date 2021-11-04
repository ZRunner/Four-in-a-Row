package fourinarow.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class AuthenticationUtils {
	
	protected static String generateJWT(String username) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		Map<String, Object> payloadClaims = new HashMap<>();
		payloadClaims.put("username", username);
		return JWT.create()
		        .withIssuer("fourinarow")
		        .withPayload(payloadClaims)
		        .sign(algorithm);
	}
	
	public static boolean checkUser(String username, String password) {
		return true;
	}
	
	public static String loginUser(String username, String password) {
		if (checkUser(username, password)) {
			return generateJWT(username);
		}
		return null;
	}
	
	public static ResponseEntity<String> POST_login(Map<String, Object> model) {
		String token = loginUser((String) model.get("username"), (String) model.get("password"));
		if (token == null) {
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
		JSONObject response = new JSONObject();
		response.put("token", token);
		return ResponseEntity.ok(response.toString());
	}

}
