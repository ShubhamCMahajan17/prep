package backend.project.journal.service;

import backend.project.journal.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@SpringBootTest
public class UserServiceTests {


    @Autowired
    UserService userService;
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    @Disabled
    public void testNewUserName(User user) {
        assertTrue(userService.saveUser(user), "user: " + user.toString());

    }

}
