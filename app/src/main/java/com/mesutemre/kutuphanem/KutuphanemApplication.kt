package com.mesutemre.kutuphanem

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.mesutemre.kutuphanem.job.ClearImageNotInArchiveWorker
import com.mesutemre.kutuphanem.job.GlobalExceptionCatchWorker
import com.mesutemre.kutuphanem.job.SendExceptionToServerWorker
import com.mesutemre.kutuphanem.util.enqueeOneTimeWorkManager
import com.mesutemre.kutuphanem.util.enqueePeriodicTimeWorkManager
import com.mesutemre.kutuphanem_base.KutuphanemBaseApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * @author Mesut Emre Çelenk
 * @sınce 10.03.2021
 * <b>Android'in tüm hilt(Dependency Injection) sürecini başlatan kod blogudur</b>
 */
@HiltAndroidApp
class KutuphanemApplication:KutuphanemBaseApplication(),Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        enqueeOneTimeWorkManager<GlobalExceptionCatchWorker>();
        enqueePeriodicTimeWorkManager<ClearImageNotInArchiveWorker>("imageClear")
        enqueePeriodicTimeWorkManager<SendExceptionToServerWorker>("exceptionSender")
    }
}