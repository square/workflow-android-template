package com.example.helloworkflow

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

    private val helloAction = WorkflowAction<State, Nothing> {
        state = state.theOtherState()
        null
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
        val sink = context.makeActionSink<WorkflowAction<State, Nothing>>()
        return Rendering(
            message = state.name,
            onClick = { sink.send(helloAction) }
        )
    }

    override fun snapshotState(state: State): Snapshot = Snapshot.of(if (state == Hello) 1 else 0)
}
