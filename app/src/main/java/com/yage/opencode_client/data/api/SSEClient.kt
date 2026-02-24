package com.yage.opencode_client.data.api

import com.yage.opencode_client.data.model.SSEEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import java.util.Base64

class SSEClient(
    private val okHttpClient: OkHttpClient
) {
    fun connect(
        baseUrl: String,
        username: String? = null,
        password: String? = null
    ): Flow<Result<SSEEvent>> = callbackFlow {
        val url = if (baseUrl.startsWith("http")) baseUrl else "http://$baseUrl"
        val request = Request.Builder()
            .url("$url/global/event")
            .header("Accept", "text/event-stream")
            .header("Cache-Control", "no-cache")
            .apply {
                if (username != null && password != null) {
                    val credential = "$username:$password"
                    val encoded = Base64.getEncoder().encodeToString(credential.toByteArray())
                    header("Authorization", "Basic $encoded")
                }
            }
            .build()

        var eventSource: EventSource? = null

        val listener = object : EventSourceListener() {
            private var eventDataBuilder = StringBuilder()

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                if (data.isNotBlank() && data != "[DONE]") {
                    try {
                        val event = kotlinx.serialization.json.Json.decodeFromString<SSEEvent>(data)
                        trySend(Result.success(event))
                    } catch (e: Exception) {
                        trySend(Result.failure(e))
                    }
                }
            }

            override fun onClosed(eventSource: EventSource) {
                close()
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: okhttp3.Response?) {
                trySend(Result.failure(t ?: Exception("SSE connection failed")))
            }
        }

        eventSource = EventSources.createFactory(okHttpClient)
            .newEventSource(request, listener)

        awaitClose {
            eventSource?.cancel()
        }
    }
}
