package com.renan.portifolio.to_dolist.util.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun LoadingIndicator(isLoading: Boolean, error: String?, onRetry: () -> Unit){
    AnimatedVisibility(
        visible = isLoading,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ){
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center){
            if (isLoading){
                CircularProgressIndicator()
            }
            if (error != null){
                RetryContent(
                    error= error,
                    onRetry= onRetry
                )
            }
        }
    }
}