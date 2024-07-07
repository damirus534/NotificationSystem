package pl.drussok.notificationsystem.infrastructure.remote.models

import java.io.Serializable
import java.sql.Timestamp

data class Notification(
    val notificationId: Long,
    var date: Timestamp?,
    val notifiedLogin: User,
    var assignedLogin: User,
    var content: String,
    var address: String
) : Serializable
