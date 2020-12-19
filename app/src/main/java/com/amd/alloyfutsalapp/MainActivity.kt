package com.amd.alloyfutsalapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.amd.alloyfutsalapp.databinding.ActivityMainBinding
import com.amd.alloyfutsalapp.db.FieldDatabases
import com.amd.alloyfutsalapp.factory.MainProviderFactory
import com.amd.alloyfutsalapp.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getStringExtra("param") == null) {
            setTheme(R.style.Theme_AlloyFutsalApp)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.nav_host_fragment)

        binding.navView.setupWithNavController(navController)

        val repo = FieldRepo(FieldDatabases(this))
        val fieldFactory = MainProviderFactory(repo)
        viewModel = ViewModelProvider(this, fieldFactory).get(MainViewModel::class.java)

        val menuView = binding.navView.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            val activeLabel = item.findViewById<View>(R.id.largeLabel)
            (activeLabel as? TextView)?.setPadding(0, 0, 0, 0)
        }
    }
}