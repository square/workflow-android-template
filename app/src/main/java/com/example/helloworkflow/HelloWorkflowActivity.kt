package com.example.helloworkflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.workflow.ui.ExperimentalWorkflowUi
import com.squareup.workflow.ui.ViewRegistry
import com.squareup.workflow.ui.WorkflowRunner
import com.squareup.workflow.ui.setContentWorkflow

@UseExperimental(ExperimentalWorkflowUi::class)
class HelloWorkflowActivity : AppCompatActivity() {
    private val viewRegistry = ViewRegistry(HelloLayoutRunner)
    private lateinit var runner: WorkflowRunner<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runner = setContentWorkflow(savedInstanceState) {
            WorkflowRunner.Config(HelloWorkflow, viewRegistry)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        runner.onSaveInstanceState(outState)
    }
}
