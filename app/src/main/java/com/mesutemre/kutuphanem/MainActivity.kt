package com.mesutemre.kutuphanem

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mesutemre.kutuphanem.customcomponents.CurvedBottomNavigationView
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.WRITE_EXTERNAL_STORAGE_REQUEST_CODE
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private lateinit var navBottomMenu:CurvedBottomNavigationView;
    private var writeExternalStorageIzin:Int = 0;
    private lateinit var navHostFragment:NavHostFragment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        writeExternalStorageIzin = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExternalStorageIzin != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        this.initNavBottomMenu();

        floatingActionButton3.setOnClickListener {
            navHostFragment.navController.navigate(R.id.kitapEklemeFragment);
        }
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
        navHostFragment = supportFragmentManager.findFragmentById(R.id.bottomFragmentId) as NavHostFragment;
        val navController = navHostFragment.navController
        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id in arrayOf(
                    R.id.parametreEklemeFragment,
                    R.id.kitapEklemeFragment,
                    R.id.kitapDetayFragment,
                    R.id.kitapDetayDeepFragment,
                    R.id.splashFragment,
                    R.id.loginFragment,
                    R.id.profilIslemAdSoyadEpostaFragment,
                    R.id.profilIslemDogumTarCinsiyetFragment,
                    R.id.profilIslemIlgiAlanlarimFragment,
                    R.id.profilIslemIletisiTercihlerimFragment
            )){
                floatingActionButton3.hideComponent();
                navBottomMenu.hideComponent();
            }else{
                floatingActionButton3.showComponent();
                navBottomMenu.showComponent();
            }
        }
        setupWithNavController(navBottomMenu,navHostFragment.navController);
    }
}