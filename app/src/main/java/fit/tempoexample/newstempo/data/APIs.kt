package fit.tempoexample.newstempo.data

import com.google.gson.JsonObject
import fit.tempoexample.newstempo.utils.Constants.API_KEY
import fit.tempoexample.newstempo.utils.Constants.SORT_BY_KEY
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohammed Farid on 7/18/2021
 * Contact me : m.farid.shawky@gmail.com
 */
interface APIs {
    @GET("/v2/everything")
    suspend fun getNews(
        @Query("q") wordSearch: String,
        @Query("sortBy") sortBy: String? = SORT_BY_KEY,
        @Query("apiKey") apiKey: String? = API_KEY,
        @Query("pageSize") pageSize:String ="20",
        @Query("page") page: Int? = 1
    ): JsonObject
}