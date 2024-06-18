package korlibs.korge.game

open class GameComponent {
    internal var _obj: GameObject? = null
    val obj: GameObject get() = _obj!!

    val tag: String? get() = _obj?.tag
    val transform get() = obj.transform

    open fun detached() {
    }
    open fun attached() {
    }
}

fun <T : GameComponent> T.attach(obj: GameObject): T {
    obj.attach(this)
    return this
}
fun <T : GameComponent> T.detach(): T {
    _obj?.detach(this)
    return this
}
