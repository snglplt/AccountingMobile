package com.selpar.selparbulut.fragment

import ImageSliderAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.db.williamchart.view.DonutChartView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.ClickListener
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.ReselectListener
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.ShowListener
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.selpar.selparbulut.MainActivity
import com.selpar.selparbulut.R
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Communicator
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.ui.StokIhtiyacRaporuActivity
import java.util.Timer
import java.util.TimerTask
import kotlin.math.abs
import com.selpar.selparbulut.adapter.CustomMarkerView
import com.selpar.selparbulut.ui.AlisEvrakActivity
import com.selpar.selparbulut.ui.EvrakSatisActivity
import com.selpar.selparbulut.ui.OdemeEkleActivity
import com.selpar.selparbulut.ui.SatisEvrakDetayActivity
import com.selpar.selparbulut.ui.TahsilatEkleActivity

class Home : Fragment(), View.OnClickListener {
    private var baseActivity: MainActivity? = null
    private var fragmentManager: FragmentManager? = null
    private val discoverFragment: DiscoverFragment = DiscoverFragment()
    private val nearByFragment: NearByFragment = NearByFragment()
    private var bottomNavigation: MeowBottomNavigation? = null
    var cTVnearby: TextView? = null
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    lateinit var id_linearlayout:LinearLayout
    lateinit var txt_stok_ara:TextView
    lateinit var txt_cari_listesi:TextView
    lateinit var txt_arac_kabul:TextView
     var bundlem=Bundle()
    lateinit var communicator:Communicator
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
    private lateinit var viewPager: ViewPager2
    private lateinit var imageSliderAdapter: ImageSliderAdapter


    // Image resources for the slideshow
    private val images = arrayOf(
        R.drawable.tn_r1,
        R.drawable.tn_r2,
        R.drawable.tn_r3,
        R.drawable.tn_r4,
        // Add more images here as needed
    )
    private val timer = Timer()
    private var currentPage = 0

        private val barSet = listOf(
            "JAN" to 4F,
            "FEB" to 7F,
            "MAR" to 2F,
            "MAY" to 2.3F,
            "APR" to 5F,
            "JUN" to 4F
        )

        private val horizontalBarSet = listOf(
            "PORRO" to 5F,
            "FUSCE" to 6.4F,
            "EGET" to 3F
        )

        private val animationDuration = 2000L

    lateinit var calismasekli:String
    lateinit var KullaniciId:String
    lateinit var PlasiyerId:String
    lateinit var PlasiyerAdi:String
    lateinit var linearlaout_kasa_pos_cek_havale:LinearLayout
    lateinit var txtkasa:TextView
    lateinit var txtpos:TextView
    lateinit var txtceksenet:TextView
    lateinit var donutChart:DonutChartView
    lateinit var id_btnStokIhtiyacRaporu:CardView
    lateinit var id_tahsilat_ekle:CardView
    lateinit var id_odeme_ekle:CardView
    lateinit var id_alis_ekle:CardView
    lateinit var id_satis_evrak_listesi:CardView
    var KasaDurumu=0.0
    var PosDurumu=0.0
    var HavaleDurumu=0.0
    var CekSenetDurumu=0.0
    var donutSet = listOf(
        abs(KasaDurumu.toString().toFloat()),
        abs(PosDurumu.toString().toFloat()),
        abs(HavaleDurumu.toString().toFloat()),
        abs(CekSenetDurumu.toString().toFloat())
    )
    lateinit var pieChart:PieChart
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        FirmaLogo = args?.getString("FirmaLogo").toString()
        calismasekli = args?.getString("calismasekli").toString()
        KullaniciId = args?.getString("KullaniciId").toString()
        PlasiyerId = args?.getString("PlasiyerId").toString()
        PlasiyerAdi = args?.getString("PlasiyerAdi").toString()
        linearlaout_kasa_pos_cek_havale=view.findViewById(R.id.linearlaout_kasa_pos_cek_havale)
        txtkasa=view.findViewById(R.id.txtkasa)
        txtpos=view.findViewById(R.id.txtpos)
        id_btnStokIhtiyacRaporu=view.findViewById(R.id.id_btnStokIhtiyacRaporu)
        id_tahsilat_ekle=view.findViewById(R.id.id_tahsilat_ekle)
        id_odeme_ekle=view.findViewById(R.id.id_odeme_ekle)
        id_alis_ekle=view.findViewById(R.id.id_alis_ekle)
        id_satis_evrak_listesi=view.findViewById(R.id.id_satis_evrak_listesi)
        txtceksenet=view.findViewById(R.id.txtceksenet)
        donutChart=view.findViewById<DonutChartView>(R.id.donutChart)

