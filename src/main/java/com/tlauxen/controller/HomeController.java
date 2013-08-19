package com.tlauxen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Autowired
	private DataSource dataSource;

    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "index";
    }
    
    @RequestMapping("/testarConexao")
    public String testarConexao(HttpServletRequest request, HttpServletResponse response, Model model) {
    	
    	try {
	    	DBI dbi = new DBI(dataSource);
	    	Handle h = dbi.open();
	    	h.close();
	    	model.addAttribute("statusConexao", ": Conexão ativa");
    	} catch (Exception e) {
    		model.addAttribute("statusConexao", ": Conexão inativa");
    	}
    	
    	return "index";
    }
    
}
