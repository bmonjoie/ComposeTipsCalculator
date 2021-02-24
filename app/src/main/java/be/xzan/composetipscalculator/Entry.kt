package be.xzan.composetipscalculator


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import be.xzan.composetipscalculator.entity.TipsEntry

@Composable
fun Entry(
    position: Int,
    entry: TipsEntry?,
    modifier: Modifier = Modifier,
    onEntryChanged: (position: Int, new: TipsEntry?) -> Unit
) {
    ConstraintLayout(
        modifier = modifier.padding(bottom = 8.dp).fillMaxWidth()
    ) {
        val (amountConstraint, deleteConstraint) = createRefs()
        TextField(
            value = entry?.amount?.let { "%.02f".format(it) }.orEmpty(),
            onValueChange = { string: String ->
                try {
                    val value = if (string.isBlank()) null else string.replace(
                        ",",
                        "."
                    ).toFloat().let { if (it <= 0f) null else it }
                    onEntryChanged(
                        position,
                        value?.let { entry?.copy(amount = value) ?: TipsEntry(value) })
                } catch (e: NumberFormatException) {
                    // ignored
                }
            },
            modifier = Modifier.constrainAs(amountConstraint) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
                width = Dimension.value(200.dp)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            label = { Text("Amount") },
        )

        entry?.let {
            PercentDropdown(Modifier.constrainAs(createRef()) {
                start.linkTo(amountConstraint.end, margin = 8.dp)
                centerVerticallyTo(parent)
            }, entry) { percent ->
                onEntryChanged(position, entry.copy(percent = percent))
            }
            Text(
                modifier = Modifier.constrainAs(createRef()) {
                    end.linkTo(deleteConstraint.start, margin = 8.dp)
                    centerVerticallyTo(parent)
                },
                text = "%.02f".format(entry.tips),
                color = MaterialTheme.colors.onBackground
            )
            Image(
                painter = painterResource(android.R.drawable.ic_delete),
                contentDescription = "Remove line",
                modifier = Modifier.constrainAs(deleteConstraint) {
                    end.linkTo(parent.end, margin = 8.dp)
                    centerVerticallyTo(parent)
                }.clickable { onEntryChanged(position, null) },
            )
        }
    }
}

@Preview
@Composable
fun PreviewEntry() {
    MaterialTheme {
        Entry(0, TipsEntry()) { _, _ -> }
    }
}