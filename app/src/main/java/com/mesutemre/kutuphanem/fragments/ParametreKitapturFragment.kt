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
import com.mesutemre.kutuphanem.adapters.KitapTurAdapter
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.viewmodels.ParametreKitapturViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.parametre_kitaptur_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class ParametreKitapturFragment:Fragment() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val viewModel: ParametreKitapturViewModel by viewModels();
    private lateinit var adapter:KitapTurAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.parametre_kitaptur_fragment, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        adapter = KitapTurAdapter(arrayListOf(),viewModel,viewLifecycleOwner,token);
        viewModel.kitapTurListeGetir(false);
        kitapTurListeRw.layoutManager = LinearLayoutManager(context);
        kitapTurListeRw.adapter = adapter;

        observeLiveData();

        kitapTurSwipeRefreshLayout.setOnRefreshListener {
            kitapTurProgressBar.visibility = View.VISIBLE;
            kitapTurErrorTextId.visibility = View.GONE;
            kitapTurListeRw.visibility = View.GONE;

            kitapTurSwipeRefreshLayout.isRefreshing = false;
            viewModel.kitapTurListeGetir(true);
        }

        kitapTurEkleFloatingActionButton.setOnClickListener {
            val action = ParametreFragmentDirections.actionParametreFragmentToParametreEklemeFragment("kitaptur");
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeLiveData(){
        viewModel.kitapturListe.observe(viewLifecycleOwner, Observer { kitapTurListe->
            kitapTurListe?.let {
                kitapTurListeRw.visibility = View.VISIBLE;
                adapter.updateKitapTurListe(kitapTurListe);
            }
        });

        viewModel.kitapTurError.observe(viewLifecycleOwner,Observer{error->
            error.let {
                if(it){
                    kitapTurProgressBar.visibility = View.GONE;
                    kitapTurErrorTextId.visibility = View.VISIBLE;
                    kitapTurListeRw.visibility = View.GONE;
                }else{
                    kitapTurProgressBar.visibility = View.GONE;
                }
            }
        });

        viewModel.kitapTurLoading.observe(viewLifecycleOwner, Observer { loading->
            loading.let {
                if(it){
                    kitapTurProgressBar.visibility = View.VISIBLE;
                    kitapTurErrorTextId.visibility = View.GONE;
                    kitapTurListeRw.visibility = View.GONE;
                }else{
                    kitapTurProgressBar.visibility = View.GONE;
                }
            }
        })
    }
}