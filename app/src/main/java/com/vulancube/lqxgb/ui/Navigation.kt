package com.vulancube.lqxgb.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vulancube.lqxgb.data.AppViewModel
import com.vulancube.lqxgb.data.GameLevels
import com.vulancube.lqxgb.ui.screens.*

sealed class Screen(val route: String) {
    data object Menu : Screen("menu")
    data object Levels : Screen("levels")
    data object Game : Screen("game/{levelId}") {
        fun createRoute(levelId: Int) = "game/$levelId"
    }
    data object Policy : Screen("policy")
}

@Composable
fun AppNavigation(viewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route) {
            BackHandler(enabled = true) { }
            MainMenuScreen(
                viewModel = viewModel,
                onPlayClick = { navController.navigate(Screen.Levels.route) },
                onPolicyClick = { navController.navigate(Screen.Policy.route) }
            )
        }

        composable(Screen.Levels.route) {
            LevelSelectScreen(
                onBackClick = { navController.popBackStack() },
                onLevelSelect = { level ->
                    navController.navigate(Screen.Game.createRoute(level.id))
                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1
            val level = GameLevels.levels.find { it.id == levelId } ?: GameLevels.levels[0]
            GameScreen(
                level = level,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Policy.route) {
            val policyLink = viewModel.getPolicyLink()
            if (!policyLink.isNullOrEmpty()) {
                PolicyScreen(
                    link = policyLink,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
