package damian.russok.web.modules.user;

import damian.russok.web.utils.responses.apiResponses.exceptions.ApiExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> insertUser(@RequestBody User user){
        return userService.registerUser(user.getLogin(), user.getPassword()).toApiReturn();
    }
}
