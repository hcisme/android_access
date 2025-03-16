package com.chc.access.components

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun RequestPermissionView(
    guideMessage: String = "我们需要读取您的通讯录来显示联系人信息",
    guideButtonText: String = "授予读取联系人权限",
    toSettingMessage: String = "您已关闭读取联系人权限，请前往设置授权，以便我们为您显示联系人信息",
    toSettingButtonText: String = "前往设置授予",
    permissionType: String,
    onPermissionChange: (isGranted: Boolean) -> Unit,
    content: @Composable (() -> Unit)
) {
    val context = LocalContext.current
    val activity = context as Activity
    val lifecycleOwner = LocalLifecycleOwner.current
    var isAllowShowGuide by remember {
        mutableStateOf(
            shouldShowRequestPermissionRationale(
                activity,
                permissionType
            )
        )
    }
    var hasContactPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permissionType
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasContactPermission = isGranted
            onPermissionChange(isGranted)
            isAllowShowGuide = shouldShowRequestPermissionRationale(
                activity,
                permissionType
            )
        }
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // 从设置页面返回后，重新检查权限状态
        hasContactPermission = ContextCompat.checkSelfPermission(
            context,
            permissionType
        ) == PackageManager.PERMISSION_GRANTED
        isAllowShowGuide = shouldShowRequestPermissionRationale(
            activity,
            permissionType
        )
        onPermissionChange(hasContactPermission)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                hasContactPermission = ContextCompat.checkSelfPermission(
                    context,
                    permissionType
                ) == PackageManager.PERMISSION_GRANTED
                isAllowShowGuide = shouldShowRequestPermissionRationale(
                    activity,
                    permissionType
                )
                onPermissionChange(hasContactPermission)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (hasContactPermission) {
        content()
    } else {
        if (isAllowShowGuide) {
            GuideView(
                message = guideMessage,
                buttonText = guideButtonText,
                onRequestPermission = {
                    permissionLauncher.launch(permissionType)
                }
            )
        } else {
            GuideView(
                message = toSettingMessage,
                buttonText = toSettingButtonText,
                onRequestPermission = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    settingsLauncher.launch(intent)
                }
            )
        }
    }
}

@Composable
private fun GuideView(message: String, buttonText: String, onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestPermission) {
            Text(buttonText)
        }
    }
}

