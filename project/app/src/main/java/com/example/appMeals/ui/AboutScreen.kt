package com.example.appMeals.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.appMeals.R
/**
 * Composable function that displays an "About" screen.
 * This screen includes personal information such as name, first name, identifier, and group.
 *
 * @Composable
 * @AboutScreen
 * Displays a centered column with personal information text elements.
 */

@Composable
fun AboutScreen() {
    val louisa = FontFamily(
        Font(R.font.louisa)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text="Family Name: Kaoun" , fontFamily= louisa)
        Text(text="Name: Aya", fontFamily= louisa )
        Text(text="Id: 58414", fontFamily= louisa)
        Text(text="Group: F12", fontFamily= louisa)
    }
}