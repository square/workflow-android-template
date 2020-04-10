package com.example.helloworkflow

import android.view.View
import android.widget.TextView
import com.squareup.workflow.ui.ContainerHints
import com.squareup.workflow.ui.LayoutRunner
import com.squareup.workflow.ui.LayoutRunner.Companion.bind
import com.squareup.workflow.ui.ViewBinding

class HelloLayoutRunner(view: View) : LayoutRunner<HelloWorkflow.Rendering> {
    private val messageView: TextView = view.findViewById(R.id.hello_message)

    override fun showRendering(rendering: HelloWorkflow.Rendering, containerHints: ContainerHints) {
        messageView.text = rendering.message
        messageView.setOnClickListener { rendering.onClick() }
    }

    companion object : ViewBinding<HelloWorkflow.Rendering> by bind(
        R.layout.hello_goodbye_layout, ::HelloLayoutRunner
    )
}
