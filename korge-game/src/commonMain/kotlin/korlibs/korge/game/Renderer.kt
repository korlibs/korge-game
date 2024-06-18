package korlibs.korge.game

import korlibs.korge.render.*
import korlibs.korge.view.*

abstract class Renderer : Component(), Renderable {
    //abstract override fun render(ctx: RenderContext)

    companion object {
        fun render(root: GameRoot, ctx: RenderContext) {
            for (renderer in root.findComponents<Renderer>()) {
                renderer.render(ctx)
            }
        }
    }
}
