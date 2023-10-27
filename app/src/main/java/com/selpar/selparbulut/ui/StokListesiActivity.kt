package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.cariGetirAdapter
import com.selpar.selparbulut.adapter.stokGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.CariListesiModel
import com.selpar.selparbulut.model.StokModel
import org.json.JSONArray
import java.util.ArrayList

class StokListesiActivity : AppCompatActivity() {
    lateinit var  ivBack:ImageView
    lateinit var  btn_search:ImageView
    lateinit var reclerview_stok_listesi:RecyclerView
    lateinit var username:String
    lateinit var uid:String
    lateinit var txtstok_no:EditText
    var StokID= ArrayList<String>()
    var StokNumarasi= ArrayList<String>()
    var StokAdi= ArrayList<String>()
    var OEMKodu= ArrayList<String>()
    var DegisenMuadil= ArrayList<String>()
    var UreticiNumarasi= ArrayList<String>()
    var ListeFiyati= ArrayList<String>()
    var ParaBirimi= ArrayList<String>()
    var Uretici= ArrayList<String>()
    var Kalan= ArrayList<String>()
    var RafYeri= ArrayList<String>()
    var SiparisAdeti= ArrayList<String>()
    var StokTuru= ArrayList<String>()
    var Aciklama= ArrayList<String>()
    var StokGrupKodu= ArrayList<String>()
    var EticaretFiyat= ArrayList<String>()
    var Resim1= ArrayList<String>()
    var Resim2= ArrayList<String>()
    var ResimLink1= ArrayList<String>()
    var ResimLink2= ArrayList<String>()
    var Birim= ArrayList<String>()
    var GuncellemeTarihi= ArrayList<String>()
    var EklemeTarihi= ArrayList<String>()
    var KategoriIdler= ArrayList<String>()
    var AracIdler= ArrayList<String>()
    private lateinit var newArrayList: ArrayList<StokModel>
    lateinit var progressBar: ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok_listesi)
        onBaslat()
        stokListesiGetir()
        ivBack=findViewById(R.id.ivBack)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        //btn_search.setOnClickListener { arananStokGetir(txtstok_no.getText().toString()) }
        txtstok_no.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Handle the Enter key press here
                // You can perform any action you want when Enter is pressed
                // For example, you can hide the keyboard or process the input
                // ...
                arananStokGetir(txtstok_no.getText().toString())
                return@OnKeyListener true // Consume the key event
            }
            false // Let other key events be handled as usual
        })
        progressBar = findViewById(R.id.progressBar)
        Toast.makeText(this,getString(R.string.stoklarlisteleniyor),Toast.LENGTH_LONG).show()
        startLoadingAnimation()
    }

    private fun arananStokGetir(arananCari: String) {
        if(arananCari.isNotEmpty() && arananCari.length>3 ){
            stokArananGetir(arananCari)
        }else{
            Toast.makeText(this,getString(R.string.lutfenstokbilgisigiriniz),Toast.LENGTH_LONG).show()
        }

    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 2000) // Replace 3000 with your desired loading time
    }

    private fun stokArananGetir(arananStok: String) {
        startLoadingAnimation()
        Toast.makeText(this,getString(R.string.aranankriteregorestokbilgisilisteleniyor), Toast.LENGTH_LONG).show()

        StokID.clear()
        StokNumarasi.clear()
        StokAdi.clear()
        OEMKodu.clear()
        DegisenMuadil.clear()
        UreticiNumarasi.clear()
        ListeFiyati.clear()
        ParaBirimi.clear()
        Uretici.clear()
        Kalan.clear()
        RafYeri.clear()
        SiparisAdeti.clear()
        StokTuru.clear()
        Aciklama.clear()
        StokGrupKodu.clear()
        EticaretFiyat.clear()
        Resim1.clear()
        Resim2.clear()
        ResimLink1.clear()
        ResimLink2.clear()
        Birim.clear()
        GuncellemeTarihi.clear()
        EklemeTarihi.clear()
        KategoriIdler.clear()
        AracIdler.clear()

        val urlek ="&username="+username+"&uid="+uid+"&search="+arananStok

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.StokListesiArama + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("stok_listesi_aranan", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        StokID.add(item.getString("StokID"))
                        StokNumarasi.add(item.getString("StokNumarasi"))
                        StokAdi.add(item.getString("StokAdi"))
                        OEMKodu.add(item.getString("OEMKodu"))
                        DegisenMuadil.add(item.getString("DegisenMuadil"))
                        UreticiNumarasi.add(item.getString("UreticiNumarasi"))
                        ListeFiyati.add(item.getString("ListeFiyati"))
                        ParaBirimi.add(item.getString("ParaBirimi"))
                        Uretici.add(item.getString("Uretici"))
                        Kalan.add(item.getString("Kalan"))
                        RafYeri.add(item.getString("RafYeri"))
                        SiparisAdeti.add(item.getString("SiparisAdeti"))
                        StokTuru.add(item.getString("StokTuru"))
                        Aciklama.add(item.getString("Aciklama"))
                        StokGrupKodu.add(item.getString("StokGrupKodu"))
                        EticaretFiyat.add(item.getString("EticaretFiyat"))
                        Resim1.add(item.getString("Resim1"))
                        Resim2.add(item.getString("Resim2"))
                        ResimLink1.add(item.getString("ResimLink1"))
                        ResimLink2.add(item.getString("ResimLink2"))
                        Birim.add(item.getString("Birim"))
                        GuncellemeTarihi.add(item.getString("GuncellemeTarihi"))
                        EklemeTarihi.add(item.getString("EklemeTarihi"))
                        KategoriIdler.add(item.getString("KategoriIdler"))
                        AracIdler.add(item.getString("AracIdler"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.stokbulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.stokbulunmamakta))


                }
                reclerview_stok_listesi.layoutManager = LinearLayoutManager(this)
                reclerview_stok_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<StokModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.stokbulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.stokbulunmamakta))

            }
        )
        queue.add(request)











    }

    private fun onBaslat() {
        reclerview_stok_listesi=findViewById(R.id.reclerview_stok_listesi)
        btn_search=findViewById(R.id.btn_search)
        txtstok_no=findViewById(R.id.txtstok_no)
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
    }
    private fun stokListesiGetir() {
        val urlek ="&username="+username+"&uid="+uid
        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.StokListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("stok_listesi", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        StokID.add(item.getString("StokID"))
                        StokNumarasi.add(item.getString("StokNumarasi"))
                        StokAdi.add(item.getString("StokAdi"))
                        OEMKodu.add(item.getString("OEMKodu"))
                        DegisenMuadil.add(item.getString("DegisenMuadil"))
                        UreticiNumarasi.add(item.getString("UreticiNumarasi"))
                        ListeFiyati.add(item.getString("ListeFiyati"))
                        ParaBirimi.add(item.getString("ParaBirimi"))
                        Uretici.add(item.getString("Uretici"))
                        Kalan.add(item.getString("Kalan"))
                        RafYeri.add(item.getString("RafYeri"))
                        SiparisAdeti.add(item.getString("SiparisAdeti"))
                        StokTuru.add(item.getString("StokTuru"))
                        Aciklama.add(item.getString("Aciklama"))
                        StokGrupKodu.add(item.getString("StokGrupKodu"))
                        EticaretFiyat.add(item.getString("EticaretFiyat"))
                        Resim1.add(item.getString("Resim1"))
                        Resim2.add(item.getString("Resim2"))
                        ResimLink1.add(item.getString("ResimLink1"))
                        ResimLink2.add(item.getString("ResimLink2"))
                        Birim.add(item.getString("Birim"))
                        GuncellemeTarihi.add(item.getString("GuncellemeTarihi"))
                        EklemeTarihi.add(item.getString("EklemeTarihi"))
                        KategoriIdler.add(item.getString("KategoriIdler"))
                        AracIdler.add(item.getString("AracIdler"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.stokbulunmamakta))


                }
                reclerview_stok_listesi.layoutManager = LinearLayoutManager(this)
                reclerview_stok_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<StokModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.stokbulunmamakta), Toast.LENGTH_LONG).show()
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.stokbulunmamakta))
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        queue.add(request)
    }
    private fun getUserData(context: Context) {
        for (i in StokID.indices) {
            val news = StokModel(
                username,
                uid,
                StokID[i],
                StokNumarasi[i],
                StokAdi[i],
                OEMKodu[i],
                DegisenMuadil[i],
                UreticiNumarasi[i],
                ListeFiyati[i],
                ParaBirimi[i],
                Uretici[i],
                Kalan[i],
                RafYeri[i],
                SiparisAdeti[i],
                StokTuru[i],
                Aciklama[i],
                StokGrupKodu[i],
                EticaretFiyat[i],
                Resim1[i],
                Resim2[i],
                ResimLink1[i],
                ResimLink2[i],
                Birim[i],
                GuncellemeTarihi[i],
                EklemeTarihi[i],
                KategoriIdler[i],
                AracIdler[i],

            )


            newArrayList.add(news)
        }

        reclerview_stok_listesi.adapter = stokGetirAdapter(newArrayList)

    }

}