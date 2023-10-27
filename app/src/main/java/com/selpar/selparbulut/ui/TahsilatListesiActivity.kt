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
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.evrakGetirAdapter
import com.selpar.selparbulut.adapter.tahsilatGetirAdapter
import com.selpar.selparbulut.adapter.tahsilatOdemeGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.EvrakModel
import com.selpar.selparbulut.model.TahsilatOdemeModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class TahsilatListesiActivity : AppCompatActivity() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var recyclerView_tahsilat:RecyclerView
    lateinit var ivBack:ImageView
    var ID= ArrayList<String>()
    var EVRAK_NO= ArrayList<String>()
    var TIPI= ArrayList<String>()
    var CARI_ID= ArrayList<String>()
    var CARI_UNVAN= ArrayList<String>()
    var CARI_VNO= ArrayList<String>()
    var BORC= ArrayList<String>()
    var PARA_BIRIMI= ArrayList<String>()
    var ALACAK= ArrayList<String>()
    var ISLEM_TIPI= ArrayList<String>()
    var ACIKLAMA= ArrayList<String>()
    var EVRAK_TARIHI= ArrayList<String>()
    var TUTAR= ArrayList<String>()
    private lateinit var newArrayList: ArrayList<TahsilatOdemeModel>
    var edTarih1_dat:String=""
    var edTarih2_dat:String=""
    lateinit var edTarih1: EditText
    lateinit var edTarih2: EditText
    lateinit var txtToplamTahsilat:TextView
    lateinit var btnGoster:Button
    lateinit var progressBar:ProgressBar
    var toplamTahsilat=0.0
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahsilat_listesi)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        progressBar = findViewById(R.id.progressBar)
        Toast.makeText(this,getString(R.string.yapilantahsilatlarlisteniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        onTahsilatGetir()
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val startDate = LocalDate.of(year, month+1, day)

        // Belirli gün sayısını çıkarma işlemi
        val daysToSubtract =1
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
        btnGoster.setOnClickListener {tahsilatTarihGetir()}

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
        }, 5000) // Replace 3000 with your desired loading time
    }
    private fun tahsilatTarihGetir() {
        toplamTahsilat=0.0
        Toast.makeText(this,getString(R.string.secilentarihegoretahsilatlarlisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        ID.clear()
        EVRAK_NO.clear()
        TIPI.clear()
        CARI_ID.clear()
        CARI_UNVAN.clear()
        CARI_VNO.clear()
        BORC.clear()
        PARA_BIRIMI.clear()
        ALACAK.clear()
        ISLEM_TIPI.clear()
        ACIKLAMA.clear()
        EVRAK_TARIHI.clear()
        TUTAR.clear()
        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+
                edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat
        //"&CariID="+MusteriId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.TahsilatListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("tahsilat_listesi_tarih", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        ID.add(item.getString("ID"))
                        EVRAK_NO.add(item.getString("EVRAK_NO"))
                        TIPI.add(item.getString("TIPI"))
                        CARI_ID.add(item.getString("CARI_ID"))
                        CARI_UNVAN.add(item.getString("CARI_UNVAN"))
                        CARI_VNO.add(item.getString("CARI_VNO"))
                        BORC.add(item.getString("BORC"))
                        PARA_BIRIMI.add(item.getString("PARA_BIRIMI"))
                        ALACAK.add("0")
                        TUTAR.add(item.getString("TUTAR"))
                        ISLEM_TIPI.add(item.getString("ISLEM_TIPI"))
                        ACIKLAMA.add(item.getString("ACIKLAMA"))
                        EVRAK_TARIHI.add(item.getString("EVRAK_TARIHI"))
                        toplamTahsilat+=item.getString("TUTAR").toString().toFloat().toDouble()


                    }
                    var binler = 1000
                    var yuzler = 1000
                    var ondalik = 18 % 100
                    var dizi = toplamTahsilat.toString().split(".")
                    binler = dizi[0].toInt() / 1000
                    yuzler = dizi[0].toInt() % 1000
                    ondalik = (dizi[0].toInt() * 100).toInt() % 100
                    var sonucBul=""
                    if (binler == 0) {
                        sonucBul=dizi[0].toFloat().toString() + "₺"

                    } else
                        sonucBul=
                            String.format("%,d.%03d,%02d", binler, Math.abs(yuzler), ondalik) + "₺"
                    txtToplamTahsilat.setText(getString(R.string.toplam_tahsilat)+": "+sonucBul)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.tahsilatlistesibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.tahsilatlistesibulunmamakta))


                }
                recyclerView_tahsilat.layoutManager = LinearLayoutManager(this)
                recyclerView_tahsilat.setHasFixedSize(false)
                newArrayList = arrayListOf<TahsilatOdemeModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.tahsilatlistesibulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.tahsilatlistesibulunmamakta))

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }


    private fun onBaslat() {
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        ivBack=findViewById(R.id.ivBack)
        recyclerView_tahsilat=findViewById(R.id.recyclerView_tahsilat)
        edTarih1=findViewById(R.id.edTarih1)
        edTarih2=findViewById(R.id.edTarih2)
        btnGoster=findViewById(R.id.btnGoster)
        txtToplamTahsilat=findViewById(R.id.txtToplamTahsilat)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTahsilatGetir() {
        toplamTahsilat=0.0
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(1)
        val urlek ="&username="+username+"&uid="+uid+
                "&baslangic_Tarihi="+fiveDaysAgo+"&bitis_Tarihi="+today
        var url = Consts.TahsilatListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("tahsilat_listesi", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        ID.add(item.getString("ID"))
                        EVRAK_NO.add(item.getString("EVRAK_NO"))
                        TIPI.add(item.getString("TIPI"))
                        CARI_ID.add(item.getString("CARI_ID"))
                        CARI_UNVAN.add(item.getString("CARI_UNVAN"))
                        CARI_VNO.add(item.getString("CARI_VNO"))
                        BORC.add("0")
                        PARA_BIRIMI.add(item.getString("PARA_BIRIMI"))
                        ALACAK.add(item.getString("ALACAK"))
                        TUTAR.add(item.getString("TUTAR"))
                        ISLEM_TIPI.add(item.getString("ISLEM_TIPI"))
                        ACIKLAMA.add(item.getString("ACIKLAMA"))
                        EVRAK_TARIHI.add(item.getString("EVRAK_TARIHI"))
                        toplamTahsilat+=item.getString("TUTAR").toString().toFloat().toDouble()


                    }
                    var binler = 1000
                    var yuzler = 1000
                    var ondalik = 18 % 100
                    var dizi = toplamTahsilat.toString().split(".")
                    binler = dizi[0].toInt() / 1000
                    yuzler = dizi[0].toInt() % 1000
                    ondalik = (dizi[0].toInt() * 100).toInt() % 100
                    var sonucBul=""
                    if (binler == 0) {
                        sonucBul=dizi[0].toFloat().toString() + "₺"

                    } else
                        sonucBul=
                            String.format("%,d.%03d,%02d", binler, Math.abs(yuzler), ondalik) + "₺"
                    txtToplamTahsilat.setText(getString(R.string.toplam_tahsilat)+": "+sonucBul)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.tahsilatlistesibulunmamakta), Toast.LENGTH_LONG).show()


                }
                recyclerView_tahsilat.layoutManager = LinearLayoutManager(this)
                recyclerView_tahsilat.setHasFixedSize(false)
                newArrayList = arrayListOf<TahsilatOdemeModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.tahsilatlistesibulunmamakta), Toast.LENGTH_LONG).show()
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.tahsilatlistesibulunmamakta))
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)

    }
    private fun getUserData(context: Context) {
        for (i in ID.indices) {
            val news = TahsilatOdemeModel(
                username,
                uid,
                ID[i],
                EVRAK_NO[i],
                TIPI[i],
                CARI_ID[i],
                CARI_UNVAN[i],
                CARI_VNO[i],
                BORC[i],
                ALACAK[i],
                PARA_BIRIMI[i],
                ISLEM_TIPI[i],
                ACIKLAMA[i],
                EVRAK_TARIHI[i],
                TUTAR[i]

            )


            newArrayList.add(news)
        }

        recyclerView_tahsilat.adapter = tahsilatGetirAdapter(newArrayList)

    }

}