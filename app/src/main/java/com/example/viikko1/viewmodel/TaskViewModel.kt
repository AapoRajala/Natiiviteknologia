package com.example.viikko1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikko1.model.Task
import com.example.viikko1.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow(mockTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(task: Task) {
        _tasks.update { it + task }
    }

    fun toggleDone(id: Int) {
        _tasks.update { list ->
            list.map { task ->
                if (task.id == id) {
                    task.copy(done = !task.done)
                } else {
                    task
                }
            }
        }
    }

    fun removeTask(id: Int) {
        _tasks.update { tasks ->
            tasks.filterNot { it.id == id }
        }
    }

    fun updateTask(updatedTask: Task) {
        _tasks.update { tasks ->
            tasks.map { task ->
                if (task.id == updatedTask.id) updatedTask else task
            }
        }
    }
}