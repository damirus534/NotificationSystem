package pl.drussok.notificationsystem.infrastructure.utils.validators

import com.google.android.material.textfield.TextInputEditText

data class LoginValidationResult(val isValid: Boolean, val usernameError: String?, val passwordError: String?)

class LoginInputValidator {

    companion object {
        fun validate(loginInput: TextInputEditText, passwordInput: TextInputEditText) : LoginValidationResult{
            return validate(loginInput.text?.toString(), passwordInput.text?.toString())
        }

        fun validate(login: String?, password: String?): LoginValidationResult {
            var isValid = true
            var usernameError: String? = null
            var passwordError: String? = null
            if (login.isNullOrEmpty()) {
                usernameError = "Login cannot be empty"
                isValid = false
            }
            if (password.isNullOrEmpty()) {
                passwordError = "Password cannot be empty"
                isValid = false
            }
            return LoginValidationResult(isValid, usernameError, passwordError)
        }
    }
}