import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

sealed class CounterMsg 
object IncCounter : CounterMsg() // one-way message to increment counter
class GetCounter(val response: SendChannel<Int>) : CounterMsg() // a request with channel for reply.


fun counterActor(): SendChannel<CounterMsg> = GlobalScope.actor { //(1)
    var counter = 0 //(9) actor state, not shared
    for (msg in channel) { // handle incoming messages
        when (msg) {
            is IncCounter -> counter++ //(4)
            is GetCounter -> msg.response.send(counter) //(3)
        }
    }
}

suspend fun getCurrentCount(counter: SendChannel<CounterMsg>): Int { //(8)
    val response = Channel<Int>() //(2)
    counter.send(GetCounter(response))
    val receive = response.receive()
    println("Counter = $receive")
    return receive
}

fun main() = runBlocking<Unit> {
    val counter = counterActor()
    launch { //(5)
        while(getCurrentCount(counter) < 100){
            delay(100)
            println("sending IncCounter message")
            counter.send(IncCounter) //(7)
        }
    }
    launch { //(6)
        while ( getCurrentCount(counter) < 100) {
            delay(200)
        }
    }.join()
    counter.close() // shutdown the actor
}
