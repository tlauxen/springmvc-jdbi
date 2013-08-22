package com.tlauxen.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.util.StringMapper;
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
    	
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	h.execute("create table moeda (idmoeda integer, descricao varchar);");
    	h.execute("insert into moeda (idmoeda, descricao) values (?,?)", 1, "Real");
    	h.execute("insert into moeda (idmoeda, descricao) values (?,?)", 2, "Dolar");
    	Query<Map<String, Object>> q = h.createQuery("select descricao from moeda");
    	Query<String> map = q.map(StringMapper.FIRST);
    	List<String> list = map.list();
    	model.addAttribute("mensagem", list.toString());
    	h.execute("drop table moeda;");
    	h.close();

    	return "index";
    }
    
}
