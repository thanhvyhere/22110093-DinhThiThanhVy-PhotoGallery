package com.example.photogallery.ui.theme

import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.Coil
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.photogallery.R
import com.example.photogallery.viewmodel.PhotoViewModel
import com.ortiz.touchview.TouchImageView

@Composable
fun PhotoDetailScreen(
    navController: NavController,
    viewModel: PhotoViewModel,
    photoIndex: Int
) {
    val photos = viewModel.photos.value ?: emptyList()
    if (photos.isEmpty() || photoIndex >= photos.size) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No photo available")
        }
        return
    }

    val photo = photos[photoIndex]
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                TouchImageView(context).apply {
                    val imageRequest = ImageRequest.Builder(context)
                        .data(photo.uri ?: photo.resId?.let { "android.resource://${context.packageName}/$it" })
                        .size(500)
                        .crossfade(true)
                        .target { drawable ->
                            this.setImageDrawable(drawable)
                        }
                        .build()

                    // Load image using Coil
                    Coil.imageLoader(context).enqueue(imageRequest)

                    // Set zoom level to default
                    setZoom(1f)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center)
        )

        // Back Button
        IconButton(
            onClick = {
                navController.navigate("photoGrid") {
                    popUpTo("photoGrid") { inclusive = false }
                }
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        // Previous and Next buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (photoIndex > 0)
                        navController.navigate("photoDetail/${photoIndex - 1}")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (photoIndex < photos.size - 1)
                        navController.navigate("photoDetail/${photoIndex + 1}")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
    }
}

