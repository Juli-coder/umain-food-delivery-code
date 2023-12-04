package com.umain.codetest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.umain.codetest.R


val helveticaFontFamily = FontFamily(
    Font(R.font.helvetica_regular, FontWeight.Normal),
    Font(R.font.helvetica_bold, FontWeight.Bold),
)

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium),
)

val interFontFamily = FontFamily(
    Font(R.font.inter_medium, FontWeight.Medium),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = helveticaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 16.sp,
        color = TextColor
    ),
    titleMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = TextColor
    ),
    titleSmall = TextStyle(
        fontFamily = helveticaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = SubtitlesColor
    ),
    displaySmall = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 12.1.sp,
        color = TextColor
    ),
    headlineLarge = TextStyle(
        fontFamily = helveticaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 16.sp,
        color = TextColor
    ),
    headlineMedium = TextStyle(
        fontFamily = helveticaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        color = TextColor
    ),
)