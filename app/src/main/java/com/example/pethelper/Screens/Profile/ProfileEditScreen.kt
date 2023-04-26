package com.example.pethelper.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.Screens.Profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileEditScreen(controller: NavController) {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val db = Firebase.firestore
    val userRef = db.collection("users").document(user!!.uid)

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                name = document.getString("name") ?: ""
                age = document.getString("age") ?: ""
                bio = document.getString("bio") ?: ""
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        IconButton(onClick = { controller.navigate(NavScreens.ProfileScreen.route) }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button"
            )
        }
        TextField(
            value = name,
            onValueChange = {name = it},
            label = {Text(text="Name")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(text = "Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        TextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text(text = "Bio") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Button(
            onClick = {
                controller.navigate(NavScreens.ProfileEditScreen.route)
                userRef.update(
                    mapOf(
                        "name" to name,
                        "age" to age,
                        "bio" to bio,
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Сохранить")
        }
    }
}
