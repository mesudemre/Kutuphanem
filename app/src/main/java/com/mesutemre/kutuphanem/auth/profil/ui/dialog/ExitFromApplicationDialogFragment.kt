package com.mesutemre.kutuphanem.auth.profil.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.auth.profil.ui.ProfilIslemFragmentDirections
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExitFromApplicationDialogFragment:DialogFragment() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(requireContext())
        ad.setMessage(requireContext().getString(R.string.exitFromApplicationMessage))
        ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
            customSharedPreferences.clearAllSharedPreferencesData()
            val action = ExitFromApplicationDialogFragmentDirections.actionExitApplicationDialogFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        ad.setNegativeButton(requireContext().getString(R.string.hayir)){ dialogInterface, i ->
        }
        return ad.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.dismiss()
    }
}