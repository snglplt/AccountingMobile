package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.selpar.selparbulut.MainActivity
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.orijinalStokGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.OrgListesiModel
import org.json.JSONArray


class StokCariAracFragment : Fragment() {
    private var bottomNavigation: MeowBottomNavigation? = null
    lateinit var bugunAcilan:ImageView
    lateinit var acikKartlar:ImageView
    lateinit var kapaliKartlar:ImageView
    lateinit var oncekiOnarimlar:ImageView
    var bundlem=Bundle()
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    lateinit var KullaniciId:String
    lateinit var aranan:String
    lateinit var acikonarimlar:String
    lateinit var kapalionarimlar:String
    lateinit var oncekionarimlar:String
    lateinit var frame_kartlar: FrameLayout
    lateinit var txtstok_no:EditText
    companion object {
        private const val ARG_DATA = "data"

        // Veriyi ChildFragment'a göndermek için bu metodu kullanacağız
        fun newInstance(data: String): MyBooking {

            val fragment = MyBooking()
            val args = Bundle()
            args.putString(ARG_DATA, data)
            fragment.arguments = args
            return fragment
        }
    }
    private var data: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString(ARG_DATA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private val mainActivity: MainActivity? = null
    lateinit var recyclerView_orijinal_stok_listesi: RecyclerView

    lateinit var progressBar: ProgressBar
    lateinit var txt_arama: EditText
    lateinit var btn_search: ImageView
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
    lateinit var calismasekli:String
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_stok_cari_arac, container, false)

        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        FirmaLogo = args?.getString("FirmaLogo").toString()
        aranan = args?.getString("aranan").toString()
        calismasekli = args?.getString("calismasekli").toString()
        KullaniciId = args?.getString("KullaniciId").toString()
        //Toast.makeText(requireContext(),"Firma logo"+FirmaLogo,Toast.LENGTH_LONG).show()
        acikonarimlar = args?.getString("acikonarimlar").toString()
        kapalionarimlar = args?.getString("kapalionarimlar").toString()
        oncekionarimlar = args?.getString("oncekionarimlar").toString()
        //Toast.makeText(requireContext(), uid , Toast.LENGTH_LONG).show()
        //val header=mainActivity!!.requireViewById<TextView>(R.id.headerNameTV)
        // header.setText(resources.getString(R.string.my_bookings))

        txtstok_no=view.findViewById(R.id.txtstok_no)
        bugunAcilan=view.findViewById(R.id.bugunAcilan)
        acikKartlar=view.findViewById(R.id.acikKartlar)
        kapaliKartlar=view.findViewById(R.id.kapaliKartlar)
        oncekiOnarimlar=view.findViewById(R.id.oncekiOnarimlar)
//        frame_kartlar=view.findViewById(R.id.frame_kartlar)

