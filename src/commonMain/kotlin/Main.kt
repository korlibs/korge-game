import korlibs.event.*
import korlibs.korge.*
import korlibs.korge.game.*
import korlibs.korge.render.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

suspend fun main() = Korge {
    val str = Json.encodeToString(Demo().also {
        it.a = 20
        //it.tag = "hello"
    })
    println(str)
    println(Json.decodeFromString<Demo>(str))

    //val root = GameRoot(coroutineContext)
    //val obj = root.create()
    //obj.attach(Demo().also { it.a = 20 })
    //println(Json.encodeToString(obj))

    //val composable = composable(10) {}
    //composable.state = 20
    //onEvent(ScoreUpdated) {
    //    composable.state = it.score
    //}
    //views.dispatch(ScoreUpdated(20))
    sceneContainer().changeTo({ MainMyModuleScene() })
}

@Serializable
@SerialName("demo")
class Demo : GameComponent() {
    //override val componentName = "Demo"

    var a = 10

    override fun toString(): String = "Demo(a=$a)"
}

class ScoreUpdated(val score: Int) : Event(), TEvent<ScoreUpdated> {
    override val type: EventType<ScoreUpdated> get() = ScoreUpdated
    companion object : EventType<ScoreUpdated>
}

// @TODO: Mouse pick is a raytrace from camera positions
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

class MyComposableScene(state: Int) : ComposableScene<Int>(10) {
    // @Composable
    override fun main(state: Int) {
    }
}
