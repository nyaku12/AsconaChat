package github.nyaku12.ASCONAChat;

import com.google.gson.Gson;
import github.nyaku12.ASCONAChat.Chat.Chat;
import github.nyaku12.ASCONAChat.Chat.ChatService;
import github.nyaku12.ASCONAChat.Message.Message;
import github.nyaku12.ASCONAChat.Message.MessageService;
import github.nyaku12.ASCONAChat.User.User;
import github.nyaku12.ASCONAChat.User.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.aspectj.util.LangUtil.split;

@Component
public class GeneralController {
    Gson gson = new Gson();
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
    public void joinChat(Map<String, Object> jmap){
        userService.joinChat(
                gson.fromJson(jmap.get("users").toString(), Integer[].class),
                ((Number)jmap.get("chat")).intValue());
    }
    public Message createMessage(Map<String, Object> jmap, Boolean online){
        Message message = (messageService.createMessage(
                jmap.get("contain").toString(),
                ((Number) jmap.get("chat")).longValue(),
                ((Number) jmap.get("sender")).longValue(),
                (Timestamp.valueOf((String) jmap.get("time"))),
                ((Number) jmap.get("chat")).longValue()
        ));
        if(online) sendMessage(message);
        return message;
    }
    public void sendMessage(Message message){

    }
    public List<Message> getMessages(int receiver_id){
        return messageService.getMessages(receiver_id);
    }
    @PostConstruct
    public void resetOnline(){
        userService.resetOnline();
    }
    public void readMessage(Map<String, Object> jmap){
        messageService.readMessages((List<Double>)jmap.get("id"));
    }
    public int checkUser(HttpHeaders headers){
        User user = userService.getByLogin(headers.getFirst("login"));
        if(user == null) return (-1);//неверный логин
        if(!user.getPassHash().equals(
                ((Number)(headers.getFirst("password").hashCode())).longValue())){
            return (-2);//неверный пароль
        }
        if(user.getStatus()){
            return (-3);//уже зашли в систему
        }
        else return (user.getId());
    }
    public void updateOnlineById(int id, Boolean online){
        userService.updateOnline(id, online);
    }
}
