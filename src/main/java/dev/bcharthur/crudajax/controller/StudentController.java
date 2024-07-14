package dev.bcharthur.crudajax.controller;

import dev.bcharthur.crudajax.model.Student;
import dev.bcharthur.crudajax.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Student")
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Student std) {
        service.save(std);
        messagingTemplate.convertAndSend("/topic/students", service.listAll());
        return "{\"status\":\"success\"}";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Student> listStudents() {
        return service.listAll();
    }

    @RequestMapping(value = "/get/{id}")
    public Student getStudentPage(@PathVariable(name = "id") int id) {
        return service.getid(id);
    }

    @PostMapping(value = "/edit/{id}")
    public String updateStudent(@PathVariable("id") long id, Student student) {
        student.setId(id);
        service.save(student);
        messagingTemplate.convertAndSend("/topic/students", service.listAll());
        return "{\"status\":\"success\"}";
    }

    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") int id) {
        service.delete(id);
        messagingTemplate.convertAndSend("/topic/students", service.listAll());
        return "{\"status\":\"success\"}";
    }
}
