package pl.drussok.notificationsystem.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.drussok.notificationsystem.infrastructure.remote.ApiClient
import pl.drussok.notificationsystem.infrastructure.remote.models.User
import pl.drussok.notificationsystem.infrastructure.remote.responses.ServiceResponse
import pl.drussok.notificationsystem.infrastructure.utils.extensions.getErrorMessage
import pl.drussok.notificationsystem.infrastructure.utils.extensions.getValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application): AndroidViewModel(application) {

    fun checkUserCredentials(
        login: String,
        password: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val call = ApiClient.userApiEndpoint.checkCredentials(User(login = login, password = password))
            call.enqueue(object : Callback<ServiceResponse<User>> {
                override fun onResponse(
                    call: Call<ServiceResponse<User>>,
                    response: Response<ServiceResponse<User>>
                ) {
                    if (response.isSuccessful && response.getValue() != null){
                        onSuccess(response.getValue()!!)
                    }else{
                        onFailure(response.getErrorMessage())
                    }
                }

                override fun onFailure(call: Call<ServiceResponse<User>>, t: Throwable) {
                    onFailure("${t.localizedMessage}")
                }

            })
        }
    }
}