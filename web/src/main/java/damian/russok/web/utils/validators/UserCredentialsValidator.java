package damian.russok.web.utils.validators;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserCredentialsValidator {

    public enum Field {
        LOGIN, PASSWORD
    }

    /**
     * Checks if
     * @param password
     * contains at least 8 characters and at most 24 characters,
     * contains at least one digit,
     * contains at least one upper case alphabet,
     * contains at least one lower case alphabet,
     * contains at least one special character which includes !@#$%&*^
     * doesn’t contain any white space
     * @return Pair<true, emptyArrayList> if all above conditions are complied,
     * otherwise Pair<false, List<error as String>
     */

    public static Pair<Boolean, ArrayList<String>> validatePassword(String password) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (password == null || password.isEmpty()) {
            return Pair.of(false, new ArrayList<>(List.of("Password must not be empty")));
        }
        if (password.length() < 8 || password.length() > 24) {
            errorMessages.add("Password must be between 8 and 24 characters long.");
        }
        if (!Pattern.compile(".*\\d.*").matcher(password).matches()) {
            errorMessages.add("Password must contain at least one digit.");
        }
        if (!Pattern.compile(".*[a-z].*").matcher(password).matches()) {
            errorMessages.add("Password must contain at least one lower case letter.");
        }
        if (!Pattern.compile(".*[A-Z].*").matcher(password).matches()) {
            errorMessages.add("Password must contain at least one upper case letter.");
        }
        if (!Pattern.compile(".*[@#$%^&!].*").matcher(password).matches()) {
            errorMessages.add("Password must contain at least one special character (!@#$%^&).");
        }
        if (Pattern.compile("\\s").matcher(password).find()) {
            errorMessages.add("Password must not contain any white spaces.");
        }
        return Pair.of(errorMessages.isEmpty(), errorMessages);
    }

    /**
     * Checks if
     * @param login
     * contains at least 4 characters and at most 24 characters,
     * contains only letters and digits,
     * doesn’t contain any white space
     * @return Pair<true, emptyArrayList> if all above conditions are complied,
     * otherwise Pair<false, List<error as String>
     */
    public static Pair<Boolean, ArrayList<String>> validateLogin(String login) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (login == null || login.isEmpty()) {
            return Pair.of(false, new ArrayList<>(List.of("Login must not be empty")));
        }
        if (login.length() < 4 || login.length() > 24) {
            errorMessages.add("Login must be between 8 and 24 characters long.");
        }
        if (!Pattern.compile("[a-zA-Z0-9]+").matcher(login).matches()) {
            errorMessages.add("Login must contain only letters and digits.");
        }
        if (Pattern.compile("\\s").matcher(login).find()) {
            errorMessages.add("Password must not contain any white spaces.");
        }
        return Pair.of(errorMessages.isEmpty(), errorMessages);
    }

    public static Pair<Boolean, String> validateInputWithPlainText(String inputValue, Field field) {
        Pair<Boolean, ArrayList<String>> validationResult = switch (field){
            case LOGIN -> validateLogin(inputValue);
            case PASSWORD -> validatePassword(inputValue);
        };
        if (validationResult.getFirst()) {
            return Pair.of(true, "");
        } else {
            String errors = String.join("\n", validationResult.getSecond());
            return Pair.of(false, errors);
        }
    }




}
