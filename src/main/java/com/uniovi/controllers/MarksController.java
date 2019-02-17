package com.uniovi.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Mark;
import com.uniovi.services.MarksService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddMarkFormValidator;

@Controller
public class MarksController {

	@Autowired 
	private MarksService marksService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AddMarkFormValidator addMarkFormValidator;

	@RequestMapping("/mark/list")
	public String getList(Model model) {
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list";
	}

	@RequestMapping("/mark/list/update")
	public String updateList(Model model) {
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list :: tableMarks";
	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.GET)
	public String getMark(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		model.addAttribute("mark", new Mark());
		return "mark/add";
	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
	public String setMark(@Validated Mark mark, BindingResult result, Model model) {
		model.addAttribute("usersList", usersService.getUsers());
	    addMarkFormValidator.validate(mark, result);
		if (result.hasErrors()) {
			return "/mark/add";
		}
		marksService.addMark(mark);
		return "redirect:/mark/list";
	}

	@RequestMapping("/mark/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/details";
	}

	@RequestMapping("/mark/delete/{id}")
	public String deleteMark(@PathVariable Long id) {
		marksService.deleteMark(id);
		return "redirect:/mark/list";
	}

	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.GET)
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/edit";
	}

	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @Validated Mark mark, BindingResult result) {
		addMarkFormValidator.validate(mark, result);
		if (result.hasErrors()) {
			return "/mark/edit/" + id;
		}
		Mark original = marksService.getMark(id);
		// modificar solo score y description
		original.setScore(mark.getScore());
		original.setDescription(mark.getDescription());
		marksService.addMark(original);
		return "redirect:/mark/details/" + id;
	}

	@RequestMapping("/mark/filter")
	public String getFilter(Model model) {
		model.addAttribute("markList", Collections.<Mark>emptyList());
		return "mark/filter";
	}

	@RequestMapping(value = "/mark/filter", method = RequestMethod.POST)
	public String getFilter(Model model, @RequestParam Integer min, @RequestParam Integer max) {
		model.addAttribute("markList", marksService.getFilteredByMinMaxScore(min, max));
		return "mark/filter";
	}

}
