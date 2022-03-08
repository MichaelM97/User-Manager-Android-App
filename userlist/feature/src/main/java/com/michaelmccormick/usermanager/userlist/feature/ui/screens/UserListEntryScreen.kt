package com.michaelmccormick.usermanager.userlist.feature.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.michaelmccormick.usermanager.userlist.feature.ui.dialogs.AddUserDialog
import com.michaelmccormick.usermanager.userlist.feature.ui.dialogs.RemoveUserDialog

@ExperimentalComposeUiApi
@Composable
fun UserListEntryScreen(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = UserListScreenRoute.UserList.route) {
        composable(UserListScreenRoute.UserList.route) {
            UserListScreen(
                onNavigateToAddUser = { navController.navigate(UserListScreenRoute.AddUser.route) },
                onNavigateToRemoveUser = { navController.navigate(UserListScreenRoute.RemoveUser.buildRoute(it.id, it.name)) },
                userListViewModel = hiltViewModel(),
            )
        }
        dialog(UserListScreenRoute.AddUser.route) {
            AddUserDialog(
                onCloseDialog = { navController.popBackStack() },
                addUserViewModel = hiltViewModel(),
            )
        }
        dialog(
            UserListScreenRoute.RemoveUser.route,
            arguments = listOf(
                navArgument(USER_ID_ARG) { type = NavType.IntType },
                navArgument(USER_NAME_ARG) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt(USER_ID_ARG)
            val userName = backStackEntry.arguments?.getString(USER_NAME_ARG)
            if (userId == null || userName == null) {
                navController.popBackStack()
            } else {
                RemoveUserDialog(
                    userId = userId,
                    userName = userName,
                    onCloseDialog = { navController.popBackStack() },
                    removeUserViewModel = hiltViewModel(),
                )
            }
        }
    }
}

private const val USER_ID_ARG = "userId"
private const val USER_NAME_ARG = "userName"

private sealed class UserListScreenRoute(val route: String) {
    object UserList : UserListScreenRoute("userList")
    object AddUser : UserListScreenRoute("addUser")
    object RemoveUser : UserListScreenRoute("removeUser/{$USER_ID_ARG}/{$USER_NAME_ARG}") {
        fun buildRoute(userId: Int, userName: String) = "removeUser/$userId/$userName"
    }
}
