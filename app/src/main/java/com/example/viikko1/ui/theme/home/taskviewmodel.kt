package com.example.viikko1.ui.theme.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viikko1.domain.*

class TaskViewModel : ViewModel() {

    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    init {
        tasks = mockTasks
    }

    fun addTask(task: Task) {
        tasks = tasks + task
    }

    fun toggleDone(id: Int) {
        tasks = tasks.map { task ->
            if (task.id == id) {
                task.copy(done = !task.done)
            } else {
                task
            }
        }
    }

    fun removeTask(id: Int) {
        tasks = tasks.filterNot { it.id == id }
    }

    fun filterByDone(done: Boolean) {
        tasks = tasks.filter { it.done == done }
    }

    fun sortByDueDate() {
        tasks = tasks.sortedBy { it.dueDate }
    }
    fun resetTasks() {
        tasks = mockTasks
    }

}
