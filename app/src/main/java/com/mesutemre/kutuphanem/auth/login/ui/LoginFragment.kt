package com.mesutemre.kutuphanem.auth.login.ui

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
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.LoginFragmentBinding
import com.mesutemre.kutuphanem.util.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.auth.login.model.AccountCredentials
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.util.*
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.loginSingleResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.girisProgressBar.showComponent();
                    binding.cirLoginButton.isEnabled = false;
                }
                is BaseResourceEvent.Error->{
                    binding.girisProgressBar.hideComponent();
                    binding.cirLoginButton.isEnabled = true;
                    showSnackBar(getFragmentView(),getFragmentView().context.resources.getString(R.string.hataliLogin), ERROR);
                }
                is BaseResourceEvent.Success->{
                    binding.girisProgressBar.hideComponent();
                    binding.cirLoginButton.isEnabled = true;
                    val action = LoginFragmentDirections.actionLoginFragmentToAnasayfaFragment();
                    Navigation.findNavController(getFragmentView()).navigate(action);
                }
            }
        });
    }
}