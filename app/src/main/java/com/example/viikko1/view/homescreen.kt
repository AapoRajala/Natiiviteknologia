package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskTitle by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text(
            text = "Minun Tehtävälista",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Uusi tehtävä") }
            )

            Button(onClick = {
                if (newTaskTitle.isNotBlank()) {
                    viewModel.addTask(
                        Task(
                            id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                            title = newTaskTitle,
                            description = "",
                            priority = 1,
                            dueDate = "2026-02-01",
                            done = false
                        )
                    )
                    newTaskTitle = ""
                }
            }) {
                Text("Lisää")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    onToggleDone = { viewModel.toggleDone(task.id) },
                    onTaskClick = { selectedTask = task }
                )
            }
        }
    }

    if (selectedTask != null) {
        DetailScreenDialog(
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

@Composable
fun TaskItem(
    task: Task,
    onToggleDone: () -> Unit,
    onTaskClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick() },
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggleDone() }
            )

            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenDialog(
    task: Task,
    onDismiss: () -> Unit,
    onDelete: (Task) -> Unit,
    onSave: (Task) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Muokkaa tehtävää") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Otsikko") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Kuvaus") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(task.copy(title = title, description = description)) }) {
                Text("Tallenna")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDelete(task) }) {
                Text("Poista", color = MaterialTheme.colorScheme.error)
            }
        }
    )
}