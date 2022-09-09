package com.bitc.testapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bitc.testapp.fragment.BasicFragment
import com.bitc.testapp.fragment.DriveFragment
import com.bitc.testapp.fragment.PetWalkFragment
import com.bitc.testapp.fragment.RidingFragment
import com.bitc.testapp.fragment.RunningFragment

class FragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments: List<Fragment>

        init {
            fragments = listOf(
                BasicFragment(),
                PetWalkFragment(),
                RunningFragment(),
                RidingFragment(),
                DriveFragment()
            )
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }