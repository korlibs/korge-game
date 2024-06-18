import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.korge.*
import korlibs.korge.game.*
import korlibs.korge.render.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

suspend fun main() = Korge {
    sceneContainer().changeTo({ MainMyModuleScene() })
}

class MainMyModuleScene : GameScene() {
    override suspend fun main(root: GameRoot) {
        val obj = root.create()
        obj.attach(RectRenderer(Rectangle(0, 0, 100, 100)))
        obj.transform.localMatrix2D = Matrix().translated(20, 20)
        obj.attach(object : Actor() {
            override suspend fun main() {
                change(::right)
            }
            suspend fun right() {
                while (true) {
                    if (obj.x >= 20) change(::left)
                    obj.x++
                    frame()
                }
            }
            suspend fun left() {
                while (true) {
                    if (obj.x <= 0) change(::right)
                    obj.x--
                    frame()
                }
            }
        })
    }
}
