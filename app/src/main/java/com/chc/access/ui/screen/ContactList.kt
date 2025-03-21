package com.chc.access.ui.screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chc.access.components.LocationScreen
import com.chc.access.utils.PermissionPreferenceManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactList(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var isShowRationaleDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.errorContainer)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        if (permissionState.status.isGranted) {
            LocationScreen()
        } else {
            Button(
                onClick = {
                    PermissionPreferenceManager.managePermissionRequestFlow(
                        context = context,
                        permissionType = permissionState.permission,
                        onFirstRequest = {
                            permissionState.launchPermissionRequest()
                        }
                    ) {
                        isShowRationaleDialog = true
                    }
                }
            ) {
                Text("申请权限")
            }
        }
    }

    if (isShowRationaleDialog) {
        RationaleDialog(
            onConfirm = {
                if (permissionState.status.shouldShowRationale) {
                    permissionState.launchPermissionRequest()
                } else {
                    context.startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    )
                    Toast.makeText(context, "前往设置开启定位权限", Toast.LENGTH_SHORT)
                        .show()
                }
                isShowRationaleDialog = false
            },
            onDismiss = {
                isShowRationaleDialog = false
            }
        )
    }
}

@Composable
private fun RationaleDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("权限需要") },
        text = { Text("需要位置权限来获取设备位置信息") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("授予权限")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
