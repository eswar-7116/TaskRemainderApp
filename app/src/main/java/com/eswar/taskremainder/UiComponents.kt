package com.eswar.taskremainder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.PressInteraction
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

fun checkInteraction(
    context: Context,
    interaction: Interaction,
    onDateSelected: (Pair<String, String>) -> Unit
) {
    if (interaction is PressInteraction.Release) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)
        val milliSeconds = calendar.get(Calendar.MILLISECOND)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                TimePickerDialog(
                    context,
                    { _, selectedHour24, selectedMinutes ->
                        val amPm: String?
                        val selectedHour12: Int?

                        if (selectedHour24 == 0) {
                            amPm = "AM"
                            selectedHour12 = 12
                        } else if (selectedHour24 > 12) {
                            amPm = "PM"
                            selectedHour12 =
                                selectedHour24 - 12
                        } else {
                            amPm =
                                if (selectedHour24 == 12) "PM"
                                else "AM"
                            selectedHour12 = selectedHour24
                        }

                        val dueDateTime = "$selectedDay/${selectedMonth + 1}/$selectedYear " +
                                          "$selectedHour12:$selectedMinutes $amPm"
                        val dateCreated = "$day/${month + 1}/$year " +
                                          "$hour:$minutes:$seconds:$milliSeconds"

                        onDateSelected(
                            Pair(dueDateTime, dateCreated)
                        )
                    },
                    hour,
                    minutes,
                    false
                ).show()
            },
            day,
            month,
            year
        ).apply {
            datePicker.minDate = calendar.timeInMillis
            show()
        }
    }
}

fun getTasksForPreview(): List<Task> {
    return mutableListOf<Task>().also {
        for (i in 1..10) {
            it.add(
                Task(
                    name = "Task $i",
                    info = "Info $i",
                    dueDateAndTime = "${10 + i}/$i/${2020 + i} 12:0 AM",
                    isCompleted = if (i % 2 == 0) 1 else 0,
                    creationDateAndTime = "${10 + i}/$i/${2010 + i} 12:0 AM"
                )
            )
        }
    }
}