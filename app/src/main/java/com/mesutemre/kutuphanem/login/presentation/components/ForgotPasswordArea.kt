package com.mesutemre.kutuphanem.login.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.thinyAllegraPrimary

@Composable
fun ForgotPasswordArea() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.sdp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.sifremiUnuttumLabel),
                tint = MaterialTheme.colorPalette.primaryTextColor
            )
            Text(
                text = stringResource(id = R.string.sifremiUnuttumLabel),
                style = MaterialTheme.typography.thinyAllegraPrimary,
                modifier = Modifier
                    .padding(start = 4.sdp)
            )
        }
    }
}