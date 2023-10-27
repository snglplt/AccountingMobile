package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.KartDetayIstekSikayetAdapter
import com.selpar.selparbulut.adapter.KartDetayOnarimAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.IstekSikayetModel
import com.selpar.selparbulut.model.KartDetayModel
import org.json.JSONArray
import java.util.ArrayList


class IstekSikayetFragment : Fragment() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var id:String

    private lateinit var newArrayList: ArrayList<IstekSikayetModel>
    lateinit var recylerview_istek_sikayet:RecyclerView
    var KabulId= ArrayList<String>()
    var KabulNo= ArrayList<String>()
    var Sikayet= ArrayList<String>()
    var IstekTuru= ArrayList<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_istek_sikayet, container, false)
        val args=this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        id = args?.getString("id").toString()
        recylerview_istek_sikayet=view.findViewById(R.id.recylerview_istek_sikayet)
        sikayetGetir()
        return view
    }
    private fun sikayetGetir(){

        val urlek ="&username="+username+"&uid="+uid+"&KabulId="+id

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartiSikayet + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("istek_sikayet", url)
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
                        KabulNo.add(item.getString("KabulNo"))
                        Sikayet.add(item.getString("Sikayet"))
                        IstekTuru.add(item.getString("IstekTuru"))







                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message, Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitisteksikayetbulunmamakta))


                }
                recylerview_istek_sikayet.layoutManager = LinearLayoutManager(requireContext())
                recylerview_istek_sikayet.setHasFixedSize(false)
                newArrayList = arrayListOf<IstekSikayetModel>()
                getUserData(requireContext())

            }, { error ->
                Toast.makeText(requireContext(),error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitisteksikayetbulunmamakta))


            }
        )
        queue.add(request)
    }
    private fun getUserData(context: Context) {
        for (i in KabulId.indices) {
            val news = IstekSikayetModel(
                username,
                uid,
                KabulId[i],
                KabulNo[i],
                Sikayet[i],
                IstekTuru[i],
            )


            newArrayList.add(news)
        }
        recylerview_istek_sikayet.adapter = KartDetayIstekSikayetAdapter(newArrayList)

    }

}