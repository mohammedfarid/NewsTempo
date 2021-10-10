package fit.tempoexample.newstempo.presentation.news

import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.tempoexample.newstempo.data.entities.response.NewsResponse
import fit.tempoexample.newstempo.presentation.NewsRepository
import fit.tempoexample.newstempo.presentation.bases.BaseViewModel
import fit.tempoexample.newstempo.utils.State
import fit.tempoexample.newstempo.utils.Utils
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {
    fun getNewsResponse(wordSearch: String, page: Int? = 1) =
        liveData(Dispatchers.IO) {
            emit(State.LoadingState)
            try {
                emit(State.DataState(newsRepository.getNews(wordSearch, page = page)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Utils.resolveError(e))
            }
        }
}