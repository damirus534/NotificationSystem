package web.spring.api.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.spring.utils.responses.serviceResponses.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import static web.spring.utils.responses.serviceResponses.ServiceResponse.ServiceResponseStatus.*;
import static web.spring.utils.validators.UserCredentialsValidator.Field.LOGIN;
import static web.spring.utils.validators.UserCredentialsValidator.Field.PASSWORD;
import static web.spring.utils.validators.UserCredentialsValidator.validateInputWithPlainText;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ServiceResponse<User> registerUser(String login, String password) {
        Pair<Boolean, String> loginValidation = validateInputWithPlainText(login.trim(), LOGIN);
        if (!loginValidation.getFirst()) {
            return new ServiceResponse<>(VALIDATION_ERROR, loginValidation.getSecond(), null);
        }

        Pair<Boolean, String> passwordValidation = validateInputWithPlainText(password.trim(), PASSWORD);
        if (!passwordValidation.getFirst()) {
            return new ServiceResponse<>(VALIDATION_ERROR, passwordValidation.getSecond(), null);
        }

        if (userRepository.findByLogin(login) != null) {
            return new ServiceResponse<>(DUPLICATE, "User with this login already exists", null);
        }
        User userToInsert = new User(login, passwordEncoder.encode(password));
        try {
            userRepository.saveAndFlush(userToInsert);
        }catch (Exception e){
            return new ServiceResponse<>(UNKNOWN_ERROR, e.getLocalizedMessage(), null);
        }
        return new ServiceResponse<>(CREATED, "User registered successfully", userToInsert);
    }

    public ServiceResponse<User> checkCredentials(String login, String password) {
        Pair<Boolean, String> loginValidation = validateInputWithPlainText(login.trim(), LOGIN);
        if (!loginValidation.getFirst()) {
            return new ServiceResponse<>(VALIDATION_ERROR, loginValidation.getSecond(), null);
        }

        User loggedUser = userRepository.findByLogin(login);
        if(loggedUser == null){
            return new ServiceResponse<>(NOT_FOUND, "User with this username doesn't exist", null);
        }
        if (!passwordEncoder.matches(password, loggedUser.getPassword())){
            return new ServiceResponse<>(VALIDATION_ERROR, "Password is incorrect", null);
        }
        loggedUser.setPassword(null);
        return new ServiceResponse<>(ACCEPTED, "User credentials confirmed", loggedUser);
    }

    public ServiceResponse<List<User>> findAllUsers(String excludedUserLogin) {
        List<User> foundUsers = userRepository.findAllByLoginIsNot(excludedUserLogin);
        if(foundUsers == null || foundUsers.isEmpty()){
            return new ServiceResponse<>(NOT_FOUND, "There are no other users", null);
        }
        return new ServiceResponse<>(SUCCESS, "Found " + foundUsers.size() + " users", foundUsers);
    }


}