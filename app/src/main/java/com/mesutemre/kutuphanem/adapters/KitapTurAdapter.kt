package com.mesutemre.kutuphanem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemKitapturBinding
import com.mesutemre.kutuphanem.listener.KitapturDeleteClickListener
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewmodels.ParametreKitapturViewModel
import org.json.JSONObject

class KitapTurAdapter(val kitapTurListe:ArrayList<KitapturModel>,
                      val viewModel:ParametreKitapturViewModel,
                      val lifeCycleOwner: LifecycleOwner,
                      val token:String?):RecyclerView.Adapter<KitapTurAdapter.KitapTurViewHolder>(),KitapturDeleteClickListener  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitapTurViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = DataBindingUtil.inflate<ItemKitapturBinding>(inflater,R.layout.item_kitaptur,parent,false);
        return KitapTurViewHolder(view);
    }

    override fun getItemCount(): Int {
        return kitapTurListe.size;
    }

    override fun onBindViewHolder(holder: KitapTurViewHolder, position: Int) {
        holder.view.kitaptur = kitapTurListe.get(position);
        holder.view.listener = this;
    }

    fun updateKitapTurListe(liste:List<KitapturModel>){
        kitapTurListe.clear();
        kitapTurListe.addAll(liste);
        notifyDataSetChanged();
    }

    override fun onKitapturDeleteClicked(view: View, selectedKitaptur: KitapturModel) {
        val ad = AlertDialog.Builder(view.rootView.context);
        ad.setMessage(selectedKitaptur.aciklama+" "+view.context.resources.getString(R.string.kitapTurSilmekIstiyormusunuz));
        ad.setPositiveButton(view.context.resources.getString(R.string.evet)){ dialogInterface, i ->
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("id",selectedKitaptur.kitapTurId);
            jsonObj.put("durum","PASIF");
            jsonObj.put("aciklama",selectedKitaptur.aciklama);

            viewModel.deleteKitapturParametre(jsonObj.toString());
            observeKitapturSil(view);
        }

        ad.setNegativeButton(view.context.resources.getString(R.string.hayir)){dialogInterface, i ->

        }
        ad.create().show();
    }

    private fun observeKitapturSil(view: View){
        viewModel.kitapTurSilResponse.observe(lifeCycleOwner,Observer{response->
            showSnackBar(view,response.statusMessage, SUCCESS);
            if(token != null){
                viewModel.kitapTurListeGetir(true);
            }
        });

        viewModel.kitapTurSilError.observe(lifeCycleOwner, Observer {error->
            error?.let {
                if(it){
                    showSnackBar(view,view.context.resources.getString(R.string.kitapTurSilmeHata), SUCCESS);
                }
            }
        });
    }

    class KitapTurViewHolder(var view: ItemKitapturBinding): RecyclerView.ViewHolder(view.root) {
    }
}