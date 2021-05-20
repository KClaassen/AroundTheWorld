package com.example.android.capstoneproject_aroundtheworld.trips

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.android.capstoneproject_aroundtheworld.R
import com.example.android.capstoneproject_aroundtheworld.databinding.FragmentNewTripBinding
import com.example.android.capstoneproject_aroundtheworld.models.Trip
import kotlinx.android.synthetic.main.fragment_new_trip.*
import java.text.SimpleDateFormat
import java.util.*

class NewTripFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentNewTripBinding

    //For date picker when creating new Trip
    private var cal = Calendar.getInstance()
    private lateinit var datesetListener: DatePickerDialog.OnDateSetListener
    private var datePickerViewId: Int = -1

    /**
     * Lazily initialize our [TripsViewModel].
     */
    private val viewModel: TripsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, TripsViewModel.Factory(activity.application)).get(
                TripsViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_trip, container, false)

        //Initializing ViewModel
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.trip = Trip(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )

        datesetListener = DatePickerDialog.OnDateSetListener {
            view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateinView(
                    when(datePickerViewId) {
                        R.id.new_trip_date_from -> {
                            new_trip_date_from
                        }
                        R.id.new_trip_date_to -> {
                            new_trip_date_to
                        }
                        else -> {
                            null
                        }
                    }
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_trip_date_from.setOnClickListener(this)
        new_trip_date_to.setOnClickListener(this)
    }

    //@RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.new_trip_date_from -> {
                DatePickerDialog(requireContext(),
                        datesetListener, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
                datePickerViewId = R.id.new_trip_date_from
            }
            R.id.new_trip_date_to -> {
                DatePickerDialog(requireContext(),
                        datesetListener, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
                datePickerViewId = R.id.new_trip_date_to
            }
        }
    }

    private fun updateDateinView(editText: EditText?) {
        val dateFormat  = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        editText?.setText(simpleDateFormat.format(cal.time).toString())
    }

}