package com.sanket.mobileapp_poc2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanket.mobileapp_poc2.R
import com.sanket.mobileapp_poc2.databinding.FragmentDashboardBinding
import com.sanket.mobileapp_poc2.network.ConnectionDetector
import com.sanket.mobileapp_poc2.network.NetworkHelper
import com.sanket.mobileapp_poc2.network.RetrofitBuilder
import com.sanket.mobileapp_poc2.network.Status
import com.sanket.mobileapp_poc2.utils.EndlessRecyclerViewScrollListener
import com.sanket.mobileapp_poc2.utils.ViewModelFactory
import com.sanket.mobileapp_poc2.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

const val INITIAL_PAGE = 1

class DashboardFragment : Fragment() {
    lateinit var binding: FragmentDashboardBinding
    lateinit var viewModel: UserViewModel
    lateinit var adapter: DashboardAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val connectionDetector = ConnectionDetector()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        scrollListener = object :
            EndlessRecyclerViewScrollListener((recycler_users.layoutManager) as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (connectionDetector.isConnectingToInternet(requireActivity())) {
                    if (page > INITIAL_PAGE)
                        viewModel.getUsers(page = page)
                } else
                    Toast.makeText(
                        requireContext(),
                        "No Internet Connection !",
                        Toast.LENGTH_SHORT
                    ).show()

            }
        }

        setupAdapter()

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(NetworkHelper(RetrofitBuilder.apiService)))
                .get(UserViewModel::class.java)

        if (connectionDetector.isConnectingToInternet(requireActivity()))
            viewModel.getUsers(INITIAL_PAGE)
        else
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()


        viewModel.users.observe(viewLifecycleOwner, Observer {
            swipeTorefresh.isRefreshing = false
            when (it.status) {
                Status.LOADING -> {
                    progress_circular.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.data?.let { it1 -> adapter.addData(it1) }
                    progress_circular.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    progress_circular.visibility = View.GONE
                }
            }
        })


        swipeTorefresh.setOnRefreshListener {
            scrollListener.resetState()
            setupAdapter()
            viewModel.getUsers(INITIAL_PAGE)
        }

    }

    private fun setupAdapter() {
        adapter = DashboardAdapter(ArrayList())
        recycler_users.adapter = adapter
        recycler_users.addOnScrollListener(scrollListener)
    }
}