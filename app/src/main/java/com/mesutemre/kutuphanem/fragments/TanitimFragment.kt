package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme
import com.mesutemre.kutuphanem.R

class TanitimFragment : Fragment() {

    private var index:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1;
    }

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tanitim, container, false);
        val tanitimTextView = root.findViewById<ComposeView>(R.id.tanitimTextView);
        when(index){
            0->{
                tanitimTextView.setContent {
                    MdcTheme {
                        compTanitimText(R.string.tanitimText1);
                    }
                }
            }
            1->{
                tanitimTextView.setContent {
                    MdcTheme {
                        compTanitimText(R.string.tanitimText2);
                    }
                }
            }
            2->{
                tanitimTextView.setContent {
                    MdcTheme {
                        compTanitimText(R.string.tanitimText3);
                    }
                }

            }

        }
        return root;
    }

    @ExperimentalAnimationApi
    @Composable
    private fun compTanitimText(stringId:Int){
        val alegryaFontFamily = FontFamily(Font(R.font.alegreya_bold));
        Text(
            text = stringResource(stringId),
            fontFamily = alegryaFontFamily,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = colorResource(id = R.color.whiteTextColor),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp, 0.dp, 5.dp)
        )
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int) =
            TanitimFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber);
                }
            }
    }
}