        txtstok_no.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Handle the Enter key press here
                // You can perform any action you want when Enter is pressed
                // For example, you can hide the keyboard or process the input
                // ...
                arananOrijinalStokGetir(txtstok_no.getText().toString())
                return@OnKeyListener true // Consume the key event
            }
            false // Let other key events be handled as usual
        })
        if(calismasekli=="hom" || calismasekli=="ms"){
            bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
            bottomNavigation!!.show(5, true)
            bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
            bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.ic_chat))
            bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
            bottomNavigation!!.add(MeowBottomNavigation.Model(5, R.drawable.arama))
            bottomNavigation!!.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
                when (item.id) {
                    1 -> {
                        //assert(getFragmentManager() != null)
                        //frame_kartlar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Home Tıklandı", Toast.LENGTH_LONG).show()
                        val childFragment = Home()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", KullaniciId)
                        childFragment.arguments = bundlem
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()

                    }

                    2 -> {
                        //Toast.makeText(requireContext(),username+ "----"+uid, Toast.LENGTH_LONG).show()
                        // communicator.padData(username)

                        val childFragment = MyBooking()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", "FirmaLogo")
                        bundlem.putString("KullaniciId", KullaniciId)
                        childFragment.arguments = bundlem

                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()
                    } //requireFragmentManager().beginTransaction().replace(R.id.frame, MyBooking()).commit()
                    3 -> {
                        val childFragment = CariFragment()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", KullaniciId)
                        childFragment.arguments = bundlem
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()
                    } //requireFragmentManager().beginTransaction().replace(R.id.frame, ChatList()).commit()
                    4 -> {
                        val childFragment = AracFragment()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", KullaniciId)
                        childFragment.arguments = bundlem
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()

                    } /*requireFragmentManager().beginTransaction()
                   .replace(R.id.frame, NotificationActivity()).commit()*/
                    5-> {
                    val childFragment = StokCariAracFragment()
                    bundlem.putString("username",username)
                    bundlem.putString("uid",uid)
                    bundlem.putString("FirmaLogo","FirmaLogo")
                    bundlem.putString("KullaniciId", KullaniciId)
                    childFragment.arguments=bundlem

                    childFragmentManager.beginTransaction()
                        .replace(R.id.frame, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                        .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                        .commit()

                        /*requireFragmentManager().beginTransaction()
                        .replace(R.id.frame, NotificationActivity()).commit()*/
                    }
                    else -> {}
                }

            }
            )}
        else{
            bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
            bottomNavigation!!.show(3, true)
            bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            // bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
            bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_chat))
            // bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
            bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.arama))
            bottomNavigation!!.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
                when (item.id) {
                    1 -> {
                       // id_linearlayout.visibility = View.GONE
                        assert(getFragmentManager() != null)
                        val childFragment = Home()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", KullaniciId)
                        childFragment.arguments = bundlem
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()
                    }

                    2 -> {
                        val childFragment = CariFragment()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", KullaniciId)
                        bundlem.putString("aranan", "CARI")
                        childFragment.arguments = bundlem

                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()
                    } //requireFragmentManager().beginTransaction().replace(R.id.frame, ChatList()).commit()
                    3 -> {
                        // onKontrol()
                        val childFragment = StokCariAracFragment()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", KullaniciId)
                        childFragment.arguments = bundlem

                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                childFragment
                            ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                            .commit()
                        /* txt_stok_ara.setOnClickListener {
                        onKontrol()
                        try{

                            val childFragment = StokCariAracFragment()
                            bundlem.putString("username",username)
                            bundlem.putString("uid",uid)
                            bundlem.putString("FirmaLogo",FirmaLogo)
                            bundlem.putString("aranan","STOK")
                            childFragment.arguments=bundlem

                            childFragmentManager.beginTransaction()
                                .replace(R.id.frame, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                        }catch (e:Exception){}

                    }
                    txt_cari_listesi.setOnClickListener {
                        onKontrol()
                        try {
                            val childFragment = CariFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("aranan", "CARI")
                            childFragment.arguments = bundlem

                            childFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    childFragment
                                ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                        }catch (e:Exception){}
                    }
                    txt_arac_kabul.setOnClickListener {
                        onKontrol()
                        try {
                            val childFragment = AracFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("aranan", "ARAC")
                            childFragment.arguments = bundlem

                            childFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    childFragment
                                ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                        }catch (e:java.lang.Exception){}
                    }*/

                    }
                    /*requireFragmentManager().beginTransaction()
                    .replace(R.id.frame, NotificationActivity()).commit()*/

                    else -> {}
                }

            })}
        bottomNavigation!!.setOnShowListener(MeowBottomNavigation.ShowListener {
            // your codes
        })
        bottomNavigation!!.setOnReselectListener(MeowBottomNavigation.ReselectListener {
            // your codes
        })/*
        if(aranan=="CARI"){
            val childFragment = AracFragment()

            bundlem.putString("username",username)
            bundlem.putString("uid",uid)
            bundlem.putString("FirmaLogo",FirmaLogo)
            childFragment.arguments=bundlem
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                .commit()
        }*/
        /*
        bugunAcilan.setOnClickListener {
            val childFragment = AcikKartlarFragment()
            bugunAcilan.background=resources.getDrawable(R.drawable.button_mavi)
            acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
            kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
            oncekiOnarimlar.background=resources.getDrawable(R.drawable.button_customer)

            bundlem.putString("username",username)
            bundlem.putString("uid",uid)
            bundlem.putString("FirmaLogo",FirmaLogo)
            childFragment.arguments=bundlem
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                .commit()
        }
        acikKartlar.setOnClickListener {
            val childFragment = AcikKartlarGercekFragment()
            bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
            acikKartlar.background=resources.getDrawable(R.drawable.button_mavi)
            kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
            oncekiOnarimlar.background=resources.getDrawable(R.drawable.button_customer)
            bundlem.putString("username",username)
            bundlem.putString("uid",uid)
            bundlem.putString("FirmaLogo",FirmaLogo)
            childFragment.arguments=bundlem
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                .commit()
        }
        kapaliKartlar.setOnClickListener {
            val childFragment = KapaliKartlarFragment()
            bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
            acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
            oncekiOnarimlar.background=resources.getDrawable(R.drawable.button_customer)
            kapaliKartlar.background=resources.getDrawable(R.drawable.button_mavi)
            bundlem.putString("username",username)
            bundlem.putString("uid",uid)
            bundlem.putString("FirmaLogo",FirmaLogo)
            childFragment.arguments=bundlem
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                .commit()
        }
        oncekiOnarimlar.setOnClickListener {
            val childFragment = OncekiOnarimlarFragment()
            bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
            acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
            kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
            oncekiOnarimlar.background=resources.getDrawable(R.drawable.button_mavi)
            bundlem.putString("username",username)
            bundlem.putString("uid",uid)
            bundlem.putString("FirmaLogo",FirmaLogo)
            childFragment.arguments=bundlem
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                .commit()
        }*/

        if(acikonarimlar=="acikonarimlar"){
            AcikKartlar()
        }
        if(kapalionarimlar=="kapalionarimlar"){
            KapaliOnarimlar()
        }
        if(oncekionarimlar=="oncekionarimlar"){
            OncekiOnarimlar()
        }
        recyclerView_orijinal_stok_listesi=view.findViewById(R.id.recyclerView_orijinal_stok_listesi)

        progressBar=view.findViewById(R.id.progressBar)
        txt_arama=view.findViewById(R.id.txt_arama)
        btn_search=view.findViewById(R.id.btn_search)

        Toast.makeText(requireContext(),getString(R.string.orijinalstoklistesiicinaramayapin), Toast.LENGTH_LONG).show()
        //startLoadingAnimation()
        btn_search.setOnClickListener { arananCariGetir(txt_arama.getText().toString()) }
        return view
    }
    private fun arananCariGetir(arananStok: String) {
        if(arananStok.isNotEmpty()){
            arananOrijinalStokGetir(arananStok)
        }else{
            Toast.makeText(requireContext(),getString(R.string.lutfenstokbilgisigiriniz), Toast.LENGTH_LONG).show()
        }

    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 2000) // Replace 3000 with your desired loading time
    }
    private fun arananOrijinalStokGetir(arananStok: String) {
        startLoadingAnimation()
        Sira.clear()
        StokNumarasi.clear()
        StokAdi.clear()
        OEMKodu.clear()
        ListeFiyati.clear()
        ParaBirimi.clear()
        Uretici.clear()
        Resim1.clear()
        GuncellemeTarihi.clear()
        Toast.makeText(requireContext(),getString(R.string.stoklarlisteleniyor), Toast.LENGTH_LONG).show()
        val urlek ="&username="+username+"&uid="+uid+"&search="+arananStok
        var url = Consts.OrgListesi + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
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
                    Toast.makeText(requireContext(),getString(R.string.stokbulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.stokbulunmamakta))


                }
                recyclerView_orijinal_stok_listesi.layoutManager = LinearLayoutManager(requireContext())
                recyclerView_orijinal_stok_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<OrgListesiModel>()
                getUserData(requireContext())

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(),getString(R.string.stokbulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.stokbulunmamakta))

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
    fun AcikKartlar(){
        val childFragment = AcikKartlarGercekFragment()
        bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
        acikKartlar.background=resources.getDrawable(R.drawable.button_mavi)
        kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun KapaliOnarimlar(){
        val childFragment = KapaliKartlarFragment()
        bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
        acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
        kapaliKartlar.background=resources.getDrawable(R.drawable.button_mavi)
        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()

    }
    fun OncekiOnarimlar(){
        val childFragment = OncekiOnarimlarFragment()
        bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
        acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
        kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
        oncekiOnarimlar.background=resources.getDrawable(R.drawable.button_mavi)
        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()

    }

}