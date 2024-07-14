package dev.bcharthur.crudajax.listener;

import dev.bcharthur.crudajax.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StudentEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @TransactionalEventListener
    public void handleAfterCreate(Student student) {
        messagingTemplate.convertAndSend("/topic/students", "Created: " + student.getId());
    }

    @TransactionalEventListener
    public void handleAfterUpdate(Student student) {
        messagingTemplate.convertAndSend("/topic/students", "Updated: " + student.getId());
    }

    @TransactionalEventListener
    public void handleAfterDelete(Student student) {
        messagingTemplate.convertAndSend("/topic/students", "Deleted: " + student.getId());
    }
}
