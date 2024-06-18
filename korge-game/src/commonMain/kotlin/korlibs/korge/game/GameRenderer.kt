package korlibs.korge.game

import korlibs.korge.render.*
import korlibs.korge.view.*

abstract class GameRenderer : GameComponent(), Renderable {
    //abstract override fun render(ctx: RenderContext)

    companion object {
        fun render(root: GameRoot, ctx: RenderContext) {
            for (renderer in root.findComponents<GameRenderer>()) {
                renderer.render(ctx)
            }
        }
    }
}
