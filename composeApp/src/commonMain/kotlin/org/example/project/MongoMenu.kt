package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints.Companion.fixed
import androidx.compose.ui.unit.Dp

private val gegg: Color
    get() {
        return Color(3225097)
    }
@Composable
public fun MongoMenu() {
    Layout({
        Box(Modifier.layoutId("border")) { border() }
        Box(Modifier.layoutId("border2")) { border() }
    }) { measurables, constraints ->
        val placeables =
            measurables.map { measurable ->
                when (measurable.layoutId) {
                    "border" -> measurable.measure(fixed(100, 100))
                    "border2" -> measurable.measure(constraints)
                    else -> error("Unexpected tag")
                }
            }
    layout(80, 240) { placeables.forEach {it.placeRelative(0,0)}}
    }
}

private fun BoxScope.border() {
    BorderStroke(
        width = Dp(5F),
        color = gegg
    )
}