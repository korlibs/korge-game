package korlibs.korge.game

import kotlinx.coroutines.*

open class Actor : Behaviour() {
    open suspend fun main() {
    }

    private val frameWaiters = arrayListOf<CompletableDeferred<Unit>>()

    protected suspend fun frame() {
        val cd = CompletableDeferred<Unit>()
        frameWaiters += cd
        cd.await()
    }

    override fun fixedUpdate() {
        for (w in frameWaiters) w.complete(Unit)
        frameWaiters.clear()
    }

    private var job: Job? = null

    override fun attached() {
        detached()
        job = CoroutineScope(obj.root.coroutineContext).launch { main() }
    }

    override fun detached() {
        job?.cancel()
        job = null
    }
}
