package com.crosie.crosie_compose_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.crosie.crosie_compose_ui.ui.theme.CrosiecomposeuiTheme
import com.crosie.flexible_drawer.FlexibleDrawer
import com.crosie.flexible_drawer.rememberDrawerState2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrosiecomposeuiTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun navDrawer() {

    CrosiecomposeuiTheme() {
        val drawerState = rememberDrawerState2(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        FlexibleDrawer(drawerState = drawerState,
            drawerContent = {
                //whatever is inside the drawer
                Column {
                    Text(text = "Helllo!!", color = Color.Black)
                    Text(text = "Helllo!!", color = Color.Black)
                    Text(text = "Helllo!!", color = Color.Black)
                    Text(text = "Helllo!!", color = Color.Black)
                }
            },
            pushAside = true,
            drawerPortion = 0.25f
        ) {
            //stuff outside drawer
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                Text(text = "Helllo!!", color = Color.Black)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CrosiecomposeuiTheme {
        Greeting("Android")
    }
}