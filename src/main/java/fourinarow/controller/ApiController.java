package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@RestController
@RequestMapping("/api") //make all URL's through this controller relative to /api
/* use @GetMapping for GET method and @PostMapping for POST method */
public class ApiController {
	
	private final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@GetMapping("ping")
	public String hello() throws Exception {
		logger.info("Ping log");
		return new JSONObject("{response:pong}").toString();
	}

}
