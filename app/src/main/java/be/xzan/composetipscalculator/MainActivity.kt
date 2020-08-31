package be.xzan.composetipscalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.state
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import be.xzan.composetipscalculator.entity.TipsEntry

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = remember { mutableStateOf(false) }
            MaterialTheme(
                colors = if (isDarkTheme.value) darkColors() else lightColors()
            ) {
                Column(
                    Modifier.background(MaterialTheme.colors.background).fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        if (isDarkTheme.value) "Light theme" else "Dark theme",
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(16.dp)
                            .clickable { isDarkTheme.value = !isDarkTheme.value })
                    val (list, setList) = state { listOf<TipsEntry>() }
                    Tips(list) { position, new ->
                        if (position >= list.size && new != null) {
                            setList(list + new)
                        } else if (new != null) {
                            setList(list.mapIndexed { i, it -> if (i == position) new else it })
                        } else if (position < list.size) {
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
    LazyColumnFor((0..tips.size + 1).toList(), modifier = Modifier.padding(16.dp)) { index ->
        when {
            index <= tips.size -> key(index) { Entry(index, tips.getOrNull(index), onEntryChanged) }
            else -> Text(
                text = "Total: %.02f".format(tips.sumByDouble { it.total.toDouble() }),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
            )
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
