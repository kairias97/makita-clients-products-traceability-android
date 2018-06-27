package ni.com.fetesa.makitamovil.services

import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ni.com.fetesa.makitamovil.R
import io.fabric.sdk.android.services.settings.IconRequest.build
import android.support.v4.app.NotificationManagerCompat




open class FCMListenerService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("From", "From: " + remoteMessage.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d("Payload", "Message data payload: " + remoteMessage.data)



        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d("Body", "Message Notification Body: " + remoteMessage.notification!!.body!!)
            val mBuilder = NotificationCompat.Builder(this, "makita")
                    .setSmallIcon(R.drawable.fetesa_logo)
                    .setContentTitle(remoteMessage.notification?.title ?: "Notificación Makita")
                    .setContentText(remoteMessage.notification?.body ?: "")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(remoteMessage.notification?.body ?: ""))

            val notificationManager = NotificationManagerCompat.from(this)

// notificationId is a unique int for each notification that you must define
            notificationManager.notify( 0, mBuilder.build())


        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
