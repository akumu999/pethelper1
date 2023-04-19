package com.example.pethelper.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Screens.Pets.AddItemDialog
import com.example.pethelper.Screens.Pets.Item


@Composable
fun PetsScreen(controller: NavController) {
    val items = remember { mutableStateListOf<Item>() }
    var isDialogOpen by remember { mutableStateOf(false) }

    Column {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(text = "Мои питомцы",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 130.dp, top = 10.dp)
            )
            FloatingActionButton(
                onClick = { isDialogOpen = true },
                backgroundColor = Color.Blue,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(42.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }
        }



        LazyColumn {
            items(items) { item ->
                Text(item.name, fontWeight = FontWeight.Bold, modifier = Modifier.clickable{ isDialogOpen = true })
                Text(item.type, modifier = Modifier.clickable{ isDialogOpen = true })
                Divider()
            }
        }

        if (isDialogOpen) {
            AddItemDialog(
                onAddItem = { type, breed, name, age, gender, description ->
                    items.add(Item(type, breed, name, age, gender, description))
                },
                onDismiss = { isDialogOpen = false }
            )
        }
    }
}