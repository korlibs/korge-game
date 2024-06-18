package korlibs.korge.game

import korlibs.datastructure.*
import kotlin.coroutines.*
import kotlin.reflect.*

class GameRoot(val coroutineContext: CoroutineContext) : Extra by Extra.Mixin() {
    //val gameObjectPool = Pool({ it.reset() }) { GameObject(this, it) }
    internal val objs = fastArrayListOf<GameObject>()
    //val components = LinkedHashMap<KClass<*>, FastArrayList<Any>>()

    fun create(): GameObject = GameObject().also {
        it.root = this
        objs += it
    }
    fun destroy(obj: GameObject) {
        objs -= obj
        obj.reset()
        //gameObjectPool.free(obj)
    }
    fun findByName(name: String?): GameObject? = objs.find { it.name == name }
    fun findWithTag(tag: String?): GameObject? = objs.find { it.tag == tag }
    fun findWithTagAll(tag: String?): List<GameObject> = objs.filter { it.tag == tag }
    fun <T : GameComponent> findComponents(clazz: KClass<T>, out: MutableList<T> = arrayListOf()): List<T> {
        for (obj in objs) obj.getComponentsInChildren(clazz, out)
        return out
    }
    inline fun <reified T : GameComponent> findComponents(out: MutableList<T> = arrayListOf()): List<T> = findComponents(T::class, out)
}
