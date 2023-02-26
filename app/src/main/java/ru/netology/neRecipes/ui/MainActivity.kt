package ru.netology.neRecipes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.netology.neRecipes.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navView.setOnItemReselectedListener { bottomNavMenuItem ->
            when (bottomNavMenuItem.itemId) {
                R.id.home -> {
                    // Respond to navigation item 1 reselection
                    println("home")
                    onSupportNavigateUp()
                }
                R.id.filter -> {
                    // Respond to navigation item 2 reselection
                    println("filter")
                }
                R.id.favorites -> {
                    // Respond to navigation item 3 reselection
                    println("favorites")
                    //onSupportNavigateUp()
                }
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainListFragment,
                R.id.filterFragment,
                R.id.favoritesFragment
            )
        )

        navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

}