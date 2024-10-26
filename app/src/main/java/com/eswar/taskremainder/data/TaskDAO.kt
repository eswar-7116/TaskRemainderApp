package com.eswar.taskremainder.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TaskDAO {

    @Query("SELECT * FROM Task")
    fun getAllTasks(): LiveData<List<Task>>

    @Upsert
    suspend fun updateTask(task: Task)
    /*
    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task ORDER BY name ASC")
    suspend fun getTasksByName(): List<Task>

    @Query("SELECT * FROM Task WHERE name LIKE :name")
    suspend fun getTasksWithName(name: String): List<Task>
    */
}