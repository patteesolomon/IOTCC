package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineScope


//TODO: make the route pages
@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = MainMenu
            ) {
                composable<MainMenu> {
                    val args = it.toRoute<MainMenu>()
                    Column(
                        modifier = Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { navController.navigate(Commands) }) {
                            TabLayout("Commands", Color.Cyan, 12.sp).tab()
                        }
                        Button(onClick = { navController.navigate(ConnectionT) }) {
                            TabLayout("Connection", Color.Yellow, 12.sp).tab()
                        }
                        Button(onClick = { navController.navigate(DataBased) }) {
                            //contentColorFor(Color.Blue)
//                      MongoMenu()
                            TabLayout("DataBased", Color.Green, 12.sp).tab()
                        }
                        Button(onClick = { navController.navigate(Config) }) {
                            //contentColorFor(Color.Blue)
                            TabLayout("Config", Color.LightGray, 12.sp).tab()
                        }
                    }
                }
                composable<Commands> {
                    val args = it.toRoute<Commands>()
                    Column(
                        modifier = Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { navController.navigate(MainMenu) }) {
                            TabLayout("MainMenu", Color.Red, 12.sp).tab()
                        }

                    }
                }
                composable<ConnectionT> {
                    val args = it.toRoute<ConnectionT>()
                    Column(
                        modifier = Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box {
                            Text("You are a legend among your men.")
                        }
                        Button(onClick = { navController.navigate(MainMenu) }) {
                            TabLayout("MainMenu", Color.Red, 12.sp).tab()
                        }
                    }
                }
                composable<DataBased> {
                    val args = it.toRoute<DataBased>()
                    Column(
                        modifier = Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { navController.navigate(MainMenu) }) {
                            TabLayout("MainMenu", Color.Red, 12.sp).tab()
                        }
                    }
                }
                composable<Config> {
                    val args = it.toRoute<Config>()
                    Column(
                        modifier = Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { navController.navigate(MainMenu) }) {
                            TabLayout("MainMenu", Color.Red, 12.sp).tab()
                        }
                    }
                }
            }
            LaunchedEffect(key1 = navController) {
                onNavHostReady(navController)
            }
        }
    }

    private fun CoroutineScope.onNavHostReady(controller: NavHostController) {
        controller.run { navigate(MainMenu) }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
