package edu.gvsu.cis.cis357_semester_project

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.io.File
import java.util.concurrent.Executors

@Composable
fun CameraScreen(navController: NavController) {

    val context = LocalContext.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    if (!hasPermission) {
        Text("Camera permission required")
        return
    }

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    Column(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({

                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    imageCapture = ImageCapture.Builder().build()

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        ctx as androidx.lifecycle.LifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture
                    )

                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {
                val photoFile = File(
                    context.filesDir,
                    "photo_${System.currentTimeMillis()}.jpg"
                )

                val outputOptions =
                    ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imageCapture?.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("imagePath", photoFile.absolutePath)

                            navController.popBackStack()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e("Camera", "Error: ${exception.message}")
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capture Photo")
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}