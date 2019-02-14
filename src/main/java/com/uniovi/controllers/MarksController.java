package com.uniovi.controllers;

import java.util.Collections;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Mark;
import com.uniovi.services.MarksService;

@Controller
public class MarksController {

	@Autowired // Inyectar el servicio
	private MarksService marksService;

	@RequestMapping("/mark/list")
	public String getList(Model model) {
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list";
	}

	@RequestMapping(value = "/mark/add")
	public String getMark() {
		return "mark/add";
	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
	public String setMark(@ModelAttribute Mark mark) {
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

	@RequestMapping(value = "/mark/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/edit";
	}

	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Mark mark) {
		mark.setId(id);
		marksService.addMark(mark);
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
