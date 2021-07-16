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
import com.mesutemre.kutuphanem.databinding.ItemYayineviBinding
import com.mesutemre.kutuphanem.listener.YayineviDeleteClickListener
import com.mesutemre.kutuphanem.model.SnackTypeEnum
import com.mesutemre.kutuphanem.model.YayineviModel
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewmodels.ParametreYayineviViewModel
import org.json.JSONObject

class YayinEviAdapter(val yayinEviListe:ArrayList<YayineviModel>,
                      val viewModel: ParametreYayineviViewModel,
                      val lifeCycleOwner:LifecycleOwner,
                      val token:String?):
        RecyclerView.Adapter<YayinEviAdapter.YayinEviViewHolder>(),YayineviDeleteClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YayinEviViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = DataBindingUtil.inflate<ItemYayineviBinding>(inflater,R.layout.item_yayinevi,parent,false);
        return YayinEviViewHolder(view);
    }

    override fun getItemCount(): Int {
        return yayinEviListe.size;
    }

    override fun onBindViewHolder(holder: YayinEviViewHolder, position: Int) {
        holder.view.yayinevi = yayinEviListe.get(position);
        holder.view.listener = this;
    }

    fun updateYayineviListe(liste:List<YayineviModel>){
        yayinEviListe.clear();
        yayinEviListe.addAll(liste);
        notifyDataSetChanged();
    }

    override fun onYayineviDeleteClicked(view: View,selectedYayinevi: YayineviModel) {
        val ad = AlertDialog.Builder(view.rootView.context);
        ad.setMessage(selectedYayinevi.aciklama+" "+view.context.resources.getString(R.string.yayinEviSilmekIstiyormusunuz));
        ad.setPositiveButton(view.context.resources.getString(R.string.evet)){ dialogInterface, i ->
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("id",selectedYayinevi.yayinEviId);
            jsonObj.put("durum","PASIF");
            jsonObj.put("aciklama",selectedYayinevi.aciklama);

            viewModel.deleteYayineviParametre(jsonObj.toString());
            observeYayinevSil(view);
        }

        ad.setNegativeButton(view.context.resources.getString(R.string.hayir)){dialogInterface, i ->

        }
        ad.create().show();
    }

    private fun observeYayinevSil(view: View){
        viewModel.yayinEviSilResponse.observe(lifeCycleOwner, Observer { response->
            showSnackBar(view,response.statusMessage,SnackTypeEnum.SUCCESS);
            if(token != null){
                viewModel.yayinEviListeGetir(true);
            }

        });

        viewModel.yayinEviSilError.observe(lifeCycleOwner, Observer {error->
            error?.let {
                if(it){
                    showSnackBar(view,view.context.resources.getString(R.string.yayinEviSilmeHata),SnackTypeEnum.ERROR);
                }
            }
        });
    }

    class YayinEviViewHolder(var view:ItemYayineviBinding):RecyclerView.ViewHolder(view.root) {
    }

}