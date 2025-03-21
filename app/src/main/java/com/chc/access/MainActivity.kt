package com.chc.access

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.chc.access.navigationHost.NavHostComp
import com.chc.access.ui.theme.AccessTheme
import com.chc.access.utils.LocalNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val rememberNavController = rememberNavController()
//            val permissionLauncher =
//                rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}
//
//            LaunchedEffect(Unit) {
//                val isFirstOpen = PreferenceManager.isFirstLaunch(this@MainActivity)
//
//                if (isFirstOpen) {
//                    permissionLauncher.launch(arrayOf(android.Manifest.permission.READ_CONTACTS))
//                    PreferenceManager.setFirstLaunchComplete(this@MainActivity)
//                }
//            }

            CompositionLocalProvider(
                LocalNavController provides rememberNavController
            ) {
                AccessTheme(
                    dynamicColor = false
                ) {
                    NavHostComp()
                }
            }
        }
    }
}
