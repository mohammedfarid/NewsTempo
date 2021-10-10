package fit.tempoexample.newstempo.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import fit.tempoexample.newstempo.utils.PrefsManager

/**
 * Created by Mohammed Farid on 10/8/2021
 * Contact me : m.farid.shawky@gmail.com
 */
@HiltAndroidApp
class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mPrefsManger = PrefsManager(applicationContext)
    }

    companion object {
        val TAG = NewsApp::class.java.simpleName
        var mPrefsManger: PrefsManager? = null
    }
}