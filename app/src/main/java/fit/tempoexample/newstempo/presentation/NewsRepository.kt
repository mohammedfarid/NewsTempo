package fit.tempoexample.newstempo.presentation

import com.google.gson.JsonObject
import fit.tempoexample.newstempo.domain.NewsAPIUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Farid on 10/9/2021
 * Contact me : m.farid.shawky@gmail.com
 */
class NewsRepository @Inject constructor(private val newsAPIUseCase: NewsAPIUseCase) {
    suspend fun getNews(wordSearch: String, page: Int? = 1): JsonObject {
        return newsAPIUseCase.invoke(wordSearch, page = page)
    }
}