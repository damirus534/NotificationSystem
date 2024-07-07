package pl.drussok.notificationsystem.infrastructure.utils.classes

import kotlinx.datetime.DateTimeUnit
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class TimeStampFormatter {
    companion object {
        fun formatTimestamp(timestamp: Timestamp): String{
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
            val date = Date(timestamp.time)
            return dateFormat.format(date)
        }
        fun formatToJavaTimestamp(timestamp: Timestamp) : Timestamp{
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
            val date = Date(timestamp.time)
            return Timestamp(java.util.Date(dateFormat.format(date)).time)
        }
    }
}