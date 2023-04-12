package com.example.pethelper

import android.os.Bundle
import android.text.style.ClickableSpan
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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog


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
            text = "PETHELPER",
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
    viewModel: LoginViewModel,
    controller: NavController,
) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Login Screen")
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
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login(controller) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4)
        ) {
            Text(text = "Войти", color = Color.White, fontSize = 20.sp)
        }
        if (viewModel.error.value.isNotEmpty()) {
            Text(
                text = viewModel.error.value,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
    }
}

class LoginViewModel : ViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    val error = mutableStateOf("")

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun login(controller: NavController) {
        val email = email.value ?: return
        val password = password.value ?: return



        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    controller.navigate(NavScreens.MainScreen.route)
                } else {
                    error.value = "Incorrect email or password"
                }
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
            .background(Bisque2)
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
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.register(controller) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться", color = Color.White, fontSize = 20.sp)
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
                title = { Text(text = "PETHELPER") }
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
    Scaffold(bottomBar = {
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
    }, content = {padding ->
        Column(modifier = Modifier.padding(16.dp)){ } })
    
}

@Composable
fun CatalogScreen(controller: NavController){
    Scaffold(bottomBar = {
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
    }, content = {padding ->
        Column(modifier = Modifier.padding(16.dp)){ } })

}
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

data class Item(val type: String, val breed: String, val name: String, val age: String, val gender: String, val descripton: String)



@Composable
fun ProfileScreen(controller: NavController) {
    Scaffold(bottomBar = {
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
    }, content = {padding ->
        Column(modifier = Modifier.padding(16.dp)){ } })

}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

