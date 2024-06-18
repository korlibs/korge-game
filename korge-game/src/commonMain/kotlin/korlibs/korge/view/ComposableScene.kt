package korlibs.korge.view

import korlibs.korge.scene.*

abstract class ComposableScene<TState>(initialState: TState) : Scene() {
    final override suspend fun SContainer.sceneMain() {
        // Render composable
    }

    //@Composable
    abstract fun main(state: TState)
}