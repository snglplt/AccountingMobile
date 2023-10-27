package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.selpar.selparbulut.R


class YetkiYokFragment : Fragment() {
    private var bottomNavigation: MeowBottomNavigation? = null
    var bundlem=Bundle()
    lateinit var username:String
    lateinit var uid:String
    lateinit var FirmaLogo:String
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_yetki_yok, container, false)
        bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation!!.show(2, true)
        bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
        bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
        bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.ic_chat))
        bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
        bottomNavigation!!.add(MeowBottomNavigation.Model(5, R.drawable.arama))
        bottomNavigation!!.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
            when (item.id) {
                1 -> {
                    //assert(getFragmentManager() != null)
                   // frame_kartlar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Home Tıklandı", Toast.LENGTH_LONG).show()
                    val childFragment = Home()
                    bundlem.putString("username", username)
                    bundlem.putString("uid", uid)
                    bundlem.putString("FirmaLogo", FirmaLogo)
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
                    //Toast.makeText(requireContext(),username+ "----"+uid, Toast.LENGTH_LONG).show()
                    // communicator.padData(username)

                    val childFragment = MyBooking()
                    bundlem.putString("username", username)
                    bundlem.putString("uid", uid)
                    bundlem.putString("FirmaLogo", "FirmaLogo")
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
                    val childFragment = ChatList()
                    bundlem.putString("username", username)
                    bundlem.putString("uid", uid)
                    bundlem.putString("FirmaLogo", FirmaLogo)
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
                    val childFragment = NotificationActivity()
                    bundlem.putString("username", username)
                    bundlem.putString("uid", uid)
                    bundlem.putString("FirmaLogo", FirmaLogo)
                    childFragment.arguments = bundlem
                    childFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            childFragment
                        ) // Eğer fragment_container adında bir container layout varsa, bu adı değiştirmelisiniz.
                        .addToBackStack(null) // İsteğe bağlı olarak geri tuşuna basıldığında ParentFragment'a geri dönebilmeniz için geri yükleme yığınına ekleyebilirsiniz.
                        .commit()

                } /*requireFragmentManager().beginTransaction()
                    .replace(R.id.frame, NotificationActivity()).commit()*/

                else -> {}
            }

        }
        )
        bottomNavigation!!.setOnShowListener(MeowBottomNavigation.ShowListener {
            // your codes
        })
        bottomNavigation!!.setOnReselectListener(MeowBottomNavigation.ReselectListener {
            // your codes
        })

        return view
    }

}