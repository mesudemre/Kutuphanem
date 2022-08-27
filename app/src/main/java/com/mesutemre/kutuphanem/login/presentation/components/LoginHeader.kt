package com.mesutemre.kutuphanem.login.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.Typography
import com.mesutemre.kutuphanem.ui.theme.loginShapeBackground
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.xxlargeAllegraWhiteBold

@Composable
fun LoginHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.sdp)
            .clip(RoundedCornerShape(bottomStart = 28.sdp, bottomEnd = 28.sdp))
            .background(loginShapeBackground)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_login_bk),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 32.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = Typography.xxlargeAllegraWhiteBold
            )
        }
    }
}
