package com.example.polls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.MainMenu;
import com.example.polls.repository.MenuRepository;

@RestController
@RequestMapping("/api")
public class MenuController {
	
	@Autowired
	MenuRepository menuRepository;
	
	@GetMapping("/menu/getMenuItems")
	public @ResponseBody List<MainMenu> getMenuItems()
	{
		List<MainMenu> allMenuItems =  menuRepository.findAll();
		return allMenuItems;
		
	}
}
