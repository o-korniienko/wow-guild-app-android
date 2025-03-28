package com.wowguild.util

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.wowguild.tool.SaveDataProvider
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

internal object WowGuildLogger {

    enum class LogLevels {
        PUSHSDK_LOG_LEVEL_ERROR,
        PUSHSDK_LOG_LEVEL_DEBUG
    }

    /**
     * Logging tag
     */
    private const val TAG_LOGGING = "WOWGuild"

    fun error(message: String) {
        if (Build.VERSION.SDK_INT >= 26) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)
            Log.e(TAG_LOGGING, "$formatted $message")
        } else {
            val formatted =
                SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
            Log.e(TAG_LOGGING, "$formatted $message")
        }
    }

    fun debug(context: Context, message: String) {
        val saveDattaProvider = SaveDataProvider(context.applicationContext)
        if (saveDattaProvider.logLevel == LogLevels.PUSHSDK_LOG_LEVEL_DEBUG.name) {
            if (Build.VERSION.SDK_INT >= 26) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)
                Log.d(TAG_LOGGING, "$formatted $message")
            } else {
                val formatted =
                    SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
                Log.d(TAG_LOGGING, "$formatted $message")
            }
        }
    }

    fun debugFirebaseRemoteMessage(context: Context, remoteMessage: RemoteMessage) {
        debug(context, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            debug(context, "Message from remote: $remoteMessage")
            debug(context, "Message from remote data: ${remoteMessage.data}")
            debug(context, "Message from remote messageId: ${remoteMessage.messageId}")
            debug(context, "Message from remote messageType: ${remoteMessage.messageType}")
            debug(context, "Message from remote priority: ${remoteMessage.priority}")
            debug(context, "Message from remote rawData: ${remoteMessage.rawData}")
            debug(context, "Message from remote ttl: ${remoteMessage.ttl}")
            debug(context, "Message from remote to: ${remoteMessage.to}")
            debug(context, "Message from remote sentTime: ${remoteMessage.sentTime}")
            debug(context, "Message from remote collapseKey: ${remoteMessage.collapseKey}")
            debug(
                context,
                "Message from remote originalPriority: ${remoteMessage.originalPriority}"
            )
            debug(context, "Message from remote senderId: ${remoteMessage.senderId}")
            debug(context, "Message from remote data to string: ${remoteMessage.data}")

            debug(
                context,
                "data payload(Map<String, String> toString): " + remoteMessage.data.toString()
            )
        }
    }
}