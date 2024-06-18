package korlibs.korge.view

import korlibs.korge.game.*
import korlibs.korge.render.*
import kotlin.coroutines.*

fun Container.gameView(context: CoroutineContext): GameView = GameView(context).addTo(this)

class GameView(context: CoroutineContext) : View() {
    val gameRoot: GameRoot by lazy {
        GameRoot(context)
    }

    override fun renderInternal(ctx: RenderContext) {
        Renderer.render(gameRoot, ctx)
    }

    init {
        addFastUpdater {
            for (renderer in gameRoot.findComponents<Behaviour>()) {
                if (renderer.enabled) {
                    renderer.update(it)
                    renderer.fixedUpdate()
                }
            }
        }
    }
}
