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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.parametre_yayinevi_fragment, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        adapter = YayinEviAdapter(arrayListOf(),viewModel,viewLifecycleOwner,token);
        viewModel.yayinEviListeGetir(false);
        yayinEviListeRw.layoutManager = LinearLayoutManager(context);
        yayinEviListeRw.adapter = adapter;

        observeLiveData();

        yayinEviSwipeRefreshLayout.setOnRefreshListener {
            yayinEviListeRw.visibility = View.GONE;
            yayinEviErrorTextId.visibility = View.GONE;
            yayinEviProgressBar.visibility = View.VISIBLE;

            yayinEviSwipeRefreshLayout.isRefreshing = false;

            viewModel.yayinEviListeGetir(true);
        }

        yayinEviEkleFloatingActionButton.setOnClickListener {
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
                    yayinEviErrorTextId.visibility = View.VISIBLE;
                    yayinEviListeRw.visibility = View.GONE;
                }else{
                    yayinEviListeRw.visibility = View.VISIBLE;
                    yayinEviErrorTextId.visibility = View.GONE;
                }
            }
        });

        viewModel.yayinEviLoading.observe(viewLifecycleOwner, Observer {loading->
            loading.let {
                if(it){
                    yayinEviProgressBar.visibility = View.VISIBLE;
                    yayinEviListeRw.visibility = View.GONE;
                    yayinEviErrorTextId.visibility = View.GONE;
                }else{
                    yayinEviProgressBar.visibility = View.GONE;
                }
            }
        })
    }

}