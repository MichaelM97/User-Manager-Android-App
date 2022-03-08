package com.michaelmccormick.usermanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.michaelmccormick.usermanager.ui.theme.UserManagerTheme
import com.michaelmccormick.usermanager.userlist.feature.ui.screens.UserListEntryScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserManagerTheme {
                UserListEntryScreen()
            }
        }
    }
}
