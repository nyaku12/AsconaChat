package github.nyaku12.ASCONAChat;

import github.nyaku12.ASCONAChat.Chat.Chat;
import github.nyaku12.ASCONAChat.Chat.ChatService;
import github.nyaku12.ASCONAChat.Message.Message;
import github.nyaku12.ASCONAChat.Message.MessageService;
import github.nyaku12.ASCONAChat.User.User;
import github.nyaku12.ASCONAChat.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GeneralController {
    @Autowired
    UserService userService;
    @Autowired
    ChatService chatService;
    @Autowired
    MessageService messageService;
    public User createUser(Map<String, Object> jmap){
        return (userService.createUser(
                jmap.get("login").toString(),
                ((Number) jmap.get("password").hashCode()).longValue(),
                jmap.get("name").toString(),
                jmap.get("enscryptionKey").toString()));
    }
    public Chat createChat(Map<String, Object> jmap){
        return (chatService.createChat(
                jmap.get("name").toString(),
                (Boolean) jmap.get("open"),
                ((Number) jmap.get("passhash").hashCode()).longValue()
        ));
    }
    public Message createMessage(Map<String, Object> jmap){
        return(messageService.createMessage(
                jmap.get("contain").toString(),
                ((Number) jmap.get("receiver")).longValue(),
                ((Number) jmap.get("sender")).longValue()
        ));
    }
    public int checkUser(HttpHeaders headers){
        User user = userService.getByLogin(headers.getFirst("login"));
        if(user == null) return (1);//неверный логин
        if(user.getPassHash().equals(
                ((Number)(headers.getFirst("password").hashCode())).longValue())){
            return (2);//неверный пароль
        }
        else return (200);
    }
}
