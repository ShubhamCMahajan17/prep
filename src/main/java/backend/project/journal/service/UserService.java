package backend.project.journal.service;

import backend.project.journal.entity.User;
import backend.project.journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class UserService {


    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveUpdatedUser(User userInDB, User updatedUser) {
        try {
            userInDB.setUserName(updatedUser.getUserName());
            userInDB.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            userInDB.setRoles(Arrays.asList("USER"));

            userRepository.save(userInDB);

            return true;
        } catch (Exception exception) {

            return false;
        }

    }

    public boolean saveUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%---{}", exception);
            log.error("---");
            log.debug("---");
            log.trace("---");
            log.warn("---");
            return false;
        }

    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


}
