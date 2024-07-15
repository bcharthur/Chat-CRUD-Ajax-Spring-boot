package dev.bcharthur.crudajax.listener;

import dev.bcharthur.crudajax.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ChatEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @TransactionalEventListener
    public void handleAfterCreate(Chat chat) {
        messagingTemplate.convertAndSend("/topic/chats", "Created: " + chat.getId());
    }

    @TransactionalEventListener
    public void handleAfterUpdate(Chat chat) {
        messagingTemplate.convertAndSend("/topic/chats", "Updated: " + chat.getId());
    }

    @TransactionalEventListener
    public void handleAfterDelete(Chat chat) {
        messagingTemplate.convertAndSend("/topic/chats", "Deleted: " + chat.getId());
    }
}
