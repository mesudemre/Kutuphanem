package com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlack
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparent

@Composable
fun KitapYorumYazmaItem(
    kullaniciResim: String,
    yorumText: String,
    onChangeYorum: (String) -> Unit,
    sendYorum: () -> Unit
) {
    TextField(
        value = yorumText,
        modifier = Modifier
            .padding(2.sdp)
            .fillMaxWidth(),
        leadingIcon = {
            SubcomposeAsyncImage(model = kullaniciResim,
                modifier = Modifier
                    .size(32.sdp)
                    .clip(CircleShape),
                contentDescription = null,
                loading = {
                    if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                })
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorPalette.black,
            unfocusedIndicatorColor = MaterialTheme.colorPalette.transparent
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.kitapDetayYorumHint),
                style = MaterialTheme.typography.smallUbuntuTransparent
            )
        },
        textStyle = MaterialTheme.typography.smallUbuntuBlack,
        trailingIcon = {
            if (yorumText.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Send, contentDescription = stringResource(
                        id = R.string.kitapDetayYorumGonder
                    ),
                    tint = MaterialTheme.colorPalette.lacivert,
                    modifier = Modifier
                        .rippleClick {
                            sendYorum()
                        }
                )
            }
        },
        onValueChange = {
            onChangeYorum(it)
        })
}