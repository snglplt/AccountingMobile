package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.GelenFaturaAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.model.GelenFaturaModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList

class GelenEFaturaActivity : AppCompatActivity() {
    lateinit var ivBack:ImageView
    lateinit var recyclerView_gelen_fatura:RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    lateinit var entegrator:String
    lateinit var entegratorkullanici:String
    lateinit var entegratorsifre:String
    lateinit var vergino:String
    lateinit var progressBar:ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gelen_efatura)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        onGelenFaturaGetir()
        progressBar = findViewById(R.id.progressBar)
        Toast.makeText(this,getString(R.string.suanentegratorebaglanipfaturalarlisteleniyor),Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        //Toast.makeText(this,entegrator,Toast.LENGTH_LONG).show()
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

    private fun onGelenFaturaGetir() {
        if(entegrator=="EFINANS"){
           // Toast.makeText(this,"EFINANS ENTEGRATÖR",Toast.LENGTH_LONG).show()
            val urlsb = "ekullanici=" + entegratorkullanici+"&esifre="+entegratorsifre+"&vergino="+vergino
            var url = "https://pratikhasar.com/finans/e_gelen_listesi_finans.php?" + urlsb
            Log.d("park_enterasyon", url)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["finans_gelen_faturalar"] as JSONArray
                        val itemList: ArrayList<GelenFaturaModel> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)

                            val itemModel = GelenFaturaModel(
                                vergino,
                                item.getString("InvoiceUUID"),
                                item.getString("SupplierTaxId"),
                                item.getString("SupplierName"),
                                item.getString("SupplierName"),
                                item.getString("SenderDate"),
                                "EFINANS",
                                entegratorkullanici,
                                entegratorsifre,
                                item.getString("InvoiceRef"),
                                item.getString("PayableAmount"),
                            )

                            itemList.add(itemModel)


                        }
                        val adapter =
                            GelenFaturaAdapter(itemList)

                        recyclerView_gelen_fatura.adapter = adapter
                        recyclerView_gelen_fatura.layoutManager = LinearLayoutManager(this)
                        recyclerView_gelen_fatura.setHasFixedSize(false)
                        newArrayList = arrayListOf<ClipData.Item>()
                        this?.let {
                            getUserData(it)

                        }
                    } catch (e: Exception) {
                        //  Toast.makeText(requireContext(), "HATA: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.faturabulunmamakta))
                    // in this case we are simply displaying a toast message.

                }
            )
            val timeout = 10000 // 10 seconds in milliseconds
            request.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(request)
        }
        else if(entegrator=="PARK"){
           // Toast.makeText(this,"PARK ENTEGRATÖR",Toast.LENGTH_LONG).show()
            val urlsb = "ekullanici=" + entegratorkullanici+"&esifre="+entegratorsifre
            var url = "https://pratikhasar.com/park/park.php?" + urlsb
            Log.d("park_enterasyon", url)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["park_faturalar"] as JSONArray
                        val itemList: ArrayList<GelenFaturaModel> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)

                            val itemModel = GelenFaturaModel(
                                "1",
                                item.getString("InvoiceUUID"),
                                item.getString("SupplierTaxId"),
                                item.getString("SupplierName"),
                                item.getString("SupplierName"),
                                item.getString("SenderDate"),
                                "PARK",
                                entegratorkullanici,
                                entegratorsifre,
                                item.getString("InvoiceRef"),
                                item.getString("PayableAmount"),
                            )

                            itemList.add(itemModel)


                        }
                        val adapter =
                            GelenFaturaAdapter(itemList)

                        recyclerView_gelen_fatura.adapter = adapter
                        recyclerView_gelen_fatura.layoutManager = LinearLayoutManager(this)
                        recyclerView_gelen_fatura.setHasFixedSize(false)
                        newArrayList = arrayListOf<ClipData.Item>()
                        this?.let {
                            getUserData(it)

                        }
                    } catch (e: Exception) {
                        //  Toast.makeText(requireContext(), "HATA: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.faturabulunmamakta))
                    // in this case we are simply displaying a toast message.

                }
            )
            val timeout = 10000 // 10 seconds in milliseconds
            request.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(request)
        }else {
            val urlsb = "firma_id=" + "1"
            var url = "https://pratikhasar.com/netting/e_gelen_listesi_selpar.php?" + urlsb
            Log.d("RESİMYOL", url)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["aktarilmayan_faturalar"] as JSONArray
                        val itemList: ArrayList<GelenFaturaModel> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)

                            val itemModel = GelenFaturaModel(
                                "1",
                                item.getString("id"),
                                item.getString("durumu"),
                                item.getString("firma"),
                                item.getString("cari"),
                                item.getString("tarih"),
                                item.getString("profil"),
                                item.getString("evrakno"),
                                item.getString("fatura_tipi"),
                                item.getString("fatura_id"),
                                item.getString("tutar"),
                            )

                            itemList.add(itemModel)


                        }
                        val adapter =
                            GelenFaturaAdapter(itemList)

                        recyclerView_gelen_fatura.adapter = adapter
                        recyclerView_gelen_fatura.layoutManager = LinearLayoutManager(this)
                        recyclerView_gelen_fatura.setHasFixedSize(false)
                        newArrayList = arrayListOf<ClipData.Item>()
                        this?.let {
                            getUserData(it)

                        }
                    } catch (e: Exception) {
                        //  Toast.makeText(requireContext(), "HATA: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.faturabulunmamakta))
                    // in this case we are simply displaying a toast message.

                }
            )
            val timeout = 10000 // 10 seconds in milliseconds
            request.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(request)
            val simpleCallback = object :
                ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.RIGHT
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

                    // If you want to add a background, a text, an icon
                    //  as the user swipes, this is where to start decorating
                    //  I will link you to a library I created for that below
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
                                R.color.sitecolor
                            )
                        )
                        //.addSwipeLeftLabel(getString(R.string.sil))
                        .addSwipeRightLabel("PDF")
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

                @SuppressLint("ResourceAsColor")
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    when (direction) {
                        ItemTouchHelper.RIGHT -> {
                            val txt_faturaid =
                                viewHolder.itemView.findViewById<TextView>(R.id.txt_fatura_id)
                            val txt_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txt_id)

                            onPdfGoster(
                                txt_id.getText().toString(),
                                txt_faturaid.getText().toString()
                            )
                            onGelenFaturaGetir()

                            /*onarim_guncelle(txt_stokAdi.getText().toString(),
                                txt_StokNo.getText().toString(),txtMiktar.getText().toString().toInt(),txtFiyat.getText().toString().toFloat(),txt_kdv.getText().toString())
                            // Do something when a user swept right*/
                        }
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView_gelen_fatura)
        }

    }
    private fun onPdfGoster(id: String, fatura_id: String) {
        if(entegrator=="EFINANS"){
            val i= Intent(this, FaturaPdfGosterActivity::class.java)
            i.putExtra("path","https://pratikhasar.com/finans/e_gelen_pdf_finans.php?InvoiceUUID="+
                    id+"&id="+fatura_id+"&ekullanici="+entegratorkullanici+"&esifre="+entegratorsifre+"&vergino="+vergino)
            startActivity(i)
        }
        else if(entegrator=="PARK"){
            val i= Intent(this, FaturaPdfGosterActivity::class.java)
            i.putExtra("path","https://pratikhasar.com/park/e_gelen_pdf_park.php?InvoiceUUID="+
                    id+"&id="+fatura_id+"&ekullanici="+entegratorkullanici+"&esifre="+entegratorsifre)
            startActivity(i)
        }else {
            val i = Intent(this, FaturaPdfGosterActivity::class.java)
            i.putExtra(
                "path",
                "https://pratikhasar.com/netting/e_gelen_pdf.php?firma_id=1&InvoiceUUID=" + id + "&id=" + fatura_id
            )
            startActivity(i)
        }


    }
    private fun getUserData(context: Context) {
        val itemList: ArrayList<GelenFaturaModel> = ArrayList()


        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    private fun onBaslat() {
        entegrator=intent.getStringExtra("entegrator").toString()
        entegratorkullanici=intent.getStringExtra("entegratorkullanici").toString()
        entegratorsifre=intent.getStringExtra("entegratorsifre").toString()
        vergino=intent.getStringExtra("vergino").toString()
        ivBack=findViewById(R.id.ivBack)
        recyclerView_gelen_fatura=findViewById(R.id.recyclerView_gelen_fatura)
    }
}