package hu.zsoltkiss.moviefacts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import hu.zsoltkiss.moviefacts.ui.theme.appBarContainerColor
import hu.zsoltkiss.moviefacts.ui.theme.appBarTitleStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MFAppBar(
    title: String,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = appBarTitleStyle,
                )
            }
        },
        modifier = Modifier.testTag("MFAppBar"),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = appBarContainerColor,
        )

    )
}