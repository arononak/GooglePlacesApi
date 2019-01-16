package com.example.googleplacesautocomplete.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googleplacesautocomplete.R
import com.example.googleplacesautocomplete.db.HistoryEntity
import com.example.googleplacesautocomplete.vm.HistoryViewModel
import com.example.googleplacesautocomplete.vm.ViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@Suppress("DEPRECATION")
class MainActivity : DaggerAppCompatActivity(), PlaceSelectionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        historyViewModel = ViewModelProviders.of(this, viewModelFactory)[HistoryViewModel::class.java]

        historyViewModel.historyLiveData.observe(this, Observer { items ->
            initHistoryList(items)
        })

        (fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment).setOnPlaceSelectedListener(this)
    }

    override fun onPlaceSelected(place: Place) {
        Toast.makeText(applicationContext, place.name.toString(), Toast.LENGTH_SHORT).show()
        historyViewModel.insertHistory(HistoryEntity.fromPlace(place))
    }

    override fun onError(status: Status) {
        Toast.makeText(applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initHistoryList(items: MutableList<HistoryEntity>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.VERTICAL }
            if (adapter == null) {
                adapter = HistoryAdapter(context, items, { position ->
                    val item = (adapter!! as HistoryAdapter).items[position]
                    startActivity(MapsActivity.newIntent(context, Marker(item.name, item.latitude, item.longitude)))
                    overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stay)
                }, { position ->
                    val item = (adapter!! as HistoryAdapter).items[position]
                    Toast.makeText(applicationContext, item.name, Toast.LENGTH_SHORT).show()
                    historyViewModel.deleteHistory(position)
                })
            } else {
                (adapter!! as HistoryAdapter).apply {
                    this.items = items
                    notifyDataSetChanged()
                }
            }
        }
    }
}
