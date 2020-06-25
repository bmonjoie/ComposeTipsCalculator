package be.xzan.composetipscalculator

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.input.KeyboardType
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.Dimension
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import be.xzan.composetipscalculator.entity.TipsEntry

@Composable
fun Entry(
    entry: TipsEntry?,
    onEntryChanged: (old: TipsEntry?, new: TipsEntry?) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
    ) {
        val (amountConstraint, deleteConstraint) = createRefs()
        FilledTextField(
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
                        entry,
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
                onEntryChanged(entry, entry.copy(percent = percent))
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
                }.clickable { onEntryChanged(entry, null) },
                asset = imageResource(android.R.drawable.ic_delete)
            )
        }
    }
}

@Preview
@Composable
fun PreviewEntry() {
    MaterialTheme {
        Entry(TipsEntry()) { _, _ -> }
    }
}