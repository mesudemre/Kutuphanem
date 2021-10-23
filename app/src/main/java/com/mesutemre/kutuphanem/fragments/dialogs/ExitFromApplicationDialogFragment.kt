package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.fragments.ProfilIslemFragmentDirections
import com.mesutemre.kutuphanem.util.CustomSharedPreferences

class ExitFromApplicationDialogFragment(val customSharedPreferences: CustomSharedPreferences,
                                        val currentFragment:Fragment):DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(requireContext());
        ad.setMessage(requireContext().getString(R.string.exitFromApplicationMessage));
        ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
            customSharedPreferences.clearAllSharedPreferencesData();
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToLoginFragment();
            currentFragment.view?.let {
                Navigation.findNavController(it).navigate(action)
            };
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