package pl.drussok.notificationsystem.infrastructure.utils.classes

sealed class UiState<out T>{
    object NotInitialized: UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Failure(val errorMessage: String, var functionToInvoke: () -> Unit = {}) : UiState<Nothing>()
    data class Success<T>(val value: T) : UiState<T>()
}