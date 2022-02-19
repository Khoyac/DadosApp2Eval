package com.khoyac.dadosapp2eval

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Build

import android.telephony.TelephonyManager

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var db = FirebaseFirestore.getInstance()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification!!.body?.let { Log.e("FIREBASE", it) }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(Constants.MessageNotificationKeys.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = R.string.msg_token_fmt
            Log.d(Constants.MessageNotificationKeys.TAG, msg.toString())
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(Constants.MessageNotificationKeys.TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        db.collection("tokens").add(
            hashMapOf(
                "token" to token
            )
        )
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

}