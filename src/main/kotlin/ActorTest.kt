import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import java.math.BigInteger
import java.util.*

class Message(val action: String, val payload: String? = null, val response: SendChannel<String>? = null)

@ObsoleteCoroutinesApi
fun CoroutineScope.getActor() = actor<Message> {
    var state = "default"
    for (i in channel) {
        when (i.action) {
            "getState" -> i.response?.send(state)
            "setState" -> state = i.payload ?: state
        }
    }
}

fun main() = runBlocking<Unit> {
    val actor = getActor()
    launch {
        val channel = Channel<String>()
        actor.send(Message("getState", response = channel))
        println(channel.receive())
    }
    launch {
        actor.send(Message("setState", "newState"))
    }
    launch {
        val channel = Channel<String>()
        actor.send(Message("getState", response = channel))
        println(channel.receive())
    }
}