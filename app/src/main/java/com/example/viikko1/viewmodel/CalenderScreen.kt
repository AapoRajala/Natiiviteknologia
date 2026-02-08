package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()

    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kalenteri") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // Ryhmittele tehtävät dueDate:n mukaan
        val tasksByDate = tasks.groupBy { it.dueDate }.toSortedMap()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tasksByDate.forEach { (date, dateTasks) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(dateTasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggleDone = { viewModel.toggleDone(task.id) },
                        onTaskClick = { selectedTask = task }
                    )
                }
            }
        }
    }

    // ✏EDIT TASK
    if (selectedTask != null) {
        EditTaskDialog(
            task = selectedTask!!,
            onDismiss = { selectedTask = null },
            onDelete = {
                viewModel.removeTask(it.id)
                selectedTask = null
            },
            onSave = {
                viewModel.updateTask(it)
                selectedTask = null
            }
        )
    }
}