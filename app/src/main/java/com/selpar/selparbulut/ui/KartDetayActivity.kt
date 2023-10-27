package com.selpar.selparbulut.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.selpar.selparbulut.R
import com.selpar.selparbulut.fragment.AcikKartlarFragment
import com.selpar.selparbulut.fragment.ChatList
import com.selpar.selparbulut.fragment.Home
import com.selpar.selparbulut.fragment.IstekSikayetFragment
import com.selpar.selparbulut.fragment.MyBooking
import com.selpar.selparbulut.fragment.NotificationActivity
import com.selpar.selparbulut.fragment.OnarimFragment
import com.selpar.selparbulut.fragment.ResimFragment
import com.selpar.selparbulut.fragment.SahibiFragment
import com.selpar.selparbulut.fragment.SigortaFragment
import org.w3c.dom.Text

class KartDetayActivity : AppCompatActivity() {
    lateinit var frgOnarim:ImageView
    lateinit var frgIstekSikayet:ImageView
    lateinit var frgSahibi:ImageView
    lateinit var frgSigorta:ImageView
    lateinit var frgResim:ImageView
    var bundlem=Bundle()
    lateinit var username:String
    lateinit var uid:String
    lateinit var id:String
    lateinit var ivBack:ImageView
    lateinit var txtFirmaAdi: TextView
    lateinit var txtTelefon: TextView
    lateinit var txtkm: TextView
    lateinit var txtplaka: TextView
    lateinit var VergiNo:String
    lateinit var VergiDairesi:String
    lateinit var Adresi:String
    lateinit var Il:String
    lateinit var Ilce:String
    lateinit var CariGSM:String
    lateinit var KullaniciId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kart_detay)
        onBaslat()
        username=intent.getStringExtra("username").toString()
        uid=intent.getStringExtra("uid").toString()
        KullaniciId=intent.getStringExtra("KullaniciId").toString()
        id=intent.getStringExtra("id").toString()
        VergiNo=intent.getStringExtra("VergiNo").toString()
        VergiDairesi=intent.getStringExtra("VergiDairesi").toString()
        Adresi=intent.getStringExtra("Adresi").toString()
        Il=intent.getStringExtra("Il").toString()
        Ilce=intent.getStringExtra("Ilce").toString()
        CariGSM=intent.getStringExtra("CariGSM").toString()
       // Toast.makeText(this,CariGSM,Toast.LENGTH_LONG).show()
        if(intent.getStringExtra("firmaadi")!!.length>20)
        txtFirmaAdi.setText(intent.getStringExtra("firmaadi").toString().substring(0,20))
        else
            txtFirmaAdi.setText(intent.getStringExtra("firmaadi"))
        txtTelefon.setText(getString(R.string.telefon)+": "+intent.getStringExtra("telefon"))
        txtkm.setText("KM: "+intent.getStringExtra("km"))
        txtplaka.setText(getString(R.string.plakaa)+": "+intent.getStringExtra("plaka"))
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack=findViewById(R.id.ivBack)
        ivBack.setOnClickListener(View.OnClickListener { finish() })

        OnarimFragmentAc()
        frgOnarim.setOnClickListener { OnarimFragmentAc() }
        frgIstekSikayet.setOnClickListener { IstekSikayetFragmentAc() }
        frgSahibi.setOnClickListener { SahibiFragmentAc() }
        frgSigorta.setOnClickListener { SigortaFragmentAc() }
        frgResim.setOnClickListener { ResimFragmentAc() }


    }

    fun OnarimFragmentAc() {
        val childFragment = OnarimFragment()
        frgOnarim.background=resources.getDrawable(R.drawable.button_mavi)
        frgIstekSikayet.background=resources.getDrawable(R.drawable.button_customer)
        frgSahibi.background=resources.getDrawable(R.drawable.button_customer)
        frgSigorta.background=resources.getDrawable(R.drawable.button_customer)
        frgResim.background=resources.getDrawable(R.drawable.button_customer)

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("KullaniciId",KullaniciId)
        bundlem.putString("id",id)
        childFragment.arguments=bundlem
        var fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_kart_detay,  childFragment)
            .commit()
    }
    fun IstekSikayetFragmentAc() {
        val childFragment = IstekSikayetFragment()
        frgOnarim.background=resources.getDrawable(R.drawable.button_customer)
        frgIstekSikayet.background=resources.getDrawable(R.drawable.button_mavi)
        frgSahibi.background=resources.getDrawable(R.drawable.button_customer)
        frgSigorta.background=resources.getDrawable(R.drawable.button_customer)
        frgResim.background=resources.getDrawable(R.drawable.button_customer)

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        var fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_kart_detay,  childFragment)
            .commit()

    }
    fun SahibiFragmentAc(){
        val childFragment = SahibiFragment()
        frgOnarim.background=resources.getDrawable(R.drawable.button_customer)
        frgIstekSikayet.background=resources.getDrawable(R.drawable.button_customer)
        frgSahibi.background=resources.getDrawable(R.drawable.button_mavi)
        frgSigorta.background=resources.getDrawable(R.drawable.button_customer)
        frgResim.background=resources.getDrawable(R.drawable.button_customer)

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("VergiNo",VergiNo)
        bundlem.putString("KullaniciId",KullaniciId)
        bundlem.putString("VergiDairesi",VergiDairesi)
        bundlem.putString("Adresi",Adresi)
        bundlem.putString("Il",Il)
        bundlem.putString("Ilce",Ilce)
        bundlem.putString("CariGSM",CariGSM)

        childFragment.arguments=bundlem
        var fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_kart_detay,  childFragment)
            .commit()
    }
    fun SigortaFragmentAc(){
        val childFragment = SigortaFragment()
        frgOnarim.background=resources.getDrawable(R.drawable.button_customer)
        frgIstekSikayet.background=resources.getDrawable(R.drawable.button_customer)
        frgSahibi.background=resources.getDrawable(R.drawable.button_customer)
        frgSigorta.background=resources.getDrawable(R.drawable.button_mavi)
        frgResim.background=resources.getDrawable(R.drawable.button_customer)

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        var fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_kart_detay,  childFragment)
            .commit()
    }
    fun ResimFragmentAc(){
        val childFragment = ResimFragment()
        frgOnarim.background=resources.getDrawable(R.drawable.button_customer)
        frgIstekSikayet.background=resources.getDrawable(R.drawable.button_customer)
        frgSahibi.background=resources.getDrawable(R.drawable.button_customer)
        frgSigorta.background=resources.getDrawable(R.drawable.button_customer)
        frgResim.background=resources.getDrawable(R.drawable.button_mavi)

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        var fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_kart_detay,  childFragment)
            .commit()

    }
    private fun onBaslat() {
        frgOnarim=findViewById(R.id.frgOnarim)
        frgIstekSikayet=findViewById(R.id.frgIstekSikayet)
        frgSahibi=findViewById(R.id.frgSahibi)
        frgSigorta=findViewById(R.id.frgSigorta)
        frgResim=findViewById(R.id.frgResim)
        txtFirmaAdi=findViewById(R.id.txtFirmaAdi)
        txtTelefon=findViewById(R.id.txtTelefon)
        txtkm=findViewById(R.id.txtkm)
        txtplaka=findViewById(R.id.txtplaka)

    }
}


