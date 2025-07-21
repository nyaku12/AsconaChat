package github.nyaku12.ASCONAChat.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    public Chat createChat (String name, Boolean open, Long passhash){
        Chat chat = new Chat(name, open, passhash);
        chatRepository.save(chat);
        return chat;
    }
}
