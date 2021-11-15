package fourinarow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String signin(Model model) throws Exception {
		logger.info("Login page");
		
		List<String> css = new ArrayList<String>();
		css.add("/css/login.css");
		List<String> js = new ArrayList<String>();
		js.add("/js/login.js");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","Sign in");
		
		return "connection/signin"; 
	}
	
	@RequestMapping(value="/signup", method= RequestMethod.GET)
	public String signup(Model model) throws Exception {
		logger.info("Register page");
		
		List<String> css = new ArrayList<String>();
		css.add("/css/login.css");
		List<String> js = new ArrayList<String>();
		js.add("/js/login.js");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","Sign up");
		
		return "connection/signin"; 
	}
	

	/**************************************
	 * Espace de l'utilisateur
	 * **Gestion du compte : /settings
	 * **Choix du jeu : /choice
	 ***************************************/
	@RequestMapping(value="/settings/{page}", method= RequestMethod.GET)
	public String settings(Model model, @PathVariable(value = "page") String page) throws Exception {
		logger.info("Settings page");
		
		List<String> css = new ArrayList<String>();
		css.add("/css/dashboard.css");
		List<String> js = new ArrayList<String>();
		js.add("/js/dashboard.js");
		
		switch(page) {
		  case "statistics":
				model.addAttribute("title","Statistics");
		    break;
		  case "history":
				model.addAttribute("title","Game history");
		    break;
		  default:
				model.addAttribute("title","Settings");
		}
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		
		return "/user/dashboard";
	}
	
	@RequestMapping(value="/home", method= RequestMethod.GET)
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
