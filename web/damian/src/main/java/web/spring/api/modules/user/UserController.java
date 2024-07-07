package web.spring.api.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.spring.utils.responses.apiResponses.exceptions.ApiFailureResponseHandler;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ApiFailureResponseHandler apiExceptionHandler = new ApiFailureResponseHandler();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> insertUser(@RequestBody User user){
        return userService.registerUser(user.getLogin(), user.getPassword()).toApiReturn();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> checkCredentials(@RequestBody User user) {
        return userService.checkCredentials(user.getLogin(), user.getPassword(), true).toApiReturn();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        return userService.findAllUsers("").toApiReturn();
    }

}