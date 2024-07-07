package pl.drussok.notificationsystem.infrastructure.remote.repositories

import pl.drussok.notificationsystem.infrastructure.remote.models.User
import pl.drussok.notificationsystem.infrastructure.remote.responses.ServiceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRepository {

    @POST("/user/login")
    fun checkCredentials(@Body user: User): Call<ServiceResponse<User>>

    @GET("/user/all")
    fun getUsers(): Call<ServiceResponse<List<User>>>


}