package pl.drussok.notificationsystem.infrastructure.remote.repositories

import pl.drussok.notificationsystem.infrastructure.remote.models.Notification
import pl.drussok.notificationsystem.infrastructure.remote.responses.ServiceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationRepository {

    @GET("notification/assigned/{login}")
    fun getAssignedNotificationsByUserLogin(@Path("login") login: String): Call<ServiceResponse<List<Notification>>>

    @POST("notification/edit")
    fun editNotification(@Body notification: Notification): Call<ServiceResponse<Notification>>
}