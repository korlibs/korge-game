package korlibs.korge.game

import korlibs.datastructure.*
import kotlin.reflect.*

// @TODO: Tree should be construct at GameObject level or keep it in Transform
class GameObject internal constructor(val root: GameRoot) : Extra {
    var name: String? = null
    var tag: String? = null
    override var extra: ExtraType = ExtraType()
    private val components = fastArrayListOf<GameComponent>()
    val transform = Transform().attach(this)

    init {
        root.objs += this
    }

    fun attach(component: GameComponent) {
        component.detach()
        component._obj = this
        components += component
        component.attached()
    }
    fun detach(component: GameComponent) {
        components -= component
        component._obj = null
        component.detached()
    }
    fun detachAll() {
        while (components.isNotEmpty()) {
            detach(components.last())
        }
    }

    fun destroy() {
        root.destroy(this)
    }

    fun reset() {
        name = null
        tag = null
        extra?.clear()
        transform.reset()
        components.clear()
        detachAll()
    }

    fun <T : GameComponent> getComponents(clazz: KClass<T>, out: MutableList<T> = arrayListOf()): List<T> {
        for (c in components) if (clazz.isInstance(c)) out.add(c as T)
        return out
    }
    fun <T : GameComponent> getComponentsInChildren(clazz: KClass<T>, out: MutableList<T> = arrayListOf()): List<T> {
        for (child in this.transform.children) child.obj.getComponentsInChildren(clazz, out)
        getComponents(clazz, out)
        return out
    }
    fun <T : GameComponent> getComponent(clazz: KClass<T>): T? = components.first { it::class == clazz } as? T?
    inline fun <reified T : GameComponent> getComponent(): T? = getComponent(T::class)
    inline fun <reified T : GameComponent> getComponents(out: MutableList<T> = arrayListOf()): List<T> = getComponents(T::class, out)
    inline fun <reified T : GameComponent> getComponentsInChildren(out: MutableList<T> = arrayListOf()): List<T> = getComponentsInChildren(T::class, out)
}
