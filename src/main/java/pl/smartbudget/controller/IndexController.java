package pl.smartbudget.controller;

import java.security.Principal;

import javax.annotation.PostConstruct;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.smartbudget.service.UserService;

@Controller
public class IndexController {
	


	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}

	@PostConstruct
	public void startDBManager() {

		// hsqldb manager
		DatabaseManagerSwing
				.main(new String[] { "--url", "jdbc:hsqldb:mem:dataSource", "--user", "sa", "--password", "" });

	}

}