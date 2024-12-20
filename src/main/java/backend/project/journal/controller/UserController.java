package backend.project.journal.controller;

import backend.project.journal.entity.User;
import backend.project.journal.response.WeatherResponse;
import backend.project.journal.service.UserService;
import backend.project.journal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInDb = userService.findByUserName(authentication.getName());

        userService.saveUpdatedUser(userInDb, updatedUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeatherDetails();
        return new ResponseEntity<>("Hi " + authentication.getName() + "weather details: " +weatherResponse.getCurrent().getTemperature(), HttpStatus.OK);

    }
}
