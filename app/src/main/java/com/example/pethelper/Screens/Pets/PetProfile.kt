import androidx.compose.runtime.*
import Pet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun PetProfile(pet: Pet, controller: NavController) {
    var name by remember { mutableStateOf(pet.name) }
    var type by remember { mutableStateOf(pet.type) }
    var breed by remember { mutableStateOf(pet.breed) }
    var gender by remember { mutableStateOf(pet.gender) }
    var age by remember { mutableStateOf(pet.age) }
    var description by remember { mutableStateOf(pet.description) }
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    val petRef = db.collection("users").document(user!!.uid).collection("pets").document(pet.id)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Bisque2)
    ) {
        Text(
            text = "Редактирование информации о питомце",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Вид") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = breed,
            onValueChange = { breed = it },
            label = { Text("Порода") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Пол") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Возраст") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
                modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = {
                        val petData = hashMapOf(
                            "name" to name,
                            "type" to type,
                            "breed" to breed,
                            "gender" to gender,
                            "age" to age,
                            "description" to description
                        )
                        petRef.set(petData)
                        controller.navigate(NavScreens.PetsScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Сохранить изменения") }
    }
}

