package pl.drussok.notificationsystem.infrastructure.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.drussok.notificationsystem.infrastructure.remote.responses.ServiceResponse
import retrofit2.Response

fun <T> Response<ServiceResponse<T>>.getValue(): T? {
    return this.body()?.value
}

fun <T> Response<ServiceResponse<T>>.getRetMessage(): String {
    return this.body()?.message ?: this.message()
}

fun <T> Response<T>.getErrorServiceResponse(): ServiceResponse<T>? {
    return try {
        val errorBody = this.errorBody()?.string()
        val gson = Gson()
        val type = object : TypeToken<ServiceResponse<T>>() {}.type
        gson.fromJson(errorBody, type)
    } catch (e: Exception) {
        null
    }
}

fun <T> Response<ServiceResponse<T>>.getErrorMessage(): String {
    return try {
        val errorBody = this.errorBody()?.string()
        val gson = Gson()
        val type = object : TypeToken<ServiceResponse<T>>() {}.type
        val errorResponse: ServiceResponse<T>? = gson.fromJson(errorBody, type)
        errorResponse?.message ?: "Unknown error"
    } catch (e: Exception) {
        "Unknown error"
    }
}
