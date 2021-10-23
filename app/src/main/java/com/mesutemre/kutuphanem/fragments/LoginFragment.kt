package com.mesutemre.kutuphanem.fragments

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.LoginFragmentBinding
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.AccountCredentials
import com.mesutemre.kutuphanem.util.*
import com.mesutemre.kutuphanem.viewmodels.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 23.10.2021
 */
@AndroidEntryPoint
class LoginFragment:BaseFragment<LoginFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding
            = LoginFragmentBinding::inflate;
    override val layoutName: String = "login_fragment.xml";

    private val viewModel: LoginFragmentViewModel by viewModels();
    private lateinit var kullaniciAdi:String;

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onCreateViewFragment(view: View) {
        super.onCreateViewFragment(view);
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val isLollipopOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
                if(isLollipopOrAbove){
                    activity!!.finishAndRemoveTask();
                }else{
                    activity!!.finishAffinity();
                }
            }
        });
    }

    override fun onStartFragment() {
        binding.cirLoginButton.setOnClickListener {
            kullaniciAdi = binding.editTextKullaniciAdi.text.toString().trim();
            val sifre:String = binding.editTextSifre.text.toString().trim();

            if(TextUtils.isEmpty(kullaniciAdi)){
                binding.textInputKullaniciAdi.error = resources.getString(R.string.bosKullaniciAdiHata);
                return@setOnClickListener;
            }

            if(TextUtils.isEmpty(sifre)) {
                binding.textInputSifre.error= resources.getString(R.string.bosSifreHata);
                return@setOnClickListener;
            }

            binding.editTextKullaniciAdi.hideKeyboard(binding.editTextKullaniciAdi);
            val accountCredentials = AccountCredentials(kullaniciAdi,sifre);
            viewModel.doLogin(accountCredentials);
            observeLiveData();
        }

        binding.textInputKullaniciAdi.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputKullaniciAdi));
        binding.textInputSifre.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputSifre));
    }

    private fun observeLiveData(){
        viewModel.token.observe(viewLifecycleOwner,Observer { token->
            token?.let {
                customSharedPreferences.putStringToSharedPreferences(APP_TOKEN_KEY,token);
                customSharedPreferences.putStringToSharedPreferences(KULLANICI_ADI_KEY,kullaniciAdi);
                val action = LoginFragmentDirections.actionLoginFragmentToAnasayfaFragment();
                Navigation.findNavController(binding.loginViewId).navigate(action);
            }
        });

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {isLoading->
            isLoading?.let {
                binding.cirLoginButton.isEnabled = !it;
                binding.errorTextView.hideComponent();
                if(it){
                    binding.girisProgressBar.showComponent();
                }else{
                    binding.girisProgressBar.hideComponent();
                }
            }
        });

        viewModel.loginError.observe(viewLifecycleOwner,Observer{loginError->
            loginError?.let {
                binding.girisProgressBar.hideComponent();
                if(it){
                    binding.errorTextView.showComponent();
                }else{
                    binding.errorTextView.hideComponent();
                }
            }
        });
    }
}