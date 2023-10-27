package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.ResimAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.CariListesiModel
import com.selpar.selparbulut.model.EvrakResimModel
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.util.ArrayList


class KabulResimleriFragment : Fragment() {
    lateinit var floatingActionButton:FloatingActionButton
    var bundlem = Bundle()
    lateinit var username: String
    lateinit var uid: String
    lateinit var KullaniciId: String
    lateinit var id: String
    lateinit var coursesGV: GridView
    private val CAMERA_KABUL = 1
    private lateinit var bitmap: Bitmap
    lateinit var image_kabul:ImageView
    lateinit var btn_resim_ekle:Button
    lateinit var resim_kabul:String
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_kabul_resimleri, container, false)
        floatingActionButton=view.findViewById(R.id.floatingActionButton)
        image_kabul=view.findViewById(R.id.image_kabul)
        btn_resim_ekle=view.findViewById(R.id.btn_resim_ekle)
        val args=this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        id = args?.getString("id").toString()
        KullaniciId = args?.getString("KullaniciId").toString()
        kabulKartiResimGetir()
        floatingActionButton.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_KABUL)

        }
        btn_resim_ekle.setOnClickListener {
            val urlek =
                "?username=" + username + "&uid=" + uid +"&KullaniciId="+KullaniciId+ "&KabulId=" + id+"&KabulResim="+resim_kabul
            var url = Consts.BASE_URLS
            var url_degisken=url+urlek
            Log.d("kabul_resim",url_degisken)
            val queue = Volley.newRequestQueue(requireContext())
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    Toast.makeText(requireContext(),getString(R.string.resim_gonderildi),Toast.LENGTH_LONG).show()
                    kabulKartiResimGetir()
                    btn_resim_ekle.visibility=GONE
                    image_kabul.visibility=GONE

                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                    Toast.makeText(requireContext(),getString(R.string.resim_gonderilemedi),Toast.LENGTH_LONG).show()

                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["username"]=username
                    params["uid"]=uid
                    params["id"]=id
                    params["KabulResim"] = resim_kabul
                    params["tur"] = "ResimEkle"
                    return params
                }
            }
            queue.add(postRequest)



        }
        return view
    }
    fun ImageToString(map:Bitmap) : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        map.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_KABUL && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_kabul.setImageBitmap(bitmap)
            btn_resim_ekle.visibility=VISIBLE
            image_kabul.visibility= VISIBLE
            resim_kabul=ImageToString(bitmap)

        }





    }

    private fun kabulKartiResimGetir() {
        val urlek = "&username=" + username + "&uid=" + uid + "&KabulId=" + id
        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartiResimlerKabul + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("olayyeri_resim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    val evrakModelArrayList: ArrayList<EvrakResimModel> =
                        ArrayList<EvrakResimModel>()

                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)

                        val EvrakAdi = item.getString("ResimYolu")
                        val Resimtest = item.getString("Resimler")
                        val ResimYolu = item.getString("Resimler").split(",")
                        if(Resimtest!="null") {
                            for (j in 0 until ResimYolu.size)
                                evrakModelArrayList.add(
                                    EvrakResimModel(
                                        username,
                                        uid,
                                        EvrakAdi,
                                        ResimYolu[j]
                                    )
                                )
                        }else
                            Toast.makeText(requireContext(),getString(R.string.aracaaitkabulresmibulunmamakta),
                                Toast.LENGTH_LONG).show()
                        val alert= Alert()
                        alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitkabulresmibulunmamakta))


                    }
                    val adapter2 = ResimAdapter(requireContext(), evrakModelArrayList)
                    coursesGV.adapter = adapter2
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),getString(R.string.aracaaitkabulresmibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitkabulresmibulunmamakta))

                }


            }, { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitkabulresmibulunmamakta))


            }
        )
        queue.add(request)


    }


}