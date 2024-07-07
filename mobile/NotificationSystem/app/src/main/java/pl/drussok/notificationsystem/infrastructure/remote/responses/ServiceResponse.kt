package pl.drussok.notificationsystem.infrastructure.remote.responses

data class ServiceResponse<T>(
    val responseStatus: ServiceResponseStatus,
    val message: String,
    val value: T
) {
    fun isSuccess(): Boolean {
        return responseStatus == ServiceResponseStatus.SUCCESS ||
               responseStatus == ServiceResponseStatus.ACCEPTED ||
               responseStatus == ServiceResponseStatus.CREATED
    }
}

enum class ServiceResponseStatus {
    SUCCESS, ACCEPTED, CREATED, VALIDATION_ERROR, DUPLICATE, NO_PERMISSION, UNKNOWN_ERROR, NOT_FOUND, FORBIDDEN_ACTION
}