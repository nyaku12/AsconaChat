package github.nyaku12.ASCONAChat;

import github.nyaku12.ASCONAChat.User.User;
import github.nyaku12.ASCONAChat.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GeneralController {
    @Autowired
    UserService userService;
    public User createUser(String login, Long passHash, String name, String enscryptionKey){
        return (userService.createUser(login, passHash, name, enscryptionKey));
    }
    public User createUser(Map<String, Object> jmap){
        return (userService.createUser(
                jmap.get("login").toString(),
                ((Number) jmap.get("password")).longValue(),
                jmap.get("name").toString(),
                jmap.get("enscryptionKey").toString()));
    }
}
