package com.example.photogallery.ui.theme

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.example.photogallery.model.Photo
import com.example.photogallery.viewmodel.PhotoViewModel
import java.io.File

@Composable
fun PhotoGridScreen(navController: NavController, viewModel: PhotoViewModel) {
    val photos by viewModel.photos.observeAsState(emptyList())
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedPhoto by remember { mutableStateOf<Photo?>(null) }

    val photoUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri.value?.let { uri ->
                viewModel.addPhoto(Photo(uri = uri))
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
                photoUri.value = uri
                cameraLauncher.launch(uri)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Photo")
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(photos.size) { index ->
                val photo = photos[index]
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.uri ?: photo.resId)
                        .size(500) // Resize hình gốc
                        .crossfade(true)
                        .build(),
                    contentDescription = "Photo Thumbnail",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navController.navigate("photoDetail/$index")
                                },
                                onLongPress = {
                                    selectedPhoto = photo // Set the selected photo
                                    showDialog = true // Show the dialog
                                }
                            )
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
        if (showDialog && selectedPhoto != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Choose Action") },
                text = {
                    Column {
                        Text("Do you want to delete or favorite this photo?")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Handle delete action
                            showDialog = false
                            Toast.makeText(context, "Photo deleted", Toast.LENGTH_SHORT).show()
                            selectedPhoto?.let {
                                viewModel.deletePhoto(it)
                                Toast.makeText(context, "Photo deleted", Toast.LENGTH_SHORT).show()
                            }
                            // Implement actual delete logic here
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            // Handle favorite action
                            showDialog = false
                            selectedPhoto?.let {
                                viewModel.addFavorite(it) // Add photo to favorites
                                Toast.makeText(context, "Photo favorited", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Favorite")
                    }
                }
            )
            }
    }
}
