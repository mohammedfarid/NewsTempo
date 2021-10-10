package fit.tempoexample.newstempo.utils

import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Mohammed Farid on 8/1/2021
 * Contact me : m.farid.shawky@gmail.com
 */
class Utils {
    companion object {
        fun resolveError(e: Exception): State.ErrorState {
            var error = e
            when (e) {
                is SocketTimeoutException -> {
                    error = NetworkErrorException(errorMessage = "connection error!")
                }
                is ConnectException -> {
                    error = NetworkErrorException(errorMessage = "no internet access!")
                }
                is UnknownHostException -> {
                    error = NetworkErrorException(errorMessage = "no internet access!")
                }
            }

            if (e is HttpException) {
                val message =
                    JSONObject(e.response()?.errorBody()?.string() ?: "").getString("message")
                println(message)
                when (e.code()) {
                    502 -> {
                        error = NetworkErrorException(e.code(), "internal error!")
                    }
                    401 -> {
                        error = NetworkErrorException(e.code(), message)
                    }
                    400 -> {
                        error = NetworkErrorException.parseException(e)
                    }
                    426 -> {
                        error = NetworkErrorException(e.code(), message)
                    }
                    429 -> {
                        error = NetworkErrorException(e.code(), message)
                    }
                }
            }

            return State.ErrorState(error)
        }
    }
}