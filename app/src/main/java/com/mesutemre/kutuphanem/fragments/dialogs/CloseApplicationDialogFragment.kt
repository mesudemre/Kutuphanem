package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.mesutemre.kutuphanem.R

/**
 * @Author: mesutemre.celenk
 * @Date: 9.09.2021
 */
class CloseApplicationDialogFragment(val currentFragment: Fragment): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(requireContext());
        ad.setMessage(requireContext().getString(R.string.closeAppLabel));
        ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
            val isLollipopOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
            if(isLollipopOrAbove){
                currentFragment.activity?.finishAndRemoveTask();
            }else{
                currentFragment.activity?.finishAffinity();
            }
        }
        ad.setNegativeButton(requireContext().getString(R.string.hayir)){ dialogInterface, i ->
        }
        return ad.create();
    }

    override fun onDestroy() {
        super.onDestroy();
        this.dismiss();
    }
}