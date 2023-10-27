package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.selpar.selparbulut.MainActivity
import com.selpar.selparbulut.R
import org.w3c.dom.Text
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.ClickListener

class MyBooking : Fragment() {
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
    lateinit var acikonarimlar:String
    lateinit var kapalionarimlar:String
    lateinit var oncekionarimlar:String
    lateinit var frame_kartlar: FrameLayout
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
    lateinit var id_linearlayout: LinearLayout
    lateinit var txt_stok_ara:TextView
    lateinit var txt_cari_listesi:TextView
    lateinit var txt_arac_kabul:TextView
    lateinit var calismasekli:String
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_my_booking, container, false)

        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        FirmaLogo = args?.getString("FirmaLogo").toString()
        calismasekli = args?.getString("calismasekli").toString()
        KullaniciId = args?.getString("KullaniciId").toString()
        //Toast.makeText(requireContext(),"Firma logo"+FirmaLogo,Toast.LENGTH_LONG).show()
        acikonarimlar = args?.getString("acikonarimlar").toString()
        kapalionarimlar = args?.getString("kapalionarimlar").toString()
        oncekionarimlar = args?.getString("oncekionarimlar").toString()
        //Toast.makeText(requireContext(), uid , Toast.LENGTH_LONG).show()
        //val header=mainActivity!!.requireViewById<TextView>(R.id.headerNameTV)
       // header.setText(resources.getString(R.string.my_bookings))

        bugunAcilan=view.findViewById(R.id.bugunAcilan)
        acikKartlar=view.findViewById(R.id.acikKartlar)
        kapaliKartlar=view.findViewById(R.id.kapaliKartlar)
        oncekiOnarimlar=view.findViewById(R.id.oncekiOnarimlar)
        id_linearlayout = view.findViewById(R.id.id_linearlayout)
        txt_stok_ara = view.findViewById(R.id.txt_stok_ara)
        txt_cari_listesi = view.findViewById(R.id.txt_cari_listesi)
        txt_arac_kabul = view.findViewById(R.id.txt_arac_kabul)
        frame_kartlar=view.findViewById(R.id.frame_kartlar)

        if(calismasekli=="hom" || calismasekli=="ms") {
            bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
            bottomNavigation!!.show(2, true)
            bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
            bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.ic_chat))
            bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
            bottomNavigation!!.add(MeowBottomNavigation.Model(5, R.drawable.arama))
            bottomNavigation!!.setOnClickMenuListener(ClickListener { item ->
                when (item.id) {
                    1 -> {
                        id_linearlayout.visibility = GONE
                        assert(getFragmentManager() != null)
                        val childFragment = Home()
                        bundlem.putString("username", username)
                        bundlem.putString("uid", uid)
                        bundlem.putString("FirmaLogo", FirmaLogo)
                        bundlem.putString("calismasekli", calismasekli)
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
                        if (calismasekli == "hom" || calismasekli == "ms") {
                            id_linearlayout.visibility = GONE
                            val childFragment = MyBooking()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("KullaniciId", KullaniciId)
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
                            id_linearlayout.visibility = GONE
                            val childFragment = YetkiYokFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("KullaniciId", KullaniciId)
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
                    4 -> {
                        if (calismasekli == "hom" || calismasekli == "ms") {
                            id_linearlayout.visibility = GONE
                            val childFragment = AracFragment()
                            bundlem.putString("username", username)
                            bundlem.putString("uid", uid)
                            bundlem.putString("FirmaLogo", FirmaLogo)
                            bundlem.putString("calismasekli", calismasekli)
                            bundlem.putString("KullaniciId", KullaniciId)
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
                            bundlem.putString("KullaniciId", KullaniciId)
                            bundlem.putString("aranan", "ARAC")
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
            bottomNavigation!!.setOnClickMenuListener(ClickListener { item ->
                when (item.id) {
                    1 -> {
                        id_linearlayout.visibility = GONE
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

            }
            )
        }
        val childFragment = AcikKartlarFragment()
        bugunAcilan.background=resources.getDrawable(R.drawable.button_mavi)
        acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
        kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("FirmaLogo",FirmaLogo)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
        bugunAcilan.setOnClickListener {
            val childFragment = AcikKartlarFragment()
            bugunAcilan.background=resources.getDrawable(R.drawable.button_mavi)
            acikKartlar.background=resources.getDrawable(R.drawable.button_customer)
            kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
            oncekiOnarimlar.background=resources.getDrawable(R.drawable.button_customer)

            bundlem.putString("username",username)
            bundlem.putString("uid",uid)
            bundlem.putString("FirmaLogo",FirmaLogo)
            bundlem.putString("KullaniciId",KullaniciId)
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
            bundlem.putString("KullaniciId",KullaniciId)
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
            bundlem.putString("KullaniciId",KullaniciId)
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
            bundlem.putString("KullaniciId",KullaniciId)
            childFragment.arguments=bundlem
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_kartlar, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                .commit()
        }
        bottomNavigation!!.setOnShowListener(MeowBottomNavigation.ShowListener {
            // your codes
        })
        bottomNavigation!!.setOnReselectListener(MeowBottomNavigation.ReselectListener {
            // your codes
        })
        if(acikonarimlar=="acikonarimlar"){
            AcikKartlar()
        }
        if(kapalionarimlar=="kapalionarimlar"){
            KapaliOnarimlar()
        }
        if(oncekionarimlar=="oncekionarimlar"){
            OncekiOnarimlar()
        }
        return view
    }
    private fun onKontrol() {
        if(id_linearlayout.visibility== View.VISIBLE){
            id_linearlayout.visibility=GONE
        }else{
            id_linearlayout.visibility= View.VISIBLE
        }
    }

    fun AcikKartlar(){
        val childFragment = AcikKartlarGercekFragment()
        bugunAcilan.background=resources.getDrawable(R.drawable.button_customer)
        acikKartlar.background=resources.getDrawable(R.drawable.button_mavi)
        kapaliKartlar.background=resources.getDrawable(R.drawable.button_customer)
        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("KullaniciId",KullaniciId)
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