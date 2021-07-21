package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.YayinEviAdapter
import com.mesutemre.kutuphanem.databinding.ParametreYayineviFragmentBinding
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.viewmodels.ParametreYayineviViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.parametre_yayinevi_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class ParametreYayineviFragment:Fragment() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val viewModel:ParametreYayineviViewModel by viewModels();
    private lateinit var adapter:YayinEviAdapter;
    private var parametreYayinEviBinding:ParametreYayineviFragmentBinding? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parametreYayinEviBinding = ParametreYayineviFragmentBinding.inflate(inflater);
        return parametreYayinEviBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        adapter = YayinEviAdapter(arrayListOf(),viewModel,viewLifecycleOwner,token);
        viewModel.yayinEviListeGetir(false);
        parametreYayinEviBinding!!.yayinEviListeRw.layoutManager = LinearLayoutManager(context);
        parametreYayinEviBinding!!.yayinEviListeRw.adapter = adapter;

        observeLiveData();

        parametreYayinEviBinding!!.yayinEviSwipeRefreshLayout.setOnRefreshListener {
            parametreYayinEviBinding!!.yayinEviListeRw.visibility = View.GONE;
            parametreYayinEviBinding!!.yayinEviErrorTextId.visibility = View.GONE;
            parametreYayinEviBinding!!.yayinEviProgressBar.visibility = View.VISIBLE;

            parametreYayinEviBinding!!.yayinEviSwipeRefreshLayout.isRefreshing = false;

            viewModel.yayinEviListeGetir(true);
        }

        parametreYayinEviBinding!!.yayinEviEkleFloatingActionButton.setOnClickListener {
            val action = ParametreFragmentDirections.actionParametreFragmentToParametreEklemeFragment("yayinevi");
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeLiveData(){
        viewModel.yayineviListe.observe(viewLifecycleOwner, Observer { yayineviListe->
            yayineviListe?.let {
                adapter.updateYayineviListe(yayineviListe);
            }
        });

        viewModel.yayinEviError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if(it){
                    parametreYayinEviBinding!!.yayinEviErrorTextId.visibility = View.VISIBLE;
                    parametreYayinEviBinding!!.yayinEviListeRw.visibility = View.GONE;
                }else{
                    parametreYayinEviBinding!!.yayinEviListeRw.visibility = View.VISIBLE;
                    parametreYayinEviBinding!!.yayinEviErrorTextId.visibility = View.GONE;
                }
            }
        });

        viewModel.yayinEviLoading.observe(viewLifecycleOwner, Observer {loading->
            loading.let {
                if(it){
                    parametreYayinEviBinding!!.yayinEviProgressBar.visibility = View.VISIBLE;
                    parametreYayinEviBinding!!. yayinEviListeRw.visibility = View.GONE;
                    parametreYayinEviBinding!!.yayinEviErrorTextId.visibility = View.GONE;
                }else{
                    parametreYayinEviBinding!!.yayinEviProgressBar.visibility = View.GONE;
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView();
        parametreYayinEviBinding = null;
    }

}