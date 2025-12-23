package com.test.pwassignment.auth

import com.google.firebase.auth.FirebaseAuth

object AuthManager {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onError(it.message ?: "Login failed")
            }
    }

    fun logout() {
        auth.signOut()
    }
}
