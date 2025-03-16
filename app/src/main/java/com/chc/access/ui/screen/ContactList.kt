package com.chc.access.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chc.access.components.RequestPermissionView
import com.chc.access.utils.Contact
import com.chc.access.utils.getContacts

@Composable
fun ContactList(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val contacts = remember { mutableStateListOf<Contact>() }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.errorContainer)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        RequestPermissionView(
            permissionType = android.Manifest.permission.READ_CONTACTS,
            onPermissionChange = { isGranted ->
                if (isGranted) {
                    contacts.clear()
                    contacts.addAll(getContacts(context))
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer)
            ) {
                items(contacts) { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {}
                            .padding(16.dp)
                    ) {
                        Text(
                            text = contact.name,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = contact.phone,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
