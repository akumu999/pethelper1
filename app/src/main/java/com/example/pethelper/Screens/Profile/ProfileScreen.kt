package com.example.pethelper.Screens.Profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.R
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(controller: NavController) {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val db = Firebase.firestore
    val userRef = db.collection("users").document(user!!.uid)


    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                name = document.getString("name") ?: ""
                surname = document.getString("surname") ?: ""
                age = document.getString("age") ?: ""
                bio = document.getString("bio") ?: ""
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(Bisque2)) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
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
            elevation = 4.dp,
        ) {
            Column() {

                Text(
                    text = "Имя: " + name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = "Фамилия: " + surname,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = "Возраст: " + age,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = "О себе: " + bio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
        }
    }
}
