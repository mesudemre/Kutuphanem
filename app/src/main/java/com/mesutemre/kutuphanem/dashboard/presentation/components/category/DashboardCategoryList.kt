package com.mesutemre.kutuphanem.dashboard.presentation.components.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem

@Composable
fun DashboardCategoryList(list:List<DashboardKategoriItem>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(list) {item->
            DashboardCategoryItem(item = item)
        }
    }
}