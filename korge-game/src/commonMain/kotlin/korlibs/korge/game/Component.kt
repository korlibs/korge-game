package korlibs.korge.game

open class Component {
    internal var _obj: GameObject? = null
    val obj: GameObject get() = _obj!!

    val tag: String? get() = _obj?.tag
    val transform get() = obj.transform

    open fun detached() {
    }
    open fun attached() {
    }
}

fun <T : Component> T.attach(obj: GameObject): T {
    obj.attach(this)
    return this
}
fun <T : Component> T.detach(): T {
    _obj?.detach(this)
    return this
}
