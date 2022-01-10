package com.mesutemre.kutuphanem.anasayfa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.SplashFragmentBinding
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.hideComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 23.10.2021
 */

@AndroidEntryPoint
class SplashFragment:BaseFragment<SplashFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SplashFragmentBinding
            = SplashFragmentBinding::inflate;
    override val layoutName: String = "splash_fragment.xml";

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private lateinit var token:String;
    private lateinit var action:NavDirections;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
    }

    override fun onStartFragment() {
        binding.splashMainLayoutId.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                action = SplashFragmentDirections.actionSplashFragmentToAnasayfaFragment();
                if(token == null || token.length == 0){
                    action = SplashFragmentDirections.actionSplashFragmentToLoginFragment();
                }
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                p0?.hideComponent();
                Navigation.findNavController(binding.splashMainLayoutId).navigate(action);
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
    }
}