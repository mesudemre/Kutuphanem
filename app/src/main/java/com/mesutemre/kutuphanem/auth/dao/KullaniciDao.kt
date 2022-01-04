package com.mesutemre.kutuphanem.auth.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.auth.profil.model.KullaniciKitapTurModel

@Dao
interface KullaniciDao {

    @Query("SELECT username,ad,soyad,eposta,dogumTarihi,resim,cinsiyet,haberdarmi FROM kullanici WHERE username=:p_username")
    suspend fun getKullaniciBilgiByUsername(p_username:String): Kullanici;

    @Insert
    suspend fun kullaniciKaydet(kullanici: Kullanici):Unit;

    @Query("DELETE FROM Kullanici WHERE username=:p_username")
    suspend fun kullaniciSil(p_username: String):Unit;

    @Query("SELECT * FROM KullaniciKitapTurModel WHERE username=:p_username")
    suspend fun getKullaniciIlgiAlanListe(p_username: String):List<KullaniciKitapTurModel>;

    @Insert
    suspend fun kullaniciIlgialanKaydet(vararg ilgiAlan: KullaniciKitapTurModel):Unit;

    @Query("DELETE FROM KullaniciKitapTurModel WHERE username=:p_username")
    suspend fun kullaniciIlgiAlanSil(p_username: String):Unit;
}