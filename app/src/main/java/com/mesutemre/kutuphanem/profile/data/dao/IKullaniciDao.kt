package com.mesutemre.kutuphanem.profile.data.dao

import androidx.room.*
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciIlgiAlanEntity

@Dao
interface IKullaniciDao {

    @Transaction
    @Query(
        "SELECT k.*,kia.* FROM KullaniciBilgiEntity k JOIN KullaniciIlgiAlanEntity kia ON kia.kullaniciad = k.kullaniciad " +
                " WHERE k.kullaniciad=:kullaniciAd"
    )
    fun getKullaniciBilgiByKullaniciAdi(kullaniciAd: String): Map<KullaniciBilgiCinsiyetEntity, List<KullaniciIlgiAlanEntity>>

    @Insert
    fun cinsiyetKaydet(vararg cinsiyetEntity: KullaniciCinsiyetEntity)

    @Insert
    fun kullaniciIlgiAlanKaydet(vararg ilgiAlan: KullaniciIlgiAlanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun kullaniciBilgiKaydet(vararg kullaniciBilgi: KullaniciBilgiEntity)

    @Query("DELETE FROM KullaniciBilgiEntity WHERE kullaniciad=:kullaniciAd")
    fun deleteKullaniciBilgiByKullaniciAd(kullaniciAd: String)

    @Query("DELETE FROM KullaniciCinsiyetEntity WHERE kullaniciad=:kullaniciAd")
    fun deleteKullaniciCinsiyet(kullaniciAd: String)

    @Query("DELETE FROM KullaniciIlgiAlanEntity WHERE kullaniciad=:kullaniciAd")
    fun deleteKullaniciIlgiAlanListe(kullaniciAd: String)
}