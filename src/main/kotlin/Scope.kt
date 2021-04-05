import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SomethingWithLifecycle : CoroutineScope{
    private val job = Job()

    fun close() = job.cancel()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun doSomething(){
        //processReferences(references)
    }
}