        Toast.makeText(requireContext(),"KullaniciId: "+KullaniciId,Toast.LENGTH_LONG).show()
        donutChart.donutTotal=abs(KasaDurumu.toString().toFloat())+abs(PosDurumu.toString().toFloat())+
                abs(HavaleDurumu.toString().toFloat())+abs(CekSenetDurumu.toString().toFloat())
        donutChart.donutColors = intArrayOf(
            Color.parseColor("#F75EA645"),
            Color.parseColor("#7C4DFF"),
            Color.parseColor("#FF6E40"),
            Color.parseColor("#64FFDA")
        )
        donutChart.animation.duration = animationDuration
        donutChart.animate(donutSet)
        linearlaout_kasa_pos_cek_havale.visibility= VISIBLE
        ///pasta
         pieChart = view.findViewById(R.id.pieChart)
        ///
      //  grafik bas


        //grafik son

        // Verileri oluşturun

        ///pasta son
        onKasaHavalePosCekSenet()/*
        if(calismasekli=="ms" || calismasekli=="hom"){
            linearlaout_kasa_pos_cek_havale.visibility= VISIBLE
            onKasaHavalePosCekSenet()

        }else{
            linearlaout_kasa_pos_cek_havale.visibility= GONE
        }
*/

        //Toast.makeText(requireContext(),"JHOME GELDİ",Toast.LENGTH_LONG).show()


        //Toast.makeText(requireContext(),username+ "home"+uid, Toast.LENGTH_LONG).show()
        communicator=activity as Communicator
        id_linearlayout = view.findViewById(R.id.id_linearlayout)
        linearlaout_kasa_pos_cek_havale = view.findViewById(R.id.linearlaout_kasa_pos_cek_havale)
        txt_stok_ara = view.findViewById(R.id.txt_stok_ara)
        txt_cari_listesi = view.findViewById(R.id.txt_cari_listesi)
        txt_arac_kabul = view.findViewById(R.id.txt_arac_kabul)
        viewPager = view.findViewById(R.id.viewPager)
        imageSliderAdapter = ImageSliderAdapter(images)
        viewPager.adapter = imageSliderAdapter
        startImageSlideshow()
        /*var barChart=view.findViewById<BarChartView>(R.id.barChart)
        barChart.animation.duration = animationDuration
        barChart.animate(barSet)
        var barChartHorizontal=view.findViewById<HorizontalBarChartView>(R.id.barChartHorizontal)
        barChartHorizontal.animation.duration = animationDuration
        barChartHorizontal.animate(horizontalBarSet)*/


        //baseActivity.headerNameTV.setText(resources.getString(R.string.service_one))
        fragmentManager = childFragmentManager

       // requireFragmentManager().beginTransaction().add(R.id.frame, discoverFragment).commit()
// Grafik çizimi için serileri ve renderlayıcıları oluşturun

        // Grafik için veri oluşturma


        id_btnStokIhtiyacRaporu.setOnClickListener {
            val i=Intent(requireContext(),StokIhtiyacRaporuActivity::class.java)
            i.putExtra("username",username)
            i.putExtra("uid",uid)
            i.putExtra("FirmaLogo",FirmaLogo)
            i.putExtra("KullaniciId",KullaniciId)
            startActivity(i)
        }
        id_tahsilat_ekle.setOnClickListener {
            val i=Intent(requireContext(),TahsilatEkleActivity::class.java)
            i.putExtra("username",username)
            i.putExtra("uid",uid)
            i.putExtra("FirmaLogo",FirmaLogo)
            i.putExtra("KullaniciId",KullaniciId)
            i.putExtra("PlasiyerId",PlasiyerId)
            i.putExtra("PlasiyerAdi",PlasiyerAdi)
            startActivity(i)
        }
        id_odeme_ekle.setOnClickListener {
            val i=Intent(requireContext(),OdemeEkleActivity::class.java)
            i.putExtra("username",username)
            i.putExtra("uid",uid)
            i.putExtra("FirmaLogo",FirmaLogo)
            i.putExtra("KullaniciId",KullaniciId)
            i.putExtra("PlasiyerId",PlasiyerId)
            i.putExtra("PlasiyerAdi",PlasiyerAdi)
            startActivity(i)
        }
        id_alis_ekle.setOnClickListener {
            val i=Intent(requireContext(),AlisEvrakActivity::class.java)
            i.putExtra("username",username)
            i.putExtra("uid",uid)
            i.putExtra("FirmaLogo",FirmaLogo)
            i.putExtra("KullaniciId",KullaniciId)
            startActivity(i)
        }
        id_satis_evrak_listesi.setOnClickListener {
            val i=Intent(requireContext(), EvrakSatisActivity::class.java)
            i.putExtra("username",username)
            i.putExtra("uid",uid)
            i.putExtra("FirmaLogo",FirmaLogo)
            i.putExtra("KullaniciId",KullaniciId)
            startActivity(i)
        }


