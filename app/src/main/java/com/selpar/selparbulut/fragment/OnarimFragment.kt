package com.selpar.selparbulut.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.KartDetayOnarimAdapter
import com.selpar.selparbulut.adapter.acikKartGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import com.selpar.selparbulut.model.KartDetayModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList


class OnarimFragment : Fragment() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var id:String

    private lateinit var newArrayList: ArrayList<KartDetayModel>
    var KabulId= ArrayList<String>()
    var StokID= ArrayList<String>()
    var SatirID= ArrayList<String>()
    var FirmaKodu= ArrayList<String>()
    var FirmaAdi= ArrayList<String>()
    var KabulNo= ArrayList<String>()
    var Plaka= ArrayList<String>()
    var GrupKodu= ArrayList<String>()
    var StokKodu= ArrayList<String>()
    var Miktar= ArrayList<String>()
    var BirimFiyat= ArrayList<String>()
    var KdvsizTutar= ArrayList<String>()
    var Tutar= ArrayList<String>()
    var KabulDurumu= ArrayList<String>()
    var Kullanici= ArrayList<String>()
    var PersonelAdi= ArrayList<String>()
    var ISTURU= ArrayList<String>()
    var StokAdi= ArrayList<String>()
    lateinit var recylerview_onarim:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_onarim, container, false)
        recylerview_onarim=view.findViewById(R.id.recylerview_onarim)
        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        id = args?.getString("id").toString()
        stokGetir()

        return view
    }

    private fun stokGetir(){

        val urlek ="&username="+username+"&uid="+uid+"&KabulId="+id

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartiDetayi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("kart_detay", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        KabulId.add(item.getString("KabulId"))
                        StokID.add(item.getString("StokID"))
                        SatirID.add(item.getString("SatirID"))
                        FirmaKodu.add(item.getString("FirmaKodu"))
                        FirmaAdi.add(item.getString("FirmaAdi"))
                        KabulNo.add(item.getString("KabulNo"))
                        Plaka.add(item.getString("Plaka"))
                        GrupKodu.add(item.getString("GrupKodu"))
                        StokKodu.add(item.getString("StokKodu"))
                        Miktar.add(item.getString("Miktar"))
                        BirimFiyat.add(item.getString("BirimFiyat"))
                        KdvsizTutar.add(item.getString("KdvsizTutar"))
                        Tutar.add(item.getString("Tutar"))
                        KabulDurumu.add(item.getString("KabulDurumu"))
                        Kullanici.add(item.getString("Kullanici"))
                        PersonelAdi.add(item.getString("PersonelAdi"))
                        ISTURU.add(item.getString("ISTURU"))
                        StokAdi.add(item.getString("StokAdi"))




                    }
                } catch (e: Exception) {
                   // Toast.makeText(requireContext(),e.message, Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.isemrineaitparcavehizmetbulunmamakta))

                }
                recylerview_onarim.layoutManager = LinearLayoutManager(requireContext())
                recylerview_onarim.setHasFixedSize(false)
                newArrayList = arrayListOf<KartDetayModel>()
                getUserData(requireContext())

            }, { error ->
                //Toast.makeText(requireContext(),error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.isemrineaitparcavehizmetbulunmamakta))


            }
        )
        queue.add(request)
    }
    private fun getUserData(context: Context) {
        for (i in KabulId.indices) {
            val news = KartDetayModel(
                username,
                uid,
                KabulId[i],
                StokID[i],
                SatirID[i],
                FirmaKodu[i],
                FirmaAdi[i],
                KabulNo[i],
                Plaka[i],
                GrupKodu[i],
                StokKodu[i],
                Miktar[i],
                BirimFiyat[i],
                KdvsizTutar[i],
                Tutar[i],
                KabulDurumu[i],
                Kullanici[i],
                PersonelAdi[i],
                ISTURU[i],
                StokAdi[i],
            )


            newArrayList.add(news)
        }
        recylerview_onarim.adapter = KartDetayOnarimAdapter(newArrayList)

    }


}