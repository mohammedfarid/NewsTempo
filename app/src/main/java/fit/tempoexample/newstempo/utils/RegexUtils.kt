package fit.tempoexample.newstempo.utils

import java.util.regex.Pattern

object RegexUtils {
    val REMOVE_PATTERN: Pattern = Pattern.compile(
        "\\[\\+([0-9\\sa-z])+\\]"
    )

}