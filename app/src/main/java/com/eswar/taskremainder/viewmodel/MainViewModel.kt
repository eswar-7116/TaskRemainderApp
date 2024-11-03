package com.eswar.taskremainder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eswar.taskremainder.data.Task
import com.eswar.taskremainder.data.TaskRepository
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository): ViewModel() {
    var tasks: LiveData<List<Task>> = taskRepository.getAllTasks()

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }
}