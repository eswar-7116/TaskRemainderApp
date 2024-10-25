package com.eswar.taskremainder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eswar.taskremainder.data.TaskRepository

class MainViewModelFactory(private val taskRepository: TaskRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(taskRepository) as T
        }
        throw IllegalArgumentException("Illegal Model Class!")
    }
}