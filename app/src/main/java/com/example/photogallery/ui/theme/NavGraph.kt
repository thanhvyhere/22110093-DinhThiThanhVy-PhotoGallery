package com.example.photogallery.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.photogallery.viewmodel.PhotoViewModel

@Composable
fun NavGraph(startDestination: String = "photoGrid") {
    val navController = rememberNavController()
    val photoViewModel: PhotoViewModel = viewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("photoGrid") {
            PhotoGridScreen(navController, photoViewModel)
        }
        composable(
            route = "photoDetail/{photoIndex}",
            arguments = listOf(navArgument("photoIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("photoIndex") ?: 0
            PhotoDetailScreen(navController, photoViewModel, index)
        }
        // Màn hình thêm ảnh hoặc settings
        composable("addPhoto") {
            // Tạo một màn hình tạm thời, ví dụ:
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Add Photo Screen - Chức năng thêm ảnh hoặc cài đặt")
            }
        }
    }
}
