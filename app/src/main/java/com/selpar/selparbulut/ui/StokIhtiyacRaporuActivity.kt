package com.selpar.selparbulut.ui

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
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.SpinnerAdapter
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
import com.selpar.selparbulut.adapter.acikKartGetirAdapter
import com.selpar.selparbulut.adapter.stokIhtiyacRaporuGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import com.selpar.selparbulut.model.StokIhtiyacRaporu
import com.selpar.selparbulut.model.TahsilatOdemeModel
import org.json.JSONArray
import java.time.LocalDate

class StokIhtiyacRaporuActivity : AppCompatActivity() {
    lateinit var ivBack:ImageView
    lateinit var btn_search:ImageView
    lateinit var progressBar:ProgressBar
    lateinit var edTarih1:EditText
    lateinit var edTarih2:EditText
    lateinit var txtstok_no:EditText
    lateinit var txturun_no:EditText
    lateinit var spinner_gun:Spinner
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    lateinit var edTarih1_dat:String
    lateinit var edTarih2_dat:String
    var itemList_gun_araligi=ArrayList<String>()
    var GrupKodu=ArrayList<String>()
    var StokNo=ArrayList<String>()
    var StokAdi=ArrayList<String>()
    var SatilanAdet=ArrayList<String>()
    var IlSonArasiFark=ArrayList<String>()
    var GunlukSatis=ArrayList<String>()
    var AylikSatis=ArrayList<String>()
    var Mevcut=ArrayList<String>()
    var KalanGun=ArrayList<String>()
    var IhtiyacAdeti=ArrayList<String>()
    var Raf=ArrayList<String>()
    var newArrayList=ArrayList<StokIhtiyacRaporu>()
    lateinit var recylerview_stok_ihtiyac_raporu:RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok_ihtiyac_raporu)
        onBaslat()

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.stoknogiriparamayapin), Toast.LENGTH_LONG).show()

        itemList_gun_araligi.add("30")
        itemList_gun_araligi.add("60")
        itemList_gun_araligi.add("90")
        itemList_gun_araligi.add("120")
        itemList_gun_araligi.add("150")
        itemList_gun_araligi.add("180")
        itemList_gun_araligi.add("210")
        itemList_gun_araligi.add("240")
        itemList_gun_araligi.add("270")
        itemList_gun_araligi.add("300")
        itemList_gun_araligi.add("330")


    val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
        itemList_gun_araligi as List<Any?>
        )
        spinner_gun.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

        //startLoadingAnimation()

        // Toast.makeText(requireContext(),username+ "kartlar"+uid,Toast.LENGTH_LONG).show()
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val startDate = LocalDate.of(year, month+1, day)

        // Belirli gün sayısını çıkarma işlemi
        val calendar = Calendar.getInstance()

        // Şu anki yıl bilgisini alın
        val currentYear = calendar.get(Calendar.YEAR)

        // Bir önceki yılı hesaplayın
        val previousYear = currentYear - 1
        val daysToSubtract = 365
        val daysToSubtract_ = 365
        val resultDate = startDate.minusDays(daysToSubtract.toLong())

        val resultDate_ = startDate.minusDays(daysToSubtract_.toLong())
        edTarih1_dat=previousYear.toString()+"-"+resultDate.monthValue+"-"+resultDate.dayOfMonth
        edTarih1.setText(resultDate.dayOfMonth.toString()+"-"+resultDate.monthValue+"-"+previousYear)
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
        btn_search.setOnClickListener {
            if((txtstok_no.getText().toString().isNotEmpty() && txturun_no.getText().toString().isEmpty()) ||
                txtstok_no.getText().toString().isEmpty() && txturun_no.getText().toString().isNotEmpty()) {
                startLoadingAnimation()
                stokIhtiyacRaporuGetir()
            }else{
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.lutfenkriterseciniz))
            }
       }

    }

    private fun stokIhtiyacRaporuGetir() {
        GrupKodu.clear()
        StokNo.clear()
        StokAdi.clear()
        SatilanAdet.clear()
        IlSonArasiFark.clear()
        GunlukSatis.clear()
        AylikSatis.clear()
        Mevcut.clear()
        KalanGun.clear()
        IhtiyacAdeti.clear()
        Raf.clear()
        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+
                edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat+"&gun="+spinner_gun.selectedItem.toString()+
                "&stokNo="+txtstok_no.getText().toString()+"&stokAdi="+txturun_no.getText().toString()
        var url = Consts.StokIhtiyacRaporu + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("stok_ihtiyac_raporu", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        GrupKodu.add(item.getString("GrupKodu"))
                        StokNo.add(item.getString("StokNo"))
                        StokAdi.add(item.getString("StokAdi"))
                        SatilanAdet.add(item.getString("SatilanAdet"))
                        IlSonArasiFark.add(item.getString("İlSonArasiFark"))
                        GunlukSatis.add(item.getString("GunlukSatis"))
                        AylikSatis.add(item.getString("AylikSatis"))
                        Mevcut.add(item.getString("Mevcut"))
                        KalanGun.add(item.getString("KalanGun"))
                        IhtiyacAdeti.add(item.getString("IhtiyacAdeti"))
                        Raf.add(item.getString("Raf"))
                    }
                    recylerview_stok_ihtiyac_raporu.layoutManager = LinearLayoutManager(this)
                    recylerview_stok_ihtiyac_raporu.setHasFixedSize(false)
                    newArrayList = arrayListOf<StokIhtiyacRaporu>()
                    getUserData(this)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.odemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.odemelistesibulunmamakta))


                }

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(this,getString(R.string.odemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.odemelistesibulunmamakta))

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }
    private fun getUserData(context: Context) {
        for (i in GrupKodu.indices) {
            val news = StokIhtiyacRaporu(
                FirmaLogo,
                username,
                uid,
                GrupKodu[i],
                StokNo[i],
                StokAdi[i],
                SatilanAdet[i],
                IlSonArasiFark[i],
                GunlukSatis[i],
                AylikSatis[i],
                Mevcut[i],
                KalanGun[i],
                IhtiyacAdeti[i],
                Raf[i],


                )


            newArrayList.add(news)
        }
        recylerview_stok_ihtiyac_raporu.adapter = stokIhtiyacRaporuGetirAdapter(newArrayList)

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
        ivBack=findViewById(R.id.ivBack)
        progressBar=findViewById(R.id.progressBar)
        edTarih1=findViewById(R.id.edTarih1)
        edTarih2=findViewById(R.id.edTarih2)
        btn_search=findViewById(R.id.btn_search)
        txtstok_no=findViewById(R.id.txtstok_no)
        txturun_no=findViewById(R.id.txturun_no)
        spinner_gun=findViewById(R.id.spinner_gun)
        recylerview_stok_ihtiyac_raporu=findViewById(R.id.recylerview_stok_ihtiyac_raporu)
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        FirmaLogo=intent.getStringExtra("FirmaLogo").toString()
    }

}