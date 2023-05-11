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
import com.google.firebase.auth.FirebaseUser

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

                    controller.navigate(NavScreens.DoctorScreen.route)
                } else {
                    error.value = "Неверный E-mail или пароль"
                }
            }
    }
}