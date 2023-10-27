package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.selpar.selparbulut.R
import com.selpar.selparbulut.model.SharedViewModel


class ResimFragment : Fragment() {

    lateinit var evrakResim:Button
    lateinit var olayYeri:Button
    lateinit var servisTespit:Button
    lateinit var yapimAsamasi:Button
    lateinit var bitmisHali:Button
    lateinit var kabulResim:Button
    var bundlem=Bundle()
    lateinit var username:String
    lateinit var uid:String
    lateinit var id:String
    lateinit var KullaniciId:String
    private lateinit var sharedViewModel: SharedViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_resim, container, false)
        val args=this.arguments
        username=args?.getString("username").toString()
        uid=args?.getString("uid").toString()
        id=args?.getString("id").toString()
        KullaniciId=args?.getString("KullaniciId").toString()

        kabulResim=view.findViewById(R.id.kabulResim)
        evrakResim=view.findViewById(R.id.evrakResim)
        olayYeri=view.findViewById(R.id.olayYeri)
        servisTespit=view.findViewById(R.id.servisTespit)
        yapimAsamasi=view.findViewById(R.id.yapimAsamasi)
        bitmisHali=view.findViewById(R.id.bitmisHali)
        KabulResimGetir()
        kabulResim.setOnClickListener { KabulResimGetir() }
        evrakResim.setOnClickListener { EvrakResimGetir() }
        olayYeri.setOnClickListener { OlayYeriResimGetir() }
        servisTespit.setOnClickListener { ServisTespitResimGetir() }
        yapimAsamasi.setOnClickListener { YapimAsamasiResimGetir() }
        bitmisHali.setOnClickListener { BitmisHaliResimGetir() }

        return view
    }
    fun KabulResimGetir(){
        olayYeri.setBackgroundResource(R.drawable.button_customer)
        kabulResim.setBackgroundColor(Color.parseColor("#FF6E40"))

//        evrakResim.background=resources.getDrawable(R.drawable.button_mavi)
        evrakResim.background=resources.getDrawable(R.drawable.button_customer)
        servisTespit.background=resources.getDrawable(R.drawable.button_customer)
        yapimAsamasi.background=resources.getDrawable(R.drawable.button_customer)
        bitmisHali.background=resources.getDrawable(R.drawable.button_customer)
        val childFragment = KabulResimleriFragment()

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("KullaniciId",KullaniciId)
        bundlem.putString("id",id)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar_resim, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun EvrakResimGetir(){
        olayYeri.setBackgroundResource(R.drawable.button_customer)
        evrakResim.setBackgroundColor(Color.parseColor("#FF6E40"))

//        evrakResim.background=resources.getDrawable(R.drawable.button_mavi)
        servisTespit.background=resources.getDrawable(R.drawable.button_customer)
        yapimAsamasi.background=resources.getDrawable(R.drawable.button_customer)
        bitmisHali.background=resources.getDrawable(R.drawable.button_customer)
        kabulResim.background=resources.getDrawable(R.drawable.button_customer)
        val childFragment = EvrakResimleriFragment()

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar_resim, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun OlayYeriResimGetir(){
        evrakResim.setBackgroundResource(R.drawable.button_customer)
        olayYeri.setBackgroundColor(Color.parseColor("#FF6E40"))

//        evrakResim.background=resources.getDrawable(R.drawable.button_mavi)
        servisTespit.background=resources.getDrawable(R.drawable.button_customer)
        yapimAsamasi.background=resources.getDrawable(R.drawable.button_customer)
        bitmisHali.background=resources.getDrawable(R.drawable.button_customer)
        kabulResim.background=resources.getDrawable(R.drawable.button_customer)
        val childFragment = OlayYeriResimleriFragment()

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar_resim, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun ServisTespitResimGetir(){
        evrakResim.setBackgroundResource(R.drawable.button_customer)
        servisTespit.setBackgroundColor(Color.parseColor("#FF6E40"))

//        evrakResim.background=resources.getDrawable(R.drawable.button_mavi)
        olayYeri.background=resources.getDrawable(R.drawable.button_customer)
        yapimAsamasi.background=resources.getDrawable(R.drawable.button_customer)
        bitmisHali.background=resources.getDrawable(R.drawable.button_customer)
        kabulResim.background=resources.getDrawable(R.drawable.button_customer)
        val childFragment = ServisTespitFragment()

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar_resim, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun YapimAsamasiResimGetir(){
        evrakResim.setBackgroundResource(R.drawable.button_customer)
        yapimAsamasi.setBackgroundColor(Color.parseColor("#FF6E40"))

//        evrakResim.background=resources.getDrawable(R.drawable.button_mavi)
        olayYeri.background=resources.getDrawable(R.drawable.button_customer)
        servisTespit.background=resources.getDrawable(R.drawable.button_customer)
        bitmisHali.background=resources.getDrawable(R.drawable.button_customer)
        kabulResim.background=resources.getDrawable(R.drawable.button_customer)
        val childFragment = YapimAsamasiFragment()

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar_resim, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }
    fun BitmisHaliResimGetir(){
        bitmisHali.setBackgroundColor(Color.parseColor("#FF6E40"))
//        evrakResim.background=resources.getDrawable(R.drawable.button_mavi)
        olayYeri.background=resources.getDrawable(R.drawable.button_customer)
        servisTespit.background=resources.getDrawable(R.drawable.button_customer)
        yapimAsamasi.background=resources.getDrawable(R.drawable.button_customer)
        evrakResim.background=resources.getDrawable(R.drawable.button_customer)
        kabulResim.background=resources.getDrawable(R.drawable.button_customer)
        val childFragment = BitmisHaliFragment()

        bundlem.putString("username",username)
        bundlem.putString("uid",uid)
        bundlem.putString("id",id)
        bundlem.putString("KullaniciId",KullaniciId)
        childFragment.arguments=bundlem
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_kartlar_resim, childFragment) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
            .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
            .commit()
    }

}