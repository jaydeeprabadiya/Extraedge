package com.extraedge.mvvm.view.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.apps.extraedge.utils.Utility
import com.extraedge.R
import com.extraedge.base.BaseActivity
import com.extraedge.mvvm.model.APIError
import com.extraedge.mvvm.model.response.GetRocketDetailsResponse
import com.extraedge.mvvm.view.adapter.RocketImageAdapter
import com.extraedge.mvvm.viewmodel.RocketViewModel
import com.extraedge.utils.Constants
import com.extraedge.webservice.ApiObserver
import kotlinx.android.synthetic.main.activity_roket_details.*

class RoketDetailsActivity : BaseActivity(), View.OnClickListener {
    private var TAG = this::class.java.simpleName
    private var viewofferModel: RocketViewModel? = null
    var rocketId: String? = null
    var hieghtinch: Double?=null
    var diameter: Double?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roket_details)


        viewofferModel = ViewModelProvider(this).get(RocketViewModel::class.java)
        img_Back.setOnClickListener(this)

        if (intent.hasExtra(Constants.BUNDLE_KEY.ROKET_ID)) {
            rocketId = intent.getStringExtra(Constants.BUNDLE_KEY.ROKET_ID)
            Log.e(TAG,"RECEIVE_ROKET_ID"+rocketId)


        }

        getRocketdetails()


    }

    override fun viewActivity(): Activity {
        return this
    }

    override fun onNetworkStateChange(isConnect: Boolean) {
    }


    private fun getRocketdetails() {
        when {
            isNetworkConnected(viewActivity()) -> {
                showProgress()
                viewofferModel!!.getRocketdetailsData(rocketId!!)
                    .observe(
                        this,
                        ApiObserver(object :
                            ApiObserver.ChangeListener<GetRocketDetailsResponse> {
                            override fun onSuccess(dataWrapper: GetRocketDetailsResponse) {
                                try {
                                    hideProgress()
                                    if (dataWrapper.id!=null) {
//                                    rocketAdapter = RocketlistAdapter(dataWrapper)
//                                    rcrocket.adapter = rocketAdapter

                                        txt_name.text = dataWrapper.name
                                        if (dataWrapper.active!=null)
                                        {
                                            if (dataWrapper.active==false)
                                            {
                                                txtactivestatus.text="Status: "+"False"
                                            }
                                            else
                                            {
                                                txtactivestatus.text="Status: "+"True"
                                            }

                                        }
                                        if (dataWrapper.height!!.meters!=null)
                                        {
                                            hieghtinch = 39.37* dataWrapper.height.meters!!
                                        }
                                        if (dataWrapper.diameter!!.meters!=null)
                                        {
                                            diameter = 39.37* dataWrapper.diameter.meters!!
                                        }
                                        val number2digits:Double = String.format("%.2f", hieghtinch).toDouble()
                                        txtheight_mtr.text = "Height Feet: " +dataWrapper.height!!.feet.toString()
                                        txt_height_inch.text = "Height Inch: "+number2digits.toString()

                                        val diameterdigits:Double = String.format("%.2f", diameter).toDouble()
                                        txtdiameter_height.text = "Diameter Feet: " +dataWrapper.diameter.feet.toString()
                                        txt_diameter_inch.text = "Diameter inch: " +diameterdigits.toString()

                                        txtcost.text= "Cost: "+dataWrapper.cost_per_launch.toString()
                                        txtsuccessrate.text = "Successrate: "+dataWrapper.success_rate_pct.toString()+"%"
                                        txtdescription.text = dataWrapper.description
                                        txtwpurl.text = "Wikipidea: "+dataWrapper.wikipedia
                                        init(dataWrapper)
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

    override fun onClick(p0: View?) {
        when (p0) {
            img_Back -> {
                onBackPressed()
            }
        }
    }

    private fun init(result: GetRocketDetailsResponse) {

        view_rocket_details.setAdapter(RocketImageAdapter(viewActivity()!!, result))
    }
}