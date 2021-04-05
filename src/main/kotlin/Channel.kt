import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

fun CoroutineScope.createChannel(): ReceiveChannel<Int> = produce {
    println("channel generating")
    for (i in 1..10) {
        delay(100)
        send(i)// has to wait for receiver
        "cycle iteration".also(::println)
    }
}

fun main() = runBlocking<Unit> {
    val channel = createChannel()

//    GlobalScope.launch {
//        for(i in createChannel())
//            println(i)
//    }.apply { join() }


    while(!channel.isClosedForReceive){
        channel.receive().also(::println) // has to wait for sender
    }
}

fun CoroutineScope.some(){
    launch {
        delay(33)
    }

}