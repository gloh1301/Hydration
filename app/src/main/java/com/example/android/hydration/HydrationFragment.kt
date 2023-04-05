package com.example.android.hydration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import java.time.DayOfWeek

private const val ARG_DAY_OF_WEEK = "day_of_week"

class HydrationFragment : Fragment() {

    private lateinit var dayOfWeek: String

    private lateinit var waterRatingBar: RatingBar
    private lateinit var addGlassButton: ImageButton
    private lateinit var resetGlassesButton: ImageButton

    private lateinit var waterRecord: WaterRecord

    private val waterViewModel by lazy {
        WaterViewModelFactory((requireActivity().application as HydrationApplication).repository)
            .create(WaterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dayOfWeek = it.getString(ARG_DAY_OF_WEEK)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hydration, container, false)

        waterRatingBar = view.findViewById(R.id.water_rating_bar)
        addGlassButton = view.findViewById(R.id.add_glass_button)
        resetGlassesButton = view.findViewById(R.id.reset_count_button)

        waterViewModel.getRecordForDay(dayOfWeek).observe(requireActivity()) { wr ->
            if (wr == null) {
                waterViewModel.insertNewRecord(WaterRecord(dayOfWeek, 0))
            } else {
                waterRecord = wr
                waterRatingBar.progress = waterRecord.glasses

                addGlassButton.setOnClickListener {
                    addGlass()
                }

                resetGlassesButton.setOnClickListener {
                    resetGlasses()
                }
            }
        }

        return view
    }

    private fun addGlass() {
        if (waterRecord.glasses < resources.getInteger(R.integer.max_glasses)) {
            waterRecord.glasses++
            waterViewModel.updateRecord(waterRecord)
        }
    }

    private fun resetGlasses() {
        waterRecord.glasses = 0
        waterViewModel.updateRecord(waterRecord)
    }

    companion object {

        @JvmStatic
        fun newInstance(dayOfWeek: String) =
            HydrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DAY_OF_WEEK, dayOfWeek)
                }
            }
    }
}