package com.selpar.selparbulut.ui

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
import com.selpar.selparbulut.adapter.orijinalStokGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.CariListesiModel
import com.selpar.selparbulut.model.OrgListesiModel
import org.json.JSONArray

class OrijinalStokListesiActivity : AppCompatActivity() {
    lateinit var recyclerView_orijinal_stok_listesi:RecyclerView
    lateinit var ivBack:ImageView
    lateinit var progressBar:ProgressBar
    lateinit var txtstok_no:EditText
    lateinit var btn_search:ImageView
    lateinit var username:String
    lateinit var uid:String
    var Sira=ArrayList<String>()
    var StokNumarasi=ArrayList<String>()
    var StokAdi=ArrayList<String>()
    var OEMKodu=ArrayList<String>()
    var ListeFiyati=ArrayList<String>()
    var ParaBirimi=ArrayList<String>()
    var Uretici=ArrayList<String>()
    var Resim1=ArrayList<String>()
    var GuncellemeTarihi=ArrayList<String>()
    var newArrayList=ArrayList<OrgListesiModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orijinal_stok_listesi)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.orijinalstoklistesiicinaramayapin), Toast.LENGTH_LONG).show()
        //startLoadingAnimation()
        //btn_search.setOnClickListener { arananCariGetir(txt_arama.getText().toString()) }
        txtstok_no.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Handle the Enter key press here
                // You can perform any action you want when Enter is pressed
                // For example, you can hide the keyboard or process the input
                // ...
                arananCariGetir(txtstok_no.getText().toString())
                return@OnKeyListener true // Consume the key event
            }
            false // Let other key events be handled as usual
        })
    }
    private fun arananCariGetir(arananStok: String) {
        if(arananStok.isNotEmpty()){
            arananOrijinalStokGetir(arananStok)
        }else{
            Toast.makeText(this,getString(R.string.lutfenstokbilgisigiriniz),Toast.LENGTH_LONG).show()
        }

    }

    private fun arananOrijinalStokGetir(arananStok: String) {
        startLoadingAnimation()
        Toast.makeText(this,getString(R.string.stoklarlisteleniyor),Toast.LENGTH_LONG).show()
        val urlek ="&username="+username+"&uid="+uid+"&search="+arananStok
        var url = Consts.OrgListesi + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("orijinal_stok_listesi_aranan", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        Sira.add(item.getString("Sira"))
                        StokNumarasi.add(item.getString("StokNumarasi"))
                        StokAdi.add(item.getString("StokAdi"))
                        OEMKodu.add(item.getString("OEMKodu"))
                        ListeFiyati.add(item.getString("ListeFiyati"))
                        ParaBirimi.add(item.getString("ParaBirimi"))
                        Uretici.add(item.getString("Uretici"))
                        Resim1.add(item.getString("Resim1"))
                        GuncellemeTarihi.add(item.getString("GuncellemeTarihi"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.stokbulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.stokbulunmamakta))


                }
                recyclerView_orijinal_stok_listesi.layoutManager = LinearLayoutManager(this)
                recyclerView_orijinal_stok_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<OrgListesiModel>()
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
    private fun getUserData(context: Context) {
        for (i in Sira.indices) {
            val news = OrgListesiModel(
                username,
                uid,
                Sira[i],
                StokNumarasi[i],
                StokAdi[i],
                OEMKodu[i],
                ListeFiyati[i],
                ParaBirimi[i],
                Uretici[i],
                Resim1[i],
                GuncellemeTarihi[i]
            )


            newArrayList.add(news)
        }

        recyclerView_orijinal_stok_listesi.adapter = orijinalStokGetirAdapter(newArrayList)

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
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        recyclerView_orijinal_stok_listesi=findViewById(R.id.recyclerView_orijinal_stok_listesi)
        ivBack=findViewById(R.id.ivBack)
        progressBar=findViewById(R.id.progressBar)
        txtstok_no=findViewById(R.id.txtstok_no)
        btn_search=findViewById(R.id.btn_search)
    }
}