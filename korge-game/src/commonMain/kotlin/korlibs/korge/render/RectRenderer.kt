package korlibs.korge.render

import korlibs.graphics.shader.*
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.korge.game.*
import korlibs.korge.view.*
import korlibs.math.geom.*

class RectRenderer(rect: Rectangle) : Renderer() {
    var rect = rect

    override fun render(ctx: RenderContext) {
        val vertices = RectVertexProvider.provide(rect.left, rect.top, rect.right, rect.bottom, this.transform.globalMatrix2D, Bitmaps.white, Colors.WHITE)
        val baseBitmap = Bitmaps.white
        val smoothing = false
        val renderBlendMode = BlendMode.NORMAL
        val program: Program? = null

        ctx.useBatcher { batch ->
            //batch.texture1212
            //batch.setTemporalUniforms(_programUniforms) {
            //updateProgramUniforms?.invoke(ctx)
            batch.drawVertices(
                vertices, ctx.getTex(baseBitmap).base, smoothing, renderBlendMode,
                program = program,
            )
            //}
        }
    }
}

object RectVertexProvider {
    fun provide(left: Double, top: Double, right: Double, bottom: Double, m: Matrix, bmp: BmpCoords, renderColorMul: RGBA, vertices: TexturedVertexArray = TexturedVertexArray(4, TexturedVertexArray.QUAD_INDICES)): TexturedVertexArray {
        vertices.quad(0, left, top, right - left, bottom - top, m, bmp, renderColorMul)
        return vertices
    }
}