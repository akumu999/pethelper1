package com.example.pethelper.Screens.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _age = MutableLiveData("")
    val age: LiveData<String> = _age

    private val _bio = MutableLiveData("")
    val bio: LiveData<String> = _bio

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onAgeChange(age: String) {
        _age.value = age
    }

    fun onBioChange(bio: String) {
        _bio.value = bio
    }

    fun saveProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = firestore.collection("users").document(userId)
            val userData = hashMapOf(
                "name" to name.value,
                "age" to age.value,
                "bio" to bio.value
            )
            userRef.set(userData)
        }
    }
}
