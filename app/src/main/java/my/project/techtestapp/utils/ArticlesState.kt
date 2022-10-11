package my.project.techtestapp.utils

sealed class ArticlesState {
    object Loading : ArticlesState()
    object Data : ArticlesState()
    data class Error(val message: String) : ArticlesState()
}