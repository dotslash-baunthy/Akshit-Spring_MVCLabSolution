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

	@RequestMapping("/list")
	public String listStudents(Model model) {
		List<Student> students = studentService.printAll();

		model.addAttribute("Students", students);

		return "list-Students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// get the Student from the service

		Student student = new Student();
		// set Student as a model attribute to pre-populate the form
		theModel.addAttribute("Student", student);

		// send over to our form
		return "Student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int id, Model model) {

		// get the Student from the service
		Student fetchedStudent = studentService.findById(id);

		// set Student as a model attribute to pre-populate the form
		model.addAttribute("Student", fetchedStudent);

		// send over to our form
		return "Student-form";
	}

	@PostMapping("/insert")
	public String insertStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		Student fetchedStudent;
		if (id != 0) {
			fetchedStudent = studentService.findById(id);
			fetchedStudent.setName(name);
			fetchedStudent.setDepartment(department);
			fetchedStudent.setCountry(country);
		} else
			fetchedStudent = new Student(name, department, country);
		// save the Student
		studentService.insert(fetchedStudent);

		// use a redirect to prevent duplicate submissions
		return "redirect:/students/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int id) {

		// delete the Student
		studentService.delete(id);

		// redirect to /students/list
		return "redirect:/students/list";

	}

}
