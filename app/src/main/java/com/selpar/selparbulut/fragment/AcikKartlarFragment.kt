package com.selpar.selparbulut.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.acikKartGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList

class AcikKartlarFragment : Fragment() {

     var bundlem=Bundle()
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    lateinit var KullaniciId:String
    lateinit var recylerview_acikkartlar:RecyclerView
    companion object {
        private const val ARG_DATA = "data"

        // Veriyi ChildFragment'a göndermek için bu metodu kullanacağız
        fun newInstance(data: String): AcikKartlarFragment {
            val fragment = AcikKartlarFragment()
            val args = Bundle()
            args.putString(ARG_DATA, data)
            fragment.arguments = args
            return fragment
        }
    }
    private var data: String? = null
    private lateinit var newArrayList: ArrayList<AcikKartlarModel>
     var KabulId=ArrayList<String>()
     var FirmaKodu=ArrayList<String>()
     var FirmaAdi=ArrayList<String>()
     var KabulNo=ArrayList<String>()
     var GirisTarihi=ArrayList<String>()
     var Plaka=ArrayList<String>()
     var KabulDurumu=ArrayList<String>()
     var Kullanici=ArrayList<String>()
     var KabulTuru=ArrayList<String>()
     var KabulTutar=ArrayList<String>()
     var Cariunvan=ArrayList<String>()
     var AracMarka=ArrayList<String>()
     var AracModel=ArrayList<String>()
     var AracModelYili=ArrayList<String>()
     var AracResim=ArrayList<String>()
     var AracMarkaResim=ArrayList<String>()
     var CariTelefon=ArrayList<String>()
     var AracKM=ArrayList<String>()
     var VergiNo=ArrayList<String>()
     var VergiDairesi=ArrayList<String>()
     var Adresi=ArrayList<String>()
     var Il=ArrayList<String>()
     var Ilce=ArrayList<String>()
     var CariGSM=ArrayList<String>()
    var AracRengi= ArrayList<String>()
    var AracVersiyon= ArrayList<String>()
    var YakitDurumu= ArrayList<String>()
    var SarjDurumu= ArrayList<String>()
    var TahminiTeslimTarihi= ArrayList<String>()
    var TahminiTutar= ArrayList<String>()
    var AracSaseno= ArrayList<String>()
    var AracMotorNo= ArrayList<String>()
    lateinit var progressBar: ProgressBar



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_acik_kartlar, container, false)
        arguments?.let {
            data = it.getString(ARG_DATA)
        }
        progressBar = view.findViewById(R.id.progressBar)
        Toast.makeText(requireContext(),getString(R.string.bugun_acilan_kartlarlisteleniyor),Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        FirmaLogo = args?.getString("FirmaLogo").toString()
        KullaniciId = args?.getString("KullaniciId").toString()
        recylerview_acikkartlar=view.findViewById(R.id.recylerview_acikkartlar)
       // Toast.makeText(requireContext(),username+ "kartlar"+uid,Toast.LENGTH_LONG).show()
        bugunAcilanKartGetir()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bugunAcilanKartGetir() {
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(10)

        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+today+"&bitis_Tarihi="+today

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url =Consts.BASE_URL + urlek
            //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
                   // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
            //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("islerim_bugun", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                  //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        KabulId.add(item.getString("KabulID"))
                        FirmaKodu.add(item.getString("FirmaKodu"))
                        // Toast.makeText(this,KabulId)
                         FirmaAdi.add(item.getString("FirmaAdi"))
                        KabulNo.add(item.getString("KabulNo"))
                        GirisTarihi.add(item.getString("GirisTarihi"))
                        Plaka.add(item.getString("Plaka"))
                        KabulDurumu.add(item.getString("KabulDurumu"))
                        Kullanici.add(item.getString("Kullanici"))
                        KabulTuru.add(item.getString("KabulTuru"))
                        KabulTutar.add(item.getString("KabulTutar"))
                        Cariunvan.add(item.getString("Cariunvan"))
                        AracMarka.add(item.getString("AracMarka"))
                        AracModel.add(item.getString("AracModel"))
                        AracModelYili.add(item.getString("AracModelYili"))
                        AracResim.add(item.getString("AracResim"))
                        AracMarkaResim.add(item.getString("AracMarkaResim"))
                        CariTelefon.add(item.getString("CariTelefon"))
                        AracKM.add(item.getString("AracKM"))
                        VergiNo.add(item.getString("VergiNo"))
                        VergiDairesi.add(item.getString("VergiDairesi"))
                        Adresi.add(item.getString("Adresi"))
                        Il.add(item.getString("Il"))
                        Ilce.add(item.getString("Ilce"))
                        CariGSM.add(item.getString("CariGSM"))
                        AracRengi.add(item.getString("AracRengi"))
                        AracVersiyon.add(item.getString("AracVersiyon"))
                        YakitDurumu.add(item.getString("YakitDurumu"))
                        SarjDurumu.add(item.getString("SarjDurumu"))
                        TahminiTeslimTarihi.add(item.getString("TahminiTeslimTarihi"))
                        TahminiTutar.add(item.getString("TahminiTutar"))
                        AracSaseno.add(item.getString("AracSaseno"))
                        AracMotorNo.add(item.getString("AracMotorNo"))

                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),getString(R.string.bugunacilankartbulunmuyor),Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.bugunacilankartbulunmuyor))

                }
                recylerview_acikkartlar.layoutManager = LinearLayoutManager(requireContext())
                recylerview_acikkartlar.setHasFixedSize(false)
                newArrayList = arrayListOf<AcikKartlarModel>()
                getUserData(requireContext())

            }, { error ->
               // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(),getString(R.string.bugunacilankartbulunmuyor),Toast.LENGTH_LONG).show()
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.bugunacilankartbulunmuyor))
                Log.e("TAG", "RESPONSE IS $error")

            }
        )
        queue.add(request)











}
    private fun getUserData(context: Context) {
        for (i in KabulId.indices) {
            val news = AcikKartlarModel(
                KullaniciId,
                FirmaLogo,
                username,
                uid,
                KabulId[i],
                FirmaKodu[i],
                FirmaAdi[i],
                KabulNo[i],
                GirisTarihi[i],
                Plaka[i],
                KabulDurumu[i],
                Kullanici[i],
                KabulTuru[i],
                KabulTutar[i],
                Cariunvan[i],
                AracMarka[i],
                AracModel[i],
                AracModelYili[i],
                AracResim[i],
                AracMarkaResim[i],
                CariTelefon[i],
                AracKM[i],
                VergiNo[i],
                VergiDairesi[i],
                Adresi[i],
                Il[i],
                Ilce[i],
                CariGSM[i],
                AracRengi[i],
                AracVersiyon[i],
                YakitDurumu[i],
                SarjDurumu[i],
                TahminiTeslimTarihi[i],
                TahminiTutar[i],
                AracSaseno[i],
                AracMotorNo[i],

            )


            newArrayList.add(news)
        }
        recylerview_acikkartlar.adapter = acikKartGetirAdapter(newArrayList)

    }



}