package web.spring.utils.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidator {
    public static boolean isPolishAddressValid(String address) {
        String regex = ".*\\d{2}-\\d{3}.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }
}
