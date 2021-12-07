package com.akshitbaunthy.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.akshitbaunthy.springmvc.entity.Student;
import com.akshitbaunthy.springmvc.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	// Endpoint to get all students in the table
	@RequestMapping("/list")
	public String listStudents(Model model) {
		List<Student> students = studentService.getAll();

		model.addAttribute("Students", students);

		return "list-Students";
	}

	// Endpoint to show form for inserting new student
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// Get the Student from the service

		Student student = new Student();
		// Set Student as a model attribute to pre-populate the form
		theModel.addAttribute("Student", student);

		// Send over to student form
		return "Student-form";
	}

	// Endpoint to show form for updating old student
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int id, Model model) {

		// Get the Student from the service
		Student fetchedStudent = studentService.getById(id);

		// Set Student as a model attribute to pre-populate the form
		model.addAttribute("Student", fetchedStudent);

		// Send over to student form
		return "Student-form";
	}

	// Endpoint for updating old student or inserting old student based on whether the ID passed in the argument is 0 or not
	@PostMapping("/insert")
	public String insertStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		Student fetchedStudent;
		if (id != 0) {
			fetchedStudent = studentService.getById(id);
			fetchedStudent.setName(name);
			fetchedStudent.setDepartment(department);
			fetchedStudent.setCountry(country);
		} else
			fetchedStudent = new Student(name, department, country);
		// Save the Student
		studentService.insert(fetchedStudent);

		// Use a redirect to prevent duplicate submissions
		return "redirect:/students/list";

	}

	// Endpoint for deleting existing student
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int id) {

		// Delete the Student
		studentService.delete(id);

		// Redirect to /students/list
		return "redirect:/students/list";

	}

}
