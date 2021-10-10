package fit.tempoexample.newstempo.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ConvertDateTimeUtils {
    var SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    var SHOW_FORMAT = "EEE, dd MMM yyyy"

    fun getTimeAsDateEnglish(time: String?, oldFormat: String?, newFormat: String?): String? {
        var time = time
        if (time == null || time.isEmpty()) return null
        val date: Date
        try {
            val oldDateFormat = SimpleDateFormat(oldFormat, Locale.ENGLISH)
            val newDateFormat = SimpleDateFormat(newFormat, Locale.ENGLISH)
            date = oldDateFormat.parse(time)
            time = newDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }
}