package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.RecoverySystem
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
import com.selpar.selparbulut.adapter.evrakGetirAdapter
import com.selpar.selparbulut.adapter.stokGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.CariHesapEkstreModel
import com.selpar.selparbulut.model.EvrakModel
import com.selpar.selparbulut.model.StokModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class EvrakSatisActivity : AppCompatActivity() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var ivBack:ImageView
    lateinit var edTarih1:EditText
    lateinit var edTarih2:EditText
    lateinit var recyler_satis_evrak_listesi:RecyclerView
    lateinit var btnGoster:Button
    var EVRAK_ID= ArrayList<String>()
    var EVRAK_TURU= ArrayList<String>()
    var EVRAK_TIPI= ArrayList<String>()
    var EVRAK_NUMARASI= ArrayList<String>()
    var EVRAK_TARIH= ArrayList<String>()
    var EVRAK_SAATI= ArrayList<String>()
    var EVRAK_SEVK_TARIHI= ArrayList<String>()
    var EVRAK_ODEME_TARIHI= ArrayList<String>()
    var EVRAK_UNVAN= ArrayList<String>()
    var EVRAK_VNO= ArrayList<String>()
    var EVRAK_VDAIRESI= ArrayList<String>()
    var EVRAK_ADRESI= ArrayList<String>()
    var EVRAK_IL= ArrayList<String>()
    var EVRAK_ILCE= ArrayList<String>()
    var EVRAK_TOPLAM= ArrayList<String>()
    var EVRAK_ISKONTO= ArrayList<String>()
    var EVRAK_ARA_TOPLAM= ArrayList<String>()
    var EVRAK_KDV_TOPLAMI= ArrayList<String>()
    var EVRAK_TEVKIFAT= ArrayList<String>()
    var EVRAK_YEKUN= ArrayList<String>()
    var EVRAK_TEVKIFAT_ORAN= ArrayList<String>()
    private lateinit var newArrayList: ArrayList<EvrakModel>
    var edTarih1_dat:String=""
    var edTarih2_dat:String=""
    lateinit var progressBar: ProgressBar
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evrak_satis)
        onBaslat()

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        progressBar = findViewById(R.id.progressBar)
        Toast.makeText(this,getString(R.string.satisevraklarilisteleniyor),Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        onEvrakGetir()
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
        edTarih1_dat=year.toString()+"-"+resultDate.monthValue+"-"+resultDate.dayOfMonth
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
                // variable for date picker dialog.
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
        btnGoster.setOnClickListener {satisEvrakTarihGetir()}

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

    private fun satisEvrakTarihGetir() {
        startLoadingAnimation()
        Toast.makeText(this,getString(R.string.secilensatisevraklarilisteleniyor), Toast.LENGTH_LONG).show()

        EVRAK_ID.clear()
        EVRAK_TURU.clear()
        EVRAK_TIPI.clear()
        EVRAK_NUMARASI.clear()
        EVRAK_TARIH.clear()
        EVRAK_SAATI.clear()
        EVRAK_SEVK_TARIHI.clear()
        EVRAK_ODEME_TARIHI.clear()
        EVRAK_UNVAN.clear()
        EVRAK_VNO.clear()
        EVRAK_VDAIRESI.clear()
        EVRAK_ADRESI.clear()
        EVRAK_IL.clear()
        EVRAK_ILCE.clear()
        EVRAK_TOPLAM.clear()
        EVRAK_ISKONTO.clear()
        EVRAK_ARA_TOPLAM.clear()
        EVRAK_KDV_TOPLAMI.clear()
        EVRAK_TEVKIFAT.clear()
        EVRAK_YEKUN.clear()
        EVRAK_TEVKIFAT_ORAN.clear()
        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+
                edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat
                //"&CariID="+MusteriId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.SatisEvrakListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("satis_evrak_listesi_tarih", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        EVRAK_ID.add(item.getString("EVRAK_ID"))
                        EVRAK_TURU.add(item.getString("EVRAK_TURU"))
                        EVRAK_TIPI.add(item.getString("EVRAK_TIPI"))
                        EVRAK_NUMARASI.add(item.getString("EVRAK_NUMARASI"))
                        EVRAK_TARIH.add(item.getString("EVRAK_TARIH"))
                        EVRAK_SAATI.add(item.getString("EVRAK_SAATI"))
                        EVRAK_SEVK_TARIHI.add(item.getString("EVRAK_SEVK_TARIHI"))
                        EVRAK_ODEME_TARIHI.add(item.getString("EVRAK_ODEME_TARIHI"))
                        EVRAK_UNVAN.add(item.getString("EVRAK_UNVAN"))
                        EVRAK_VNO.add(item.getString("EVRAK_VNO"))
                        EVRAK_VDAIRESI.add(item.getString("EVRAK_VDAIRESI"))
                        EVRAK_ADRESI.add(item.getString("EVRAK_ADRESI"))
                        EVRAK_IL.add(item.getString("EVRAK_IL"))
                        EVRAK_ILCE.add(item.getString("EVRAK_ILCE"))
                        EVRAK_TOPLAM.add(item.getString("EVRAK_TOPLAM"))
                        EVRAK_ISKONTO.add(item.getString("EVRAK_ISKONTO"))
                        EVRAK_ARA_TOPLAM.add(item.getString("EVRAK_ARA_TOPLAM"))
                        EVRAK_KDV_TOPLAMI.add(item.getString("EVRAK_KDV_TOPLAMI"))
                        EVRAK_TEVKIFAT.add(item.getString("EVRAK_TEVKIFAT"))
                        EVRAK_YEKUN.add(item.getString("EVRAK_YEKUN"))
                        EVRAK_TEVKIFAT_ORAN.add(item.getString("EVRAK_TEVKIFAT_ORAN"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.satisevraklistesibulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.satisevraklistesibulunmuyor))

                }
                recyler_satis_evrak_listesi.layoutManager = LinearLayoutManager(this)
                recyler_satis_evrak_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<EvrakModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.satisevraklistesibulunmuyor), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.satisevraklistesibulunmuyor))

            }
        )
        queue.add(request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onEvrakGetir() {
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(5)
        val urlek ="&username="+username+"&uid="+uid+
        "&baslangic_Tarihi="+today+"&bitis_Tarihi="+today
        var url = Consts.SatisEvrakListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("evrak_listesi", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        EVRAK_ID.add(item.getString("EVRAK_ID"))
                        EVRAK_TURU.add(item.getString("EVRAK_TURU"))
                        EVRAK_TIPI.add(item.getString("EVRAK_TIPI"))
                        EVRAK_NUMARASI.add(item.getString("EVRAK_NUMARASI"))
                        EVRAK_TARIH.add(item.getString("EVRAK_TARIH"))
                        EVRAK_SAATI.add(item.getString("EVRAK_SAATI"))
                        EVRAK_SEVK_TARIHI.add(item.getString("EVRAK_SEVK_TARIHI"))
                        EVRAK_ODEME_TARIHI.add(item.getString("EVRAK_ODEME_TARIHI"))
                        EVRAK_UNVAN.add(item.getString("EVRAK_UNVAN"))
                        EVRAK_VNO.add(item.getString("EVRAK_VNO"))
                        EVRAK_VDAIRESI.add(item.getString("EVRAK_VDAIRESI"))
                        EVRAK_ADRESI.add(item.getString("EVRAK_ADRESI"))
                        EVRAK_IL.add(item.getString("EVRAK_IL"))
                        EVRAK_ILCE.add(item.getString("EVRAK_ILCE"))
                        EVRAK_TOPLAM.add(item.getString("EVRAK_TOPLAM"))
                        EVRAK_ISKONTO.add(item.getString("EVRAK_ISKONTO"))
                        EVRAK_ARA_TOPLAM.add(item.getString("EVRAK_ARA_TOPLAM"))
                        EVRAK_KDV_TOPLAMI.add(item.getString("EVRAK_KDV_TOPLAMI"))
                        EVRAK_TEVKIFAT.add(item.getString("EVRAK_TEVKIFAT"))
                        EVRAK_YEKUN.add(item.getString("EVRAK_YEKUN"))
                        EVRAK_TEVKIFAT_ORAN.add(item.getString("EVRAK_TEVKIFAT_ORAN"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.satisevraklistesibulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.satisevraklistesibulunmuyor))

                }
                recyler_satis_evrak_listesi.layoutManager = LinearLayoutManager(this)
                recyler_satis_evrak_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<EvrakModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.satisevraklistesibulunmuyor), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.satisevraklistesibulunmuyor))

            }
        )
        queue.add(request)

    }
    private fun getUserData(context: Context) {
        for (i in EVRAK_ID.indices) {
            val news = EvrakModel(
                username,
                uid,
                EVRAK_ID[i],
                EVRAK_TURU[i],
                EVRAK_TIPI[i],
                EVRAK_NUMARASI[i],
                EVRAK_TARIH[i],
                EVRAK_SAATI[i],
                EVRAK_SEVK_TARIHI[i],
                EVRAK_ODEME_TARIHI[i],
                EVRAK_UNVAN[i],
                EVRAK_VNO[i],
                EVRAK_VDAIRESI[i],
                EVRAK_ADRESI[i],
                EVRAK_IL[i],
                EVRAK_ILCE[i],
                EVRAK_TOPLAM[i],
                EVRAK_ISKONTO[i],
                EVRAK_ARA_TOPLAM[i],
                EVRAK_KDV_TOPLAMI[i],
                EVRAK_TEVKIFAT[i],
                EVRAK_YEKUN[i],
                EVRAK_TEVKIFAT_ORAN[i]

                )


            newArrayList.add(news)
        }

        recyler_satis_evrak_listesi.adapter = evrakGetirAdapter(newArrayList)

    }

    private fun onBaslat() {
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        ivBack=findViewById(R.id.ivBack)
        edTarih1=findViewById(R.id.edTarih1)
        edTarih2=findViewById(R.id.edTarih2)
        recyler_satis_evrak_listesi=findViewById(R.id.recyler_satis_evrak_listesi)
        btnGoster=findViewById(R.id.btnGoster)
    }
}