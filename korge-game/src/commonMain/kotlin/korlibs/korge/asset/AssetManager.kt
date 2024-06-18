package korlibs.korge.asset

import korlibs.datastructure.*
import korlibs.image.bitmap.*
import korlibs.korge.game.*
import kotlinx.coroutines.*

class AssetManager(val root: GameRoot) {
    fun getImage(name: String): Deferred<BmpSlice> {
        TODO()
    }
}

val GameRoot.assets by extraPropertyThis { AssetManager(this) }
