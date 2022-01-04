package com.mesutemre.kutuphanem.util.customcomponents.selection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemSelectionBinding
import com.mesutemre.kutuphanem.util.customcomponents.selection.model.SelectItemModel

/**
 * @Author: mesutemre.celenk
 * @Date: 13.11.2021
 */
class SelectionAdapter(val onSelectItem:(item: SelectItemModel)->Unit
): RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder>(),Filterable{

    private var selectionList:MutableList<SelectItemModel> = mutableListOf();
    private var filteredSelectionList:MutableList<SelectItemModel> = mutableListOf();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = DataBindingUtil.inflate<ItemSelectionBinding>(inflater,
            R.layout.item_selection,parent,false);
        return SelectionViewHolder(view);
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        holder.view.selectItem = filteredSelectionList.get(position);
        holder.view.onSelectItem = onSelectItem;
    }

    override fun getItemCount(): Int {
        return filteredSelectionList.size;
    }

    fun renderLists(list:MutableList<SelectItemModel>){
        selectionList = list;
        filteredSelectionList = selectionList;
        notifyDataSetChanged();
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filteredSelectionList = selectionList else {
                    val filteredList = mutableListOf<SelectItemModel>();
                    selectionList
                        .filter {
                                    (it.selectedItemLabel.contains(constraint!!))

                        }
                        .forEach { filteredList.add(it) }
                    filteredSelectionList = filteredList

                }
                return FilterResults().apply { values = filteredSelectionList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredSelectionList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as MutableList<SelectItemModel>
                notifyDataSetChanged()
            }

        }
    }

    companion object class SelectionViewHolder(var view: ItemSelectionBinding):RecyclerView.ViewHolder(view.root) {
    }
}