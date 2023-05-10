package com.example.pethelper.Screens.Login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.common.Consts
import com.google.firebase.auth.FirebaseAuth

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

    fun login(controller: NavController, context: Context) {
        val email = email.value ?: return
        val password = password.value ?: return




        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful ) {
                    saveTokenToPrefs(context, token = Consts.TOKEN_KEY)
                    getTokenFromPrefs(context)
                    checkAuth(context, controller)
                    saveSessionAfterLogin(context, controller)
                    controller.navigate(NavScreens.DoctorScreen.route)
                } else {
                    error.value = "Неверный E-mail или пароль"
                }
            }
    }


    // Функция сохранения токена в SharedPreferences
    fun saveTokenToPrefs(context: Context, token: String) {
        val prefs = context.getSharedPreferences(Consts.PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(Consts.TOKEN_KEY, token)
        editor.apply()
    }

    // Функция получения токена из SharedPreferences
    fun getTokenFromPrefs(context: Context): String? {
        val prefs = context.getSharedPreferences(Consts.PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Consts.TOKEN_KEY, null)
    }

    // Функция проверки авторизации при запуске приложения
    fun checkAuth(context: Context, controller: NavController) {
        val token = getTokenFromPrefs(context)
        if (token != null) {
            FirebaseAuth.getInstance().signInWithCustomToken(token)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        controller.navigate(NavScreens.DoctorScreen.route)
                    } else {
                        controller.navigate(NavScreens.LoginScreen.route)
                    }
                }
        } else {
            controller.navigate(NavScreens.LoginScreen.route)
        }
    }

    // Функция сохранения токена в SharedPreferences после успешной аутентификации
    fun saveSessionAfterLogin(context: Context, controller: NavController) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            user.getIdToken(true).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result?.token
                    if (token != null) {
                        saveTokenToPrefs(context, token)
                        controller.navigate(NavScreens.DoctorScreen.route)
                    }
                }
            }
        }
    }
}