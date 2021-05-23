package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.mesutemre.kutuphanem.R
import java.util.*

class DogumTarihiDialogFragment(val editText:TextInputEditText,val dogumTarihi:Date): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal:Calendar = Calendar.getInstance();
        cal.time = this.dogumTarihi;
        val gun = cal.get(Calendar.DAY_OF_MONTH);
        val ay = cal.get(Calendar.MONTH)+1;
        val yil = cal.get(Calendar.YEAR);
        var datePicker = DatePickerDialog(
            requireContext(),
            R.style.DatePickerTheme,
            DatePickerDialog.OnDateSetListener { datePicker, y, a, g ->
            editText.setText("$g.${a+1}.$y");
        },yil,ay-1,gun);
        if(ay<10){
            datePicker = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                DatePickerDialog.OnDateSetListener { datePicker, y, a, g ->
                editText.setText("$g.0${a+1}.$y");
            },yil,ay-1,gun);
        }

        datePicker!!.setTitle(resources.getString(R.string.selectTarih));
        datePicker!!.setButton(DialogInterface.BUTTON_POSITIVE,resources.getString(R.string.ayarlaLabel),datePicker);
        datePicker!!.setButton(DialogInterface.BUTTON_NEGATIVE,resources.getString(R.string.iptalLabel),datePicker);

        return datePicker;
    }

    override fun onDestroy() {
        super.onDestroy();
        this.dismiss();
    }
}