import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.*

private const val N_WORKERS = 4

class Content
class Location
class Reference {
    fun resolveLocation(): Location = TODO()
    constructor(index: Int)
}

data class LocContent(val location: Location, val content: Content)

fun downloadContent(loc: Location): Content = TODO()
fun processContent(ref: Reference, content: Content): Unit = TODO()

private fun CoroutineScope.downloader(
    references: ReceiveChannel<Reference>,
    locations: SendChannel<Location>,
    contents: ReceiveChannel<LocContent>
) = launch {
    val requested = mutableMapOf<Location, MutableList<Reference>>()
    while (true) {
        select<Unit> {
            references.onReceive { ref ->
                val loc = ref.resolveLocation()
                val refs = requested[loc]
                if (refs == null) {
                    requested[loc] = mutableListOf(ref)
                    locations.send(loc)
                } else {
                    refs.add(ref)
                }
            }
            contents.onReceive { (loc, content) ->
                val refs = requested.remove(loc)!!
                for (ref in refs) {
                    processContent(ref, content)
                }
            }
        }
    }
}

private fun CoroutineScope.worker(
    locations: ReceiveChannel<Location>,
    contents: SendChannel<LocContent>
) = launch {
    for (loc in locations) {
        val content = downloadContent(loc)
        contents.send(LocContent(loc, content))
    }
}

fun CoroutineScope.processReferences(
    references: ReceiveChannel<Reference>
) {
    val locations = Channel<Location>()
    val contents = Channel<LocContent>()
    repeat(N_WORKERS) { worker(locations, contents) }
    downloader(references, locations, contents)
}

fun main() = runBlocking {
    withTimeout(3000) {
        val references = Channel<Reference>()
        processReferences(references)
        var index = 1
        while (true) {
            references.send(Reference(index++))
        }
    }
}
