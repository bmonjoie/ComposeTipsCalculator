package be.xzan.composetipscalculator

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Position
import androidx.compose.ui.unit.dp
import be.xzan.composetipscalculator.entity.TipsEntry

@Composable
fun PercentDropdown(
    modifier: Modifier,
    entry: TipsEntry,
    onPercentageSelected: (percentage: Int) -> Unit = {}
) {
    val opened = remember { mutableStateOf(false) }
    DropdownMenu(
        dropdownModifier = Modifier.padding(16.dp).background(color = MaterialTheme.colors.background),
        dropdownOffset = Position((-40).dp, 0.dp),
        toggle = { Text(text = "%d%%".format(entry.percent), color = MaterialTheme.colors.onBackground) },
        toggleModifier = modifier.clickable { opened.value = !opened.value },
        expanded = opened.value, onDismissRequest = { opened.value = false }) {
        ScrollableColumn {
            (0..100).forEach {
                DropdownMenuItem(
                    onClick = {}) {
                    Text(
                        modifier = Modifier.clickable {
                            opened.value = false
                            onPercentageSelected(it)
                        },
                        color = MaterialTheme.colors.onBackground,
                        text = "%d%%".format(it),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}