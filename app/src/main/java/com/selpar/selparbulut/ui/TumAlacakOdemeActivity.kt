package com.selpar.selparbulut.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.FragmentAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.fragment.AlacaklarFragment
import com.selpar.selparbulut.fragment.OdemelerFragment
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.SharedViewModel
import com.selpar.selparbulut.model.TahsilatOdemeModel
import org.json.JSONArray
import java.lang.Math.abs
import java.time.LocalDate
import java.util.ArrayList

class TumAlacakOdemeActivity : AppCompatActivity() {
    lateinit var recyler_tum_alacak_odeme_listesi:RecyclerView
    lateinit var ivBack:ImageView
    lateinit var edTarih1:EditText
    lateinit var edTarih2:EditText
    lateinit var progressBar:ProgressBar
    lateinit var btnGoster:Button
    lateinit var username:String
    lateinit var uid:String
    lateinit var edTarih1_dat:String
    lateinit var edTarih2_dat:String
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
    private lateinit var newArrayList: ArrayList<TahsilatOdemeModel>
    private lateinit var faturaViewModel: SharedViewModel
    var borc_=0.0
    var alacak_=0.0
    lateinit var txt_gelir:TextView
    lateinit var txt_gider:TextView
    lateinit var txt_tahsilat:TextView
    lateinit var txt_odeme:TextView
    lateinit var txt_gider_hesaplama:EditText
    lateinit var txt_gelir_hesaplama:EditText
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tum_alacak_odeme)
        onBaslat()

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.bugunyapilanalacakodemebilgilerilisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()

        var viewPager = findViewById(R.id.viewPager) as ViewPager
        var tablayout = findViewById(R.id.tablayout) as TabLayout
        faturaViewModel= ViewModelProvider(this).get(SharedViewModel::class.java)
        val fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(AlacaklarFragment(), getString(R.string.alacaklar), "marka 4")
        fragmentAdapter.addFragment(OdemelerFragment(), getString(R.string.odemeler), "marka 4")
        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
        faturaViewModel.username=intent.getStringExtra("username").toString()
        faturaViewModel.uid=intent.getStringExtra("uid").toString()
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val startDate = LocalDate.of(year, month+1, day)

        // Belirli gün sayısını çıkarma işlemi
        val daysToSubtract = 1
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
        faturaViewModel.tarih1=edTarih1_dat
        faturaViewModel.tarih2=edTarih2_dat
        onTumTahsilatGetir()
        onTumOdemeGetir()

        btnGoster.setOnClickListener {

            Toast.makeText(this,getString(R.string.bugunyapilanalacakodemebilgilerilisteleniyor), Toast.LENGTH_LONG).show()
            startLoadingAnimation()
            faturaViewModel.tarih1=edTarih1_dat
            faturaViewModel.tarih2=edTarih2_dat
            onTumTahsilatGetir()
            onTumOdemeGetir()
            Toast.makeText(this,"alacak"+alacak_.toString(),Toast.LENGTH_LONG).show()
           // txt_gider_hesaplama.setText("100")
            //txt_gelir_hesaplama.setText("50")

            val fragmentAdapter = FragmentAdapter(supportFragmentManager)
            fragmentAdapter.addFragment(AlacaklarFragment(), getString(R.string.alacaklar), "marka 4")
            fragmentAdapter.addFragment(OdemelerFragment(), getString(R.string.odemeler), "marka 4")
            viewPager.adapter = fragmentAdapter
            tablayout.setupWithViewPager(viewPager)

        }
