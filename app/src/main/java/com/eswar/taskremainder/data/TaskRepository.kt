package com.eswar.taskremainder.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val taskDao: TaskDAO) {
    private var tasks: List<Task> = emptyList()

    suspend fun getAllTasks(): List<Task> = withContext(Dispatchers.IO) { taskDao.getAllTasks() }

    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) { taskDao.updateTask(task) }
}