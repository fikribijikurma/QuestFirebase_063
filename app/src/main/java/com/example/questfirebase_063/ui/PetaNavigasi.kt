package com.example.questfirebase_063.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questfirebase_063.ui.theme.route.DestinasiDetail
import com.example.questfirebase_063.ui.theme.route.DestinasiEdit
import com.example.questfirebase_063.ui.theme.route.DestinasiEntry
import com.example.questfirebase_063.ui.theme.route.DestinasiHome

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Halaman Utama (Daftar Siswa)
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick = { idSiswa ->
                    navController.navigate("${DestinasiDetail.route}/$idSiswa")
                }
            )
        }

        // Halaman Tambah Siswa (Entry)
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        // Halaman Detail Siswa
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.itemIdArg) {
                type = NavType.StringType
            })
        ) {
            DetailScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
                onEditClick = { idSiswa ->
                    navController.navigate("${DestinasiEdit.route}/$idSiswa")
                }
            )
        }

        // Halaman Edit Siswa
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.itemIdArg) {
                type = NavType.StringType
            })
        ) {
            EditSiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}