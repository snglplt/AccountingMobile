package com.selpar.selparbulut.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.MainActivity
import com.selpar.selparbulut.R
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import org.json.JSONArray

class SingInActivity : AppCompatActivity() {
    lateinit var CBsignIn:Button
    lateinit var edkullaniciadi:EditText
    lateinit var password:EditText
    var adi:String=""
    var soyadi:String=""
    var username:String=""
    var uid:String=""
    var calismasekli:String=""
    var entegrator:String=""
    var entegratorkullanici:String=""
    var entegratorsifre:String=""
    var vergino:String=""
    var PlasiyerId:String=""
    var PlasiyerAdi:String=""
    var KullaniciId:String=""
    var FirmaLogo:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)
        onBaslat()
        CBsignIn.setOnClickListener {
            giris_yap(edkullaniciadi.getText().toString(), password.getText().toString())


        }
        val sharedPreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

        val getUsername = sharedPreferens.getString("username", "")
        val getPassword = sharedPreferens.getString("uid", "")
        val calismasekli= sharedPreferens.getString("calismasekli", "")
        if (getUsername != "" && getPassword != "" ) {
            if(calismasekli!="yp") {
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("adi", sharedPreferens.getString("adi", "").toString())
                i.putExtra("soyadi", sharedPreferens.getString("soyadi", "").toString())
                i.putExtra("calismasekli", sharedPreferens.getString("calismasekli", "").toString())
                i.putExtra("username", sharedPreferens.getString("username", "").toString())
                i.putExtra("uid", sharedPreferens.getString("uid", "").toString())
                i.putExtra("entegrator", sharedPreferens.getString("entegrator", "").toString())
                i.putExtra("entegratorkullanici", sharedPreferens.getString("entegratorkullanici", "").toString())
                i.putExtra("entegratorsifre", sharedPreferens.getString("entegratorsifre", "").toString())
                i.putExtra("vergino", sharedPreferens.getString("vergino", "").toString())
                i.putExtra("PlasiyerId", sharedPreferens.getString("PlasiyerId", "").toString())
                i.putExtra("PlasiyerAdi", sharedPreferens.getString("PlasiyerAdi", "").toString())
                i.putExtra("KullaniciId", sharedPreferens.getString("KullaniciId", "").toString())
                i.putExtra("FirmaLogo", sharedPreferens.getString("FirmaLogo", "").toString())


                startActivity(i)
                finish()
            }
            else{
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("adi", sharedPreferens.getString("adi", "").toString())
                i.putExtra("soyadi", sharedPreferens.getString("soyadi", "").toString())
                i.putExtra("calismasekli", sharedPreferens.getString("calismasekli", "").toString())
                i.putExtra("username", sharedPreferens.getString("username", "").toString())
                i.putExtra("uid", sharedPreferens.getString("uid", "").toString())
                i.putExtra("entegrator", sharedPreferens.getString("entegrator", "").toString())
                i.putExtra("entegratorkullanici", sharedPreferens.getString("entegratorkullanici", "").toString())
                i.putExtra("entegratorsifre", sharedPreferens.getString("entegratorsifre", "").toString())
                i.putExtra("vergino", sharedPreferens.getString("vergino", "").toString())
                i.putExtra("PlasiyerId", sharedPreferens.getString("PlasiyerId", "").toString())
                i.putExtra("PlasiyerAdi", sharedPreferens.getString("PlasiyerAdi", "").toString())
                i.putExtra("KullaniciId", sharedPreferens.getString("KullaniciId", "").toString())
                i.putExtra("FirmaLogo", sharedPreferens.getString("FirmaLogo", "").toString())

                startActivity(i)
                finish()
            }
        }

    }
    private fun giris_yap(k: String, s: String) {

        val urlsb = "&kadi=" + k + "&sifre=" + s
        var url = Consts.BASE_URLS+"?tur=giris" + urlsb
        Log.d("GIRISYAP: ", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["giris"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        adi=item.getString("adi")
                        soyadi=item.getString("soyadi")
                        username=item.getString("username")
                        uid=item.getString("uid")
                        calismasekli=item.getString("calismasekli")
                        entegrator=item.getString("entegrator")
                        entegratorkullanici=item.getString("entegratorkullanici")
                        entegratorsifre=item.getString("entegratorsifre")
                        vergino=item.getString("vergino")
                        PlasiyerId=item.getString("PlasiyerId")
                        PlasiyerAdi=item.getString("PlasiyerAdi")
                        KullaniciId=item.getString("KullaniciId")
                        FirmaLogo=item.getString("FirmaLogo")
                    }
                    if(calismasekli=="yp"){

                        val sharedPreferens =
                            getSharedPreferences("MY_PRE", MODE_PRIVATE)
                        val editor = sharedPreferens.edit()
                        editor.putString("uid", uid)
                        editor.putString("username", username)
                        editor.putString("adi", adi)
                        editor.putString("soyadi", soyadi)
                        editor.putString("calismasekli", calismasekli)
                        editor.putString("entegrator", entegrator)
                        editor.putString("entegratorkullanici", entegratorkullanici)
                        editor.putString("entegratorsifre", entegratorsifre)
                        editor.putString("vergino", vergino)
                        editor.putString("PlasiyerId", PlasiyerId)
                        editor.putString("PlasiyerAdi", PlasiyerAdi)
                        editor.putString("KullaniciId", KullaniciId)
                        editor.putString("FirmaLogo", FirmaLogo)

                        editor.apply()
                        val i=Intent(this,MainActivity::class.java)
                        i.putExtra("adi",adi)
                        i.putExtra("soyadi",soyadi)
                        i.putExtra("username",username)
                        i.putExtra("uid",uid)
                        i.putExtra("calismasekli",calismasekli)
                        i.putExtra("entegrator",entegrator)
                        i.putExtra("entegratorkullanici",entegratorkullanici)
                        i.putExtra("entegratorsifre",entegratorsifre)
                        i.putExtra("vergino",vergino)
                        i.putExtra("PlasiyerId",PlasiyerId)
                        i.putExtra("PlasiyerAdi",PlasiyerAdi)
                        i.putExtra("KullaniciId",KullaniciId)
                        i.putExtra("FirmaLogo",FirmaLogo)
                        startActivity(i)
                        finish()
                    }else{
                        val sharedPreferens =
                            getSharedPreferences("MY_PRE", MODE_PRIVATE)
                        val editor = sharedPreferens.edit()
                        editor.putString("uid", uid)
                        editor.putString("username", username)
                        editor.putString("adi", adi)
                        editor.putString("soyadi", soyadi)
                        editor.putString("calismasekli", calismasekli)
                        editor.putString("entegrator", entegrator)
                        editor.putString("entegratorkullanici", entegratorkullanici)
                        editor.putString("entegratorsifre", entegratorsifre)
                        editor.putString("vergino", vergino)
                        editor.putString("PlasiyerId", PlasiyerId)
                        editor.putString("PlasiyerAdi", PlasiyerAdi)
                        editor.putString("KullaniciId", KullaniciId)
                        editor.putString("FirmaLogo", FirmaLogo)

                        editor.apply()
                        val i=Intent(this,MainActivity::class.java)
                        i.putExtra("adi",adi)
                        i.putExtra("soyadi",soyadi)
                        i.putExtra("username",username)
                        i.putExtra("uid",uid)
                        i.putExtra("calismasekli",calismasekli)
                        i.putExtra("entegrator",entegrator)
                        i.putExtra("entegratorkullanici",entegratorkullanici)
                        i.putExtra("entegratorsifre",entegratorsifre)
                        i.putExtra("vergino",vergino)
                        i.putExtra("PlasiyerId",PlasiyerId)
                        i.putExtra("PlasiyerAdi",PlasiyerAdi)
                        i.putExtra("KullaniciId",KullaniciId)
                        i.putExtra("FirmaLogo",FirmaLogo)
                        startActivity(i)
                        finish()
                    }


                } catch (e: Exception) {
                    Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
                }



            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(
                    this,
                    error.message+"hata",
                    Toast.LENGTH_SHORT
                ).show()
                val alert= Alert()
                alert.showAlert(this,getString(R.string.uyari),error.message.toString())
                //Toast.makeText(this,"Böyle bir kullanıcı yok",Toast.LENGTH_LONG).show()

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds

        queue.add(request)
    }

    private fun onBaslat() {
        CBsignIn=findViewById(R.id.CBsignIn)
        edkullaniciadi=findViewById(R.id.edkullaniciadi)
        password=findViewById(R.id.password)
    }
}