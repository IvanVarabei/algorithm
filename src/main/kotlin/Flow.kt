import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

// Flow is cold: describes the data,
// this code doesn't run until collected
fun createFlow(): Flow<Int> = flow {
    coroutineContext
    println("\n$this")
    println("flow generating")
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}
fun main() = runBlocking<Unit> {
    //createFlow()

    createFlow().collect { i ->
        println(i)
    }
}