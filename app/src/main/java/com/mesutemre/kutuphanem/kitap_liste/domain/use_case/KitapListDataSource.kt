package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class KitapListDataSource @Inject constructor(
    private val kitapListeRepository: KitapListeRepository
) : PagingSource<Int, KitapDto>() {

    override fun getRefreshKey(state: PagingState<Int, KitapDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KitapDto> {
        return try {
            val page = params.key ?: 0
            val response = kitapListeRepository.getKitapListe(
                KitapDto(
                    minKayitNum = page * 3,
                    maxKayitNum = 3
                )
            )
            delay(500)
            LoadResult.Page(
                response.body() ?: emptyList(), prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.body()!!.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}