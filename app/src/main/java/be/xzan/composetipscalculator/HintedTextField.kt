package be.xzan.composetipscalculator

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.engine.geometry.Offset
import androidx.ui.graphics.Color
import androidx.ui.graphics.Paint
import androidx.ui.input.EditorStyle
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.VisualTransformation
import androidx.ui.layout.Stack
import androidx.ui.material.MaterialColors
import androidx.ui.material.MaterialTheme
import androidx.ui.material.themeColor
import androidx.ui.material.themeTextStyle
import androidx.ui.tooling.preview.Preview

@Composable
fun HintedTextField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    editorStyle: EditorStyle? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified,
    onFocus: () -> Unit = {},
    onBlur: () -> Unit = {},
    focusIdentifier: String? = null,
    onImeActionPerformed: (ImeAction) -> Unit = {},
    visualTransformation: VisualTransformation? = null,
    isFocusedByDefault: Boolean = false
) {
    var isFocused by +state { isFocusedByDefault }
    val secondaryColor = +themeColor { secondary }
    Stack {
        aligned(Alignment.TopLeft) {
            TextField(
                value,
                onValueChange,
                editorStyle,
                keyboardType,
                imeAction,
                { isFocused = true; onFocus() },
                { isFocused = false; onBlur() },
                focusIdentifier,
                onImeActionPerformed,
                visualTransformation
            )
            Draw { canvas, parentSize ->
                canvas.drawLine(
                    Offset(0f, parentSize.height.value),
                    Offset(parentSize.width.value, parentSize.height.value),
                    Paint().apply {
                        strokeWidth = 2.dp.toPx().value
                        color = if (isFocused) secondaryColor else Color.LightGray
                    })
            }
        }
        if (value.isBlank()) {
            aligned(Alignment.TopLeft) {
                Text(
                    hint,
                    style = (+themeTextStyle { body1 }).copy(color = Color.LightGray)
                )
            }
        }
    }
}

@Preview
@Composable
fun HintedTextFieldPreview() {
    MaterialTheme {
        HintedTextField(hint = "Enter some text", value = "")
    }
}

@Preview
@Composable
fun FocusedHintedTextFieldPreview() {
    MaterialTheme(
        colors = MaterialColors(secondary = Color(0xFFFF0000))
    ) {
        HintedTextField(hint = "Enter some text", value = "", isFocusedByDefault = true)
    }
}