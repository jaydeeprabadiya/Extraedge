package com.extraedge.mvvm.view.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.extraedge.utils.Utility
import com.extraedge.R
import com.extraedge.base.BaseActivity
import com.extraedge.mvvm.model.APIError
import com.extraedge.mvvm.model.response.CategoryEntity
import com.extraedge.mvvm.model.response.CategoryFilterResponse
import com.extraedge.mvvm.model.response.GetRocketResponse
import com.extraedge.mvvm.view.adapter.RocketlistAdapter
import com.extraedge.mvvm.viewmodel.RocketViewModel
import com.extraedge.utils.Constants
import com.extraedge.webservice.ApiObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class RocketListActivity : BaseActivity(), View.OnClickListener {
    private var TAG = this::class.java.simpleName
    private var viewrocketModel: RocketViewModel? = null
    private var rocketAdapter: RocketlistAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewrocketModel = ViewModelProvider(this).get(RocketViewModel::class.java)
        rcrocket.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)



        rcrocket.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {

            var gestureDetector =
                GestureDetector(viewActivity(), object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        return true
                    }
                })

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    val position = rv.getChildAdapterPosition(child)

                    Log.e(TAG,"ROKET_ID==>"+rocketAdapter?.getData()?.get(position)?.id)
                    startActivity(
                        Intent(viewActivity(), RoketDetailsActivity::class.java)
                            .putExtra(
                                Constants.BUNDLE_KEY.ROKET_ID,
                                rocketAdapter?.getData()?.get(position)?.id
                            )
                    )

                }

                return null == true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })

        getOffers()
    }

    private fun getOffers() {

        when {
            isNetworkConnected(viewActivity()) -> {
                showProgress()
                viewrocketModel!!.getCategoryData()
                    .observe(
                        this,
                        ApiObserver(object :
                            ApiObserver.ChangeListener<ArrayList<GetRocketResponse>> {
                            override fun onSuccess(dataWrapper: ArrayList<GetRocketResponse>?) {
                                try {
                                    hideProgress()
                                    if (dataWrapper?.size!! > 0) {
                                        rocketAdapter = RocketlistAdapter(dataWrapper)
                                        rcrocket.adapter = rocketAdapter

                                        viewrocketModel!!.allStoreItems()?.observe(this@RocketListActivity) {
                                            if (it.isEmpty()) {
                                                insertData(dataWrapper)
                                            } else {
                                            }
                                        }

                                    }
                                }
                                catch (e:Exception)
                                {
                                    e.printStackTrace()
                                }
                            }

                            override fun onError(error: APIError?) {
                                try {
                                    hideProgress()
                                    showToast(error?.httpErrorMessage!!)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        })
                    )
            }
            else -> {
                showToast(getString(R.string.no_internet_message))
            }
        }
    }

    private fun insertData(dataWrapper: ArrayList<GetRocketResponse>) {
        val listByCategoryFilter = ArrayList<CategoryFilterResponse>()
        for (pos in 0 until dataWrapper.size) {
            if (!listByCategoryFilter.contains(
                    CategoryFilterResponse(
                        dataWrapper[pos].description!!,
                        ArrayList())))
                listByCategoryFilter.add(
                    CategoryFilterResponse(
                        dataWrapper[pos].description!!,
                        ArrayList()
                    )
                )
        }
        for (pos in 0 until listByCategoryFilter.size) {
            for (pos1 in 0 until dataWrapper.size) {
                if (listByCategoryFilter[pos].category == dataWrapper[pos1].description) {
                    listByCategoryFilter[pos].data.add(dataWrapper[pos1])
                }
            }
        }

        val categoryEntity = CategoryEntity(0, listByCategoryFilter)
        viewrocketModel!!.insertCategoryInfo(categoryEntity)
    }

    override fun viewActivity(): Activity {
       return this
    }

    override fun onNetworkStateChange(isConnect: Boolean) {

    }

    override fun onClick(p0: View?) {

    }
}