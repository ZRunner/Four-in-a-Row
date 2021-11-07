package fourinarow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fourinarow.classes.Tictactoe;
import fourinarow.classes.Tictactoe.Players;
import fourinarow.model.User;
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
	public ResponseEntity<String> updateTictactoe(@RequestParam int index, HttpSession session,@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		if(session.getAttribute("tictactoe")==null){
			session.setAttribute("tictactoe", new Tictactoe());
		}
		((Tictactoe) session.getAttribute("tictactoe")).setSquare(index,Players.PLAYER);
		if(((Tictactoe) session.getAttribute("tictactoe")).getMessage().equals("ok")) {
			if(((Tictactoe) session.getAttribute("tictactoe")).getWinner()==null) {
				/* Implement AI there */
				do{
					((Tictactoe) session.getAttribute("tictactoe")).setSquare((int)(Math.random() * 9),Players.IA);
				}while(!((Tictactoe) session.getAttribute("tictactoe")).getMessage().equals("ok"));
				/* End Implement AI */
			}
			Tictactoe game = (Tictactoe) session.getAttribute("tictactoe");
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
			return ResponseEntity.status(400).body(((Tictactoe) session.getAttribute("tictactoe")).getMessage());
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
	public ResponseEntity<String> getTictactoe(HttpSession session, HttpServletRequest request) {
		System.out.println(((User) request.getAttribute("user")).getUsername());
		if(session.getAttribute("tictactoe")==null){
			session.setAttribute("tictactoe", new Tictactoe());
		}
		return ResponseEntity.ok(session.getAttribute("tictactoe").toString());		
	}
}