/*
       // onTumAlacakOdemeGetir()
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
        btnGoster.setOnClickListener {tumAlacakOdemeTarihGetir()}*/
    }

    private fun onHesapla() {
        //var hesap=this.alacak_-this.borc_
        var alacak=txt_gelir_hesaplama.text.toString().toFloat().toDouble()
        var borc=txt_gider_hesaplama.text.toString().toFloat().toDouble()
        Toast.makeText(this,"sonuc= "+alacak,Toast.LENGTH_LONG).show()
        var hesap=alacak-borc
          //  txt_gider_hesaplama.text.toString().toFloat().toDouble()-txt_gelir_hesaplama.text.toString().toFloat().toDouble()
        var binler = 1000
        var yuzler = 1000
        var ondalik = 18 % 100
       // var dizi = hesap.toString().split(".")
        binler = hesap.toInt() / 1000
        yuzler = hesap.toInt() % 1000
        ondalik = (hesap.toInt() * 100).toInt() % 100
        var sonucBul=""
        if (binler == 0) {
            sonucBul=hesap.toFloat().toString() + "₺"

        } else
            sonucBul=
                String.format("%,d.%03d,%02d", binler, abs(yuzler), ondalik) + "₺"
        //txt_bakiye.setText(().toInt().toString())
        if((hesap)<0){
            txt_tahsilat.visibility= INVISIBLE
            txt_odeme.visibility=VISIBLE
            txt_odeme.setText(sonucBul)

        }else{
            txt_odeme.visibility=INVISIBLE
            txt_tahsilat.visibility=VISIBLE
            txt_tahsilat.setText(sonucBul)


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTumTahsilatGetir() {
        alacak_=0.0
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(5)
        val urlek ="&username="+username+"&uid="+uid+
                "&baslangic_Tarihi="+edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat
        var url = Consts.TahsilatListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("tum_liste", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val tahsilat=response["TahsilatToplam"]
                    /*val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        alacak_+= item.getString("ALACAK").toString().toFloat().toDouble()


                    }*/
                    alacak_=tahsilat.toString().toFloat().toDouble()
                    Toast.makeText(this,"alacak_tahsilat"+alacak_.toString(),Toast.LENGTH_LONG).show()

                    var binler = 1000
                    var yuzler = 1000
                    var ondalik = 18 % 100
                    var dizi = tahsilat.toString().split(".")
                    binler = dizi[0].toInt() / 1000
                    yuzler = dizi[0].toInt() % 1000
                    ondalik = (dizi[0].toInt() * 100).toInt() % 100
                    var sonucBul=""
                    if (binler == 0) {
                        sonucBul=dizi[0].toFloat().toString() + "₺"

                    } else
                        sonucBul=
                            String.format("%,d.%03d,%02d", binler, abs(yuzler), ondalik) + "₺"
                    txt_gelir.setText(sonucBul+"("+getString(R.string.tahsilat)+")")
                    txt_gelir_hesaplama.setText(dizi[0].toString())
                    onHesapla()

                   // onTumOdemeGetir(tahsilat as Double)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()


                }


            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.alacakodemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),error.message.toString())
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTumOdemeGetir() {
        borc_=0.0
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(5)
        val urlek ="&username="+username+"&uid="+uid+
                "&baslangic_Tarihi="+edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat
        var url = Consts.OdemeListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("tum_liste", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val odeme=response["OdemeToplam"]
                    borc_=odeme.toString().toFloat().toDouble()
                   /* val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        borc_+= item.getString("BORC").toString().toFloat().toDouble()


                    }
*/
                    var sonucBul=""
                    var binler = 1000
                    var yuzler = 1000
                    var ondalik = 18 % 100
                    var dizi = odeme.toString().split(".")
                    binler = dizi[0].toInt() / 1000
                    yuzler = dizi[0].toInt() % 1000
                    ondalik = (dizi[0].toInt() * 100).toInt() % 100
                    if (binler == 0) {
                        sonucBul=dizi[0].toFloat().toString() + "₺"

                    } else
                        sonucBul=
                            String.format("%,d.%03d,%02d", binler, abs(yuzler), ondalik) + "₺"
                    txt_gider.setText(sonucBul+"("+getString(R.string.odeme)+")")
                    txt_gider_hesaplama.setText(dizi[0].toString())
                   // onHesapla(alacak_, odeme as Double)
                    onHesapla()

                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()


                }


            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.alacakodemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),error.message.toString())
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)

    }/*
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
                EVRAK_TARIHI[i]

            )


            newArrayList.add(news)
        }

        recyler_tum_alacak_odeme_listesi.adapter = tahsilatOdemeGetirAdapter(newArrayList)
    }

    private fun tumAlacakOdemeTarihGetir() {
        Toast.makeText(this,getString(R.string.secilentarihegoretumalacakodemebilgilerilisteleniyor), Toast.LENGTH_LONG).show()
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
        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+
                edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat
        //"&CariID="+MusteriId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.TumALacakOdemeListesi + urlek
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
                        ISLEM_TIPI.add(item.getString("ISLEM_TIPI"))
                        ACIKLAMA.add(item.getString("ACIKLAMA"))
                        EVRAK_TARIHI.add(item.getString("EVRAK_TARIHI"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.alacakodemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.alacakodemelistesibulunmamakta))


                }
                recyler_tum_alacak_odeme_listesi.layoutManager = LinearLayoutManager(this)
                recyler_tum_alacak_odeme_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<TahsilatOdemeModel>()
                getUserData(this)

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.alacakodemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.alacakodemelistesibulunmamakta))

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }
*/
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
    private fun onBaslat() {
        ivBack=findViewById(R.id.ivBack)
        edTarih1=findViewById(R.id.edTarih1)
        edTarih2=findViewById(R.id.edTarih2)
        btnGoster=findViewById(R.id.btnGoster)
        txt_gelir=findViewById(R.id.txt_gelir)
        txt_gider=findViewById(R.id.txt_gider)
        txt_tahsilat=findViewById(R.id.txt_tahsilat)
        txt_odeme=findViewById(R.id.txt_odeme)
        progressBar = findViewById(R.id.progressBar)
        txt_gelir_hesaplama = findViewById(R.id.txt_gelir_hesaplama)
        txt_gider_hesaplama = findViewById(R.id.txt_gider_hesaplama)
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
    }
    fun getSharedViewModel(): SharedViewModel {
        return faturaViewModel
    }
}