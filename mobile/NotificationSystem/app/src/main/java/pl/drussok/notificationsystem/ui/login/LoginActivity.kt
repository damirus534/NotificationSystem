package pl.drussok.notificationsystem.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import pl.drussok.notificationsystem.databinding.ActivityLoginBinding
import pl.drussok.notificationsystem.infrastructure.remote.ApiClient
import pl.drussok.notificationsystem.infrastructure.remote.models.User
import pl.drussok.notificationsystem.infrastructure.utils.validators.LoginInputValidator
import pl.drussok.notificationsystem.ui.notifications.NotificationsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        binding.error.isVisible = false
        val validationResult = LoginInputValidator.validate(binding.editTextLogin, binding.editTextPassword)
        //if for ex. validationResult.usernameError = null eror is not displayed
        binding.editTextLayoutLogin.error = validationResult.usernameError
        binding.editTextLayoutPassword.error = validationResult.passwordError
        if (validationResult.isValid){
            binding.progressIndicator.isVisible = true
            binding.formContainer.isEnabled = false
            viewModel.checkUserCredentials(
                login = "${binding.editTextLogin.text}",
                password = "${binding.editTextPassword.text}",
                onSuccess = { user ->
                    binding.formContainer.isEnabled = true
                    binding.progressIndicator.isVisible = false
                    startNotificationsPanelActivity(user)
                },
                onFailure = { errorMessage ->
                    binding.formContainer.isEnabled = true
                    binding.progressIndicator.isVisible = false
                    binding.error.text = errorMessage
                    binding.error.isVisible = true
                }
            )
        }
    }

    private fun startNotificationsPanelActivity(user: User){
        this.startActivity(Intent(this, NotificationsActivity::class.java)
                .putExtra("user", user)
        )
    }

}