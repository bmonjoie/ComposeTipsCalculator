package be.xzan.composetipscalculator

import androidx.compose.Composable
import androidx.ui.foundation.Clickable
import androidx.ui.material.ripple.Ripple

@Composable
fun RippledClickable(
    bounded: Boolean,
    onClick: (() -> Unit)? = null,
    consumeDownOnStart: Boolean = false,
    children: @Composable() () -> Unit
) {
    Ripple(bounded) {
        Clickable(onClick, consumeDownOnStart, children)
    }
}