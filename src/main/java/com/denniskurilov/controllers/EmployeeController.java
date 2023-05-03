package com.denniskurilov.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.denniskurilov.dao.EmployeeDAO;
import com.denniskurilov.models.Employee;

@Controller
public class EmployeeController {

	private EmployeeDAO employeeDAO;

	public EmployeeController(EmployeeDAO employeeDAO) {
		super();
		this.employeeDAO = employeeDAO;
	}

	@GetMapping("/")
	public String getAll(Model model) {
		model.addAttribute("employees", employeeDAO.getAll());
		return "index";
	}

	@GetMapping("/{id}")
	public String get(@PathVariable("id") long id, Model model) {
		model.addAttribute("employee", employeeDAO.get(id));
		return "item";
	}

	@GetMapping("/new")
	public String create(@ModelAttribute("employee") Employee employee) {
		return "new";
	}

	@PostMapping("/new")
	public String create(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new";
		}
		employeeDAO.save(employee);
		return "redirect:/";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") long id, Model model) {
		model.addAttribute("employee", employeeDAO.get(id));
		return "edit";
	}

	@PatchMapping("/{id}/edit")
	public String update(@PathVariable("id") long id, @ModelAttribute("employee") @Valid Employee employee,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "edit";
		}
		employee.setId(id);
		employeeDAO.update(employee);
		return "redirect:/" + id;
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") long id) {
		employeeDAO.delete(id);
		return "redirect:/";
	}

}
