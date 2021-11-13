package com.mesutemre.kutuphanem.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.adapters.SelectionAdapter
import com.mesutemre.kutuphanem.databinding.SelectionDialogBinding
import com.mesutemre.kutuphanem.model.SelectItemModel
import com.mesutemre.kutuphanem.util.hideComponent

/**
 * @Author: mesutemre.celenk
 * @Date: 13.11.2021
 */
class SelectionDialogFragment(
    val isFilterable:Boolean,
    val selectionList:MutableList<SelectItemModel>,
    val onSelectItem:(item:SelectItemModel)->Unit
): BottomSheetDialogFragment(),SearchView.OnQueryTextListener {

    private lateinit var binding:SelectionDialogBinding;
    private var adapter:SelectionAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectionDialogBinding.inflate(inflater);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectionRecyclerViewId.layoutManager = LinearLayoutManager(context);

        adapter = SelectionAdapter(::selectItem);
        adapter!!.renderLists(selectionList);
        adapter!!.notifyDataSetChanged();

        binding.selectionRecyclerViewId.adapter = adapter;

        if(isFilterable){
            binding.selectionSearchView.setOnQueryTextListener(this)
        }else {
            binding.selectionSearchView.hideComponent();
        }
    }

    private fun selectItem(item: SelectItemModel) {
        this.onSelectItem(item);
        this.dismiss();
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter!!.filter.filter(query);
        return false;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter!!.filter.filter(newText);
        return false;
    }

}