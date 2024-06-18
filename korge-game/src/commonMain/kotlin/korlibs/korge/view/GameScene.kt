package korlibs.korge.view

import korlibs.korge.game.*
import korlibs.korge.scene.*

abstract class GameScene : Scene() {
    abstract suspend fun main(root: GameRoot)

    final override suspend fun SContainer.sceneMain() {
        val view = gameView(coroutineContext)
        main(view.gameRoot)
    }
}