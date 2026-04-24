package edu.gvsu.cis.cis357_semester_project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

@Composable
fun AppNavigation(viewModel: ThoughtViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {

        composable("home") {
            HomeScreen(navController, viewModel)
        }

        composable("manage") {
            ManageScreen(navController, viewModel)
        }

        composable("viewer") {
            ViewerScreen(navController, viewModel)
        }

        composable("camera") {
            CameraScreen(navController)
        }
    }
}