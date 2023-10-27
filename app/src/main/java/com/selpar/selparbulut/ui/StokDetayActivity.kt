package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.selpar.selparbulut.R

class StokDetayActivity : AppCompatActivity() {
    lateinit var txtStokNumarasi:TextView
    lateinit var txtStokAdi:TextView
    lateinit var txtOEMKodu:TextView
    lateinit var txtListeFiyati:TextView
    lateinit var txtParaBirimi:TextView
    lateinit var txtUretici:TextView
    lateinit var txtKalan:TextView
    lateinit var txtRafYeri:TextView
    lateinit var txtSiparisAdeti:TextView
    lateinit var txtStokTuru:TextView
    lateinit var txtAciklama:TextView
    lateinit var txtStokGrupKodu:TextView
    lateinit var txtEticaretFiyat:TextView
    lateinit var txtGuncellemeTarihi:TextView
    lateinit var txtEklemeTarihi:TextView
    lateinit var txtKategoriIdler:TextView
    lateinit var txtAracIdler:TextView
    lateinit var ivBack:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok_detay)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })

    }

    private fun onBaslat() {
        ivBack=findViewById(R.id.ivBack)
        txtStokNumarasi=findViewById(R.id.txtStokNumarasi)
        txtStokAdi=findViewById(R.id.txtStokAdi)
        txtOEMKodu=findViewById(R.id.txtOEMKodu)
        txtListeFiyati=findViewById(R.id.txtListeFiyati)
        txtParaBirimi=findViewById(R.id.txtParaBirimi)
        txtUretici=findViewById(R.id.txtUretici)
        txtKalan=findViewById(R.id.txtKalan)
        txtRafYeri=findViewById(R.id.txtRafYeri)
        txtSiparisAdeti=findViewById(R.id.txtSiparisAdeti)
        txtStokTuru=findViewById(R.id.txtStokTuru)
        txtAciklama=findViewById(R.id.txtAciklama)
        txtStokGrupKodu=findViewById(R.id.txtStokGrupKodu)
        txtEticaretFiyat=findViewById(R.id.txtEticaretFiyat)
        txtGuncellemeTarihi=findViewById(R.id.txtGuncellemeTarihi)
        txtEklemeTarihi=findViewById(R.id.txtEklemeTarihi)
        txtKategoriIdler=findViewById(R.id.txtKategoriIdler)
        txtAracIdler=findViewById(R.id.txtAracIdler)
        txtStokNumarasi.setText(getString(R.string.stoknumarasi)+": "+intent.getStringExtra("StokNumarasi"))
        txtStokAdi.setText(getString(R.string.stokadi)+": "+ intent.getStringExtra("StokAdi"))
        txtOEMKodu.setText(getString(R.string.oemkodu)+": "+intent.getStringExtra("OEMKodu"))
        txtListeFiyati.setText(getString(R.string.listefiyati)+": "+intent.getStringExtra("ListeFiyati"))
        txtParaBirimi.setText(getString(R.string.parabirimi)+": "+intent.getStringExtra("ParaBirimi"))
        txtUretici.setText(getString(R.string.uretici)+": "+intent.getStringExtra("Uretici"))
        txtKalan.setText(getString(R.string.kalan)+": "+intent.getStringExtra("Kalan"))
        txtRafYeri.setText(getString(R.string.rafyeri)+": "+intent.getStringExtra("RafYeri"))
        txtSiparisAdeti.setText(getString(R.string.siparisadeti)+": "+intent.getStringExtra("SiparisAdeti"))
        txtStokTuru.setText(getString(R.string.stokturu)+": "+intent.getStringExtra("StokTuru"))
        txtAciklama.setText(getString(R.string.aciklama)+": "+intent.getStringExtra("Aciklama"))
        txtStokGrupKodu.setText(getString(R.string.stokgrupkodu)+": "+intent.getStringExtra("StokGrupKodu"))
        txtEticaretFiyat.setText(getString(R.string.eticaretfiyat)+": "+intent.getStringExtra("EticaretFiyat"))
        txtGuncellemeTarihi.setText(getString(R.string.guncellemetarihi)+": "+intent.getStringExtra("GuncellemeTarihi"))
        txtEklemeTarihi.setText(getString(R.string.eklemetarihi)+": "+intent.getStringExtra("EklemeTarihi"))
        txtKategoriIdler.setText(getString(R.string.kategoriIdler)+": "+intent.getStringExtra("KategoriIdler"))
        txtAracIdler.setText(getString(R.string.aracIdler)+": "+intent.getStringExtra("AracIdler"))

    }
}