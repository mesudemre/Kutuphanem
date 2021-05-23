package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.viewmodels.ParametreEklemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.parametre_ekleme_fragment.*

@AndroidEntryPoint
class ParametreEklemeFragment: Fragment() {

    private var tur:String? = null;

    private val viewModel: ParametreEklemeViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.parametre_ekleme_fragment, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        arguments?.let {
            tur = ParametreEklemeFragmentArgs.fromBundle(it).paramTur;
            if(tur.equals("kitaptur")){
                paramTurTextView.text = context?.resources?.getString(R.string.parametreParamKitaptur);
            }else{
                paramTurTextView.text = context?.resources?.getString(R.string.parametreParamYayinevi);
            }
        }

        backLayoutId.setOnClickListener {
            val action = ParametreEklemeFragmentDirections.actionParametreEklemeFragmentToParametreFragment();
            Navigation.findNavController(it).navigate(action);
        }

        parametreKaydetButton.setOnClickListener {
            val aciklama = editTextParametreAciklama.text.toString().trim();
            if(TextUtils.isEmpty(aciklama)){
                textInputParametreAciklama.error = it.context.resources.getString(R.string.parametreAciklamaHata);
                return@setOnClickListener;
            }
            viewModel.parametreEkle(tur,aciklama);
            observeLiveData();
        }
        textInputParametreAciklama.editText!!.addTextChangedListener(TextInputErrorClearListener(textInputParametreAciklama));
    }

    private fun observeLiveData(){
        viewModel.parametreEklemeResponse.observe(viewLifecycleOwner, Observer { response->
            response?.let {
                parametreEklemeErrorTextView.visibility = View.GONE;
                parametreEklemeProgressBar.visibility = View.GONE;
                Toast.makeText(context,response.statusMessage,Toast.LENGTH_LONG).show();
                paramTurTextView.text = "";
            }
        });
        viewModel.parametreEklemeLoading.observe(viewLifecycleOwner, Observer {
            parametreEklemeErrorTextView.visibility = View.GONE;
            parametreKaydetButton.isEnabled = !it;
            if (it){
                parametreEklemeProgressBar.visibility = View.VISIBLE;
            }else{
                parametreEklemeProgressBar.visibility = View.GONE;
            }
        });
        viewModel.parametreEklemeError.observe(viewLifecycleOwner, Observer {
            parametreEklemeProgressBar.visibility = View.GONE;
            if(it){
                parametreEklemeErrorTextView.visibility = View.VISIBLE;
            }else{
                parametreEklemeErrorTextView.visibility = View.GONE;
            }
        });
    }

}