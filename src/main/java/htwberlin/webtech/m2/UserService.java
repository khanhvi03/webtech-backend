package htwberlin.webtech.m2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll(){
        Iterable<User> iterator = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (User user : iterator) users.add(user);
        return users;
    }

    public User get(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException());
    }

    public boolean deleteUser(Long id){
        if(!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}
