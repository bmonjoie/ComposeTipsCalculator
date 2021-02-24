package be.xzan.composetipscalculator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.xzan.composetipscalculator.entity.TipsEntry

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            MaterialTheme(
                colors = if (isDarkTheme) darkColors() else lightColors()
            ) {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        if (isDarkTheme) "Light theme" else "Dark theme",
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { isDarkTheme = !isDarkTheme })
                    var list by remember { mutableStateOf(listOf<TipsEntry>()) }
                    Tips(list) { position, new ->
                        if (position >= list.size && new != null) {
                            list = list + new
                        } else if (new != null) {
                            list = list.mapIndexed { i, it -> if (i == position) new else it }
                        } else if (position < list.size) {
                            list = list.toMutableList().apply { removeAt(position) }
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
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(tips.size + 2) { index ->
            when {
                index <= tips.size -> key(index) {
                    Entry(
                        index,
                        tips.getOrNull(index),
                        onEntryChanged = onEntryChanged
                    )
                }
                else -> Text(
                    text = "Total: %.02f".format(tips.sumByDouble { it.total.toDouble() }),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Tips(listOf(TipsEntry(100f), TipsEntry(200f, 4))) { _, _ -> }
    }
}
