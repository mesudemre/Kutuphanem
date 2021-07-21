package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import com.mesutemre.kutuphanem.R

class TanitimFragment : Fragment() {

    private var index:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tanitim, container, false);
        when(index){
            0->{
                root.findViewById<MaterialTextView>(R.id.tanitimTextView1).visibility = View.VISIBLE;
                root.findViewById<MaterialTextView>(R.id.tanitimTextView2).visibility = View.GONE;
                root.findViewById<MaterialTextView>(R.id.tanitimTextView3).visibility = View.GONE;
            }
            1->{
                root.findViewById<MaterialTextView>(R.id.tanitimTextView1).visibility = View.GONE;
                root.findViewById<MaterialTextView>(R.id.tanitimTextView2).visibility = View.VISIBLE;
                root.findViewById<MaterialTextView>(R.id.tanitimTextView3).visibility = View.GONE;
            }
            2->{
                root.findViewById<MaterialTextView>(R.id.tanitimTextView1).visibility = View.GONE;
                root.findViewById<MaterialTextView>(R.id.tanitimTextView2).visibility = View.GONE;
                root.findViewById<MaterialTextView>(R.id.tanitimTextView3).visibility = View.VISIBLE;
            }

        }
        return root;
    }


    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int) =
            TanitimFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
    }
}