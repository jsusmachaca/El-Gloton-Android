package com.example.elgloton

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.elgloton.api.APIAuth
import com.example.elgloton.api.models.AuthResponse
import com.example.elgloton.api.models.User
import com.example.elgloton.components.Dashboard
import com.example.elgloton.components.Home
import com.example.elgloton.components.LoginDialog
import com.example.elgloton.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), LoginDialog.LoginDialogListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginDialog = LoginDialog()
        loginDialog.show(supportFragmentManager, "login_dialog")

        replaceFragment(Home())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.dashboard -> replaceFragment(Dashboard())
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
        val authRequest = User(username, password)

        val call = APIAuth.authService.login(authRequest)

        call.enqueue(object : Callback<AuthResponse> { // Define AuthResponse según tu API
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
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }

}