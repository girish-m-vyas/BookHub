package com.girish.bookhub.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.girish.bookhub.R
import com.girish.bookhub.fragment.DashboardFragment
import com.girish.bookhub.fragment.FavouritesFragment
import com.girish.bookhub.fragment.HelpFragment
import com.girish.bookhub.fragment.ProfileFragment



class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousManuItem:MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinator)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousManuItem != null){
                previousManuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousManuItem = it
            when (it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Dashboard"
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, FavouritesFragment())
                            .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Favourites"
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment())
                            .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Profile"
                }
                R.id.help -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, HelpFragment())
                            .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Help"
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    @SuppressLint("RestrictedApi")
    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard() {
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }

}


