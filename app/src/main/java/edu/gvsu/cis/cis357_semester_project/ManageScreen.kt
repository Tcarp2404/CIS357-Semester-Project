package edu.gvsu.cis.cis357_semester_project

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen(navController: NavController, viewModel: ThoughtViewModel) {

    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    val selectedEmotion by viewModel.selectedEmotion.collectAsState()
    val thoughts by viewModel.thoughts.collectAsState()

    val imagePath = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>("imagePath")

    val currentList = thoughts[selectedEmotion] ?: emptyList()
    val bgColor = getEmotionColor(selectedEmotion)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Manage Thoughts")
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
                .padding(16.dp)
        ) {

            Text("Add a Thought", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter Thought") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        viewModel.addThought(text, selectedEmotion, context, imagePath)
                        text = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Thought", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.navigate("camera") },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Take Photo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (currentList.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No thoughts yet 🧠")
                    Text("Add one to get started!")
                }
            } else {
                LazyColumn {
                    itemsIndexed(currentList) { index, thought ->

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Icon(Icons.Default.Edit, contentDescription = null)

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(thought.text, modifier = Modifier.weight(1f))

                                    IconButton(onClick = {
                                        viewModel.removeThought(selectedEmotion, index, context)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = null)
                                    }
                                }

                                thought.imagePath?.let { path ->
                                    val bitmap = BitmapFactory.decodeFile(path)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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