package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Canvas
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.CariListAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.CariListModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.time.LocalDate

class TahsilatEkleActivity : AppCompatActivity() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var KullaniciId:String
    lateinit var PlasiyerAdi:String
    lateinit var PlasiyerId:String
    lateinit var ivBack:ImageView
    lateinit var autoCari: AutoCompleteTextView
    lateinit var txtPlasiyer:TextView
    lateinit var spinnerPlasiyer:Spinner
    lateinit var spinnerOdemeTuru:Spinner
    lateinit var spinnerHesap:Spinner
    lateinit var edOdemeTarihi:EditText
    lateinit var edVirman:EditText
    lateinit var spinnerParaBirimi:Spinner
    lateinit var edTutar:EditText
    lateinit var editTextAciklama:EditText
    lateinit var tblrow_virman: LinearLayout
    lateinit var btntahsilatEkle:Button
    lateinit var btnVirmanAra:Button
    lateinit var btnCariAra:Button
    lateinit var edCaritext:EditText
    var itemList_cari_adi=ArrayList<String>()
    var itemList_OdemeAdi=ArrayList<String>()
    var itemList_OdemeId=ArrayList<String>()
    var itemList_Plasiyer=ArrayList<String>()
    var itemList_MusteriID=ArrayList<String>()
    var itemList_Hesap=ArrayList<String>()
    var itemList_HesapAdiID=ArrayList<String>()
    var itemList_BirimYazi=ArrayList<String>()
    var itemList_ParaId=ArrayList<String>()
    var itemList_cari_id=ArrayList<String>()
    val cariModelList: ArrayList<CariListModel> = ArrayList()
    lateinit var plasiyerIdBul:String
    lateinit var odemeId:String
    lateinit var hesapId:String
    lateinit var paraBirimiId:String
    lateinit var edTarih1_dat:String
     var cariId:String=""
     var virmanId:String=""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahsilat_ekle)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        onAutoCariDoldur()
        onOdemeTurleri()
        onPlasiyer()
        onParaBirimi()
        if(intent.getStringExtra("cariUnvan").toString()!="" && intent.getStringExtra("cariUnvan").toString()!="null" && intent.getStringExtra("cariUnvan").toString()!=null){
            edCaritext.setText(intent.getStringExtra("cariUnvan").toString())
        }else{

        }
        //onHesapGetir(itemList_OdemeId[position])
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val startDate = LocalDate.of(year, month+1, day)

        // Belirli gün sayısını çıkarma işlemi
        val daysToSubtract = 30
        val daysToSubtract_ = 0
        val resultDate = startDate.minusDays(daysToSubtract.toLong())

        edTarih1_dat=year.toString()+"-"+resultDate.monthValue+"-"+resultDate.dayOfMonth
        edOdemeTarihi.setText(resultDate.dayOfMonth.toString()+"-"+resultDate.monthValue+"-"+year)

        edOdemeTarihi.setOnFocusChangeListener { _, hasFocus ->
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

                        edOdemeTarihi.setText(dat_gosterilen)
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

        spinnerPlasiyer?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    plasiyerIdBul=itemList_MusteriID[position]
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        spinnerHesap?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    hesapId=itemList_HesapAdiID[position]
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        spinnerParaBirimi?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    paraBirimiId=itemList_ParaId[position]
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        spinnerOdemeTuru?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    odemeId=itemList_OdemeId[position]
                    onHesapGetir(itemList_OdemeId[position])
                    if(selectedItem.toString()=="VİRMAN"){
                        tblrow_virman.visibility= VISIBLE
                    }else{
                        tblrow_virman.visibility= GONE
                    }
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        if(PlasiyerAdi.isNotEmpty()){
            txtPlasiyer.setText(PlasiyerAdi)
            txtPlasiyer.visibility=VISIBLE
        }
        btnCariAra.setOnClickListener {
            if(edCaritext.getText().toString().isNotEmpty()){
                onCariDoldur(edCaritext.getText().toString())

            }else
                Toast.makeText(this,getString(R.string.aramagirin),Toast.LENGTH_LONG).show()
        }
        btnVirmanAra.setOnClickListener {
            if(edVirman.getText().toString().isNotEmpty()){
                onVirmanDoldur(edVirman.getText().toString())

            }else
                Toast.makeText(this,getString(R.string.aramagirin),Toast.LENGTH_LONG).show()
        }


        btntahsilatEkle.setOnClickListener {
            if( edOdemeTarihi.getText().toString().isNotEmpty()
                && edTutar.getText().toString().isNotEmpty() ){
                onApi()
            }
            else{
                val alert=Alert()
                alert.showAlert(this,getString(R.string.uyari),getString(R.string.tumlalanlaridoldur))
            }
        }


    }

    private fun onCariDoldur(cariUnavan: String) {
        val urlek ="&username="+username+"&uid="+uid+"&search="+cariUnavan+"&KullaniciId="+KullaniciId

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
                        itemList_cari_adi.add( item.getString("Cariunvan"))
                        itemList_cari_id.add( item.getString("MusteriID"))
                        val itemModel = CariListModel(
                            item.getString("Cariunvan").toString(),
                            item.getString("MusteriID")
                        )
                        cariModelList.add(itemModel)


                    }
                    onCariAlert(cariModelList)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.aranancaribulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.aranancaribulunmuyor))


                }


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
    private fun onVirmanDoldur(cariUnavan: String) {
        val urlek ="&username="+username+"&uid="+uid+"&search="+cariUnavan+"&KullaniciId="+KullaniciId

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
                        itemList_cari_adi.add( item.getString("Cariunvan"))
                        itemList_cari_id.add( item.getString("MusteriID"))
                        val itemModel = CariListModel(
                            item.getString("Cariunvan").toString(),
                            item.getString("MusteriID")
                        )
                        cariModelList.add(itemModel)


                    }
                    onVirmanAlert(cariModelList)
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.aranancaribulunmuyor), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),getString(R.string.aranancaribulunmuyor))


                }


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

    @SuppressLint("MissingInflatedId")
    private fun onCariAlert(cariModelList: ArrayList<CariListModel>) {
        Toast.makeText(this,getString(R.string.sececeginizcariyisagakaydirin),Toast.LENGTH_LONG).show()

        val alertadd = AlertDialog.Builder(this)


        alertadd.setTitle(getString(R.string.cariunvan))
        val factory = LayoutInflater.from(this)
        val view: View =
            factory.inflate(R.layout.cari_item_list, null)
        val rc_stok_listesi: RecyclerView
        rc_stok_listesi = view.findViewById<RecyclerView>(R.id.rc_cari_unvan_listesi)

        val adapter =
            CariListAdapter(cariModelList)

        rc_stok_listesi.adapter = adapter
        rc_stok_listesi.layoutManager = LinearLayoutManager(this)
        rc_stok_listesi.setHasFixedSize(false)
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
                RecyclerViewSwipeDecorator.Builder( c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.red))
                    //.addSwipeLeftLabel("SİL")
                    .addSwipeRightLabel(getString(R.string.sec))
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
                    ItemTouchHelper.RIGHT -> {
                        val txt_aciId= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_id).text
                        val txt_cari_adi= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_adi).text
                        edCaritext.setText(txt_cari_adi.toString())
                        cariId=txt_aciId.toString()


                        //StokDoldur(sharedViewModel.firma_id,txt_stokno.toString())

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_stok_listesi)

        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.tamam)
        ) { dialogInterface, which ->


        }

        alertadd.show()
    }
    private fun onVirmanAlert(cariModelList: ArrayList<CariListModel>) {
        Toast.makeText(this,getString(R.string.sececeginizcariyisagakaydirin),Toast.LENGTH_LONG).show()
        val alertadd = AlertDialog.Builder(this)


        alertadd.setTitle(getString(R.string.cariunvan))
        val factory = LayoutInflater.from(this)
        val view: View =
            factory.inflate(R.layout.cari_item_list, null)
        val rc_stok_listesi: RecyclerView
        rc_stok_listesi = view.findViewById<RecyclerView>(R.id.rc_cari_unvan_listesi)

        val adapter =
            CariListAdapter(cariModelList)

        rc_stok_listesi.adapter = adapter
        rc_stok_listesi.layoutManager = LinearLayoutManager(this)
        rc_stok_listesi.setHasFixedSize(false)
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
                RecyclerViewSwipeDecorator.Builder( c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.red))
                    //.addSwipeLeftLabel("SİL")
                    .addSwipeRightLabel(getString(R.string.sec))
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
                    ItemTouchHelper.RIGHT -> {
                        val txt_aciId= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_id).text
                        val txt_cari_adi= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_adi).text
                        edVirman.setText(txt_cari_adi.toString())
                        virmanId=txt_aciId.toString()


                        //StokDoldur(sharedViewModel.firma_id,txt_stokno.toString())

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_stok_listesi)

        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.tamam)
        ) { dialogInterface, which ->


        }

        alertadd.show()
    }

    private fun onApi() {
        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+KullaniciId+"&IslemTipi=TAHSILAT"+
                "&cariId="+cariId.replace(" ","%20")+"&plasiyerId="+plasiyerIdBul+
                "&odemeTuru="+odemeId+"&hesapId="+hesapId+"&odemeTarihi="+edTarih1_dat+"&virmanId="+virmanId+
                "&paraBirimi="+paraBirimiId+"&tutar="+edTutar.getText().toString().trim()+"&aciklama="+editTextAciklama.getText().toString().trim()
        var url = Consts.OdemeTahsilatGiris + urlek
        Log.d("tahsilat_gonder",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val mesaj = response["message"]
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),mesaj.toString())



                }catch (e:Exception){
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),e.message.toString())
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun onParaBirimi() {
        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+KullaniciId
        var url = Consts.ParaBirimleri + urlek
        Log.d("ParaBirimleri_list_doldur",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_BirimYazi.add( item.getString("BirimYazi"))
                        itemList_ParaId.add( item.getString("ParaId"))
                    }


                    val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        itemList_BirimYazi as List<Any?>
                    )
                    spinnerParaBirimi.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

                }catch (e:Exception){
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),e.message.toString())
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun onHesapGetir(id: String) {
        itemList_Hesap.clear()
        itemList_HesapAdiID.clear()
        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+KullaniciId+"&OdemeTahsilatTipi="+id
        var url = Consts.OdmeTahsilatHesaplari + urlek
        Log.d("OdemeTahsilatTipi_list_doldur",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_Hesap.add( item.getString("HesapAdi"))
                        itemList_HesapAdiID.add( item.getString("HesapId"))
                    }


                    val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        itemList_Hesap as List<Any?>
                    )
                    spinnerHesap.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

                }catch (e:Exception){
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),e.message.toString())
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun onAutoCariDoldur() {
        itemList_cari_adi.clear()
        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+KullaniciId
        var url = Consts.CariListesiTahsilat + urlek
        Log.d("cari_list_doldur",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_cari_adi.add( item.getString("Cariunvan"))
                        itemList_cari_id.add( item.getString("MusteriID"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, itemList_cari_adi)

                    autoCari.setAdapter(adapter)
                 //   autoVirman.setAdapter(adapter)

                }catch (e:Exception){
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),e.message.toString())
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun onOdemeTurleri() {
        itemList_OdemeAdi.clear()
        itemList_OdemeId.clear()
        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+KullaniciId
        var url = Consts.OdemeTurleri + urlek
        Log.d("odeme_turleri_list_doldur",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_OdemeAdi.add( item.getString("OdemeAdi"))
                        itemList_OdemeId.add( item.getString("OdemeId"))
                    }


                    val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        itemList_OdemeAdi as List<Any?>
                    )
                    spinnerOdemeTuru.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

                }catch (e:Exception){
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),e.message.toString())
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }
    private fun onPlasiyer() {
        itemList_Plasiyer.clear()
        itemList_Plasiyer.add(PlasiyerAdi)
        val urlek ="&username="+username+"&uid="+uid+"&KullaniciId="+KullaniciId
        var url = Consts.Plasiyer + urlek
        Log.d("plasiyer_list_doldur",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_Plasiyer.add( item.getString("Cariunvan"))
                        itemList_MusteriID.add( item.getString("MusteriID"))
                    }


                    val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        itemList_Plasiyer as List<Any?>
                    )
                    spinnerPlasiyer.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

                }catch (e:Exception){
                    val alert= Alert()
                    alert.showAlert(this,getString(R.string.uyari),e.message.toString())
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }
    private fun onBaslat() {
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        KullaniciId=intent.getStringExtra("KullaniciId").toString()
        PlasiyerAdi=intent.getStringExtra("PlasiyerAdi").toString()
        PlasiyerId=intent.getStringExtra("PlasiyerId").toString()
        ivBack=findViewById(R.id.ivBack)
        edVirman=findViewById(R.id.edVirman)
        btnVirmanAra=findViewById(R.id.btnVirmanAra)
        btnCariAra=findViewById(R.id.btnCariAra)
        edCaritext=findViewById(R.id.edCaritext)
        btntahsilatEkle=findViewById(R.id.btntahsilatEkle)
        tblrow_virman=findViewById(R.id.tblrow_virman)
        autoCari=findViewById(R.id.autoCari)
        txtPlasiyer=findViewById(R.id.txtPlasiyer)
        spinnerPlasiyer=findViewById(R.id.spinnerPlasiyer)
        spinnerOdemeTuru=findViewById(R.id.spinnerOdemeTuru)
        edOdemeTarihi=findViewById(R.id.edOdemeTarihi)
        spinnerParaBirimi=findViewById(R.id.spinnerParaBirimi)
        edTutar=findViewById(R.id.edTutar)
        editTextAciklama=findViewById(R.id.editTextAciklama)
        spinnerHesap=findViewById(R.id.spinnerHesap)
    }
}