package com.example.pethelper.Screens.Pets


import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddItemDialog(
    onAddItem: (String, String, String, String, String, String) -> Unit,
    onDismiss: () -> Unit,
) {
    var type by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }


    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Добавить питомца",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Тип") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Порода") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Возраст") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Пол") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = {
                        onAddItem(type, breed, name, age, gender, description)
                        onDismiss()
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}