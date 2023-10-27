package com.selpar.selparbulut.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import com.selpar.selparbulut.R

class CariDetayActivity : AppCompatActivity() {
    lateinit var txtCariUnvan:TextView
    lateinit var txtVnumarasi:TextView
    lateinit var txtVdairesi:TextView
    lateinit var txtTelefon:TextView
    lateinit var txtGSM:TextView
    lateinit var txtAdresi:TextView
    lateinit var txtSMSonay:TextView
    lateinit var txtAracPlaka:TextView
    lateinit var txtAracMarka:TextView
    lateinit var txtAracModel:TextView
    lateinit var txtAracSaseno:TextView
    lateinit var txtEmail:TextView
    lateinit var txtMusteriTipi:TextView

    lateinit var ivBack:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_detay)
        ivBack=findViewById(R.id.ivBack)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        onBaslat()
        bilgiDoldur()
    }
    private fun onBaslat() {
        txtCariUnvan=findViewById(R.id.txtCariUnvan)
        txtVnumarasi=findViewById(R.id.txtVnumarasi)
        txtVdairesi=findViewById(R.id.txtVdairesi)
        txtTelefon=findViewById(R.id.txtTelefon)
        txtGSM=findViewById(R.id.txtGSM)
        txtAdresi=findViewById(R.id.txtAdresi)
        txtSMSonay=findViewById(R.id.txtSMSonay)
        txtAracPlaka=findViewById(R.id.txtAracPlaka)
        txtAracMarka=findViewById(R.id.txtAracMarka)
        txtAracModel=findViewById(R.id.txtAracModel)
        txtAracSaseno=findViewById(R.id.txtAracSaseno)
        txtEmail=findViewById(R.id.txtEmail)
        txtMusteriTipi=findViewById(R.id.txtMusteriTipi)
    }

    private fun bilgiDoldur() {
        if(intent.getStringExtra("CariUnvan")!="null" && intent.getStringExtra("CariUnvan").toString().isNotEmpty())
        txtCariUnvan.setText(intent.getStringExtra("CariUnvan"))
        else
            txtCariUnvan.visibility=GONE
        if(intent.getStringExtra("Vnumarasi")!="null" && intent.getStringExtra("Vnumarasi").toString().isNotEmpty())
            txtVnumarasi.setText(intent.getStringExtra("Vnumarasi"))
        else
            txtVnumarasi.visibility=GONE
        if(intent.getStringExtra("Vdairesi")!="null" && intent.getStringExtra("Vdairesi").toString().isNotEmpty())
            txtVdairesi.setText(intent.getStringExtra("Vdairesi"))
        else
            txtVdairesi.visibility=GONE
        if(intent.getStringExtra("Telefon")!="null" && intent.getStringExtra("Telefon").toString().isNotEmpty())
            txtTelefon.setText(intent.getStringExtra("Telefon"))
        else
            txtTelefon.visibility=GONE
        if(intent.getStringExtra("GSM")!="null" && intent.getStringExtra("GSM").toString().isNotEmpty())
            txtGSM.setText(intent.getStringExtra("GSM"))
        else
            txtGSM.visibility=GONE
        if(intent.getStringExtra("Adresi")!="null" && intent.getStringExtra("Adresi").toString().isNotEmpty())
            txtAdresi.setText(intent.getStringExtra("Adresi"))
        else
            txtAdresi.visibility=GONE
        if(intent.getStringExtra("SMSonay")!="null" && intent.getStringExtra("SMSonay").toString().isNotEmpty())
            txtSMSonay.setText(intent.getStringExtra("SMSonay"))
        else
            txtSMSonay.visibility=GONE
        if(intent.getStringExtra("AracPlaka")!="null" && intent.getStringExtra("AracPlaka").toString().isNotEmpty())
            txtAracPlaka.setText(intent.getStringExtra("AracPlaka"))
        else
            txtAracPlaka.visibility=GONE
        if(intent.getStringExtra("AracMarka")!="null" && intent.getStringExtra("AracMarka").toString().isNotEmpty())
            txtAracMarka.setText(intent.getStringExtra("AracMarka"))
        else
            txtAracMarka.visibility=GONE
        if(intent.getStringExtra("AracModel")!="null" && intent.getStringExtra("AracModel").toString().isNotEmpty())
            txtAracModel.setText(intent.getStringExtra("AracModel"))
        else
            txtAracModel.visibility=GONE
        if(intent.getStringExtra("AracSaseno")!="null" && intent.getStringExtra("AracSaseno").toString().isNotEmpty())
            txtAracSaseno.setText(intent.getStringExtra("AracSaseno"))
        else
            txtAracSaseno.visibility=GONE
        if(intent.getStringExtra("Email")!="null" && intent.getStringExtra("Email").toString().isNotEmpty())
            txtEmail.setText(intent.getStringExtra("Email"))
        else
            txtEmail.visibility=GONE
        if(intent.getStringExtra("MusteriTipi")!="null" && intent.getStringExtra("MusteriTipi").toString().isNotEmpty())
            txtMusteriTipi.setText(intent.getStringExtra("MusteriTipi"))
        else
            txtMusteriTipi.visibility=GONE

    }


}