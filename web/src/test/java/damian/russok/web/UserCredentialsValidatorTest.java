package damian.russok.web;

import damian.russok.web.utils.validators.UserCredentialsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.util.Pair;

import static damian.russok.web.utils.validators.UserCredentialsValidator.Field.PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

public class UserCredentialsValidatorTest {
    @ParameterizedTest(name = "Valid password: {0}")
    @ValueSource(strings = {
            "d1absdwo!rD",
            "Password1!",
            "SecurePassword12@"
    })
    public void testValidPasswords(String password) {
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertTrue(validationResult.getFirst(), "Expected valid password to be true: " + password);
        assertEquals("", validationResult.getSecond(), "Expected no errors for valid password: " + password);
    }

    @Test
    public void testEmptyPassword() {
        String password = "";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must not be empty"), "Expected 'Password must not be empty' error message");
    }

    @Test
    public void testShortPassword() {
        String password = "Short1!";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must be between 8 and 24 characters long."), "Expected 'Password must be between 8 and 24 characters long.' error message");
    }

    @Test
    public void testLongPassword() {
        String password = "ThisIsAVeryLongPassword123!";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must be between 8 and 24 characters long."), "Expected 'Password must be between 8 and 24 characters long.' error message");
    }

    @Test
    public void testNoDigitPassword() {
        String password = "NoDigitPassword!";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must contain at least one digit."), "Expected 'Password must contain at least one digit.' error message");
    }

    @Test
    public void testNoLowerCasePassword() {
        String password = "UPPERCASE123!";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must contain at least one lower case letter."), "Expected 'Password must contain at least one lower case letter.' error message");
    }

    @Test
    public void testNoUpperCasePassword() {
        String password = "lowercase123!";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must contain at least one upper case letter."), "Expected 'Password must contain at least one upper case letter.' error message");
    }

    @Test
    public void testNoSpecialCharPassword() {
        String password = "NoSpecial123";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must contain at least one special character (!@#$%^&)."), "Expected 'Password must contain at least one special character (!@#$%^&).' error message");
    }

    @Test
    public void testPasswordWithWhiteSpace() {
        String password = "Password With Space!";
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(password, PASSWORD);
        assertFalse(validationResult.getFirst(), "Expected password to be invalid");
        assertTrue(validationResult.getSecond().contains("Password must not contain any white spaces."), "Expected 'Password must not contain any white spaces.' error message");
    }

    @ParameterizedTest(name = "Valid login: {0}")
    @ValueSource(strings = {
            "user123",
            "User1234",
            "login2023"
    })
    void testValidLogins(String login) {
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(login, UserCredentialsValidator.Field.LOGIN);
        assertTrue(validationResult.getFirst(), "Expected valid login to be true: " + login);
        assertEquals("", validationResult.getSecond(), "Expected no errors for valid login: " + login);
    }

    @ParameterizedTest(name = "Invalid login: {0}")
    @ValueSource(strings = {
            "user 123", // Contains space
            "Login#",   // Contains special character
            "",         // Empty login
            "srt"     // Less than 4 characters
    })
    void testInvalidLogins(String login) {
        Pair<Boolean, String> validationResult = UserCredentialsValidator.validateInputWithPlainText(login, UserCredentialsValidator.Field.LOGIN);
        assertTrue(!validationResult.getFirst(), "Expected invalid login to be false: " + login);
        assertTrue(validationResult.getSecond().contains("Login"), "Expected error message for invalid login: " + login);
    }
}
