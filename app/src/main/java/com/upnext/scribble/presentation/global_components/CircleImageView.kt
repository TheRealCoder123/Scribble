package com.upnext.scribble.presentation.global_components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.upnext.scribble.common.Helpers

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircleImageView(
    image: Any?,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp
) {
    GlideImage(
        model = image ?: Helpers.NO_IMAGE_URL,
        contentDescription = "Profile Image",
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        requestBuilderTransform = {
            it.optionalFitCenter()
        }
    )
}