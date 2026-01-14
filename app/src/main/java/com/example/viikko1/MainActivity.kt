package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.addNewTask
import com.example.viikko1.domain.filterByDone
import com.example.viikko1.domain.mockTasks
import com.example.viikko1.ui.theme.Viikko1Theme
import androidx.compose.foundation.clickable
import com.example.viikko1.domain.toggleDone
import com.example.viikko1.domain.sortByDueDate


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikko1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var taskList by remember { mutableStateOf(value = mockTasks) }
    var showOnlyDone by remember { mutableStateOf(false) }


    Column(modifier = modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            // Lisätään Uusi Tehtävä
            Button(onClick = {
                taskList = addNewTask(taskList)
            }) {
                Text("New Task")
            }


            Spacer(modifier = Modifier.width(8.dp))

            // Tehdään Nappi jolla pystytään näyttää vain tehdyt tehtävät
            Button(onClick = {
                showOnlyDone = !showOnlyDone
            }) {
                Text(if (showOnlyDone) "All" else "Only Done")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                taskList = sortByDueDate(taskList)
            }) {
                Text("By date")
            }


        }

        Spacer(modifier = Modifier.height(16.dp))

        //  Luodaan lista joka näyttää vain tehdyt tehtävät. "ShowOnlyDone" näyttää vain tehdyt tehtävät
        // Nämä haetaan KotlinFunction tiedostosta, jos ei ole näytettänää niin tämä näyttää vain koko lista näkymän
        val listToDisplay = if (showOnlyDone) {
            filterByDone(taskList)
        } else {
            taskList
        }

        listToDisplay.forEach { task ->
            Text(text = "${task.id}: ${task.title} (${task.done}) DATE : ${task.dueDate}",
              modifier = Modifier
                .clickable {
                    taskList = toggleDone(taskList, task.id)
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Viikko1Theme {
        Greeting("Android")
    }
}
