package fourinarow.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fourinarow.classes.Tictactoe;
import fourinarow.classes.Tictactoe.Players;
import fourinarow.services.AuthenticationUtils;
import fourinarow.services.AuthenticationUtils.InvalidTokenException;
import fourinarow.services.AuthenticationUtils.MissingTokenException;

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
	
	/**************************************
	 * Set a square in the Tictactoe grid
	 * If won game : reset the grid and put stats in database
	 * path : /api/setTictactoe
	 * method : GET
	 * params : 
	 * 		index : position where the player played
	 * content-type :
	 * 		out : JSON
	 ***************************************/
	@GetMapping(value="/setTictactoe",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateTictactoe(@RequestParam int index, HttpSession session) {
		if(session.getAttribute("tictactoe")==null){
			session.setAttribute("tictactoe", new Tictactoe());
		}
		Tictactoe game = (Tictactoe) session.getAttribute("tictactoe");
		game.setSquare(index,Players.PLAYER);
		if(game.getMessage().equals("ok")) {
			if (game.getWinner()==null) {/* Implement AI there */
				do{
					game.setSquare((int)(Math.random() * 9),Players.IA);
					System.out.println(game.toString());
				}while(!game.getMessage().equals("ok"));
			}
			/* End Implement AI */
			if(game.getWinner()!=null) {
				if(game.getWinner()==Players.PLAYER) {
					//Add a winning game to statistics
				}else {
					//Add a loose game to statistics
				}
				session.setAttribute("tictactoe", new Tictactoe());
			}
			return ResponseEntity.ok(game.toString());
		}else{
			JSONObject response = new JSONObject();
			response.put("timestamp", new Date().getTime());
			response.put("status", 400);
			response.put("error", "Bad Request");
			response.put("message", game.getMessage());
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response.toString());
		}
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
	
	@GetMapping(path="profile", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> profile(HttpEntity<String> httpEntity) {
		try {
			return authenticationUtils.GET_profile(httpEntity.getHeaders());
		} catch (MissingTokenException e) {
			return ResponseEntity.status(401).body("Missing token");
		} catch (InvalidTokenException e) {
			return ResponseEntity.status(401).body("Invalid token");
		}
	}

	/**************************************
	 * Get the Tictactoe grid
	 * path : /api/getTictactoe
	 * method : GET
	 * content-type :
	 * 		out : the grid
	 ***************************************/
	@GetMapping(value="/getTictactoe",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTictactoe(HttpSession session) {
		if(session.getAttribute("tictactoe")==null){
			session.setAttribute("tictactoe", new Tictactoe());
		}
		return ResponseEntity.ok(session.getAttribute("tictactoe").toString());		
	}
}
