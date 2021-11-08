package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/") //make all URL's through this controller relative to /index
public class IndexController {
	
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value="/", method= RequestMethod.GET)
	public String index(Map<String, Object> model) throws Exception {
		logger.info("Hello world");
		return "index";
	}
	
	/**************************************
	 * Connexion de l'utilisateur
	 * **Identification : /signin
	 * **Creation compte : /signup
	 ***************************************/
	
	@RequestMapping(value="/signin", method= RequestMethod.GET)
	public String signin(Map<String, Object> model) throws Exception {
		logger.info("Login page");
		return "connection/signin";
	}
	
	@RequestMapping(value="/signup", method= RequestMethod.GET)
	public String signup(Map<String, Object> model) throws Exception {
		logger.info("Register page");
		
		return "connection/signup";
	}
	

	/**************************************
	 * Espace de l'utilisateur
	 * **Gestion du compte : /settings
	 * **Choix du jeu : /choice
	 ***************************************/
	@RequestMapping(value="/settings", method= RequestMethod.GET)
	public String settings(Map<String, Object> model) throws Exception {
		logger.info("Settings page");
		return "/user/dashboard";
	}
	
	@RequestMapping(value="/choice", method= RequestMethod.GET)
	public String choice(Map<String, Object> model) throws Exception {
		logger.info("Game choice page");
		return "/user/gameChoice";
	}
	
	/**************************************
	 * Différents jeux
	 * **Tic-tac-toe : /tictactoe
	 ***************************************/
	
	@RequestMapping(value="/tictactoe", method= RequestMethod.GET)
	public String tictactoe(Map<String, Object> model) throws Exception {
		logger.info("Tictactoe game page");
		return "/games/tictactoe";
	}
	

}
