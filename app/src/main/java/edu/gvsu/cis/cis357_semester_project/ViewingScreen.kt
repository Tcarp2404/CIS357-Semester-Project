package edu.gvsu.cis.cis357_semester_project

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerScreen(navController: NavController, viewModel: ThoughtViewModel) {

    val thoughts = viewModel.getThoughtsForEmotion()
    var index by remember { mutableStateOf(0) }

    val selectedEmotion by viewModel.selectedEmotion.collectAsState()
    val bgColor = getEmotionColor(selectedEmotion)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("View Thoughts")
                        Text(selectedEmotion.name, style = MaterialTheme.typography.labelSmall)
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

            if (thoughts.isNotEmpty()) {

                val currentThought = thoughts[index]

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(Icons.Default.FormatQuote, contentDescription = null)

                        Spacer(modifier = Modifier.height(8.dp))

                        AnimatedContent(targetState = currentThought.text) { text ->
                            Text(text, style = MaterialTheme.typography.headlineSmall)
                        }

                        currentThought.imagePath?.let { path ->
                            val bitmap = BitmapFactory.decodeFile(path)

                            Spacer(modifier = Modifier.height(12.dp))

                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }

            } else {
                Text("No thoughts available")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (thoughts.isNotEmpty()) {
                        index = (index + 1) % thoughts.size
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (thoughts.isNotEmpty()) {
                        index = if (index - 1 < 0) thoughts.size - 1 else index - 1
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Previous", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}