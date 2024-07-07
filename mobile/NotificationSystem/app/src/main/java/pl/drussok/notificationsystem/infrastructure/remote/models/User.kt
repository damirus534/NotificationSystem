package pl.drussok.notificationsystem.infrastructure.remote.models

import java.io.Serializable

data class User(
    val userId: Long = 0L,
    val login: String,
    val password: String = "",
    val isAdmin: Boolean = false,
    val mobileAppAccess: Boolean = false,
    val modifyNtfAddr: Boolean = false,
    val modifyNtfContent: Boolean = false,
    val modifyNtfAssiuser: Boolean = false
) : Serializable
