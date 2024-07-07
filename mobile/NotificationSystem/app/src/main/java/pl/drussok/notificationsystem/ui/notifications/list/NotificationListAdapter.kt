package pl.drussok.notificationsystem.ui.notifications.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pl.drussok.notificationsystem.R
import pl.drussok.notificationsystem.databinding.ElementNotificationListBinding
import pl.drussok.notificationsystem.infrastructure.remote.models.Notification
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class NotificationListAdapter (
    private val dataset :List<Notification>,
    private val onEditClick: (Notification) -> Unit,
    private val onRunRouteClick: (Notification) -> Unit
    ): RecyclerView.Adapter<NotificationListAdapterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        NotificationListAdapterViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.element_notification_list, parent, false))

    override fun onBindViewHolder(holder: NotificationListAdapterViewHolder, position: Int) {
        holder.bind(dataset[position], onEditClick, onRunRouteClick)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

class NotificationListAdapterViewHolder(private val binding: ElementNotificationListBinding)  : RecyclerView.ViewHolder(binding.root){

    fun bind(item: Notification, onEditClick: (Notification) -> Unit, onRunRouteClick: (Notification) -> Unit) {
        binding.addressTextView.text = item.address
        binding.content.text = item.content
        binding.createdBy.text = item.notifiedLogin.login
        binding.date.text = formatTimestamp(item.date!!)
        binding.editBtn.setOnClickListener {
            onEditClick(item)
        }
        binding.startRoute.setOnClickListener {
            onRunRouteClick(item)
        }
    }

    private fun formatTimestamp(timestamp: Timestamp): String{
        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        val date = Date(timestamp.time)
        return dateFormat.format(date)
    }

}