package com.example.elgloton

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.elgloton.api.APIAuth
import com.example.elgloton.api.models.auth.AuthResponse
import com.example.elgloton.api.models.auth.User
import com.example.elgloton.components.Dashboard
import com.example.elgloton.components.Home
import com.example.elgloton.components.dialogs.LoginDialog
import com.example.elgloton.databinding.ActivityMainBinding
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.SecureRandom
import java.util.Base64

class MainActivity : AppCompatActivity(), LoginDialog.LoginDialogListener {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    val secretKey = generateSecretKey()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getTokenExpiration()

        val sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", null)
        if (token.isNullOrEmpty()) {
            val loginDialog = LoginDialog()
            loginDialog.show(supportFragmentManager, "login_dialog")
        }

        replaceFragment(Home())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.dashboard -> {
                    val sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("access_token", "")
                    if (token != null && token.isNotEmpty()) {
                        replaceFragment(Dashboard())
                    } else {
                        val loginDialog = LoginDialog()
                        loginDialog.show(supportFragmentManager, "login_dialog")
                    }
                }
                else -> {

                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onLoginClick(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val authRequest = User(username, password)

            val call = APIAuth.authService.login(authRequest)

            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()

                        val accessToken = authResponse?.access
                        val refreshToken = authResponse?.refresh

                        val sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("access_token", accessToken)
                            putString("refresh", refreshToken)
                            apply()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Error de autenticación", Toast.LENGTH_SHORT).show()
                        val loginDialog = LoginDialog()
                        loginDialog.show(supportFragmentManager, "login_dialog")

                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@MainActivity, "Debes ingresar un usuario y una contraseña", Toast.LENGTH_SHORT).show()
            val loginDialog = LoginDialog()
            loginDialog.show(supportFragmentManager, "login_dialog")

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTokenExpiration() {
        val sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", null)

        try {
            val claims = Jwts.parser()
                .setSigningKey(secretKey) // Reemplaza con tu clave secreta
                .parseClaimsJws(token)
                .body as Claims

            val expirationTimeMillis = claims["exp"] as Long
            val currentMillis = System.currentTimeMillis() / 1000 // Convertir a segundos

            if (currentMillis > expirationTimeMillis) {
                val editor = sharedPreferences.edit()
                editor.remove("access_token")
                editor.apply()
            }

        } catch (e: JwtException) {
           println("Error")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateSecretKey(): String {
        val random = SecureRandom()
        val key = ByteArray(32) // Clave de 256 bits
        random.nextBytes(key)
        return Base64.getEncoder().encodeToString(key)
    }

}