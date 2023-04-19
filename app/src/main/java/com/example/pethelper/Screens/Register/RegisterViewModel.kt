package com.example.pethelper.Screens.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.google.firebase.auth.FirebaseAuth

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
                    controller.navigate(NavScreens.DoctorScreen.route)
                } else {
                    // Handle registration error
                }
            }
    }
}