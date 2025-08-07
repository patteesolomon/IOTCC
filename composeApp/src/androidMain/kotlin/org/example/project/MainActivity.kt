package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.project.iotcc.MongoDBConnection

//TODO: make the route pages
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Retrieve NavController from the NavHostFragment
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Set up the action bar for use with the NavController
//        setupActionBarWithNavController(navController)
        setContent {
            AppTheme { // define and route each to their own specific menus
                val navController = rememberNavController()
                var showContent by remember { mutableStateOf(false) }
                NavHost(navController, startDestination = MainMenu)
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
                            // contentColorFor(Color.Blue)
                            TabLayout("MainMenu", Color.Red, 12.sp).tab()
                            AnimatedVisibility(showContent) {
                                // command menu form for sending a command
                            }
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
                            // contentColorFor(Color.Blue)
                            TabLayout("Commands", Color.Cyan, 12.sp).tab()
                            AnimatedVisibility(showContent) {
                                // command menu form for sending a command
                            }
                        }
                    }
                    }
                    composable<Connections> {
                        val args = it.toRoute<Connections>()
                        Column(
                            modifier = Modifier
                                .safeContentPadding()
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        Button(onClick = { navController.navigate(Connections)}) {
                            //contentColorFor(Color.Blue)
                            TabLayout("Connection", Color.Yellow, 12.sp).tab()
                            AnimatedVisibility(showContent) {
                                // seeing if all connections are stable
                                // if not you can start them here or goto config
                                val cmg= MongoDBConnection().main().toString()
                                Column {
                                    Text("THIS : $cmg")
                                }
                            }
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
                                TabLayout("DataBased", Color.Green, 12.sp).tab()
                                AnimatedVisibility(showContent) {
                                    //See whats in the command database
                                    // see whats saved to the tower(NOT THE MONGODB)
                                }
                            }
                        }
                    }
                    composable<Config>{
                        val args = it.toRoute<Config>()
                        Column(
                            modifier = Modifier
                                .safeContentPadding()
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = { showContent = !showContent }) {
                                //contentColorFor(Color.Blue)
                                TabLayout("Config", Color.LightGray, 12.sp).tab()
                                AnimatedVisibility(showContent) {
                                    // change api values in the .env
                                    // change app options
                                }
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

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
@Serializable
data class OptionsMenu(
    val name: String?
)

@Serializable
object Commands

@Serializable
object Connections

@Serializable
object MainMenu

    @Serializable
    object  DataBased

@Serializable
object Config