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
    public Message createMessage(String contain, Long receiverId, Long sender_id, Timestamp senttime, Long chat_id){
        Message message = new Message(contain, receiverId, sender_id, senttime, chat_id);
        messageRepository.save(message);
        return message;
    }
    public List<Message> getMessages(int receiverId){
        return messageRepository.getByreceiverIdAndStatusFalseOrderBySenttime(receiverId);
    }
    public void readMessages(List<Double> message_id){
        for (double i : message_id){
            messageRepository.readMessage(((Number) i).longValue());
        }
    }
}
