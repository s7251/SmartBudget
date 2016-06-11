package pl.smartbudget.controller;

import javax.annotation.PostConstruct;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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