package korlibs.korge.game

import kotlinx.serialization.*

@Serializable
//@Polymorphic
//@SerialName("componentName")
abstract class GameComponent {
    //open val componentName: String get() = "GameComponent"
    //val kind: String? get() = this::class.qualifiedName

    @Transient
    internal var _obj: GameObject? = null
    @Transient
    val obj: GameObject get() = _obj!!
    @Transient
    val tag: String? get() = _obj?.tag
    @Transient
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
