package com.selpar.selparbulut.ui

import android.content.Context
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.oncekiOnarimGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.OncekiOnarimModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class OncekiOnarimlarActivity : AppCompatActivity() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    lateinit var KullaniciId:String
    lateinit var recylerview_acikkartlar: RecyclerView
    lateinit var txtarac_no: EditText
    lateinit var btn_bilgi_ara: ImageView
    private lateinit var newArrayList: ArrayList<OncekiOnarimModel>
    var KabulID= ArrayList<String>()
    var FirmaKodu= ArrayList<String>()
    var FirmaAdi= ArrayList<String>()
    var KabulNo= ArrayList<String>()
    var GirisTarihi= ArrayList<String>()
    var Plaka= ArrayList<String>()
    var KabulDurumu= ArrayList<String>()
    var Kullanici= ArrayList<String>()
    var KabulTuru= ArrayList<String>()
    var TrafigeCikis= ArrayList<String>()
    var AracKM= ArrayList<String>()
    var Evraktarihi= ArrayList<String>()
    var Evrakno= ArrayList<String>()
    var Cariunvan= ArrayList<String>()
    var VergiNo= ArrayList<String>()
    var VergiDairesi= ArrayList<String>()
    var Adresi= ArrayList<String>()
    var Il= ArrayList<String>()
    var Ilce= ArrayList<String>()
    var CariNo= ArrayList<String>()
    var CariTelefon= ArrayList<String>()
    var AracMarka= ArrayList<String>()
    var AracModel= ArrayList<String>()
    var AracModelYili= ArrayList<String>()
    var AracSaseno= ArrayList<String>()
    var AracMotorNo= ArrayList<String>()
    var FaturaNo= ArrayList<String>()
    var FaturaTarihi= ArrayList<String>()
    var CariGSM= ArrayList<String>()
    var KabulTutar= ArrayList<String>()
    var PesonelAdi= ArrayList<String>()
    var MusteriID= ArrayList<String>()
    var EvrakID= ArrayList<String>()
    var KapatmaTarihi= ArrayList<String>()
    var Sil= ArrayList<String>()
    var KapatmaAciklama= ArrayList<String>()
    var AracResim= ArrayList<String>()
    var AracMarkaResim= ArrayList<String>()
    var AracRengi= ArrayList<String>()
    var AracVersiyon= ArrayList<String>()
    var YakitDurumu= ArrayList<String>()
    var SarjDurumu= ArrayList<String>()
    var TahminiTeslimTarihi= ArrayList<String>()
    var TahminiTutar= ArrayList<String>()
    lateinit var ivBack:ImageView
    lateinit var progressBar:ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onceki_onarimlar)
        onBaslat()

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.oncekionarimlardaaramayapabilirsin), Toast.LENGTH_LONG).show()

        //btn_bilgi_ara.setOnClickListener { onarimdaAra(txtarac_no.getText().toString()) }
        txtarac_no.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Handle the Enter key press here
                // You can perform any action you want when Enter is pressed
                // For example, you can hide the keyboard or process the input
                // ...
                onarimdaAra(txtarac_no.getText().toString())
                return@OnKeyListener true // Consume the key event
            }
            false // Let other key events be handled as usual
        })
    }

    private fun onBaslat() {
        recylerview_acikkartlar=findViewById(R.id.recylerview_acikkartlar)
        ivBack=findViewById(R.id.ivBack)
        progressBar=findViewById(R.id.progressBar)
        btn_bilgi_ara=findViewById(R.id.btn_bilgi_ara)
        txtarac_no=findViewById(R.id.txtarac_no)
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        FirmaLogo=intent.getStringExtra("FirmaLogo").toString()
        KullaniciId=intent.getStringExtra("KullaniciId").toString()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onarimdaAra(plaka: String) {
        if(plaka.isNotEmpty() && plaka.length>=3){
            onarimArananPlaka(plaka)
        }else{
            Toast.makeText(this,getString(R.string.lutfenaracbilgisigiriniz), Toast.LENGTH_LONG).show()
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onarimArananPlaka(plaka: String) {
        Toast.makeText(this,getString(R.string.oncekionarimlardaaramayapiliyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(30)
        val urlek ="&username="+username+"&uid="+uid+"&search="+plaka.toString().trim()+"&baslangic_Tarihi="+fiveDaysAgo+"&bitis_Tarihi="+fiveDaysAgo+"&kabuldurumu=KAPALI"

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartlariArama + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onceki_onarim_listesi_aranan", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        KabulID.add(item.getString("KabulID"))
                        FirmaKodu.add(item.getString("FirmaKodu"))
                        // Toast.makeText(this,KabulId)
                        FirmaAdi.add(item.getString("FirmaAdi"))
                        TrafigeCikis.add(item.getString("TrafigeCikis"))
                        KabulNo.add(item.getString("KabulNo"))
                        GirisTarihi.add(item.getString("GirisTarihi"))
                        Plaka.add(item.getString("Plaka"))
                        KabulDurumu.add(item.getString("KabulDurumu"))
                        Kullanici.add(item.getString("Kullanici"))
                        KabulTuru.add(item.getString("KabulTuru"))
                        KabulTutar.add(item.getString("KabulTutar"))
                        Cariunvan.add(item.getString("Cariunvan"))
                        AracMarka.add(item.getString("AracMarka"))
                        AracModel.add(item.getString("AracModel"))
                        AracModelYili.add(item.getString("AracModelYili"))
                        AracResim.add(item.getString("AracResim"))
                        AracMarkaResim.add(item.getString("AracMarkaResim"))
                        CariTelefon.add(item.getString("CariTelefon"))
                        AracKM.add(item.getString("AracKM"))
                        VergiNo.add(item.getString("VergiNo"))
                        VergiDairesi.add(item.getString("VergiDairesi"))
                        Adresi.add(item.getString("Adresi"))
                        Il.add(item.getString("Il"))
                        Ilce.add(item.getString("Ilce"))
                        CariGSM.add(item.getString("CariGSM"))
                        TrafigeCikis.add(item.getString("TrafigeCikis"))
                        Evraktarihi.add(item.getString("Evraktarihi"))
                        CariNo.add(item.getString("CariNo"))
                        Evrakno.add(item.getString("Evrakno"))
                        AracSaseno.add(item.getString("AracSaseno"))
                        AracMotorNo.add(item.getString("AracMotorNo"))
                        FaturaNo.add(item.getString("FaturaNo"))
                        FaturaTarihi.add(item.getString("FaturaTarihi"))
                        PesonelAdi.add(item.getString("PesonelAdi"))
                        MusteriID.add(item.getString("MusteriID"))
                        EvrakID.add(item.getString("EvrakID"))
                        KapatmaTarihi.add(item.getString("KapatmaTarihi"))
                        Sil.add(item.getString("Sil"))
                        KapatmaAciklama.add(item.getString("KapatmaAciklama"))
                        AracRengi.add(item.getString("AracRengi"))
                        AracVersiyon.add(item.getString("AracVersiyon"))
                        YakitDurumu.add(item.getString("YakitDurumu"))
                        SarjDurumu.add(item.getString("SarjDurumu"))
                        TahminiTeslimTarihi.add(item.getString("TahminiTeslimTarihi"))
                        TahminiTutar.add(item.getString("TahminiTutar"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.aracbulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.aracbulunmamakta))

                }
                recylerview_acikkartlar.layoutManager = LinearLayoutManager(this)
                recylerview_acikkartlar.setHasFixedSize(false)
                newArrayList = arrayListOf<OncekiOnarimModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Araç bulunmamakta", Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.aracbulunmamakta))

            }
        )
        queue.add(request)
    }
    private fun getUserData(context: Context) {
        for (i in KabulID.indices) {
            val news = OncekiOnarimModel(
                KullaniciId,
                FirmaLogo,
                username,
                uid,
                KabulID[i],
                FirmaKodu[i],
                FirmaAdi[i],
                KabulNo[i],
                GirisTarihi[i],
                Plaka[i],
                KabulDurumu[i],
                Kullanici[i],
                KabulTuru[i],
                TrafigeCikis[i],
                AracKM[i],
                Evraktarihi[i],
                Evrakno[i],
                Cariunvan[i],
                VergiNo[i],
                VergiDairesi[i],
                Adresi[i],
                Il[i],
                Ilce[i],
                CariNo[i],
                CariTelefon[i],
                AracMarka[i],
                AracModel[i],
                AracModelYili[i],
                AracSaseno[i],
                AracMotorNo[i],
                FaturaNo[i],
                FaturaTarihi[i],
                CariGSM[i],
                KabulTutar[i],
                PesonelAdi[i],
                MusteriID[i],
                EvrakID[i],
                KapatmaTarihi[i],
                Sil[i],
                KapatmaAciklama[i],
                AracResim[i],
                AracMarkaResim[i],
                AracRengi[i],
                AracVersiyon[i],
                YakitDurumu[i],
                SarjDurumu[i],
                TahminiTeslimTarihi[i],
                TahminiTutar[i],

            )


            newArrayList.add(news)
        }
        recylerview_acikkartlar.adapter = oncekiOnarimGetirAdapter(newArrayList)

    }

}