package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview

fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = { showContent = !showContent }) {
               // contentColorFor(Color.Blue)
                TabLayout( "Commands", Color.Cyan, 12.sp).tab()
                AnimatedVisibility(showContent) {
                    // command menu form for sending a command
                }
            }
            Button(onClick = { showContent = !showContent }) {
                //contentColorFor(Color.Blue)
                TabLayout( "Connection", Color.Yellow, 12.sp).tab()
                AnimatedVisibility(showContent) {
                    // seeing if all connections are stable
                    // if not you can start them here or goto config
                }
            }
            Button(onClick = { showContent = !showContent }) {
                //contentColorFor(Color.Blue)
                TabLayout( "Database",Color.Green, 12.sp).tab()
                AnimatedVisibility(showContent) {
                    //See whats in the command database
                    // see whats saved to the tower(NOT THE MONGODB)
                }
            }
            Button(onClick = { showContent = !showContent }) {
                //contentColorFor(Color.Blue)
                TabLayout( "Config",Color.LightGray, 12.sp).tab()
                AnimatedVisibility(showContent) {
                    // change api values in the .env
                    // change app options
                }
            }
        }
    }
}

@Serializable
data class OptionsMenu(
    val name: String?
)

@Serializable
object Commands

@Serializable
data class ConnectionT(
    val name : String?,
    val pathr : String
)

@Serializable
object MainMenu