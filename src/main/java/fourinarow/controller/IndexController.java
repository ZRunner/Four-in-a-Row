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
	public String index(Model model) throws Exception {
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
		css.add("<link href=\"css/home.css\" rel=\"stylesheet\" />");
		
		model.addAttribute("js",js);
		model.addAttribute("css",css);
		model.addAttribute("title","Home");
		return "index";
	}
	
	/**************************************
	 * Connexion de l'utilisateur
	 * **Identification : /signin
	 * **Creation compte : /signup
	 ***************************************/
	
	@RequestMapping(value="/signin", method= RequestMethod.GET)
	public String signin(Model model) throws Exception {
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"/css/login.css\" rel=\"stylesheet\" />");
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/login.js\"></script>");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","Sign in");
		
		return "connection/signin"; 
	}
	
	@RequestMapping(value="/signup", method= RequestMethod.GET)
	public String signup(Model model) throws Exception {
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"/css/login.css\" rel=\"stylesheet\" />");
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/login.js\"></script>");
		
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
		css.add("<link rel=\"stylesheet\" href=\"/css/dashboard.css\">");
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/dashboard.js\"></script>");
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
	public String tictactoe(Model model) throws Exception {
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
		css.add("<link href=\"/css/games.css\" rel=\"stylesheet\" />");
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
		
		js.add("<script src=\"/js/tictactoe.js\"></script>");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","Tic-Tac-Toe");
		
		return "/games/tictactoe";
	}

	@RequestMapping(value="/nrows", method= RequestMethod.GET)
	public String nrows(Model model) throws Exception {
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
		css.add("<link href=\"/css/games.css\" rel=\"stylesheet\" />");
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/nrows.js\"></script>");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","N in a row");
		
		return "/games/nrows";
	}
	

}
