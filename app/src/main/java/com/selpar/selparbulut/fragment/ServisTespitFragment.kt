package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.GalleryAdapter
import com.selpar.selparbulut.data.Alert


class ServisTespitFragment : Fragment() {
    lateinit var username: String
    lateinit var uid: String
    lateinit var id: String
    lateinit var coursesGV: android.widget.GridView
    lateinit var galleryRecyclerView:RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_servis_tespit, container, false)
        coursesGV = view.findViewById(R.id.idGVcourses)
        galleryRecyclerView = view.findViewById(R.id.galleryRecyclerView)
        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        id = args?.getString("id").toString()
        servisTespitResimGetir()
        return view
    }

    private fun servisTespitResimGetir() {
        val urlek = "&username=" + username + "&uid=" + uid + "&KabulId=" + id
        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = com.selpar.selparbulut.interfacess.Consts.KabulKartiResimlerServisTespiti + urlek
        val deneme_url =
            "https://selparbulut.com/api.php?kaynak=KabulKartiResimler&ResimTuru=ServisTespiti&username=karparsrv&uid=5B3B491E-B0CB-ECB7-D90B-A5AD5E0A5F22&KabulId=746289"
        val queue: com.android.volley.RequestQueue =
            com.android.volley.toolbox.Volley.newRequestQueue(requireContext())
        android.util.Log.d("servistespit_resim", deneme_url)
        val request = com.android.volley.toolbox.JsonObjectRequest(
            com.android.volley.Request.Method.GET, deneme_url, null,
            { response ->
                try {

                    val json = response["data"] as org.json.JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    val evrakModelArrayList: java.util.ArrayList<com.selpar.selparbulut.model.EvrakResimModel> =
                        java.util.ArrayList<com.selpar.selparbulut.model.EvrakResimModel>()
                    var resimler=ArrayList<String>()
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)

                        val EvrakAdi = item.getString("ResimYolu")
                        val Resimtest = item.getString("Resimler")
                        val ResimYolu = item.getString("Resimler").split(",")
                        if (Resimtest != "null") {

                            for (j in 0 until ResimYolu.size) {
                                resimler.add(ResimYolu[j])
                                evrakModelArrayList.add(
                                    com.selpar.selparbulut.model.EvrakResimModel(
                                        username,
                                        uid,
                                        EvrakAdi,
                                        ResimYolu[j].trim()
                                    )

                                )
                            }
                        }else{
                            Toast.makeText(requireContext(),getString(R.string.aracaaitservistespitresmibulunmamakta), Toast.LENGTH_LONG).show()

                        val alert= Alert()
                        alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitservistespitresmibulunmamakta))
                    }}
                    val adapter2 = com.selpar.selparbulut.adapter.ResimAdapter(
                        requireContext(),
                        evrakModelArrayList
                    )
                    coursesGV.adapter = adapter2

                    //galleryRecyclerView.adapter = GalleryAdapter(resimler)
                    //galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(),getString(R.string.aracaaitservistespitresmibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitservistespitresmibulunmamakta))


                }


            }, { error ->
                android.widget.Toast.makeText(
                    requireContext(),
                    error.message,
                    android.widget.Toast.LENGTH_LONG
                ).show()
                android.util.Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitservistespitresmibulunmamakta))


            }
        )
        queue.add(request)


    }


}