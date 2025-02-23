package com.msissa.android_practice.ui

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.msissa.android_practice.R
import com.msissa.android_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MenuProvider {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Attach listeners to change the insets of elements considering the system components
        setInsets(binding.bottomNavigationView, "navigationBar")
        setInsets(binding.navHostFragment, "fragmentContainerView")
        setInsets(binding.barLayout, "appBarLayout")


        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        (binding.bottomNavigationView as com.google.android.material.navigation.NavigationBarView)
            .setupWithNavController(navController)


        setSupportActionBar(binding.toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newQuotationFragment,
                R.id.favouritesFragment,
                R.id.settingsFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        // Dynamically link the menu for the action bar
        addMenuProvider(this)


    }


    /**
     * Setup of the menu
     */
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_about, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.aboutDialogFragment -> {
                navController.navigate(R.id.aboutDialogFragment)
                true
            }
            else -> false
        }
    }

    /**
    * Helper function that sets the insets of a component considering the system components (camera...)
     */
    private fun setInsets(component : View, componentName : String) {
        ViewCompat.setOnApplyWindowInsetsListener(component) {
            view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.systemBars())

            when (componentName) {
                "navigationBar" -> {
                    view.updatePadding(
                        left = bars.left,
                        top = 0,
                        right = 0,
                        bottom = bars.bottom
                    )
                }
                "fragmentContainerView" -> {
                    view.updatePadding(
                        left = 0,
                        top = 0,
                        right = bars.right,
                        bottom = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) bars.bottom else 0
                    )
                }
                "appBarLayout" -> {
                    view.updatePadding(
                        left = bars.left,
                        top = bars.top,
                        right = 0,
                        bottom = 0
                    )
                }
            }

            WindowInsetsCompat.CONSUMED
        }

    }
}