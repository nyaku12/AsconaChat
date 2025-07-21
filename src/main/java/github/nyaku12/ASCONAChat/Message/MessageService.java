package github.nyaku12.ASCONAChat.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    public Message createMessage(String contain, Long receiverId, Long sender_id){
        Message message = new Message(contain, receiverId, sender_id);
        messageRepository.save(message);
        return message;
    }
}
