package fourinarow.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fourinarow.model.User;
import fourinarow.services.AuthenticationUtils;

@Controller
@RequestMapping("/") //make all URL's through this controller relative to /index
public class IndexController {
	
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private AuthenticationUtils authenticationUtils;

	@RequestMapping(value="/", method= RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) throws Exception {
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/navbar.js\"></script>");
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
		css.add("<link href=\"css/home.css\" rel=\"stylesheet\" />");
		
		try{
			User user = authenticationUtils.getUserFomServletRequest(request);
			if(user != null){
				model.addAttribute("logged",true);				
			}else {
				model.addAttribute("logged",false);				
			}
		}catch(Exception e){
			model.addAttribute("logged",false);
		}
		
		
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
	
	@RequestMapping(value="/admin", method= RequestMethod.GET)
	public String admin(Model model, @RequestParam String password) throws Exception {
		if(!password.equals("123abc")) {
			return "accessDenied";
		}
		List<String> css = new ArrayList<String>();
		css.add("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
		css.add("<link href=\"/css/admin.css\" rel=\"stylesheet\" />");
		List<String> js = new ArrayList<String>();
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/admin.js\"></script>");
		js.add("<script src=\"/js/navbar.js\"></script>");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","Admin panel");
		
		return "user/admin"; 
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
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/tictactoe.js\"></script>");
		js.add("<script src=\"/js/navbar.js\"></script>");
		
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
		js.add("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\" integrity=\"sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=\" crossorigin=\"anonymous\"></script>");
		js.add("<script src=\"/js/nrows.js\"></script>");
		js.add("<script src=\"/js/navbar.js\"></script>");
		
		model.addAttribute("css",css);
		model.addAttribute("js",js);
		model.addAttribute("title","N in a row");
		
		return "/games/nrows";
	}
	

}
