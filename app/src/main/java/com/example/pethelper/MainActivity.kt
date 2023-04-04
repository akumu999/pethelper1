package com.example.pethelper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.Navigation.SetNavController
import com.example.pethelper.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.sql.Date
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                SetNavController(controller = rememberNavController())
                }
            }
        }
    }
private lateinit var auth: FirebaseAuth


@Composable
fun StartScreen(controller: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        auth = Firebase.auth
        Text(
            text = "ВетСити",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), onClick = {
            controller.navigate(NavScreens.LoginScreen.route)
        }, modifier = Modifier
            .padding(top = 400.dp)
            .width(250.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(25.dp)
            )){
        Text(text = "Войти", color = Color.White, fontSize = 20.sp)
        }
        Button(modifier = Modifier
            .padding(top = 10.dp)
            .width(250.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ),colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),onClick = {
            controller.navigate(NavScreens.RegisterScreen.route)
        }){
            Text(text = "Регистрация", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun LoginScreen(
    controller: NavController,
    onLogin: (username: String, password: String) -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorState.value) {
            Text(
                text = "Incorrect username or password",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier
            .width(250.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ),colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            onClick = {
                if (username == "admin" && password == "password") {
                    onLogin(username, password)
                    controller.navigate(NavScreens.MainScreen.route)
                } else {
                    errorState.value = true
                }
            }
        ) {
            Text(text = "Login" , color = Color.White, fontSize = 20.sp)
        }
        Button(modifier = Modifier
            .padding(top = 10.dp)
            .width(250.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ),colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), onClick = {
            controller.navigate(NavScreens.LoginScreen.route)
        }){
            Text(text = "Back" , color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    controller: NavController
) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Register Screen")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.register(controller) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}

class RegisterViewModel : ViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun register(controller: NavController) {
        val email = email.value ?: return
        val password = password.value ?: return
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    controller.navigate(NavScreens.MainScreen.route)
                } else {
                    // Handle registration error
                }
            }
    }
}

@Composable
fun MainScreen(controller: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "ВетСити") }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = true,
                    onClick = { controller.navigate(NavScreens.OrderScreen.route)},
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Записаться на прием") },
                    label = { Text("Записаться на прием") }
                )

                BottomNavigationItem(
                    selected = false,
                    onClick = { controller.navigate(NavScreens.CatalogScreen.route) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Каталог товаров") },
                    label = { Text("Каталог товаров") }
                )

                BottomNavigationItem(
                    selected = false,
                    onClick = { controller.navigate(NavScreens.PetsScreen.route) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Мои питомцы") },
                    label = { Text("Мои питомцы") }
                )

                BottomNavigationItem(
                    selected = false,
                    onClick = { controller.navigate(NavScreens.ProfileScreen.route) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Профиль") },
                    label = { Text("Профиль") }
                )
            }
        },
        content = {padding ->
            Column(modifier = Modifier.padding(16.dp)){DoctorList() } },
    )
}

@Composable
fun DoctorList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        items(10) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Врач ${index + 1}")
                    Text(text = "Специализация: Ветеринар")
                }
            }
        }
    }
}


@Composable
fun OrderScreen(controller: NavController) {

}

@Composable
fun CatalogScreen(controller: NavController){

}
@Composable
fun PetsScreen(controller: NavController) {
    // Список питомцев
    val pets = remember { mutableStateListOf<String>() }
    // Имя нового питомца
    var newPetName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Мои питомцы") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (newPetName.isNotBlank()) {
                        pets.add(newPetName)
                        newPetName = ""
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить питомца")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Список питомцев
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(pets) { pet ->
                        Text(text = pet)
                    }
                }
                // Форма добавления нового питомца
                TextField(
                    value = newPetName,
                    onValueChange = { newPetName = it },
                    label = { Text(text = "Введите имя питомца") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    )
}
@Composable
fun ProfileScreen() {

}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

