package com.mesutemre.kutuphanem.parametre.ekleme.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.ParametreEklemeFragmentBinding
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.parametre_ekleme_fragment.*

@AndroidEntryPoint
class ParametreEklemeFragment: BaseFragment<ParametreEklemeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ParametreEklemeFragmentBinding
            = ParametreEklemeFragmentBinding::inflate
    override val layoutName = "parametre_ekleme_fragment.xml";

    private var tur:String? = null;
    private val viewModel: ParametreEklemeViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        arguments?.let {
            tur = ParametreEklemeFragmentArgs.fromBundle(it).paramTur;
        }
    }

    override fun onStartFragment() {
        if(tur.equals("kitaptur")){
            binding.paramTurTextView.text = context?.resources?.getString(R.string.parametreParamKitaptur);
        }else{
            binding.paramTurTextView.text = context?.resources?.getString(R.string.parametreParamYayinevi);
        }
        binding.backLayoutId.setOnClickListener {
            val action = ParametreEklemeFragmentDirections.actionParametreEklemeFragmentToParametreFragment();
            Navigation.findNavController(it).navigate(action);
        }

        parametreKaydetButton.setOnClickListener {
            val aciklama = binding.editTextParametreAciklama.text.toString().trim();
            binding.editTextParametreAciklama.hideKeyboard(binding.editTextParametreAciklama);
            if(TextUtils.isEmpty(aciklama)){
                binding.textInputParametreAciklama.error = it.context.resources.getString(R.string.parametreAciklamaHata);
                return@setOnClickListener;
            }

            viewModel.parametreEkle(tur,aciklama);
            observeLiveData(it);
        }
        binding.textInputParametreAciklama.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputParametreAciklama));
    }

    private fun observeLiveData(view:View){
        viewModel.parametreEklemeResourceEvent.observe(viewLifecycleOwner,Observer{
           when(it){
               is BaseResourceEvent.Loading->{
                   binding.parametreEklemeErrorTextView.hideComponent();
                   binding.parametreEklemeProgressBar.showComponent();
                   binding.parametreKaydetButton.isEnabled = false;
               }
               is BaseResourceEvent.Error->{
                   binding.parametreEklemeProgressBar.hideComponent();
                   binding.parametreKaydetButton.isEnabled = true;
                   binding.parametreEklemeErrorTextView.showComponent();
                   binding.parametreEklemeErrorTextView.setText(it.message)
               }
               is BaseResourceEvent.Success->{
                   getFragmentView().hideComponents(binding.parametreEklemeProgressBar,binding.parametreEklemeErrorTextView);
                   binding.parametreKaydetButton.isEnabled = true;
                   showSnackBar(view,it.data!!.statusMessage, SUCCESS);
                   binding.textInputParametreAciklama.editText!!.setText("");
               }
           }
        });
    }
}