package com.example.myfirstwork.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstwork.ui.theme.ButtonBg

@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    textColor: Color,
    backgroundColor: Color = ButtonBg,
    onClick: () -> Unit
){
    Surface(onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(18.dp))
            Text(text = text, fontSize = 14.sp, color = textColor)
        }
    }
}