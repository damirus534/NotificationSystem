package pl.drussok.notificationsystem.ui.notifications.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pl.drussok.notificationsystem.databinding.FragmentNotificationListBinding
import pl.drussok.notificationsystem.infrastructure.remote.models.Notification
import pl.drussok.notificationsystem.infrastructure.utils.classes.UiState
import pl.drussok.notificationsystem.ui.notifications.NotificationsActivity

class NotificationListFragment : Fragment() {

    private lateinit var viewModel: NotificationListViewModel
    private lateinit var binding: FragmentNotificationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NotificationListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notificationListFragmentUiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState){
                is UiState.Success -> {
                    binding.errorMessage.isVisible = false
                    binding.progressIndicator.isVisible = false
                    binding.retryButton.isVisible = false
                    setupRecyclerView(uiState.value)
                    binding.rv.isVisible = true
                }
                is UiState.Failure -> {
                    binding.rv.isVisible = false
                    binding.progressIndicator.isVisible = false
                    binding.errorMessage.text = uiState.errorMessage
                    binding.retryButton.isVisible = true
                    binding.errorMessage.isVisible = true
                }
                else -> {
                    binding.rv.isVisible = false
                    binding.errorMessage.isVisible = false
                    binding.progressIndicator.isVisible = true
                    binding.retryButton.isVisible = false
                }
            }
        }
        if (viewModel.notificationListFragmentUiState.value is UiState.NotInitialized){
            getData()
        }

        binding.retryButton.setOnClickListener {
            getData()
        }
    }

    private fun getData(){
        viewModel.getNotificationsForUser((requireActivity() as NotificationsActivity).getUser().login)
    }

    private fun setupRecyclerView(data: List<Notification>){
        val adapter = NotificationListAdapter(data, onEditClick = ::onEditClick, onRunRouteClick = ::onRunRouteClick)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
    }

    private fun onEditClick(notification: Notification) {
        findNavController().navigate(NotificationListFragmentDirections.actionNotificationListFragmentToNotificationEditFragment(notification))
    }

    private fun onRunRouteClick(notification: Notification) {
        val gmmIntentUri: Uri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+notification.address)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        requireContext().startActivity(mapIntent)
    }


}