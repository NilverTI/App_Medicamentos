package com.tantalean.mihorariomed.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tantalean.mihorariomed.ui.screens.detail.ScheduleDetailScreen
import com.tantalean.mihorariomed.ui.screens.form.ScheduleFormScreen
import com.tantalean.mihorariomed.ui.screens.list.ScheduleListScreen

object Routes {
    const val LIST = "list"
    const val FORM = "form"
    const val DETAIL = "detail"

    fun formRoute(id: Long? = null) = if (id == null) FORM else "$FORM?id=$id"
    fun detailRoute(id: Long) = "$DETAIL/$id"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LIST) {

        composable(Routes.LIST) {
            ScheduleListScreen(
                onAdd = { navController.navigate(Routes.formRoute(null)) },
                onOpenDetail = { id -> navController.navigate(Routes.detailRoute(id)) }
            )
        }

        composable(
            route = "${Routes.FORM}?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            val scheduleId = if (id == -1L) null else id

            ScheduleFormScreen(
                scheduleId = scheduleId,
                onBack = { navController.popBackStack() },
                onSaved = { savedId ->
                    if (scheduleId != null) {
                        navController.navigate(Routes.detailRoute(savedId)) {
                            // Deja la lista como base y abre detalle
                            popUpTo(Routes.LIST) { inclusive = false }
                            launchSingleTop = true
                        }
                    } else {
                        // Si fue CREAR -> volver al LISTADO
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(
            route = "${Routes.DETAIL}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L

            ScheduleDetailScreen(
                scheduleId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Routes.formRoute(id)) }
            )
        }
    }
}
