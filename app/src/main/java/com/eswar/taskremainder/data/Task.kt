package com.eswar.taskremainder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Task")
data class Task(
    @PrimaryKey(true) val id: Int = 0,
    val name: String,
    val info: String,
    val dueDateAndTime: String,
    val isCompleted: Int,
    val creationDateAndTime: String
) {
    override fun toString(): String {
        return "Task($name, $info, $dueDateAndTime, $isCompleted, $creationDateAndTime)"
    }
}