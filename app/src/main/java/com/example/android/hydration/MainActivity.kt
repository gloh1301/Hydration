package com.example.android.hydration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var waterViewPager: ViewPager2
    private lateinit var waterTabLayout: TabLayout

    private val waterViewModel by lazy {
        WaterViewModelFactory((application as HydrationApplication).repository)
            .create(WaterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waterViewPager = findViewById(R.id.water_view_pager)
        waterTabLayout = findViewById(R.id.water_days_tab_layout)

        val days = resources.getStringArray(R.array.days)

        val pagerAdapter = WaterViewPagerAdapter(this, days)
        waterViewPager.adapter = pagerAdapter

        TabLayoutMediator(waterTabLayout, waterViewPager) { tab, position ->
            tab.text = days[position]
        }.attach()

        /* example data
        val tuesday = WaterRecord("Tuesday", 4)
        waterViewModel.insertNewRecord(tuesday)

        val webnesday = WaterRecord("Wednesday", 2)
        waterViewModel.insertNewRecord(webnesday)
        */

        waterViewModel.allRecords.observe(this) { records ->
            Log.d("MAIN_ACTIVITY", "$records")
        }

        /*
        supportFragmentManager.beginTransaction()
            .add(R.id.content, HydrationFragment.newInstance("Tuesday"))
            .commit()
        */
    }
}