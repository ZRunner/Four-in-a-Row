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

import fourinarow.classes.PuissanceN;
import fourinarow.classes.PuissanceN.InvalidSizeException;
import fourinarow.classes.Tictactoe;
import fourinarow.classes.Tictactoe.Player;
import fourinarow.model.User;
import fourinarow.services.AuthenticationUtils;
import fourinarow.services.AuthenticationUtils.InvalidTokenException;
import fourinarow.services.AuthenticationUtils.MissingTokenException;
import fourinarow.services.HistoryLogRepository;

@RestController
@RequestMapping("/api") //make all URL's through this controller relative to /api
public class ApiController {
	
	private final String adminPassword = "123abc";
	private final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private AuthenticationUtils authenticationUtils;
	
	@Autowired
	private HistoryLogRepository logsRepository;
	
	@GetMapping(path="ping", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> hello() throws Exception {
		logger.info("Ping log");
		JSONObject resp = new JSONObject();
		resp.put("response","pong");
		return ResponseEntity.ok(resp.toString());
	}
     
	@PostMapping(path="login")
	@ResponseBody
	public ResponseEntity<String> login(HttpEntity<String> httpEntity) {
		JSONObject json;
		try {
			json = new JSONObject(httpEntity.getBody());
		} catch (NullPointerException e) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid JSON body");
			return ResponseEntity.status(400).body(error.toString());
		}
		
		return authenticationUtils.POST_login(json);
	}
	
	@PostMapping(path="signup")
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
			JSONObject error = new JSONObject();
			error.put("error","Missing token");
			return ResponseEntity.status(401).body(error.toString());
		} catch (InvalidTokenException e) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid token");
			return ResponseEntity.status(401).body(error.toString());
		}
	}
	
	@GetMapping(path="me/profile", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> profile(HttpEntity<String> httpEntity) {
		try {
			return authenticationUtils.GET_profile(httpEntity.getHeaders());
		} catch (MissingTokenException e) {
			JSONObject error = new JSONObject();
			error.put("error","Missing token");
			return ResponseEntity.status(401).body(error.toString());
		} catch (InvalidTokenException e) {
			JSONObject error = new JSONObject();
			error.put("error","Invalid token");
			return ResponseEntity.status(401).body(error.toString());
		}
	}
	
	@PostMapping(path="me/password", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> edit_password(HttpEntity<String> httpEntity, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		JSONObject json = new JSONObject(httpEntity.getBody());
		return authenticationUtils.POST_password(user, json);
	}
	
	@PostMapping(path="me/username", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> edit_username(HttpEntity<String> httpEntity, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		JSONObject json = new JSONObject(httpEntity.getBody());
		return authenticationUtils.POST_username(user, json);
	}

	@GetMapping(path="admin/users", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUsers(HttpEntity<String> httpEntity, @RequestParam String password) {
		if(!password.equals(adminPassword)) {
			JSONObject json = new JSONObject();
			json.put("error","Access denied.");
			return ResponseEntity.status(401).body(json.toString());
		}
		return authenticationUtils.GET_users();
	}
	/**************************************
	 * Get the Tictactoe grid
	 * path : /api/getTictactoe
	 * method : GET
	 * content-type :
	 * 		out : the grid
	 ***************************************/
	@GetMapping(value="tictactoe/get",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTictactoe(HttpSession session, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		if(session.getAttribute("tictactoe")==null){
			session.setAttribute("tictactoe", new Tictactoe(user, logsRepository));
		}
		return ResponseEntity.ok(session.getAttribute("tictactoe").toString());		
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
	@GetMapping(value="tictactoe/set",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateTictactoe(@RequestParam int index, HttpSession session,@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		if (session.getAttribute("tictactoe") == null){
			session.setAttribute("tictactoe", new Tictactoe(user, logsRepository));
		}
		Tictactoe game = (Tictactoe) session.getAttribute("tictactoe");
		game.setSquare(index,Player.PLAYER);
		if (game.getMessage().equals("ok")) {
			if (game.getWinner() == null) {
				game.playAI();
			}
			if (game.getWinner() != null) {
				game.saveLogs();
				session.setAttribute("tictactoe", new Tictactoe(user, logsRepository));
			}
			return ResponseEntity.ok(game.toString());
		}else{
			JSONObject error = new JSONObject();
			error.put("error",((Tictactoe) session.getAttribute("tictactoe")).getMessage());
			return ResponseEntity.status(400).body(error.toString());
		}
	}
	
	/**************************************
	 * Get the PuissanceN grid
	 * path : /api/ninarow/get
	 * method : GET
	 * content-type :
	 * 		out : JSON with grid and puissance
	 ***************************************/
	@GetMapping(value="ninarow/get",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ninarowGet(HttpSession session, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		if(session.getAttribute("ninarow")==null){
			JSONObject error = new JSONObject();
			error.put("error","The game isn't initialized yet");
			return ResponseEntity.status(424).body(error.toString());
		}
		return ResponseEntity.ok(session.getAttribute("ninarow").toString());		
	}
	
	/**************************************
	 * Initialization of the PuissanceN grid
	 * path : /ninarow/init
	 * method : GET
	 * params : 
	 * 		size : size of the game (correspond to "puissance" in the class)
	 * content-type :
	 * 		out : JSON
	 ***************************************/
	@GetMapping(value="ninarow/init",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ninarowInit(@RequestParam int size, HttpSession session, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		PuissanceN game;
		if(session.getAttribute("ninarow")==null){
			try {
				game = new PuissanceN(size);
				session.setAttribute("ninarow",game);
			}catch(InvalidSizeException e) {
				JSONObject error = new JSONObject();
				error.put("error",e.getMessage());
				return ResponseEntity.status(400).body(error.toString());
			}
			return ResponseEntity.ok((new JSONObject()).put("reponse","Game initialized successfully with a size of "+size).toString());
		}else {
			JSONObject error = new JSONObject();
			error.put("error","The game is already initialized");
			return ResponseEntity.status(409).body(error.toString());
		}
	}
	
	/**************************************
	 * Put a piece in the PuissanceN grid
	 * If won game : reset the grid
	 * path : /api/ninarow/set
	 * method : GET
	 * params : 
	 * 		index : column where the player played
	 * content-type :
	 * 		out : JSON
	 ***************************************/
	@GetMapping(value="ninarow/set",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ninarowSet(@RequestParam int index, HttpSession session,@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		if(session.getAttribute("ninarow")==null){
			JSONObject error = new JSONObject();
			error.put("error","The game isn't initialized yet");
			return ResponseEntity.status(424).body(error.toString());
		}
		PuissanceN game = (PuissanceN) session.getAttribute("ninarow");
		game.play(index,Player.PLAYER);
		if (game.getMessage().equals("ok")) {
			if (game.getWinner() == null) {
				game.playAI();
				if(game.getWinner() != null) {
					ResponseEntity<String> response = ResponseEntity.ok(session.getAttribute("ninarow").toString());
					session.setAttribute("ninarow",null);
					return response;
				}
			}else {
				ResponseEntity<String> response = ResponseEntity.ok(session.getAttribute("ninarow").toString());
				session.setAttribute("ninarow",null);
				return response;
			}
			return ResponseEntity.ok(game.toString());
		}else{
			JSONObject error = new JSONObject();
			error.put("error",((PuissanceN) session.getAttribute("ninarow")).getMessage());
			return ResponseEntity.status(400).body(error.toString());
		}
	}
}
