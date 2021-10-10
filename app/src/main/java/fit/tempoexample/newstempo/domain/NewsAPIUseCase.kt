package fit.tempoexample.newstempo.domain

import com.google.gson.JsonObject
import fit.tempoexample.newstempo.data.APIs
import javax.inject.Inject

/**
 * Created by Mohammed Farid on 10/9/2021
 * Contact me : m.farid.shawky@gmail.com
 */
class NewsAPIUseCase @Inject constructor(private val apIs: APIs) {
    suspend operator fun invoke(wordSearch: String,  page: Int? = 1): JsonObject {
        return apIs.getNews(wordSearch, page = page)
    }
}