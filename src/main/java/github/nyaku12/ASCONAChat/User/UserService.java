package github.nyaku12.ASCONAChat.User;

import jakarta.annotation.Nullable;
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
    @Nullable
    public User getByLogin(String login){
        return userRepository.getByLogin(login);
    }
    public void updateOnline(int id, Boolean online){
        userRepository.updateStatusById(id, online);
    }
    public Boolean findStatusById(int id){
        return (userRepository.findStatusById(id));
    }
    public void resetOnline(){
        userRepository.resetOnline();
    }
}
