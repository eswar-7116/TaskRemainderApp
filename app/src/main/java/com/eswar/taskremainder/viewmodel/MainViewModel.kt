package com.eswar.taskremainder.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eswar.taskremainder.data.Task
import com.eswar.taskremainder.data.TaskRepository
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository): ViewModel() {
    val tasks: MutableState<List<Task>> = mutableStateOf(emptyList<Task>())
    val loadingTasks = mutableStateOf(true)

    init {
        viewModelScope.launch {
            tasks.value = taskRepository.getAllTasks()
            loadingTasks.value = false
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch { taskRepository.updateTask(task) }
}