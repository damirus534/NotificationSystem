package pl.drussok.notificationsystem.ui.notifications.edit

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
import pl.drussok.notificationsystem.infrastructure.utils.classes.TimeStampFormatter
import pl.drussok.notificationsystem.infrastructure.utils.classes.UiState
import pl.drussok.notificationsystem.infrastructure.utils.extensions.getErrorMessage
import pl.drussok.notificationsystem.infrastructure.utils.extensions.getValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class NotificationEditViewModel(application: Application) : AndroidViewModel(application) {

    private var _notificationEditFramentUiState = MutableLiveData<UiState<List<User>>>(UiState.NotInitialized)
    val notificationEditFragmentUiState: LiveData<UiState<List<User>>>
        get() = _notificationEditFramentUiState

    lateinit var user: User


    fun getOtherUsersForSpinner() {
        val additionalResponseInfo = ". Component for changing assigned user is not available."
        viewModelScope.launch(Dispatchers.IO) {
            val call = ApiClient.userApiEndpoint.getUsers()
            call.enqueue(object : Callback<ServiceResponse<List<User>>> {
                override fun onResponse(
                    call: Call<ServiceResponse<List<User>>>,
                    response: Response<ServiceResponse<List<User>>>
                ) {
                    if (response.isSuccessful && response.getValue() != null){
                        _notificationEditFramentUiState.postValue(UiState.Success(response.getValue()!!))
                    } else if (response.code()==404){ //no more users
                        _notificationEditFramentUiState.postValue(UiState.Success(listOf()))
                    }else {
                        _notificationEditFramentUiState.postValue(UiState.Failure(response.getErrorMessage() + additionalResponseInfo))
                    }
                }
                override fun onFailure(call: Call<ServiceResponse<List<User>>>, t: Throwable) {
                    _notificationEditFramentUiState.postValue(UiState.Failure("${t.localizedMessage}" + additionalResponseInfo))
                }
            })
        }
    }

    fun editNotification(
        notification: Notification,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            ApiClient.notificationApiEndpoint
                .editNotification(notification.copy(date = null))
                .enqueue(object : Callback<ServiceResponse<Notification>>{
                    override fun onResponse(
                        call: Call<ServiceResponse<Notification>>,
                        response: Response<ServiceResponse<Notification>>
                    ) {
                        if (response.isSuccessful && response.getValue() != null){
                            onSuccess(response.message())
                        }else {
                            onError(response.getErrorMessage())
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse<Notification>>, t: Throwable) {
                        onError("${t.localizedMessage}")
                    }

                })
        }
    }
}