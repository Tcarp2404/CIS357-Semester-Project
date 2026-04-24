package edu.gvsu.cis.cis357_semester_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ThoughtViewModel = viewModel()

            LaunchedEffect(Unit) {
                viewModel.loadData(this@MainActivity)
            }

            AppNavigation(viewModel)
        }
    }
}