package damian.russok.web.modules.user;

import damian.russok.web.utils.responses.serviceResponses.ServiceResponse;
import damian.russok.web.utils.validators.UserCredentialsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static damian.russok.web.utils.responses.serviceResponses.ServiceResponse.ServiceResponseStatus.*;
import static damian.russok.web.utils.validators.UserCredentialsValidator.*;
import static damian.russok.web.utils.validators.UserCredentialsValidator.Field.LOGIN;
import static damian.russok.web.utils.validators.UserCredentialsValidator.Field.PASSWORD;

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
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setAdmin(false);
        newUser.setMobileAppAccess(false);
        try {
            userRepository.save(newUser);
        }catch (Exception e){
            return new ServiceResponse<>(UNKNOWN_ERROR, e.getLocalizedMessage(), null);
        }
        return new ServiceResponse<>(SUCCESS, "User registered successfully", newUser);
    }




}
