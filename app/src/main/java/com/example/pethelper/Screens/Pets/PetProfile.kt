import androidx.compose.runtime.*
import Pet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.ui.theme.Bisque2
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun PetProfile(pet: Pet, controller: NavController) {
    var petInfo by remember { mutableStateOf<Pet?>(null) }
    val db = FirebaseFirestore.getInstance()
    val petRef = db.collection("pets").document(pet.id)

    LaunchedEffect(pet.id) {
        val document = petRef.get().await()
        if (document.exists()) {
            val name = document.getString("name") ?: ""
            val type = document.getString("type") ?: ""
            val gender = document.getString("gender") ?: ""
            val age = document.getString("age") ?:""
            val breed = document.getString("breed") ?:""
            val description = document.getString("description") ?: ""
            petInfo = Pet(name, type, gender, breed, age, description)
        }
    }

    petInfo?.let {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize().background(Bisque2)
        ) {
            Text(
                text = "Информация о питомце",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Имя: ${it.name}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Вид: ${it.type}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Порода: ${it.type}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Пол: ${it.gender}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Возраст: ${it.age} лет",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Описание: ${it.description}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
