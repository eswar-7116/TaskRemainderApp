package com.eswar.taskremainder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.eswar.taskremainder.data.Task
import com.eswar.taskremainder.data.TaskDatabase
import com.eswar.taskremainder.data.TaskRepository
import com.eswar.taskremainder.ui.theme.TaskRemainderTheme
import com.eswar.taskremainder.viewmodel.MainViewModel
import com.eswar.taskremainder.viewmodel.MainViewModelFactory

class AddTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskRemainderTheme {
                var name by remember { mutableStateOf("") }
                var info by remember { mutableStateOf("") }
                var dueDateTime by remember { mutableStateOf("") }
                lateinit var dateCreated: String
                val blueTheme = colorResource(R.color.blueTheme)
                val whiteBackground = colorResource(R.color.ic_launcher_background)

                val taskDao = TaskDatabase.getDatabase(this)?.taskDao()
                val taskRepository = taskDao?.let { TaskRepository(it) }
                val viewModelFactory = taskRepository?.let { MainViewModelFactory(it) }
                val viewModel = viewModelFactory?.let {
                    ViewModelProvider(this, it)[MainViewModel::class.java]
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(colorResource(id = R.color.ic_launcher_background))
                    ) {
                        Spacer(Modifier.height(16.dp))

                        TitleBox("Add a Task", Modifier.align(Alignment.CenterHorizontally))

                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Task Name TextField
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Enter Task Name") },
                                singleLine = true,
                                shape = RoundedCornerShape(20)
                            )

                            Spacer(Modifier.height(40.dp))

                            // Task Info TextField
                            OutlinedTextField(
                                value = info,
                                onValueChange = { info = it },
                                label = { Text("Enter Task Info") },
                                shape = RoundedCornerShape(20)
                            )

                            Spacer(Modifier.height(45.dp))

                            // Task Due DateTime TextField
                            OutlinedTextField(value = dueDateTime,
                                onValueChange = {},
                                placeholder = { Text("Enter Task Due Date and Time") },
                                readOnly = true,
                                singleLine = true,
                                shape = RoundedCornerShape(20),
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }.also { interactionSource ->
                                    LaunchedEffect(interactionSource) {
                                        interactionSource.interactions.collect { interaction ->
                                            checkInteraction(
                                                this@AddTaskActivity,
                                                interaction
                                            ) { dates ->
                                                dueDateTime = dates.first
                                                dateCreated = dates.second
                                            }
                                        }
                                    }
                                }
                            )

                            Spacer(Modifier.height(75.dp))

                            Button(
                                onClick = {
                                    if (name.isBlank() || info.isBlank() || dueDateTime.isBlank()) {
                                        Toast.makeText(
                                            this@AddTaskActivity,
                                            "Fill all the details!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        val createdTask = Task(
                                            name = name.trim(),
                                            info = info.trim(),
                                            dueDateAndTime = dueDateTime,
                                            isCompleted = 0,
                                            creationDateAndTime = dateCreated
                                        )
                                        viewModel?.updateTask(createdTask)
                                        finish()
                                    }
                                }, colors = ButtonColors(
                                    blueTheme, whiteBackground,       // Enabled Button Colors
                                    whiteBackground, whiteBackground  // Disabled Button Colors
                                ), shape = RoundedCornerShape(30), modifier = Modifier.scale(1.4f)
                            ) { Text("Add Task") }
                        }
                    }
                }
            }
        }
    }
}