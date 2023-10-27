package com.selpar.selparbulut.ui

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.evrakDetayGetirAdapter
import com.selpar.selparbulut.adapter.evrakGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.EvrakDetay
import com.selpar.selparbulut.model.EvrakModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class SatisEvrakDetayActivity : AppCompatActivity() {
    lateinit var username: String
    lateinit var uid: String
    lateinit var EVRAK_ID: String
    lateinit var ivBack: ImageView
    lateinit var recyler_evrak_listesi_detay: RecyclerView
    var SATIR_URUN_NO = ArrayList<String>()
    var SATIR_URUN_ADI = ArrayList<String>()
    var SATIR_BIRIM = ArrayList<String>()
    var SATIR_MIKTAR = ArrayList<String>()
    var SATIR_BIRIM_FIYAT = ArrayList<String>()
    var SATIR_KDV_ORANI = ArrayList<String>()
    var SATIR_ISKONTO1 = ArrayList<String>()
    var SATIR_ISKONTO2 = ArrayList<String>()
    var SATIR_ISKONTO3 = ArrayList<String>()
    var SATIR_ISKONTO4 = ArrayList<String>()
    var SATIR_ISKONTO5 = ArrayList<String>()
    var SATIR_TOPLAM = ArrayList<String>()
    var SATIR_ISKONTO_TOPLAM = ArrayList<String>()
    var SATIR_ARA_TOPLAM = ArrayList<String>()
    var SATIR_KDV_TOPLAM = ArrayList<String>()
    var SATIR_TUTAR = ArrayList<String>()
    var SATIR_OTV = ArrayList<String>()
    var SATIR_GEKAP = ArrayList<String>()
    var SATIR_ISTURU = ArrayList<String>()
    private lateinit var newArrayList: ArrayList<EvrakDetay>
    lateinit var txtCari: TextView
    lateinit var txttoplam: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satis_evrak_detay)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        onDetayGetir()
        txtCari.setText(
            getString(R.string.cariadi) + ": " + intent.getStringExtra("cari").toString()
        )
        var dizi = intent.getStringExtra("toplam").toString().split(".")
        var yekun = dizi[0]
        var binler = yekun.toFloat().toInt() / 1000
        var yuzler = yekun.toFloat().toInt() % 1000
        var ondalik = (yekun.toFloat().toInt() * 100).toInt() % 100
        if (binler == 0) {
            txttoplam.setText(
                getString(R.string.toplamyekun) + ": " + String.format(
                    "%.2f",
                    yekun.toFloat()
                ).replace(".", ",") + "₺"
            )

        } else
            txttoplam.setText(
                getString(R.string.toplamyekun) + ": " + String.format(
                    "%,d.%03d,%02d",
                    binler,
                    yuzler,
                    ondalik
                ) + "₺"
            )

        // txttoplam.setText("Toplam Yekun: "+intent.getStringExtra("toplam").toString())
    }

    private fun onBaslat() {
        ivBack = findViewById(R.id.ivBack)
        txtCari = findViewById(R.id.txtCari)
        txttoplam = findViewById(R.id.txttoplam)
        recyler_evrak_listesi_detay = findViewById(R.id.recyler_evrak_listesi_detay)

        username = intent.getStringExtra("username").toString()
        uid = intent.getStringExtra("uid").toString()
        EVRAK_ID = intent.getStringExtra("EVRAK_ID").toString()
    }

    private fun onDetayGetir() {
        val urlek =
            "&username=" + username + "&uid=" + uid + "&EvrakID=" + EVRAK_ID + "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //"&CariID="+MusteriId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.SatisEvrakDetay + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("satis_evrak_id", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        SATIR_URUN_NO.add(item.getString("SATIR_URUN_NO"))
                        SATIR_URUN_ADI.add(item.getString("SATIR_URUN_ADI"))
                        SATIR_BIRIM.add(item.getString("SATIR_BIRIM"))
                        SATIR_MIKTAR.add(item.getString("SATIR_MIKTAR"))
                        SATIR_BIRIM_FIYAT.add(item.getString("SATIR_BIRIM_FIYAT"))
                        SATIR_KDV_ORANI.add(item.getString("SATIR_KDV_ORANI"))
                        SATIR_ISKONTO1.add(item.getString("SATIR_ISKONTO1"))
                        SATIR_ISKONTO2.add(item.getString("SATIR_ISKONTO2"))
                        SATIR_ISKONTO3.add(item.getString("SATIR_ISKONTO3"))
                        SATIR_ISKONTO4.add(item.getString("SATIR_ISKONTO4"))
                        SATIR_ISKONTO5.add(item.getString("SATIR_ISKONTO5"))
                        SATIR_TOPLAM.add(item.getString("SATIR_TOPLAM"))
                        SATIR_ISKONTO_TOPLAM.add(item.getString("SATIR_ISKONTO_TOPLAM"))
                        SATIR_ARA_TOPLAM.add(item.getString("SATIR_ARA_TOPLAM"))
                        SATIR_KDV_TOPLAM.add(item.getString("SATIR_KDV_TOPLAM"))
                        SATIR_TUTAR.add(item.getString("SATIR_TUTAR"))
                        SATIR_OTV.add(item.getString("SATIR_OTV"))
                        SATIR_GEKAP.add(item.getString("SATIR_GEKAP"))
                        SATIR_ISTURU.add(item.getString("SATIR_ISTURU"))
                    }
                    recyler_evrak_listesi_detay.layoutManager = LinearLayoutManager(this)
                    recyler_evrak_listesi_detay.setHasFixedSize(false)
                    newArrayList = arrayListOf<EvrakDetay>()
                    getUserData(this)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()

                    Toast.makeText(
                        this,
                        getString(R.string.satisevrakidbulunmuyor),
                        Toast.LENGTH_LONG
                    ).show()
                    val alert = Alert()
                    alert.showAlert(
                        this,
                        getString(R.string.uyari),
                        getString(R.string.satisevrakidbulunmuyor) + e.message
                    )


                }


            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this, getString(R.string.satisevrakidbulunmuyor), Toast.LENGTH_LONG)
                    .show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert = Alert()
                alert.showAlert(
                    this,
                    getString(R.string.uyari),
                    getString(R.string.satisevrakidbulunmuyor)
                )

            }
        )
        queue.add(request)
    }

    private fun getUserData(context: Context) {
        for (i in SATIR_URUN_NO.indices) {
            val news = EvrakDetay(
                intent.getStringExtra("cari").toString(),
                intent.getStringExtra("toplam").toString(),
                SATIR_URUN_NO[i],
                SATIR_URUN_ADI[i],
                SATIR_BIRIM[i],
                SATIR_MIKTAR[i],
                SATIR_BIRIM_FIYAT[i],
                SATIR_KDV_ORANI[i],
                SATIR_ISKONTO1[i],
                SATIR_ISKONTO2[i],
                SATIR_ISKONTO3[i],
                SATIR_ISKONTO4[i],
                SATIR_ISKONTO5[i],
                SATIR_TOPLAM[i],
                SATIR_ISKONTO_TOPLAM[i],
                SATIR_ARA_TOPLAM[i],
                SATIR_KDV_TOPLAM[i],
                SATIR_TUTAR[i],
                SATIR_OTV[i],
                SATIR_GEKAP[i],
                SATIR_ISTURU[i],

                )


            newArrayList.add(news)
        }

        recyler_evrak_listesi_detay.adapter = evrakDetayGetirAdapter(newArrayList)

    }

}