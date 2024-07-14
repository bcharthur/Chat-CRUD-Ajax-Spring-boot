package dev.bcharthur.crudajax.service;

import dev.bcharthur.crudajax.model.Student;
import dev.bcharthur.crudajax.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<Student> listAll() {
        return repo.findAll();
    }

    public void save(Student std) {
        repo.save(std);
    }

    public Student getid(long id) {
        return repo.findById(id).orElse(null);
    }

    public Student get(long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    @Scheduled(fixedRate = 1000) // Vérifie toutes les secondes
    public void checkForUpdates() {
        List<Student> students = repo.findAll();
        messagingTemplate.convertAndSend("/topic/students", students);
    }
}
