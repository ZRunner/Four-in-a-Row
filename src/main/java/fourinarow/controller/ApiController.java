package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fourinarow.services.AuthenticationUtils;
import fourinarow.services.AuthenticationUtils.InvalidTokenException;
import fourinarow.services.AuthenticationUtils.MissingTokenException;

import org.json.JSONObject;

@RestController
@RequestMapping("/api") //make all URL's through this controller relative to /api
public class ApiController {
	
	private final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private AuthenticationUtils authenticationUtils;
	
	@GetMapping(path="ping", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> hello() throws Exception {
		logger.info("Ping log");
		JSONObject resp = new JSONObject("{\"response\":\"pong\"}");
		return ResponseEntity.ok(resp.toString());
	}
	
	@PostMapping(path="login", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> login(HttpEntity<String> httpEntity) {
		JSONObject json = new JSONObject(httpEntity.getBody());
		return authenticationUtils.POST_login(json);
	}
	
	@PostMapping(path="signup", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> signup(HttpEntity<String> httpEntity) {
		JSONObject json = new JSONObject(httpEntity.getBody());
		return authenticationUtils.POST_signup(json);
	}
	
	@PostMapping(path="logout")
	public ResponseEntity<String> logout(HttpEntity<String> httpEntity) {
		try {
			return authenticationUtils.POST_logout(httpEntity.getHeaders());
		} catch (MissingTokenException e) {
			return ResponseEntity.status(401).body("Missing token");
		} catch (InvalidTokenException e) {
			return ResponseEntity.status(401).body("Invalid token");
		}
	}

}
