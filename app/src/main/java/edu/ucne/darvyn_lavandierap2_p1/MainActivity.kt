package edu.ucne.darvyn_lavadierap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import edu.ucne.darvyn_lavandierap2_p1.presentation.huacal.navigation.HuacalNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    HuacalNavHost(navController = navController)
                }
            }
        }
    }
}
