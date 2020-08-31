package be.xzan.composetipscalculator


import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import be.xzan.composetipscalculator.entity.TipsEntry

@Composable
fun Entry(
    position: Int,
    entry: TipsEntry?,
    onEntryChanged: (position: Int, new: TipsEntry?) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
    ) {
        val (amountConstraint, deleteConstraint) = createRefs()
        TextField(
            modifier = Modifier.constrainAs(amountConstraint) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
                width = Dimension.value(200.dp)
            },
            value = entry?.amount?.let { "%.02f".format(it) } ?: "",
            keyboardType = KeyboardType.Number,
            label = { Text("Amount") },
            onValueChange = { string ->
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
            })

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
                modifier = Modifier.constrainAs(deleteConstraint) {
                    end.linkTo(parent.end, margin = 8.dp)
                    centerVerticallyTo(parent)
                }.clickable { onEntryChanged(position, null) },
                asset = imageResource(android.R.drawable.ic_delete)
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