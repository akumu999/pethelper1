package com.example.pethelper.Screens.Catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pethelper.ui.theme.Bisque2

@Composable
fun CatalogList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize().background(Bisque2)
            .padding(16.dp),
    ) {
        items(10) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp).clickable{},
                elevation = 4.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Товар ${index + 1}")
                    Text(text = "Цена")
                }
            }
        }
    }

}