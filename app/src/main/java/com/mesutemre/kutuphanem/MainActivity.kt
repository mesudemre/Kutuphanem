package com.mesutemre.kutuphanem

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mesutemre.kutuphanem.customcomponents.CurvedBottomNavigationView
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.WRITE_EXTERNAL_STORAGE_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private lateinit var navBottomMenu:CurvedBottomNavigationView;
    private var writeExternalStorageIzin:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        writeExternalStorageIzin = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExternalStorageIzin != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        this.initNavBottomMenu();
    }

    private fun initNavBottomMenu(){
        navBottomMenu = findViewById(R.id.bottomNavigation);
        navBottomMenu.itemIconTintList = ContextCompat.getColorStateList(this,R.color.nav_icontint_colors);
        navBottomMenu.itemTextColor = ContextCompat.getColorStateList(this,R.color.nav_icontint_colors);
        navBottomMenu.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED;
        navBottomMenu.menu.getItem(0).isVisible = false;

        setUpNavigation();
    }

    private fun setUpNavigation(){
        val navHostFragment:NavHostFragment = supportFragmentManager.findFragmentById(R.id.bottomFragmentId) as NavHostFragment;
        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.label.toString().equals("ParametreEklemeFragment")){
                floatingActionButton3.visibility = View.GONE;
                navBottomMenu.visibility = View.GONE;
            }else{
                floatingActionButton3.visibility = View.VISIBLE;
                navBottomMenu.visibility = View.VISIBLE;
            }
        }
        NavigationUI.setupWithNavController(navBottomMenu,navHostFragment.navController);
    }
}