package com.chc.access.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chc.access.navigationHost.RouterName
import com.chc.access.utils.LocalNavController

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val navHostController = LocalNavController.current

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                navHostController.navigate(RouterName.ACCESS_PAGE)
            }
        ) {
            Text("点击去联系人界面")
        }
    }
}