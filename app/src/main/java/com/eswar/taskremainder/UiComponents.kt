package com.eswar.taskremainder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eswar.taskremainder.data.Task

@Composable
fun TitleBox(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(.75f)
            .height(80.dp)
            .shadow(
                elevation = 30.dp,
                shape = RoundedCornerShape(15),
                spotColor = Color(0xFF1100FF)
            )
            .background(colorResource(R.color.blueTheme)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = colorResource(R.color.ic_launcher_background),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            style = TextStyle(
                shadow = Shadow(blurRadius = 20f, offset = Offset(x = 0f, y = 10f))
            )
        )
    }
}

fun getTasksForPreview(): List<Task> {
    return mutableListOf<Task>().also {
        for (i in 1..10) {
            it.add(Task(
                name = "Task $i",
                info = "Info $i",
                dueDateAndTime = "${10+i}/$i/${2020+i} 12:0 AM",
                isCompleted = if (i % 2 == 0) 1 else 0,
                creationDateAndTime = "${10+i}/$i/${2010+i} 12:0 AM"
            ))
        }
    }
}