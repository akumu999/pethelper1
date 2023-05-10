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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.Screens.Catalog.product
import com.example.pethelper.Screens.Doctors.Veterinarian
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
fun CatalogScreen(controller: NavController) {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    // Хранит список товаров текущего пользователя
    var products by remember { mutableStateOf<List<product>>(emptyList()) }

    // Получаем список товаров из Firestore
    LaunchedEffect(user?.uid) {
        val querySnapshot = db.collection("products")
            .get()
            .await()
        products = querySnapshot.toObjects<product>()
    }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth().background(Bisque2),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "Прайс-лист на услуги",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 100.dp, top = 10.dp)
            )
        }

        // Отображаем список товаров в LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize().background(Bisque2).padding(bottom = 50.dp)) {
            items(products) { product ->
                Card(
                    modifier = Modifier.fillMaxSize().padding(vertical = 8.dp, horizontal = 16.dp)
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp)

                    ) {
                        Text(text = product.name, fontWeight = FontWeight.Bold)
                        Text(text = "Цена: "+product.cost)
                    }
                }
            }
        }
    }
}