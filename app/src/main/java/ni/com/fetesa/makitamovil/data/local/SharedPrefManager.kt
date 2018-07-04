package ni.com.fetesa.makitamovil.data.local

import android.content.SharedPreferences
import com.google.firebase.messaging.FirebaseMessaging
import ni.com.fetesa.makitamovil.model.MakitaUserSession

/**
 * Created by dusti on 09/05/2018.
 */
class SharedPrefManager(private val mSharedPref: SharedPreferences) {


    fun getSharedPrefString(key: PreferenceKeys): String {
        return mSharedPref.getString(key.toString(), "")
    }
    fun saveString(key: PreferenceKeys, value: String) {
        val editor: SharedPreferences.Editor = mSharedPref.edit()
        editor.putString(key.toString(), value)
        editor.commit()

    }

    fun getSharedPrefBoolean(key: PreferenceKeys): Boolean {
        return mSharedPref.getBoolean(key.toString(), false)
    }
    fun getSharedPrefFloat(key: PreferenceKeys): Float {
        return mSharedPref.getFloat(key.toString(), 0.toFloat())
    }


    enum class PreferenceFiles {
        UserSharedPref
    }
    enum class PreferenceKeys {
        AUTH_TOKEN,

        EMAIL
    }

    fun clearPreferences() {
        val users =  "${MakitaUserSession.instance.authToken ?: ""}"
        FirebaseMessaging.getInstance().unsubscribeFromTopic(users)
        mSharedPref.edit().clear().commit()
    }
}