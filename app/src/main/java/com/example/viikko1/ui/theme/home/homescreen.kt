package com.example.viikko1.ui.theme.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.domain.Task

@Composable
fun HomeScreen(
    viewModel: TaskViewModel = viewModel()
) {
    val tasks = viewModel.tasks
    var newTaskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "My tasks",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // New Task Input
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("New task") },
                singleLine = true, // Ensures it doesn't grow or show multi-line popups
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    autoCorrect = false
                )
            )

            Button(
                onClick = {
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
                },
                modifier = Modifier.height(56.dp) // Match TextField height
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filters and Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.filterByDone(true) }) {
                Text("Done")
            }

            Button(onClick = { viewModel.sortByDueDate() }) {
                Text("Sort")
            }

            Button(onClick = { viewModel.resetTasks() }) {
                Text("Reset")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = {
                                viewModel.toggleDone(task.id)
                            }
                        )

                        Text(
                            text = task.title,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        )

                        IconButton(onClick = {
                            viewModel.removeTask(task.id)
                        }) {
                            Text("❌", fontSize = 12.sp) // Simple delete icon
                        }
                    }
                }
            }
        }
    }
}
