package com.example.viikko1.domain

/**
 * Creates a new list containing all existing tasks plus one new task.
 */
fun addNewTask(tasklist: List<Task>): List<Task> {
    // Amateur style: using .size + 1 for ID
    val newId = tasklist.size + 1

    val newTask = Task(
        id = newId,
        title = "New task " + newId,
        description = "Test task Description " + newId,
        priority = 1,
        dueDate = "2026-01-14",
        done = false
    )

    // Manually creating a mutable list, adding, and returning
    val newList = tasklist.toMutableList()
    newList.add(newTask)
    return newList
}

fun filterByDone(tasklist: List<Task>): List<Task> {
    return tasklist.filter { it.done }
}

// Muokataan tehtävän tila False / trueen.
//  käytetään .map, tämä ei muokkaa ollenkaan alkuperäistä listaa ja tekee joka kerta "uuden" listan
fun toggleDone(list: List<Task>, id: Int): List<Task> {
    return list.map { task ->
        if (task.id == id) {
            task.copy(done = !task.done)
        } else {
            task
        }
    }
}

// järjestetään lista pvm mukaan
fun sortByDueDate(list: List<Task>): List<Task> {
    return list.sortedBy { it.dueDate }
}

