package fit.tempoexample.newstempo.utils

/**
 * Created by Mohammed Farid on 8/4/2021
 * Contact me : m.farid.shawky@gmail.com
 */
sealed class State<out T> {
    object LoadingState : State<Nothing>()
    data class ErrorState(var exception: Throwable) : State<Nothing>()
    data class DataState<T>(var data: T) : State<T>()
}