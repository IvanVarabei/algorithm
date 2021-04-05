import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println(Thread.currentThread().name)
        delay(100)

        launch {
            println(Thread.currentThread().name)
        }
        delay(100)

        GlobalScope.launch {
            println(Thread.currentThread().name)
        }
        delay(100)

        launch(Dispatchers.Default) { println(Thread.currentThread().name)
        }
        delay(100)

        launch(Dispatchers.IO) { println(Thread.currentThread().name)
        }
        delay(100)

        launch(Dispatchers.Unconfined) { println(Thread.currentThread().name)
        }
        delay(100)

        launch(newSingleThreadContext("hello")) { println(Thread.currentThread().name)
        }
        delay(100)
    }
}