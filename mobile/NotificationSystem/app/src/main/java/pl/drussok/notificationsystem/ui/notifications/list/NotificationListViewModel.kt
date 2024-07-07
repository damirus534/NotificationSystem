package pl.drussok.notificationsystem.ui.notifications.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.drussok.notificationsystem.infrastructure.remote.ApiClient
import pl.drussok.notificationsystem.infrastructure.remote.models.Notification
import pl.drussok.notificationsystem.infrastructure.remote.models.User
import pl.drussok.notificationsystem.infrastructure.remote.responses.ServiceResponse
import pl.drussok.notificationsystem.infrastructure.utils.classes.UiState
import pl.drussok.notificationsystem.infrastructure.utils.extensions.getErrorMessage
import pl.drussok.notificationsystem.infrastructure.utils.extensions.getValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationListViewModel(application: Application) : AndroidViewModel(application) {

    private var _notificationListFramentUiState = MutableLiveData<UiState<List<Notification>>>(UiState.NotInitialized)
    val notificationListFragmentUiState: LiveData<UiState<List<Notification>>>
        get() = _notificationListFramentUiState


    fun getNotificationsForUser(userLogin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = ApiClient.notificationApiEndpoint.getAssignedNotificationsByUserLogin(userLogin)
            call.enqueue(object : Callback<ServiceResponse<List<Notification>>> {
                override fun onResponse(
                    call: Call<ServiceResponse<List<Notification>>>,
                    response: Response<ServiceResponse<List<Notification>>>
                ) {
                    if (response.isSuccessful && response.getValue() != null){
                        _notificationListFramentUiState.postValue(UiState.Success(response.getValue()!!))
                    }else{
                        _notificationListFramentUiState.postValue(UiState.Failure(response.getErrorMessage()))
                    }
                }
                override fun onFailure(call: Call<ServiceResponse<List<Notification>>>, t: Throwable) {
                    _notificationListFramentUiState.postValue(UiState.Failure("${t.localizedMessage}"))
                }
            })
        }
    }
}