package github.nyaku12.ASCONAChat.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    public Message createMessage(String contain, Long receiverId, Long sender_id, Timestamp senttime){
        Message message = new Message(contain, receiverId, sender_id, senttime);
        messageRepository.save(message);
        return message;
    }
    public List<Message> getMessages(int receiverId){
        return messageRepository.getByreceiverIdOrderBySenttime(receiverId);
    }
}
