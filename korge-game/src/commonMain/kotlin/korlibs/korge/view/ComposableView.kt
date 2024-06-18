package korlibs.korge.view

fun <TState> Container.composable(
    initialState: TState,
    //@Composable
    composable: (TState) -> Unit
) = ComposableView(initialState, composable).addTo(this)

class ComposableView<TState>(
    initialState: TState,
    //@Composable
    val composable: (TState) -> Unit
) : Container() {
    var state: TState = initialState
        set(value) {
            field = value
            TODO() // Recompose
        }

    init {
        onNextFrame {
            //compose()
            TODO()
        }
    }
}
