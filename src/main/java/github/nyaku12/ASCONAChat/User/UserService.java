package github.nyaku12.ASCONAChat.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User createUser(String login, Long passHash, String name, String enscryptionKey){
        User user = new User(login, passHash, name, enscryptionKey);
        userRepository.save(user);
        return (user);
    }
}
