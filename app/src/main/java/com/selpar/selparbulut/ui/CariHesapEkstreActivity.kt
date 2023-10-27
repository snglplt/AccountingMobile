package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.selpar.selparbulut.adapter.acikKartGetirAdapter
import com.selpar.selparbulut.adapter.cariHesapEkstreGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import com.selpar.selparbulut.model.CariHesapEkstreModel
import com.selpar.selparbulut.model.CariListesiModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class CariHesapEkstreActivity : AppCompatActivity() {
    lateinit var edTarih1:EditText
    lateinit var edTarih2:EditText
    lateinit var btnGoster: Button
    lateinit var recyler_cari_ekstre:RecyclerView
    var edTarih1_dat:String=""
    var edTarih2_dat:String=""
    var username:String=""
    var uid:String=""
    var MusteriId:String=""
    lateinit var ivBack:ImageView
    private lateinit var newArrayList: ArrayList<CariHesapEkstreModel>
    var CariID=ArrayList<String>()
    var Kullanici=ArrayList<String>()
    var EvrakTarihi=ArrayList<String>()
    var EvrakNo=ArrayList<String>()
    var IslemTuru=ArrayList<String>()
    var VirmanCari=ArrayList<String>()
    var Aciklama=ArrayList<String>()
    var Borc=ArrayList<String>()
    var Alacak=ArrayList<String>()
    var InvoiceUUID=ArrayList<String>()
    lateinit var progressBar: ProgressBar
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_hesap_ekstre)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        Toast.makeText(this,getString(R.string.tarihsecinveektrelerilisteleyin), Toast.LENGTH_LONG).show()
       // startLoadingAnimation()
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        MusteriId=intent.getStringExtra("MusteriId").toString()

        progressBar=findViewById(R.id.progressBar)
        edTarih1=findViewById(R.id.edTarih1)
        edTarih2=findViewById(R.id.edTarih2)
        btnGoster=findViewById(R.id.btnGoster)
        recyler_cari_ekstre=findViewById(R.id.recyler_cari_ekstre)
        ivBack=findViewById(R.id.ivBack)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        //Toast.makeText(this,getString(R.string.carilereksterelisteleniyor), Toast.LENGTH_LONG).show()
        //startLoadingAnimation()
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val startDate = LocalDate.of(year, month+1, day)

        // Belirli gün sayısını çıkarma işlemi
        val daysToSubtract = 30
        val daysToSubtract_ = 0
        val resultDate = startDate.minusDays(daysToSubtract.toLong())

        val resultDate_ = startDate.minusDays(daysToSubtract_.toLong())
        var ay=""
        if(resultDate.monthValue.toString().length==1){

            ay="0"+resultDate.monthValue
        }
        edTarih1_dat=year.toString()+"-"+ay+"-"+resultDate.dayOfMonth
        edTarih1.setText(resultDate.dayOfMonth.toString()+"-"+resultDate.monthValue+"-"+year)

        edTarih2_dat=year.toString()+"-"+resultDate_.monthValue+"-"+resultDate_.dayOfMonth
        edTarih2.setText(resultDate_.dayOfMonth.toString()+"-"+resultDate_.monthValue+"-"+year)

        edTarih1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                var ay=""
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->

                            edTarih1_dat = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )


                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        edTarih1.setText(dat_gosterilen)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }
        edTarih2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                var ay=""
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->

                        edTarih2_dat = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )

                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        edTarih2.setText(dat_gosterilen)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }
        btnGoster.setOnClickListener {cariEktreGetir()}

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
    private fun cariEktreGetir() {
        Toast.makeText(this,getString(R.string.secilencarilereksterelisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(10)

        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+
                edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat+
                "&CariID="+MusteriId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.CARI_EKSTRE + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("cari_ekstre", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        CariID.add(item.getString("CariID"))
                        Kullanici.add(item.getString("Kullanici"))
                        EvrakTarihi.add(item.getString("EvrakTarihi"))
                        EvrakNo.add(item.getString("EvrakNo"))
                        IslemTuru.add(item.getString("IslemTuru"))
                        VirmanCari.add(item.getString("VirmanCari"))
                        Aciklama.add(item.getString("Aciklama"))
                        Borc.add(item.getString("Borc"))
                        Alacak.add(item.getString("Alacak"))
                        InvoiceUUID.add(item.getString("InvoiceUUID"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.cariekterebulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.cariekterebulunmuyor))


                }
                recyler_cari_ekstre.layoutManager = LinearLayoutManager(this)
                recyler_cari_ekstre.setHasFixedSize(false)
                newArrayList = arrayListOf<CariHesapEkstreModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Cari ekstre bulunmuyor", Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        queue.add(request)
    }
    private fun getUserData(context: Context) {
        for (i in CariID.indices) {
            val news = CariHesapEkstreModel(
                username,
                uid,
                intent.getStringExtra("CariUnvan").toString(),
                CariID[i],
                Kullanici[i],
                EvrakTarihi[i],
                EvrakNo[i],
                IslemTuru[i],
                VirmanCari[i],
                Aciklama[i],
                Borc[i],
                Alacak[i],
                        InvoiceUUID[i]

                )


            newArrayList.add(news)
        }
        recyler_cari_ekstre.adapter = cariHesapEkstreGetirAdapter(newArrayList)

    }


}