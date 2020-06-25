package be.xzan.composetipscalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.key
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.drawBackground
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import be.xzan.composetipscalculator.entity.TipsEntry

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by state { false }
            MaterialTheme(
                colors = if (isDarkTheme) darkColorPalette() else lightColorPalette()
            ) {
                Column(
                    Modifier.drawBackground(MaterialTheme.colors.background).fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        if (isDarkTheme) "Light theme" else "Dark theme",
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(16.dp).clickable { isDarkTheme = !isDarkTheme })
                    val (list, setList) = state { listOf<TipsEntry>() }
                    Tips(list) { position, new ->
                        if (position >= list.size && new != null) {
                            setList(list + new)
                        } else if (new != null) {
                            setList(list.mapIndexed { i, it -> if (i == position) new else it })
                        } else {
                            setList(list.toMutableList().apply { removeAt(position) })
                        }
                    }
                }
            }
        }
    }
}

/**
 * @sample DefaultPreview
 */
@Composable
fun Tips(tips: List<TipsEntry>, onEntryChanged: (position: Int, new: TipsEntry?) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        (tips + null as TipsEntry?).forEach { entry ->
            val index = tips.indexOf(entry).let { index -> if (index < 0) tips.size else index }
            key(index) {
                Entry(index, entry, onEntryChanged)
            }
        }
        Text(
            text = "Total: %.02f".format(tips.sumByDouble { it.total.toDouble() }),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Tips(listOf(TipsEntry(100f), TipsEntry(200f, 4))) { _, _ -> }
    }
}
