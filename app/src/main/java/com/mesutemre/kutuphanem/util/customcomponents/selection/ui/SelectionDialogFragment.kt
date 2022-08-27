package com.mesutemre.kutuphanem.util.customcomponents.selection.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.SelectionDialogBinding
import com.mesutemre.kutuphanem.util.customcomponents.selection.adapter.SelectionAdapter
import com.mesutemre.kutuphanem.util.customcomponents.selection.model.SelectItemModel
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.setDisplayMetricHeight

/**
 * @Author: mesutemre.celenk
 * @Date: 13.11.2021
 */
class SelectionDialogFragment(
    val selectionTitle:String,
    val isFilterable:Boolean,
    val selectionList:MutableList<SelectItemModel>,
    val onSelectItem:(item: SelectItemModel)->Unit,
    val onCloseDlg:()->Unit
): BottomSheetDialogFragment(),SearchView.OnQueryTextListener {

    private lateinit var binding:SelectionDialogBinding
    private var adapter: SelectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                it.setDisplayMetricHeight(0.8)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectionDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectionTitleTextViewId.text = selectionTitle
        binding.selectionRecyclerViewId.layoutManager = LinearLayoutManager(context)

        adapter = SelectionAdapter(::selectItem)
        adapter!!.renderLists(selectionList)
        adapter!!.notifyDataSetChanged()

        binding.selectionRecyclerViewId.adapter = adapter

        if(isFilterable){
            binding.selectionSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            binding.selectionSearchView.isIconifiedByDefault = false
            binding.selectionSearchView.queryHint = view.context.getString(R.string.pleaseSearch)
            binding.selectionSearchView.setOnQueryTextListener(this)
        }else {
            binding.selectionSearchView.hideComponent()
        }
    }

    private fun selectItem(item: SelectItemModel) {
        this.onSelectItem(item)
        this.dismiss()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter!!.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter!!.filter.filter(newText)
        return false
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onCloseDlg()
    }
}