package com.eswar.taskremainder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.eswar.taskremainder.data.Task
import com.eswar.taskremainder.data.TaskDatabase
import com.eswar.taskremainder.data.TaskRepository
import com.eswar.taskremainder.ui.theme.TaskRemainderTheme
import com.eswar.taskremainder.viewmodel.MainViewModel
import com.eswar.taskremainder.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskDao = TaskDatabase.getDatabase(this)?.taskDao()
        val taskRepository = taskDao?.let { TaskRepository(it) }
        val viewModelFactory = taskRepository?.let { MainViewModelFactory(it) }
        val viewModel = viewModelFactory?.let {
            ViewModelProvider(this, it)[MainViewModel::class.java]
        }

        enableEdgeToEdge()
        setContent {
            TaskRemainderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(this@MainActivity, viewModel, innerPadding)
                }
            }
        }
    }
}

@Composable
fun App(
    context: Context,
    viewModel: MainViewModel? = null,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    val blueTheme = colorResource(id = R.color.blueTheme)
    val whiteBackground = colorResource(id = R.color.ic_launcher_background)

    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(colorResource(id = R.color.ic_launcher_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            TitleBox(
                stringResource(R.string.app_name),
                Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(40.dp))

            val tasks = viewModel?.tasks?.observeAsState(emptyList<Task>())
            LazyColumn(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                items(tasks?.value ?: getTasksForPreview()) { task ->
                    TaskItem(task, 0) {

                    }
                    Spacer(Modifier.height(5.dp))
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(18.dp),
            shape = RoundedCornerShape(30),
            containerColor = blueTheme,
            onClick = {
                (context as Activity).also { activity ->
                    activity.startActivity(Intent(activity, AddTaskActivity::class.java))
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add a Task",
                tint = whiteBackground,
                modifier = Modifier.scale(1.5f)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    TaskRemainderTheme {
        App(LocalContext.current)
    }
}