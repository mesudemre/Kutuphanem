package com.mesutemre.kutuphanem.kitap.liste.datasource

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/**
 * @Author: mesutemre.celenk
 * @Date: 9.09.2021
 */
class KitapBegeniPagingSource(val kitapSevice: IKitapService, context:Context):PagingSource<Int, KitapModel>() {

    private val context:Context = context;

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KitapModel> {
        var page = params.key ?: 0
        try {
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("minKayitNum",(page)*4);
            jsonObj.put("maxKayitNum",4);

            val response = kitapSevice.getBegenilenKitapListe(jsonObj.toString());
            if(response.code() != 204){
               return LoadResult.Page(
                    response.body()!!,prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.body()!!.isEmpty()) null else page + 1
                )
            }else{
                return LoadResult.Error(Exception(context.getString(R.string.begeniListeBos)))
            }

        }catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, KitapModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}