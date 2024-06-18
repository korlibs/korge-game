package korlibs.korge.game

import korlibs.datastructure.*
import korlibs.math.geom.*

// @TODO: Also do 3D transform
class Transform : Component() {
    var version = 0
    var parent: Transform? = null
        set(value) {
            parent?._children?.remove(this)
            field = value
            parent?._children?.add(this)
            version++
        }
    private val _children = fastArrayListOf<Transform>()
    val children: List<Transform> get() = _children

    var localMatrix2D = Matrix()
        set(value) {
            field = value
            invalidate()
        }

    private var _globalMatrix2DVersion: Int = -1
    private var _globalMatrix2D: Matrix = Matrix()
    val globalMatrix2D: Matrix get() {
        if (_globalMatrix2DVersion != version) {
            _globalMatrix2DVersion = version
            _globalMatrix2D = localMatrix2D * (parent?.globalMatrix2D ?: Matrix())
        }
        return _globalMatrix2D
    }

    fun invalidate() {
        version++
        for (child in children) child.invalidate()
    }

    fun reset() {
        version = 0
        parent = null
        localMatrix2D = Matrix()
    }
}
