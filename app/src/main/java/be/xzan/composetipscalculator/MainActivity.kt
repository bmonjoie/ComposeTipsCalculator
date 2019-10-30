package be.xzan.composetipscalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.Pivotal
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.SimpleImage
import androidx.ui.graphics.Color
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.MaterialColors
import androidx.ui.material.MaterialTheme
import androidx.ui.material.themeTextStyle
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import be.xzan.composetipscalculator.entity.TipsEntry

@Model
class Data(private var list: List<TipsEntry> = emptyList()) {

    val tips: List<TipsEntry>
        get() = list

    fun onChange(position: Int, new: TipsEntry?) {
        if (position >= tips.size && new != null) {
            list += new
        } else if (new != null) {
            list = list.mapIndexed { index, it -> if (index == position) new else it }
        } else if (position < tips.size) {
            list = list.toMutableList().apply { removeAt(position) }
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = MaterialColors(secondary = Color(0xFFE91E63))
            ) {
                Tips(Data())
            }
        }
    }
}

@Composable
fun Tips(data: Data) {
    Column(modifier = Spacing(16.dp)) {
        data.tips.forEachIndexed { i, it ->
            Entry(i, it, data::onChange)
        }
        Entry(data.tips.size, null, data::onChange)
        Text(
            text = "Total: %.02f".format(data.tips.sumByDouble { it.total.toDouble() }),
            style = (+themeTextStyle { body1 }).copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun Entry(
    @Pivotal position: Int,
    entry: TipsEntry?,
    onEntryChanged: (position: Int, new: TipsEntry?) -> Unit
) {
    FlexRow(modifier = Spacing(bottom = 8.dp), crossAxisAlignment = CrossAxisAlignment.Center) {
        expanded(3f) {
            HintedTextField(
                hint = "Enter amount",
                value = entry?.amount?.let { "%.02f".format(it) } ?: "",
                keyboardType = KeyboardType.Number,
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
        }
        entry?.let {
            expanded(2f) {
                PercentDropdown(entry) { percent ->
                    onEntryChanged(position, entry.copy(percent = percent))
                }
            }
            expanded(2f) {
                Text(text = "%.02f".format(entry.tips))
            }
            flexible(1f) {
                Clickable({ onEntryChanged(position, null) }) {
                    SimpleImage(+imageResource(android.R.drawable.ic_delete))
                }
            }
        } ?: flexible(5f) {
            FixedSpacer(width = 0.dp, height = 0.dp)
        }
    }
}

@Composable
fun PercentDropdown(
    entry: TipsEntry,
    onPercentageSelected: (percentage: Int) -> Unit = {}
) {
    DropdownList(entry, selectedView = {
        Text(text = "%d%%".format(entry.percent))
    }) {
        Column(modifier = Spacing(16.dp)) {
            (0..100).forEach {
                RippledClickable(false, { onPercentageSelected(it) }) {
                    Text(
                        text = "%d%%".format(it),
                        style = (+themeTextStyle { body1 })
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Tips(Data(listOf(TipsEntry(100f), TipsEntry(200f, 4))))
    }
}
