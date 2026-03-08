package com.vulancube.lqxgb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vulancube.lqxgb.data.AppState
import com.vulancube.lqxgb.data.AppViewModel
import com.vulancube.lqxgb.ui.AppNavigation
import com.vulancube.lqxgb.ui.screens.SplashScreen
import com.vulancube.lqxgb.ui.theme.VulanCubeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VulanCubeTheme {
                val viewModel: AppViewModel = viewModel()
                val appState by viewModel.appState.collectAsState()

                when (val state = appState) {
                    is AppState.Loading -> {
                        SplashScreen()
                    }
                    is AppState.Remote -> {
                        startActivity(Intent(this@MainActivity, RemoteActivity::class.java).apply {
                            putExtra("content_link", state.link)
                        })
                        finish()
                    }
                    is AppState.Game -> {
                        AppNavigation(viewModel = viewModel)
                    }
                }
            }
        }
    }

}
