package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.selpar.selparbulut.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationActivity : Fragment() {
    private var bottomNavigation: MeowBottomNavigation? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_notification_activity, container, false)

        bottomNavigation = view.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation!!.show(4, true)
        bottomNavigation!!.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
        bottomNavigation!!.add(MeowBottomNavigation.Model(2, R.drawable.ic_booking))
        bottomNavigation!!.add(MeowBottomNavigation.Model(3, R.drawable.ic_chat))
        bottomNavigation!!.add(MeowBottomNavigation.Model(4, R.drawable.ic_bell))
        bottomNavigation!!.add(MeowBottomNavigation.Model(5, R.drawable.arama))
        bottomNavigation!!.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
            when (item.id) {
                1 -> {
                    assert(getFragmentManager() != null)
                    val ft = requireFragmentManager().beginTransaction()
                    ft.replace(R.id.frame, Home()).addToBackStack(null).commit()
                }

                2 -> requireFragmentManager().beginTransaction().replace(R.id.frame, MyBooking())
                    .commit()

                3 -> requireFragmentManager().beginTransaction().replace(R.id.frame, ChatList())
                    .commit()

                4 -> requireFragmentManager().beginTransaction()
                    .replace(R.id.frame, NotificationActivity()).commit()

                else -> {}
            }
        })
        bottomNavigation!!.setOnShowListener(MeowBottomNavigation.ShowListener {
            // your codes
        })
        bottomNavigation!!.setOnReselectListener(MeowBottomNavigation.ReselectListener {
            // your codes
        })
        return view }

}