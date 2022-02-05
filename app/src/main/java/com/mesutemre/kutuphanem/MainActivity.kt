package com.mesutemre.kutuphanem

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mesutemre.kutuphanem.databinding.ActivityMainBinding
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.WRITE_EXTERNAL_STORAGE_REQUEST_CODE
import com.mesutemre.kutuphanem.util.customcomponents.CurvedBottomNavigationView
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    private lateinit var binding:ActivityMainBinding

    private lateinit var navBottomMenu:CurvedBottomNavigationView
    private var writeExternalStorageIzin:Int = 0
    private lateinit var navHostFragment:NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        writeExternalStorageIzin = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(writeExternalStorageIzin != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
        }
        this.initNavBottomMenu()

        binding.floatingActionButton3.setOnClickListener {
            navHostFragment.navController.navigate(R.id.kitapEklemeFragment)
        }
    }

    private fun initNavBottomMenu(){
        this.navBottomMenu = binding.bottomNavigation
        binding.bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this,R.color.nav_icontint_colors)
        binding.bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this,R.color.nav_icontint_colors)
        binding.bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.menu.getItem(0).isVisible = false
        setUpNavigation()
    }

    private fun setUpNavigation(){
        navHostFragment = supportFragmentManager.findFragmentById(R.id.bottomFragmentId) as NavHostFragment
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
                    R.id.profilIslemIletisiTercihlerimFragment,
                    R.id.kitapAciklamaBottomSheetDialogFragment
                )){
                binding.floatingActionButton3.hideComponent()
                navBottomMenu.hideComponent()
            }else{
                binding.floatingActionButton3.showComponent()
                navBottomMenu.showComponent()
            }
        }
        setupWithNavController(navBottomMenu,navHostFragment.navController)
    }
}