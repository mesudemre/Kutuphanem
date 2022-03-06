package com.mesutemre.kutuphanem

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.mesutemre.kutuphanem.job.ClearImageNotInArchiveWorker
import com.mesutemre.kutuphanem.job.GlobalExceptionCatchWorker
import com.mesutemre.kutuphanem.job.SendExceptionToServerWorker
import com.mesutemre.kutuphanem.util.enqueeOneTimeWorkManager
import com.mesutemre.kutuphanem.util.enqueePeriodicTimeWorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * @author Mesut Emre Çelenk
 * @sınce 10.03.2021
 * <b>Android'in tüm hilt(Dependency Injection) sürecini başlatan kod blogudur</b>
 */
@HiltAndroidApp
class KutuphanemApplication:Application(),Configuration.Provider {

    companion object {
        lateinit var instance:KutuphanemApplication
        private set
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        instance = this
        enqueeOneTimeWorkManager<GlobalExceptionCatchWorker>();
        enqueePeriodicTimeWorkManager<ClearImageNotInArchiveWorker>("imageClear")
        enqueePeriodicTimeWorkManager<SendExceptionToServerWorker>("exceptionSender")
    }
}