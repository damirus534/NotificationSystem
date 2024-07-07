package pl.drussok.notificationsystem.infrastructure.utils.validators

import com.google.android.material.textfield.TextInputEditText

data class EditNotificationValidationResult(val isValid: Boolean, val addressError: String?, val contentError: String?)

class EditNotificationInputValidator {

    companion object {
        fun validate(addressInput: TextInputEditText, contentInput: TextInputEditText) : EditNotificationValidationResult{
            return validate(addressInput.text?.toString(), contentInput.text?.toString())
        }

        fun validate(address: String?, content: String?): EditNotificationValidationResult {
            var isValid = true
            var usernameError: String? = null
            var passwordError: String? = null
            if (address.isNullOrEmpty()) {
                usernameError = "Address cannot be empty"
                isValid = false
            }
            if (content.isNullOrEmpty()) {
                passwordError = "content cannot be empty"
                isValid = false
            }
            return EditNotificationValidationResult(isValid, usernameError, passwordError)
        }
    }
}