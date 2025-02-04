package hu.zsoltkiss.moviefacts.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hu.zsoltkiss.moviefacts.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val fonts = FontFamily(
    Font(R.font.exo_regular, FontWeight.Normal),
    Font(R.font.sfprotext_regular, FontWeight.Normal),
)

val noResults: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

val appBarHeader: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

val appBarTitleStyle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.irish_grover_regular)),
            fontSize = 28.sp
        )
    }


val cardTitleStyle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.chicle_regular)),
            fontSize = 24.sp,
            color = Color(0xFF78909C)
        )}

val warningDialogTitle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.exo_regular)),
            fontSize = 24.sp,
            color = Color.Black
        )}

val warningDialogText: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.exo_regular)),
            fontSize = 18.sp,
            color = Color(0xFF37474F)
        )}


val warningDialogButton: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.exo_regular)),
            fontSize = 18.sp,
            color = Color.Blue
        )}