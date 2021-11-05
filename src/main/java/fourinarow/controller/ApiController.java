package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fourinarow.classes.Tictactoe;
import fourinarow.classes.Tictactoe.Players;

import org.json.JSONObject;

@RestController
@RequestMapping("/api") //make all URL's through this controller relative to /api
/* use @GetMapping for GET method and @PostMapping for POST method */
public class ApiController {
	
	private final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@GetMapping(value="ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> hello() throws Exception {
		logger.info("Ping log");
		JSONObject resp = new JSONObject("{\"response\":\"pong\"}");
		return ResponseEntity.ok(resp.toString());
	}
	
	/**************************************
	 * Check the Tictactoe grid
	 * path : /api/updateTictactoe
	 * method : POST
	 * content-type : 
	 * 		in : JSON
	 * 		out : JSON
	 * params: 
	 * 		grid : integer array
	 ***************************************/
	@PostMapping(value="/updateTictactoe", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateTictactoe(@RequestBody Tictactoe game) {
		if(game.getMessage().equals("ok")) {
			return ResponseEntity.ok(game.toString());
		}else{
			if(game.getMessage().equals("")) {
				game.setMessage("The grid parameter isn't present");
			}
			JSONObject response = game.toJSON();
			response.put("timestamp", new Date().getTime());
			response.put("status", 400);
			response.put("error", "Bad Request");
			response.put("message", game.getMessage());
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response.toString());
		}
	}
	
}
