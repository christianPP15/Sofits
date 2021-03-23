package com.example.sofits_frontend

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.sofits_frontend.ui.Login.LoginActivity

class MainActivity : AppCompatActivity() {
    lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_autores,R.id.navigation_chat,  R.id.navigation_perfil))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val sharedPref = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
        token = sharedPref.getString(getString(R.string.TOKEN_USER), "")!!
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_options_menu, menu)

        val actionLogout = menu.findItem(R.id.action_logout)
        if (token.isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        if (token.isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val sharedPref = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
            with(sharedPref.edit()){
                remove(getString(R.string.TOKEN_USER))
                commit()
            }
            finish()
        }

    }
}