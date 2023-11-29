package com.example.elgloton.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.elgloton.MainActivity
import com.example.elgloton.R
import com.example.elgloton.api.APIAuth
import com.example.elgloton.api.models.auth.AuthResponse
import com.example.elgloton.api.models.auth.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : Fragment() {
    lateinit var editUsername: EditText
    lateinit var editPassword: EditText
    lateinit var submitButton: Button
    lateinit var register: TextView

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginView =  inflater.inflate(R.layout.fragment_login, container, false)

        editUsername = loginView.findViewById(R.id.username)
        editPassword = loginView.findViewById(R.id.password)
        submitButton = loginView.findViewById(R.id.buttonSubmit)
        register = loginView.findViewById(R.id.register)

        submitButton.setOnClickListener {
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()
            onLogin(username, password)
        }

        val registerUrl = "http://192.168.1.14:5173/auth/register"
        register.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(registerUrl)
            startActivity(intent)
        }

        return loginView

    }
    private fun onLogin(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val authRequest = User(username, password)

            val call = APIAuth.authService.login(authRequest)

            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()

                        val accessToken = authResponse?.access
                        val refreshToken = authResponse?.refresh

                        val sharedPreferences = requireContext().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("access_token", accessToken)
                            putString("refresh", refreshToken)
                            apply()
                        }

                        val fragmentManager = requireActivity().supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frame_layout, Home())
                        fragmentTransaction.commit()
                        Toast.makeText(requireContext(), "Has iniciado sesión", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(requireContext(), "Error de autenticación", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al conectarse al servidor", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "Debes ingresar un usuario y una contraseña", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigationView(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNavigationView(true)
    }
}