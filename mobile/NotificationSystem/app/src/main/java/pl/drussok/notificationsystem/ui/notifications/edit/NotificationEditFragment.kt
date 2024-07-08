package pl.drussok.notificationsystem.ui.notifications.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import pl.drussok.notificationsystem.databinding.FragmentNotificationEditBinding
import pl.drussok.notificationsystem.infrastructure.remote.models.User
import pl.drussok.notificationsystem.infrastructure.utils.classes.UiState
import pl.drussok.notificationsystem.infrastructure.utils.validators.EditNotificationInputValidator
import pl.drussok.notificationsystem.infrastructure.utils.validators.EditNotificationValidationResult
import pl.drussok.notificationsystem.ui.notifications.NotificationsActivity

class NotificationEditFragment : Fragment() {

    private lateinit var binding: FragmentNotificationEditBinding
    private lateinit var viewModel: NotificationEditViewModel
    private val args: NotificationEditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NotificationEditViewModel::class.java]
        viewModel.user = (requireActivity() as NotificationsActivity).getUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notificationEditFragmentUiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState){
                is UiState.Success -> {
                    setUiLoading(false)
                    setupSpinner(uiState.value.map { it.login })
                }
                is UiState.Failure -> {
                    binding.error.isVisible = true
                    binding.error.text = uiState.errorMessage
                    binding.progressIndicator.isVisible = false
                    setUiLoading(false)
                }
                else -> {
                    setUiLoading(true)
                }
            }
        }
        viewModel.getOtherUsersForSpinner()

        binding.backButton.setOnClickListener { findNavController().navigateUp() }

        binding.userSpinner.isEnabled = viewModel.user.modifyNtfAssiuser
        binding.editTextAddress.isEnabled = viewModel.user.modifyNtfAddr
        binding.editTextContent.isEnabled = viewModel.user.modifyNtfContent

        binding.editTextContent.setText(args.notification.content)
        binding.editTextAddress.setText(args.notification.address)

        binding.btnSave.setOnClickListener {
            val validator = EditNotificationInputValidator.validate(binding.editTextAddress, binding.editTextContent)
            binding.editTextLayoutAddress.error = validator.addressError
            binding.editTextContent.error = validator.contentError
            if(validator.isValid){
                callEditNotification()
            }
        }
    }

    private fun callEditNotification() {
        setUiLoading(true)
        viewModel.editNotification(
            notification = args.notification.apply {
                if (binding.userSpinner.selectedItem != null){ //bind assign user
                    (viewModel.notificationEditFragmentUiState.value as? UiState.Success)?.value?.find { it.login == "${binding.userSpinner.selectedItem}" }?.login?.let {
                        this.assignedLogin = User(login = it)
                    }
                }
                this.content = binding.editTextContent.text.toString()
                this.address = binding.editTextAddress.text.toString()
            },
            onSuccess = { successMessage ->
                setUiLoading(false)
                Toast.makeText(requireContext(), successMessage, Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            },
            onError = { errorMessage ->
                setUiLoading(false)
                binding.error.isVisible = true
                binding.error.text = errorMessage
            }
        )
    }

    private fun setUiLoading(isLoading: Boolean){
        binding.container.isEnabled = !isLoading
        binding.container.alpha = if(isLoading) 0.5f else 1f
        binding.progressIndicator.isVisible = isLoading
    }

    private fun setupSpinner(loginList: List<String>){
        binding.userSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, loginList)
        binding.userSpinner.setSelection(loginList.indexOf(args.notification.assignedLogin.login))
    }

}