package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ParametreEklemeFragmentBinding
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.SnackTypeEnum
import com.mesutemre.kutuphanem.util.hideKeyboard
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewmodels.ParametreEklemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.parametre_ekleme_fragment.*

@AndroidEntryPoint
class ParametreEklemeFragment: Fragment() {

    private var tur:String? = null;

    private val viewModel: ParametreEklemeViewModel by viewModels();
    private var parametreEklemeBinding:ParametreEklemeFragmentBinding? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parametreEklemeBinding = ParametreEklemeFragmentBinding.inflate(inflater);
        return parametreEklemeBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        arguments?.let {
            tur = ParametreEklemeFragmentArgs.fromBundle(it).paramTur;
            if(tur.equals("kitaptur")){
                parametreEklemeBinding!!.paramTurTextView.text = context?.resources?.getString(R.string.parametreParamKitaptur);
            }else{
                parametreEklemeBinding!!.paramTurTextView.text = context?.resources?.getString(R.string.parametreParamYayinevi);
            }
        }

        parametreEklemeBinding!!.backLayoutId.setOnClickListener {
            val action = ParametreEklemeFragmentDirections.actionParametreEklemeFragmentToParametreFragment();
            Navigation.findNavController(it).navigate(action);
        }

        parametreKaydetButton.setOnClickListener {
            val aciklama = parametreEklemeBinding!!.editTextParametreAciklama.text.toString().trim();
            parametreEklemeBinding!!.editTextParametreAciklama.hideKeyboard(parametreEklemeBinding!!.editTextParametreAciklama);
            if(TextUtils.isEmpty(aciklama)){
                parametreEklemeBinding!!.textInputParametreAciklama.error = it.context.resources.getString(R.string.parametreAciklamaHata);
                return@setOnClickListener;
            }

            viewModel.parametreEkle(tur,aciklama);
            observeLiveData(it);
        }
        parametreEklemeBinding!!.textInputParametreAciklama.editText!!.addTextChangedListener(TextInputErrorClearListener(parametreEklemeBinding!!.textInputParametreAciklama));
    }

    private fun observeLiveData(view:View){
        viewModel.parametreEklemeResponse.observe(viewLifecycleOwner, Observer { response->
            response?.let {
                parametreEklemeBinding!!.parametreEklemeErrorTextView.visibility = View.GONE;
                parametreEklemeBinding!!.parametreEklemeProgressBar.visibility = View.GONE;
                showSnackBar(view,response.statusMessage, SnackTypeEnum.SUCCESS);
                parametreEklemeBinding!!.paramTurTextView.text = "";

            }
        });
        viewModel.parametreEklemeLoading.observe(viewLifecycleOwner, Observer {
            parametreEklemeBinding!!.parametreEklemeErrorTextView.visibility = View.GONE;
            parametreEklemeBinding!!.parametreKaydetButton.isEnabled = !it;
            if (it){
                parametreEklemeBinding!!.parametreEklemeProgressBar.visibility = View.VISIBLE;
            }else{
                parametreEklemeBinding!!.parametreEklemeProgressBar.visibility = View.GONE;
            }
        });
        viewModel.parametreEklemeError.observe(viewLifecycleOwner, Observer {
            parametreEklemeBinding!!.parametreEklemeProgressBar.visibility = View.GONE;
            if(it){
                parametreEklemeBinding!!.parametreEklemeErrorTextView.visibility = View.VISIBLE;
            }else{
                parametreEklemeBinding!!.parametreEklemeErrorTextView.visibility = View.GONE;
            }
        });
    }

    override fun onDestroyView() {
        super.onDestroyView();
        parametreEklemeBinding = null;
    }

}