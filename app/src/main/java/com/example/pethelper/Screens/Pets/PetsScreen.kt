package com.example.pethelper.Screens

import Pet
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

@Composable
fun PetsScreen(controller: NavController) {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    // Хранит список питомцев текущего пользователя
    var pets by remember { mutableStateOf<List<Pet>>(emptyList()) }

    // Получаем список питомцев из Firestore
    LaunchedEffect(user?.uid) {
        val querySnapshot = db.collection("users")
            .document(user!!.uid)
            .collection("pets")
            .get()
            .await()
        pets = querySnapshot.toObjects<Pet>()
    }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth().background(Bisque2),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "Мои питомцы",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 130.dp, top = 10.dp)
            )
            FloatingActionButton(
                onClick = { controller.navigate(NavScreens.PetsAddScreen.route) },
                backgroundColor = Color.Blue,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(42.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(42.dp).background(Color.Green)
                )
            }
        }

        // Отображаем список питомцев в LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize().background(Bisque2)) {
            items(pets) { pet ->
                Card(
                    modifier = Modifier.fillMaxSize().padding(vertical = 8.dp, horizontal = 16.dp)
                        .clickable { controller.navigate(NavScreens.PetProfile.route + "/${pet.id}") })
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp)

                    ) {
                        Text(text = pet.name, fontWeight = FontWeight.Bold)
                        Text(text = pet.type)
                    }
                }
            }
        }
    }
}