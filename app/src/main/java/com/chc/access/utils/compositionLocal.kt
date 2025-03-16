package com.chc.access.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

/**
 * 路由 SharedPreferences
 */
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
