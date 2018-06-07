package ni.com.fetesa.makitamovil.services

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging


open class FirebaseInstanceService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        Log.e("tokenRefresh","onTokenRefresh")

        // Fetch updated Instance ID token and notify our app's server of any changes.
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }

}