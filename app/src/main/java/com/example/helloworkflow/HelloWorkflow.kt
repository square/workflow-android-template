package com.example.helloworkflow

import com.example.helloworkflow.HelloWorkflow.Action.HelloAction
import com.example.helloworkflow.HelloWorkflow.Rendering
import com.example.helloworkflow.HelloWorkflow.State
import com.example.helloworkflow.HelloWorkflow.State.Goodbye
import com.example.helloworkflow.HelloWorkflow.State.Hello
import com.squareup.workflow.*

object HelloWorkflow : StatefulWorkflow<Unit, State, Nothing, Rendering>() {
    enum class State {
        Hello,
        Goodbye;

        fun theOtherState(): State = when (this) {
            Hello -> Goodbye
            Goodbye -> Hello
        }
    }

    data class Rendering(
        val message: String,
        val onClick: () -> Unit
    )

    private sealed class Action : WorkflowAction<State, Nothing> {
        override fun WorkflowAction.Updater<State, Nothing>.apply() {
            when (this@Action) {
                is HelloAction -> nextState = this@Action.currentState.theOtherState()
            }
        }

        data class HelloAction(val currentState: State) : Action()
    }

    override fun initialState(
        props: Unit,
        snapshot: Snapshot?
    ): State = snapshot?.bytes?.parse { source -> if (source.readInt() == 1) Hello else Goodbye }
        ?: Hello

    override fun render(
        props: Unit,
        state: State,
        context: RenderContext<State, Nothing>
    ): Rendering {
        return Rendering(
            message = state.name,
            onClick = { context.actionSink.send(HelloAction(state)) }
        )
    }

    override fun snapshotState(state: State): Snapshot = Snapshot.of(if (state == Hello) 1 else 0)
}
