package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fourinarow.services.AuthenticationUtils;

import org.json.JSONObject;

@RestController
@RequestMapping("/api") //make all URL's through this controller relative to /api
public class ApiController {
	
	private final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@GetMapping(path="ping", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> hello() throws Exception {
		logger.info("Ping log");
		JSONObject resp = new JSONObject("{\"response\":\"pong\"}");
		return ResponseEntity.ok(resp.toString());
	}
	
	@PostMapping(path="login", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(Map<String, Object> model) {
		return AuthenticationUtils.POST_login(model);
	}

}
