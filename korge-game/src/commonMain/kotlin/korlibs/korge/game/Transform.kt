package korlibs.korge.game

import korlibs.datastructure.*
import korlibs.math.geom.*
import kotlinx.serialization.*

// @TODO: Also do 3D transform
@Serializable
class Transform : GameComponent() {
    @Transient
    protected var version = 0
    @Transient
    var parent: Transform? = null
        set(value) {
            parent?._children?.remove(this)
            field = value
            parent?._children?.add(this)
            version++
        }
    @Transient
    private val _children = fastArrayListOf<Transform>()
    @Transient
    val children: List<Transform> get() = _children

    // @TODO: Auto-computed based on what we are setting
    @Transient
    var has3D: Boolean = false

    @Transient
    var localMatrix2D = Matrix()
        set(value) {
            field = value
            invalidate()
        }

    // @TODO: Cache and improve performance of these
    @Transient
    var matTransform
        get() = localMatrix2D.toTransform()
        set(value) {
            localMatrix2D = value.toMatrix()
        }

    // @TODO: Cache and improve performance of these
    @Transient
    var x: Double
        get() = matTransform.x
        set(value) {
            matTransform = matTransform.copy(x = value)
        }
    // @TODO: Cache and improve performance of these
    @Transient
    var y: Double
        get() = matTransform.y
        set(value) {
            matTransform = matTransform.copy(y = value)
        }
    // @TODO: Cache and improve performance of these
    @Transient
    var xy: Point
        get() = Point(matTransform.x, matTransform.y)
        set(value) {
            matTransform = matTransform.copy(x = value.x, y = value.y)
        }

    @Transient
    private var _globalMatrix2DVersion: Int = -1
    @Transient
    private var _globalMatrix2D: Matrix = Matrix()
    @Transient
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

var GameObject.xy: Point
    get() = transform.xy
    set(value) { transform.xy = value }

var GameObject.x: Double
    get() = transform.x
    set(value) { transform.x = value }

var GameObject.y: Double
    get() = transform.y
    set(value) { transform.y = value }

