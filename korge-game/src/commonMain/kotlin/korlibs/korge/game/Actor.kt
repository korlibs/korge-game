package korlibs.korge.game

import kotlinx.coroutines.*

abstract class Actor : Behaviour() {
    abstract suspend fun main()

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

    /** Changes the current action to [block]. For example `change(::right)` suspend fun right() { ... } */
    fun change(block: suspend () -> Unit) {
        throw ChangeAction(block)
    }

    class ChangeAction(val block: suspend () -> Unit) : Throwable()

    private var job: Job? = null

    override fun attached() {
        detached()
        job = CoroutineScope(obj.root.coroutineContext).launch {
            var block: (suspend () -> Unit) = ::main
            while (true) {
                try {
                    block()
                    break
                } catch (e: ChangeAction) {
                    block = e.block
                }
            }
        }
    }

    override fun detached() {
        job?.cancel()
        job = null
    }
}
