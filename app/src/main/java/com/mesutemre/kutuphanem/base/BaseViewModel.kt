package com.mesutemre.kutuphanem.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/**
 * @Author: mesutemre.celenk
 * @Date: 17.08.2021
 */
abstract class BaseViewModel(application: Application)
    :AndroidViewModel(application),CoroutineScope {

    val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main;

    open val context:Context = application.baseContext;

    abstract val disposible: CompositeDisposable;

    inline suspend fun <T:Any> serviceCall(call: suspend() -> Response<T>):BaseDataEvent<T>{
        val response:Response<T>;

        try {
            response = call.invoke();
        }catch (t:Throwable){
            return BaseDataEvent.Error(t.message!!);
        }

        return if (!response.isSuccessful){
            val errorBody = response.errorBody();
            BaseDataEvent.Error(errorBody.toString());
        }else {
            return if (response.body() == null) {
                BaseDataEvent.Error("Boş response");
            }else {
                BaseDataEvent.Success(response.body());
            }
        }
    }

    inline suspend fun <T:Any> dbCall(call: suspend() -> T):BaseDataEvent<T>{
        val response:T;

        try {
            response = call.invoke();
        }catch (t:Throwable){
            return BaseDataEvent.Error(t.message!!);
        }

        return if (response == null){
            BaseDataEvent.Error("Herhangi bir data bulunamadı!");
        }else {
            BaseDataEvent.Success(response);
        }
    }


    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}