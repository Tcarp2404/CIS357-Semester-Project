package edu.gvsu.cis.cis357_semester_project

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ThoughtViewModel) {

    val selectedEmotion by viewModel.selectedEmotion.collectAsState()
    val bgColor = getEmotionColor(selectedEmotion)


    val emotionIcon = when (selectedEmotion) {
        Emotion.HAPPY -> Icons.Default.SentimentSatisfied
        Emotion.SAD -> Icons.Default.SentimentDissatisfied
        Emotion.CALM -> Icons.Default.SelfImprovement
        Emotion.MOTIVATED -> Icons.Default.FitnessCenter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("MindLift")
                        Text(
                            selectedEmotion.name,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AnimatedContent(targetState = emotionIcon) { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "How are you feeling today?",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Emotion.values().forEach { emotion ->
                Button(
                    onClick = { viewModel.setEmotion(emotion) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(emotion.name)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("viewer") },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Thoughts", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("manage") },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Manage Thoughts", color = Color.White)
            }
        }
    }
}