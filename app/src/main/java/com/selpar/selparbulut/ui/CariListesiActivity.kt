package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.RecoverySystem
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.acikKartGetirAdapter
import com.selpar.selparbulut.adapter.cariGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import com.selpar.selparbulut.model.CariListesiModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class CariListesiActivity : AppCompatActivity() {
    lateinit var reclerview_cari_listesi:RecyclerView
    lateinit var username:String
    lateinit var uid:String
    lateinit var kullaniciId:String
    var MusteriID= ArrayList<String>()
    var FirmaKodu= ArrayList<String>()
    var FirmaAdi= ArrayList<String>()
    var Kullanici= ArrayList<String>()
    var Cariunvan= ArrayList<String>()
    var Vnumarasi= ArrayList<String>()
    var Vdairesi= ArrayList<String>()
    var Telefon= ArrayList<String>()
    var GSM= ArrayList<String>()
    var Adresi= ArrayList<String>()
    var SMSonay= ArrayList<String>()
    var AracPlaka= ArrayList<String>()
    var AracMarka= ArrayList<String>()
    var AracModel= ArrayList<String>()
    var AracSaseno= ArrayList<String>()
    var RuhsatSeriNo= ArrayList<String>()
    var TrafikPolice= ArrayList<String>()
    var Il= ArrayList<String>()
    var Ilce= ArrayList<String>()
    var Email= ArrayList<String>()
    var MusteriTipi= ArrayList<String>()
    var Bakiye= ArrayList<String>()
    lateinit var btn_search:ImageView
    private lateinit var newArrayList: ArrayList<CariListesiModel>
    lateinit var txtcari_no:EditText
    lateinit var ivBack:ImageView
    lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_listesi)
        onBaslat()
        cariListesiGetir()

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.carilerlisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        btn_search.setOnClickListener { arananCariGetir(txtcari_no.getText().toString()) }
        txtcari_no.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Handle the Enter key press here
                // You can perform any action you want when Enter is pressed
                // For example, you can hide the keyboard or process the input
                // ...
                arananCariGetir(txtcari_no.getText().toString())
                Toast.makeText(this,"Edit tıklandı",Toast.LENGTH_LONG).show()
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

    private fun arananCariGetir(arananCari: String) {
        if(arananCari.isNotEmpty()){
            cariArananGetir(arananCari)
        }else{
            Toast.makeText(this,getString(R.string.caribilgisigirin),Toast.LENGTH_LONG).show()
        }

    }

    private fun cariArananGetir(arananCari: String) {
        Toast.makeText(this,getString(R.string.aranancarilisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        MusteriID.clear()
        FirmaKodu.clear()
        FirmaAdi.clear()
        Kullanici.clear()
        Cariunvan.clear()
        Vnumarasi.clear()
        Vdairesi.clear()
        Telefon.clear()
        GSM.clear()
        Adresi.clear()
        SMSonay.clear()
        AracPlaka.clear()
        AracMarka.clear()
        AracModel.clear()
        AracSaseno.clear()
        RuhsatSeriNo.clear()
        TrafikPolice.clear()
        Il.clear()
        Ilce.clear()
        Email.clear()
        MusteriTipi.clear()
        Bakiye.clear()

        val urlek ="&username="+username+"&uid="+uid+"&search="+arananCari+"&KullaniciId="+kullaniciId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.CariListesiArama + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("cari_listesi_aranan", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        if(item.getString("Cariunvan").toString()!="") {
                            MusteriID.add(item.getString("MusteriID"))
                            FirmaKodu.add(item.getString("FirmaKodu"))
                            FirmaAdi.add(item.getString("FirmaAdi"))
                            Kullanici.add(item.getString("Kullanici"))
                            Cariunvan.add(item.getString("Cariunvan"))
                            Vnumarasi.add(item.getString("Vnumarasi"))
                            Vdairesi.add(item.getString("Vdairesi"))
                            Telefon.add(item.getString("Telefon"))
                            GSM.add(item.getString("GSM"))
                            Adresi.add(item.getString("Adresi"))
                            SMSonay.add(item.getString("SMSonay"))
                            AracPlaka.add(item.getString("AracPlaka"))
                            AracMarka.add(item.getString("AracMarka"))
                            AracModel.add(item.getString("AracModel"))
                            AracSaseno.add(item.getString("AracSaseno"))
                            RuhsatSeriNo.add(item.getString("RuhsatSeriNo"))
                            TrafikPolice.add(item.getString("TrafikPolice"))
                            Il.add(item.getString("Il"))
                            Ilce.add(item.getString("Ilce"))
                            Email.add(item.getString("Email"))
                            MusteriTipi.add(item.getString("MusteriTipi"))
                            Bakiye.add(item.getString("Bakiye"))
                        }

                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.aranancaribulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.aranancaribulunmuyor))


                }
                reclerview_cari_listesi.layoutManager = LinearLayoutManager(this)
                reclerview_cari_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<CariListesiModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.aranancaribulunmuyor), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.aranancaribulunmuyor))

            }
        )
        queue.add(request)











    }

    private fun onBaslat() {
        ivBack=findViewById(R.id.ivBack)
        progressBar=findViewById(R.id.progressBar)
        reclerview_cari_listesi=findViewById(R.id.reclerview_cari_listesi)
        btn_search=findViewById(R.id.btn_search)
        txtcari_no=findViewById(R.id.txtcari_no)
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        kullaniciId=intent.getStringExtra("KullaniciId").toString()
    }
    private fun cariListesiGetir() {

        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+kullaniciId+"&CariTipi=CARİ"

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.CariListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("cari_listesi", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        if(item.getString("Cariunvan").toString()!="") {
                            MusteriID.add(item.getString("MusteriID"))
                            FirmaKodu.add(item.getString("FirmaKodu"))
                            FirmaAdi.add(item.getString("FirmaAdi"))
                            Kullanici.add(item.getString("Kullanici"))
                            Cariunvan.add(item.getString("Cariunvan"))
                            Vnumarasi.add(item.getString("Vnumarasi"))
                            Vdairesi.add(item.getString("Vdairesi"))
                            Telefon.add(item.getString("Telefon"))
                            GSM.add(item.getString("GSM"))
                            Adresi.add(item.getString("Adresi"))
                            SMSonay.add(item.getString("SMSonay"))
                            AracPlaka.add(item.getString("AracPlaka"))
                            AracMarka.add(item.getString("AracMarka"))
                            AracModel.add(item.getString("AracModel"))
                            AracSaseno.add(item.getString("AracSaseno"))
                            RuhsatSeriNo.add(item.getString("RuhsatSeriNo"))
                            TrafikPolice.add(item.getString("TrafikPolice"))
                            Il.add(item.getString("Il"))
                            Ilce.add(item.getString("Ilce"))
                            Email.add(item.getString("Email"))
                            MusteriTipi.add(item.getString("MusteriTipi"))
                            Bakiye.add(item.getString("Bakiye"))

                        }
                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.carilistesibulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.carilistesibulunmuyor))



                }
                reclerview_cari_listesi.layoutManager = LinearLayoutManager(this)
                reclerview_cari_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<CariListesiModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.carilistesibulunmuyor), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.carilistesibulunmuyor))

            }
        )
        queue.add(request)

        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT or
                        ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.gray
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.tahsilat_ekle))
                    .addSwipeRightLabel(getString(R.string.odeme_ekle))
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txtCariunvan= viewHolder.itemView.findViewById<TextView>(R.id.txtCariunvan).text
                        val i= Intent(applicationContext,TahsilatEkleActivity::class.java)
                        i.putExtra("username", intent.getStringExtra("username"))
                        i.putExtra("uid", intent.getStringExtra("uid"))
                        i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                        i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                        i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                        i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                        i.putExtra("cariUnvan", txtCariunvan.toString())
                        startActivity(i)




                    }
                    ItemTouchHelper.RIGHT -> {
                        viewHolder.itemId
                          val txtCariunvan= viewHolder.itemView.findViewById<TextView>(R.id.txtCariunvan).text
                        val i= Intent(applicationContext,OdemeEkleActivity::class.java)
                        i.putExtra("username", intent.getStringExtra("username"))
                        i.putExtra("uid", intent.getStringExtra("uid"))
                        i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                        i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                        i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                        i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                        i.putExtra("cariUnvan", txtCariunvan.toString())
                        startActivity(i)


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(reclerview_cari_listesi)










    }
    private fun getUserData(context: Context) {
        for (i in MusteriID.indices) {
            val news = CariListesiModel(
                username,
                uid,
                MusteriID[i],
                FirmaKodu[i],
                FirmaAdi[i],
                Kullanici[i],
                Cariunvan[i],
                Vnumarasi[i],
                Vdairesi[i],
                Telefon[i],
                GSM[i],
                Adresi[i],
                SMSonay[i],
                AracPlaka[i],
                AracMarka[i],
                AracModel[i],
                AracSaseno[i],
                RuhsatSeriNo[i],
                TrafikPolice[i],
                Il[i],
                Ilce[i],
                Email[i],
                MusteriTipi[i],
                Bakiye[i],
            )


            newArrayList.add(news)
        }

        reclerview_cari_listesi.adapter = cariGetirAdapter(newArrayList)

    }


}