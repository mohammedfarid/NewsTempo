package fit.tempoexample.newstempo.utils


import android.content.Context
import android.content.SharedPreferences


class PrefsManager(mContext: Context?) {

    private val FILE_NAME = "news_tempo_preferences"

    private var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences = mContext?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    //Keys
    private val KEY_LAST_WORD_SEARCH = "last_word_search_key"


    private var DEFAULT_LAST_WORD_SEARCH = null

    fun setLastWordSearchKey(lastWordSearch: String) {
        mSharedPreferences?.edit()?.putString(KEY_LAST_WORD_SEARCH, lastWordSearch)?.apply()
    }

    fun getLastWordSearchKey(): String? {
        return mSharedPreferences?.getString(
            KEY_LAST_WORD_SEARCH,
            DEFAULT_LAST_WORD_SEARCH
        )
    }
}