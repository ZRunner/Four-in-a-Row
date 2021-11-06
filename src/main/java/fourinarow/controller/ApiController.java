package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
		((Tictactoe) session.getAttribute("tictactoe")).setSquare(index,Players.PLAYER);
		if(((Tictactoe) session.getAttribute("tictactoe")).getMessage().equals("ok")) {
			/* Implement AI there */
			do{
				((Tictactoe) session.getAttribute("tictactoe")).setSquare((int)(Math.random() * 9),Players.IA);
				System.out.println(((Tictactoe) session.getAttribute("tictactoe")).toString());
			}while(!((Tictactoe) session.getAttribute("tictactoe")).getMessage().equals("ok"));
			Tictactoe game = (Tictactoe) session.getAttribute("tictactoe");
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
			Tictactoe game = (Tictactoe) session.getAttribute("tictactoe");
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
