package korlibs.korge.game

import korlibs.time.*

open class Behaviour : GameComponent() {
    var enabled: Boolean = true

    open fun update(delta: FastDuration) { }
    open fun fixedUpdate() { }

    companion object {
        fun update(root: GameRoot, delta: FastDuration) {
            for (renderer in root.findComponents<Behaviour>()) {
                if (renderer.enabled) renderer.update(delta)
            }
        }
        fun fixedUpdate(root: GameRoot) {
            for (renderer in root.findComponents<Behaviour>()) {
                if (renderer.enabled) renderer.fixedUpdate()
            }
        }
    }
}
