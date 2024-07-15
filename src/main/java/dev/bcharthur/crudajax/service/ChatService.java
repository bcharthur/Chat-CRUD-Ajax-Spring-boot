package dev.bcharthur.crudajax.service;

import dev.bcharthur.crudajax.model.Chat;
import dev.bcharthur.crudajax.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<Chat> listAll() {
        return chatRepository.findAll();
    }

    public Chat save(Chat chat) {
        chatRepository.save(chat);
        messagingTemplate.convertAndSend("/topic/chats", chat);
        return chat;
    }

    public Chat updateMessage(Long id, String newMessage) {
        Chat chat = chatRepository.findById(id).orElse(null);
        if (chat != null) {
            chat.setMessage(newMessage);
            chatRepository.save(chat);
            messagingTemplate.convertAndSend("/topic/chats", chat);
        }
        return chat;
    }

    public void deleteMessage(Long id) {
        chatRepository.deleteById(id);
        messagingTemplate.convertAndSend("/topic/chats/delete", id);
    }

    public Chat hideMessage(Long id) {
        Chat chat = chatRepository.findById(id).orElse(null);
        if (chat != null) {
            chat.setMessage("ce message a été masqué par un administrateur");
            chatRepository.save(chat);
            messagingTemplate.convertAndSend("/topic/chats", chat);
        }
        return chat;
    }
}
