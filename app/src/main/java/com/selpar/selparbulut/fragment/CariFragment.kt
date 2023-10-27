package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.cariGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.CariListesiModel
import org.json.JSONArray
import java.util.ArrayList


class CariFragment : Fragment() {
    lateinit var reclerview_cari_listesi: RecyclerView
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
    lateinit var btn_search: ImageView
    private lateinit var newArrayList: ArrayList<CariListesiModel>
    lateinit var txtcari_no: EditText
    lateinit var progressBar: ProgressBar
    lateinit var FirmaLogo:String
    lateinit var calismasekli:String
    private var bottomNavigation: MeowBottomNavigation? = null
    var bundlem=Bundle()
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cari, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        reclerview_cari_listesi = view.findViewById(R.id.reclerview_cari_listesi)
        btn_search = view.findViewById(R.id.btn_search)
        txtcari_no = view.findViewById(R.id.txtcari_no)
        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        FirmaLogo = args?.getString("FirmaLogo").toString()
        calismasekli = args?.getString("calismasekli").toString()
        kullaniciId=args?.getString("KullaniciId").toString()

        cariListesiGetir()

        Toast.makeText(requireContext(), getString(R.string.carilerlisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        btn_search.setOnClickListener { arananCariGetir(txtcari_no.getText().toString()) }
        if(calismasekli=="hom" || calismasekli=="ms") {
            bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
            bottomNavigation!!.show(3, true)
            bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
            bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.ic_chat))
            bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
            bottomNavigation!!.add(MeowBottomNavigation.Model(5, R.drawable.arama))
            bottomNavigation!!.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
                when (item.id) {
                    1 -> {
                        //id_linearlayout.visibility = View.GONE
                        assert(getFragmentManager() != null)
                        val childFragment = Home()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("calismasekli", calismasekli)
                        bundlem.putString("KullaniciId", kullaniciId)
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
                        if (calismasekli == "hom" || calismasekli == "ms") {
                          //  id_linearlayout.visibility = View.GONE
                            val childFragment = MyBooking()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("KullaniciId", kullaniciId)
                            childFragment.arguments = bundlem

                            childFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    childFragment
                                ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.burayayetkinizbulunmamakta),
                                Toast.LENGTH_LONG
                            ).show()
                            //id_linearlayout.visibility = View.GONE
                            val childFragment = YetkiYokFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("KullaniciId", kullaniciId)
                            childFragment.arguments = bundlem

                            childFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    childFragment
                                ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                            //bottomNavigation!!.show(1, true)
                        }
                    } //requireFragmentManager().beginTransaction().replace(R.id.frame, MyBooking()).commit()
                    3 -> {
                        val childFragment = CariFragment()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("calismasekli", calismasekli)
                        bundlem.putString("aranan", "CARI")
                        bundlem.putString("KullaniciId", kullaniciId)
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
                        if (calismasekli == "hom" || calismasekli == "ms") {
                          //  id_linearlayout.visibility = View.GONE
                            val childFragment = AracFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("KullaniciId", kullaniciId)
                            childFragment.arguments = bundlem

                            childFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    childFragment
                                ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                        } else {
                            val childFragment = YetkiYokAracFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("aranan", "ARAC")
                            bundlem.putString("KullaniciId", kullaniciId)
                            childFragment.arguments = bundlem

                            childFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    childFragment
                                ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                                .commit()
                        }
                    }

                    5 -> {
                        // onKontrol()
                        val childFragment = StokCariAracFragment()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("calismasekli", calismasekli)
                        bundlem.putString("KullaniciId", kullaniciId)
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

            }
            )
        }else{
            bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
            bottomNavigation!!.show(2, true)
            bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            //  bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
            bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_chat))
            // bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
            bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.arama))
            bottomNavigation!!.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
                when (item.id) {
                    1 -> {
                     //   id_linearlayout.visibility = View.GONE
                        assert(getFragmentManager() != null)
                        val childFragment = Home()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("KullaniciId", kullaniciId)
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
                        bundlem.putString("KullaniciId", kullaniciId)
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
                        bundlem.putString("KullaniciId", kullaniciId)
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

            }
            )
        }
        bottomNavigation!!.setOnShowListener(MeowBottomNavigation.ShowListener {
            // your codes
        })
        bottomNavigation!!.setOnReselectListener(MeowBottomNavigation.ReselectListener {
            // your codes
        })
        return view
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

    private fun arananCariGetir(arananCari: String) {
        if (arananCari.isNotEmpty()) {
            cariArananGetir(arananCari)
        } else {
            Toast.makeText(requireContext(), getString(R.string.caribilgisigirin), Toast.LENGTH_LONG).show()
        }

    }

    private fun cariArananGetir(arananCari: String) {
        Toast.makeText(requireContext(), getString(R.string.aranancarilisteleniyor), Toast.LENGTH_LONG).show()
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

        val urlek =
            "&username=" + username + "&uid=" + uid + "&search=" + arananCari + "&KullaniciId=" + kullaniciId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.CariListesiArama + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
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
                        if (item.getString("Cariunvan").toString() != "") {
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
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.aranancaribulunmuyor),
                        Toast.LENGTH_LONG
                    ).show()
                    val alert = Alert()
                    alert.showAlert(
                        requireContext(),
                        getString(R.string.uyari),
                        getString(R.string.aranancaribulunmuyor)
                    )


                }
                reclerview_cari_listesi.layoutManager = LinearLayoutManager(requireContext())
                reclerview_cari_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<CariListesiModel>()
                getUserData(requireContext())

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), getString(R.string.aranancaribulunmuyor), Toast.LENGTH_LONG)
                    .show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert = Alert()
                alert.showAlert(
                    requireContext(),
                    getString(R.string.uyari),
                    getString(R.string.aranancaribulunmuyor)
                )

            }
        )
        queue.add(request)


    }



    private fun cariListesiGetir() {
        val urlek =
            "&username=" + username + "&uid=" + uid + "&KullaniciId=" + kullaniciId + "&CariTipi=CARİ"

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.CariListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
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
                        if (item.getString("Cariunvan").toString() != "") {
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
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.carilistesibulunmuyor),
                        Toast.LENGTH_LONG
                    ).show()
                    val alert = Alert()
                    alert.showAlert(
                        requireContext(),
                        getString(R.string.uyari),
                        getString(R.string.carilistesibulunmuyor)
                    )


                }
                reclerview_cari_listesi.layoutManager = LinearLayoutManager(requireContext())
                reclerview_cari_listesi.setHasFixedSize(false)
                newArrayList = arrayListOf<CariListesiModel>()
                getUserData(requireContext())

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(), getString(R.string.carilistesibulunmuyor), Toast.LENGTH_LONG)
                    .show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert = Alert()
                alert.showAlert(
                    requireContext(),
                    getString(R.string.uyari),
                    getString(R.string.carilistesibulunmuyor)
                )

            }
        )
        queue.add(request)


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