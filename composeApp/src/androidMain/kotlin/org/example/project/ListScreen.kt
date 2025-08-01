package org.example.project

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (Int, String) -> Unit
) {
    val drawables = listOf(
        ConnectionT,
        Config,
        Commands,
        DataBased
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(drawables) { index, resId ->
            val text = "Item$index"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(resId as Int, text) }
            ) {
                Image(
                    painter = painterResource(id = resId as Int),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .weight(1f) 
                        .sharedElement(
                            sharedContentState = TODO(),
                            animatedVisibilityScope = TODO(),
                            boundsTransform = TODO(),
                            placeHolderSize = TODO(),
                            renderInOverlayDuringTransition = TODO(),
                            zIndexInOverlay = TODO(),
                            clipInOverlayDuringTransition = TODO()
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    modifier = Modifier
                        .weight(1f)
                        .sharedElement(
                            sharedContentState = TODO(),
                            animatedVisibilityScope = TODO(),
                            boundsTransform = TODO(),
                            placeHolderSize = TODO(),
                            renderInOverlayDuringTransition = TODO(),
                            zIndexInOverlay = TODO(),
                            clipInOverlayDuringTransition = TODO()
                        )
                )
            }
        }
    }
}