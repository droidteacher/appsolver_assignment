package hu.zsoltkiss.moviefacts.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import hu.zsoltkiss.moviefacts.ui.theme.warningDialogButton
import hu.zsoltkiss.moviefacts.ui.theme.warningDialogText
import hu.zsoltkiss.moviefacts.ui.theme.warningDialogTitle

@Composable
fun NoNetworkDialog(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        containerColor = Color.Gray,
        icon = {
            Icon(Icons.Default.Warning, contentDescription = "Warning icon", tint = Color.Yellow)
        },
        title = {
            Text(
                text = "Connection lost",
                style = warningDialogTitle
            )
        },
        text = {
            Text(
                text = "Device is offline. You are only able to display data from local cache. You won't be able to fetch movie details until connection is restored.",
                style = warningDialogText
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("OK", style = warningDialogButton)
            }
        }
    )
}