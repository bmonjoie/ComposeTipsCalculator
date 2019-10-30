package be.xzan.composetipscalculator

import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.compose.unaryPlus
import androidx.ui.core.DrawShadow
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.DropdownPopup
import androidx.ui.foundation.PopupProperties
import androidx.ui.foundation.SimpleImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.DrawShape
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.themeColor
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview

@Composable
fun DropdownList(
    context: Any,
    isOpenedByDefault: Boolean = false,
    selectedView: @Composable() () -> Unit,
    dropdownListChildren: @Composable() () -> Unit
) {
    var isOpen by +stateFor(context) { isOpenedByDefault }
    RippledClickable(true, { isOpen = !isOpen }) {
        Container {
            Row(crossAxisAlignment = CrossAxisAlignment.Center) {
                selectedView()
                WidthSpacer(width = 4.dp)
                SimpleImage(
                    image = +imageResource(android.R.drawable.arrow_down_float),
                    tint = +themeColor { onBackground }
                )
            }
            if (isOpen) {
                DropdownPopup(
                    popupProperties = PopupProperties {
                        isOpen = false
                    }
                ) {
                    MaterialTheme {
                        DrawShape(shape = RectangleShape, color = +themeColor { background })
                        Container(width = 80.dp, height = 200.dp) {
                            VerticalScroller {
                                dropdownListChildren()
                            }
                        }
                        DrawShadow(shape = RectangleShape, elevation = 4.dp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ClosedDropdownListPreview() {
    MaterialTheme {
        DropdownList(context = Unit, selectedView = { Text(text = "Closed") }) {
            Column {
                Text(text = "1")
                Text(text = "2")
                Text(text = "3")
                Text(text = "4")
                Text(text = "5")
                Text(text = "6")
                Text(text = "7")
                Text(text = "8")
            }
        }
    }
}

@Preview
@Composable
fun OpenedDropdownListPreview() {
    MaterialTheme {
        DropdownList(
            context = Unit,
            isOpenedByDefault = true,
            selectedView = { Text(text = "Opened") }) {
            Column {
                Text(text = "1")
                Text(text = "2")
                Text(text = "3")
                Text(text = "4")
                Text(text = "5")
                Text(text = "6")
                Text(text = "7")
                Text(text = "8")
            }
        }
    }
}