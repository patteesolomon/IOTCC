package org.example.project


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class TabLayout(tabName: String = "", fontCt: Color, fontSt: TextUnit) {
    private var tName: String = tabName;
    private var fontC: Color = fontCt;
    private var fontS: TextUnit = fontSt;
    @Composable
    fun tab(){
        Column(
            modifier = Modifier.size(87.dp, 23.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = tName,
                color = fontC,
                fontSize = fontS
            )
        }
    }
}