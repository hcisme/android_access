package com.chc.access.navigationHost

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chc.access.ui.screen.ContactList
import com.chc.access.ui.screen.HomePage
import com.chc.access.utils.LocalNavController

@Composable
fun NavHostComp(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val enterTransition = remember {
        slideInVertically(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            initialOffsetY = { it }
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 200
            )
        )
    }
    val exitTransition = remember {
        slideOutVertically(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutLinearInEasing
            ),
            targetOffsetY = { it }
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = 200
            )
        )
    }

    NavHost(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = RouterName.HOME_PAGE
    ) {
        composable(RouterName.HOME_PAGE) {
            HomePage()
        }

        composable(
            RouterName.ACCESS_PAGE,
            enterTransition = { enterTransition },
            popEnterTransition = null,
            popExitTransition = { exitTransition }
        ) {
            ContactList()
        }
    }
}