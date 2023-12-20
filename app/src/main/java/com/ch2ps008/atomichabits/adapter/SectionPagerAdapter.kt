package com.ch2ps008.atomichabits.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ch2ps008.atomichabits.ui.habit.HabitFragment

class SectionsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    var habit: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = HabitFragment()
        fragment.arguments = Bundle().apply {
            putInt(HabitFragment.ARG_POSITION, position + 1)
            putString(HabitFragment.ARG_HABIT, habit)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}