        if(calismasekli=="hom" || calismasekli=="ms") {
            bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
            bottomNavigation!!.show(1, true)
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
            bottomNavigation!!.show(1, true)
            bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
          //  bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
            bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_chat))
           // bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
            bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.arama))
            bottomNavigation!!.setOnClickMenuListener(ClickListener { item ->
                when (item.id) {
                    1 -> {
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
                        bundlem.putString("aranan", "CARI")
                        bundlem.putString("KullaniciId",KullaniciId)
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
        bottomNavigation!!.setOnShowListener(ShowListener {
            // your codes
        })
        bottomNavigation!!.setOnReselectListener(ReselectListener {
            // your codes
        })
       /* if(acikonarimlar=="acikonarimlar"){
            acikOnarimlar()
            acikonarimlar=""
        }
        if(kapalionarimlar=="kapalionarimlar"){
            kapaliOnarimlar()
            kapalionarimlar=""
        }
        if(oncekionarimlar=="oncekionarimlar"){
            oncekiOnarimlar()
            oncekionarimlar=""
        }*/
        return view
    }

    override fun onStart() {
        super.onStart()
        onKasaHavalePosCekSenet()
    }

    override fun onResume() {
        super.onResume()
        onKasaHavalePosCekSenet()
    }

    override fun onDestroy() {
        super.onDestroy()
        onKasaHavalePosCekSenet()
    }
    private fun onKasaHavalePosCekSenet() {
            val urlek ="&username="+username+"&uid="+uid
            //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
            var url = Consts.KasaPosHavaleCekSenet + urlek
            //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
            // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
            //Consts.BASE_URL + urlek
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            Log.d("kasa", url)
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {

                         KasaDurumu = response["KasaDurumu"].toString().toFloat().toDouble()
                         PosDurumu = response["PosDurumu"].toString().toFloat().toDouble()
                         HavaleDurumu = response["HavaleDurumu"].toString().toFloat().toDouble()
                         CekSenetDurumu = response["CekSenetDurumu"].toString().toFloat().toDouble()
                        val entries = ArrayList<PieEntry>()
                        entries.add(PieEntry(KasaDurumu.toFloat(), "Kasa"))
                        entries.add(PieEntry(PosDurumu.toFloat(), "Pos "))
                        entries.add(PieEntry(HavaleDurumu.toFloat(), "Havale"))


                        // Veri kümesini oluşturun
                        val dataSet = PieDataSet(entries, "Hesap Takibi")
                        dataSet.colors = listOf(Color.GREEN, Color.BLUE, Color.RED)

                        // Grafiği özelleştirin
                        val pieData = PieData(dataSet)
                        pieChart.data = pieData
                        pieChart.setEntryLabelTextSize(15f)
                        pieChart.setEntryLabelColor(Color.BLACK)
                        pieChart.setCenterTextColor(Color.BLACK)
                        pieChart.setUsePercentValues(true)
                        pieChart.description.isEnabled = false

                        // Grafiği güncelleyin


                        pieChart.invalidate()
                        var dizi=response["KasaDurumu"].toString().split(".")
                        var yekun=dizi[0]
                        var binler = yekun.toFloat().toInt() / 1000
                        var yuzler = yekun.toFloat().toInt() % 1000
                        var ondalik = (yekun.toFloat().toInt() * 100).toInt() % 100
                        if (binler == 0) {
                            txtkasa.setText(String.format("%.2f", yekun.toFloat()).replace(".", ",")+"₺")

                        } else
                            txtkasa.setText(String.format("%,d.%03d,%02d", binler, abs(yuzler), ondalik)+"₺")

                        dizi=response["PosDurumu"].toString().split(".")
                        yekun=dizi[0]
                        binler = yekun.toFloat().toInt() / 1000
                        yuzler = yekun.toFloat().toInt() % 1000
                        ondalik = (yekun.toFloat().toInt() * 100).toInt() % 100
                        try{
                            if (binler == 0) {
                                txtpos.setText(yekun+","+dizi[1]+"₺")

                            } else
                                txtpos.setText(String.format("%,d.%03d,%02d", binler, abs(yuzler), dizi[1])+"₺")
                        }catch (e:Exception){
                            if (binler == 0) {
                                txtpos.setText(String.format("%.2f", yekun.toFloat()).replace(".", ",")+"₺")

                            } else
                                txtpos.setText(String.format("%,d.%03d,%02d", binler, abs(yuzler), ondalik)+"₺")
                        }
                        //txtkasa.setText(response["KasaDurumu"].toString()+"₺")
                        //txtpos.setText(response["PosDurumu"].toString()+"₺")
                       // txthavale.setText(response["HavaleDurumu"].toString()+"₺")
                        dizi=response["HavaleDurumu"].toString().split(".")
                        yekun=dizi[0]
                        binler = yekun.toFloat().toInt() / 1000
                        yuzler = yekun.toFloat().toInt() % 1000

                        //
                        dizi=response["CekSenetDurumu"].toString().split(".")
                        yekun=dizi[0]
                        binler = yekun.toFloat().toInt() / 1000
                        yuzler = yekun.toFloat().toInt() % 1000
                        ondalik = (yekun.toFloat().toInt() * 100).toInt() % 100
                        if (binler == 0) {
                            txtceksenet.setText(String.format("%.2f", yekun.toFloat()).replace(".", ",")+"₺")

                        } else
                            txtceksenet.setText(String.format("%,d.%03d,%02d", binler, abs(yuzler), ondalik)+"₺")
                        //txtceksenet.setText(response["CekSenetDurumu"].toString()+"₺")
                         donutSet = listOf(
                            abs(KasaDurumu.toString().toFloat()),
                            abs(PosDurumu.toString().toFloat()),
                            abs(HavaleDurumu.toString().toFloat()),
                            abs(CekSenetDurumu.toString().toFloat())
                        )
                       // Toast.makeText(requireContext(),"kasa"+KasaDurumu,Toast.LENGTH_LONG).show()
                        donutChart.donutTotal=abs(KasaDurumu.toString().toFloat())+abs(PosDurumu.toString().toFloat())+
                                abs(HavaleDurumu.toString().toFloat())+abs(CekSenetDurumu.toString().toFloat())
                        donutChart.donutColors = intArrayOf(
                            Color.parseColor("#F75EA645"),
                            Color.parseColor("#7C4DFF"),
                            Color.parseColor("#FF6E40"),
                            Color.parseColor("#64FFDA")
                        )
                        donutChart.animation.duration = animationDuration
                        donutChart.animate(donutSet)





                    } catch (e: Exception) {
                        //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                        Toast.makeText(requireContext(),getString(R.string.carilistesibulunmuyor), Toast.LENGTH_LONG).show()
                        val alert= Alert()
                        alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.carilistesibulunmuyor))



                    }


                }, { error ->
                    // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),getString(R.string.carilistesibulunmuyor), Toast.LENGTH_LONG).show()
                    Log.e("TAG", "RESPONSE IS $error")
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.carilistesibulunmuyor))

                }
            )
            queue.add(request)













    }

    private fun onKontrol() {
        if(id_linearlayout.visibility==VISIBLE){
            id_linearlayout.visibility=GONE
        }else{
            id_linearlayout.visibility= VISIBLE
        }
    }

    fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), view,Gravity.CENTER_VERTICAL)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_cards, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gallery_cards -> {
                    // Menü öğesi tıklandığında yapılacak işlemler
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
/*
   fun acikOnarimlar(){
       val childFragment = MyBooking()
       bundlem.putString("username",username)
       bundlem.putString("uid",uid)
       bundlem.putString("yedek","yedek")
       bundlem.putString("acikonarimlar",acikonarimlar)
       bundlem.putString("kapalionarimlar","")
       bottomNavigation!!.show(2, true)



       childFragment.arguments=bundlem

       childFragmentManager.beginTransaction()
           .replace(R.id.frame, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
           .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
           .commit()
   }
    fun kapaliOnarimlar(){
        val childFragment = MyBooking()
        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("yedek","yedek")
        bundlem.putString("kapalionarimlar",kapalionarimlar)
        bundlem.putString("acikonarimlar","")
        bottomNavigation!!.show(2, true)


        childFragment.arguments=bundlem

        childFragmentManager.beginTransaction()
            .replace(R.id.frame, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun oncekiOnarimlar(){
        val childFragment = MyBooking()
        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("yedek","yedek")
        bundlem.putString("kapalionarimlar","")
        bundlem.putString("acikonarimlar","")
        bundlem.putString("oncekionarimlar",oncekionarimlar)
        bottomNavigation!!.show(2, true)


        childFragment.arguments=bundlem

        childFragmentManager.beginTransaction()
            .replace(R.id.frame, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }*/
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        baseActivity = activity as MainActivity
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Eski fragmentın XML görünümünü silin
        // Örnek olarak, kök görünümü null olarak ayarlayabilirsiniz
        //view = null
    }
        private fun startImageSlideshow() {
        val handler = android.os.Handler()

        val update = Runnable {
            if (currentPage == images.size) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }

        // Delay in milliseconds between image transitions
        val delay = 3000L

        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, delay, delay)
    }

}