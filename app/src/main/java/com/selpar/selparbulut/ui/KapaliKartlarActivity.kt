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
import android.widget.Button
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
import com.selpar.selparbulut.adapter.kapaliKartGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class KapaliKartlarActivity : AppCompatActivity() {
    lateinit var recylerview_acikkartlar: RecyclerView
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    lateinit var KullaniciId:String
    private lateinit var newArrayList: ArrayList<AcikKartlarModel>
    var KabulId= ArrayList<String>()
    var FirmaKodu= ArrayList<String>()
    var FirmaAdi= ArrayList<String>()
    var KabulNo= ArrayList<String>()
    var GirisTarihi= ArrayList<String>()
    var Plaka= ArrayList<String>()
    var KabulDurumu= ArrayList<String>()
    var Kullanici= ArrayList<String>()
    var KabulTuru= ArrayList<String>()
    var KabulTutar= ArrayList<String>()
    var Cariunvan= ArrayList<String>()
    var AracMarka= ArrayList<String>()
    var AracModel= ArrayList<String>()
    var AracModelYili= ArrayList<String>()
    var AracResim= ArrayList<String>()
    var AracMarkaResim= ArrayList<String>()
    var CariTelefon= ArrayList<String>()
    var AracKM= ArrayList<String>()
    var VergiNo= ArrayList<String>()
    var VergiDairesi= ArrayList<String>()
    var Adresi= ArrayList<String>()
    var Il= ArrayList<String>()
    var Ilce= ArrayList<String>()
    var CariGSM= ArrayList<String>()
    var AracRengi= ArrayList<String>()
    var AracVersiyon= ArrayList<String>()
    var YakitDurumu= ArrayList<String>()
    var SarjDurumu= ArrayList<String>()
    var TahminiTeslimTarihi= ArrayList<String>()
    var TahminiTutar= ArrayList<String>()
    var AracSaseno= ArrayList<String>()
    var AracMotorNo= ArrayList<String>()
    lateinit var ivBack: ImageView
    lateinit var btn_bilgi_ara:ImageView
    lateinit var txtarac_no:EditText
    lateinit var progressBar: ProgressBar
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kapali_kartlar)
        onBaslat()

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.kapalikartlarlisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        KapaliKarlarGetir()
       // btn_bilgi_ara.setOnClickListener { kapaliKartAra(txtarac_no.getText().toString()) }
        txtarac_no.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Handle the Enter key press here
                // You can perform any action you want when Enter is pressed
                // For example, you can hide the keyboard or process the input
                // ...
                kapaliKartAra(txtarac_no.getText().toString())
                return@OnKeyListener true // Consume the key event
            }
            false // Let other key events be handled as usual
        })
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
    private fun kapaliKartAra(aranan: String) {
        if(aranan.isNotEmpty() && aranan.length>=3){
            kartArananGetir(aranan)
        }else{
            Toast.makeText(this,getString(R.string.lutfenaracbilgisigiriniz), Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun kartArananGetir(aranan: String)  {
        Toast.makeText(this,getString(R.string.kapalikartlardaaramayapiliyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        KabulId.clear()
        FirmaKodu.clear()
        FirmaAdi.clear()
        KabulNo.clear()
        GirisTarihi.clear()
        Plaka.clear()
        KabulDurumu.clear()
        Kullanici.clear()
        KabulTuru.clear()
        KabulTutar.clear()
        Cariunvan.clear()
        AracMarka.clear()
        AracModel.clear()
        AracModelYili.clear()
        AracResim.clear()
        AracMarkaResim.clear()
        CariTelefon.clear()
        AracKM.clear()
        VergiNo.clear()
        VergiDairesi.clear()
        Adresi.clear()
        Il.clear()
        Ilce.clear()
        CariGSM.clear()
        AracRengi.clear()
        AracVersiyon.clear()
        YakitDurumu.clear()
        SarjDurumu.clear()
        TahminiTeslimTarihi.clear()
        TahminiTutar.clear()
        AracSaseno.clear()
        AracMotorNo.clear()
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(30)
        val urlek ="&username="+username+"&uid="+uid+"&search="+aranan+"&baslangic_Tarihi="+today+"&bitis_Tarihi="+fiveDaysAgo+"&kabuldurumu=KAPALI"

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartlariArama + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("kapali_listesi_aranan", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        KabulId.add(item.getString("KabulID"))
                        FirmaKodu.add(item.getString("FirmaKodu"))
                        // Toast.makeText(this,KabulId)
                        FirmaAdi.add(item.getString("FirmaAdi"))
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
                        AracRengi.add(item.getString("AracRengi"))
                        AracVersiyon.add(item.getString("AracVersiyon"))
                        YakitDurumu.add(item.getString("YakitDurumu"))
                        SarjDurumu.add(item.getString("SarjDurumu"))
                        TahminiTeslimTarihi.add(item.getString("TahminiTeslimTarihi"))
                        TahminiTutar.add(item.getString("TahminiTutar"))
                        AracSaseno.add(item.getString("AracSaseno"))
                        AracMotorNo.add(item.getString("AracMotorNo"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.aracbulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.aracbulunmamakta))


                }
                recylerview_acikkartlar.layoutManager = LinearLayoutManager(this)
                recylerview_acikkartlar.setHasFixedSize(false)
                newArrayList = arrayListOf<AcikKartlarModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.aracbulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        queue.add(request)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun KapaliKarlarGetir() {
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(10)
        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+fiveDaysAgo+"&bitis_Tarihi="+today+"&kabuldurumu=KAPALI"

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.BASE_URL + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("islerim_bugun", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //Toast.makeText(requireContext(), response["message"].toString(), Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        KabulId.add(item.getString("KabulID"))
                        FirmaKodu.add(item.getString("FirmaKodu"))
                        // Toast.makeText(this,KabulId)
                        FirmaAdi.add(item.getString("FirmaAdi"))
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
                        AracRengi.add(item.getString("AracRengi"))
                        AracVersiyon.add(item.getString("AracVersiyon"))
                        YakitDurumu.add(item.getString("YakitDurumu"))
                        SarjDurumu.add(item.getString("SarjDurumu"))
                        TahminiTeslimTarihi.add(item.getString("TahminiTeslimTarihi"))
                        TahminiTutar.add(item.getString("TahminiTutar"))
                        AracSaseno.add(item.getString("AracSaseno"))
                        AracMotorNo.add(item.getString("AracMotorNo"))



                    }
                } catch (e: Exception) {
                    Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()

                }
                recylerview_acikkartlar.layoutManager = LinearLayoutManager(this)
                recylerview_acikkartlar.setHasFixedSize(false)
                newArrayList = arrayListOf<AcikKartlarModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.aracbulunmamakta))

            }
        )
        queue.add(request)











    }
    private fun getUserData(context: Context) {
        for (i in KabulId.indices) {
            val news = AcikKartlarModel(
                KullaniciId,
                FirmaLogo,
                username,
                uid,
                KabulId[i],
                FirmaKodu[i],
                FirmaAdi[i],
                KabulNo[i],
                GirisTarihi[i],
                Plaka[i],
                KabulDurumu[i],
                Kullanici[i],
                KabulTuru[i],
                KabulTutar[i],
                Cariunvan[i],
                AracMarka[i],
                AracModel[i],
                AracModelYili[i],
                AracResim[i],
                AracMarkaResim[i],
                CariTelefon[i],
                AracKM[i],
                VergiNo[i],
                VergiDairesi[i],
                Adresi[i],
                Il[i],
                Ilce[i],
                CariGSM[i],
                AracRengi[i],
                AracVersiyon[i],
                YakitDurumu[i],
                SarjDurumu[i],
                TahminiTeslimTarihi[i],
                TahminiTutar[i],
                AracSaseno[i],
                AracMotorNo[i],

                )


            newArrayList.add(news)
        }
        recylerview_acikkartlar.adapter = kapaliKartGetirAdapter(newArrayList)

    }

}