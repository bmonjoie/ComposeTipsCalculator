package be.xzan.composetipscalculator

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.clickable
import androidx.ui.foundation.drawBackground
import androidx.ui.layout.padding
import androidx.ui.material.DropdownMenu
import androidx.ui.material.DropdownMenuItem
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.Position
import androidx.ui.unit.dp
import be.xzan.composetipscalculator.entity.TipsEntry

@Composable
fun PercentDropdown(
    modifier: Modifier,
    entry: TipsEntry,
    onPercentageSelected: (percentage: Int) -> Unit = {}
) {
    var opened by state { false }
    DropdownMenu(
        dropdownModifier = Modifier.padding(16.dp).drawBackground(color = MaterialTheme.colors.background),
        dropdownOffset = Position((-40).dp, 0.dp),
        toggle = { Text(text = "%d%%".format(entry.percent), color = MaterialTheme.colors.onBackground) },
        toggleModifier = modifier.clickable { opened = !opened },
        expanded = opened, onDismissRequest = { opened = false }) {
        VerticalScroller {
            (0..100).forEach {
                DropdownMenuItem(
                    onClick = {}) {
                    Text(
                        modifier = Modifier.clickable {
                            opened = false
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