package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable

@Serializable
object Commands

@Serializable
object ConnectionT

@Serializable
object MainMenu

@Serializable
object DataBased

@Serializable
object Config

//TODO: make the route pages
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    @Composable
    private fun CoroutineScope.OnNavHostReady(navController: NavHostController) {
        val navController: NavHostController = rememberNavController()
        fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()

            setContent {
                AppTheme { // define and route each to their own specific menus
                    NavHost(
                        navController,
                        startDestination = MainMenu,
                        modifier = TODO(),
                        contentAlignment = TODO(),
                        route = TODO(),
                        typeMap = TODO(),
                        enterTransition = TODO(),
                        exitTransition = TODO(),
                        popEnterTransition = TODO(),
                        popExitTransition = TODO(),
                        sizeTransform = TODO(),
                        builder = TODO()
                    ){}
                    LazyColumn(Modifier.fillMaxSize(),
                        verticalArrangement.spacedBy(16.dp),
                        PaddingValues(16.dp))
                    {
                        composable<MainMenu> {
                            val args = it.toRoute<MainMenu>()
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
                        composable<Commands> {
                            val args = it.toRoute<Commands>()
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
                                Button(onClick = { navController.navigate(ConnectionT) }) {
                                    TabLayout("Connection", Color.Yellow, 12.sp).tab()
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
                                Button(onClick = { navController.navigate(DataBased) }) {
                                    //contentColorFor(Color.Blue)
                                    MongoMenu()
                                    TabLayout("DataBased", Color.Green, 12.sp).tab()
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
                                Button(onClick = { navController.navigate(Config) }) {
                                    //contentColorFor(Color.Blue)
                                    TabLayout("Config", Color.LightGray, 12.sp).tab()
                                }
                            }
                        }
                    }
                }
            }
        }

        fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp()// || super.onSupportNavigateUp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
