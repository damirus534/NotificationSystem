package pl.drussok.notificationsystem.infrastructure.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pl.drussok.notificationsystem.infrastructure.remote.repositories.NotificationRepository
import pl.drussok.notificationsystem.infrastructure.remote.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit: Retrofit by lazy {

        val client = OkHttpClient.Builder()
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val userApiEndpoint: UserRepository by lazy {
        retrofit.create(UserRepository::class.java)
    }

    val notificationApiEndpoint: NotificationRepository by lazy {
        retrofit.create(NotificationRepository::class.java)
    }
}