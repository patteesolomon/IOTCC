package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

//TODO find a way to setup navigation using your libs
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Retrieve NavController from the NavHostFragment
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController

        // Set up the action bar for use with the NavController
//        setupActionBarWithNavController(navController)
        setContent {
            AppTheme { // define and route each to their own specific menus
                App()
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
