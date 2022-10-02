package com.extraedge.mvvm.view.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.extraedge.R
import com.extraedge.mvvm.model.response.GetRocketResponse
import kotlinx.android.synthetic.main.raw_rocket_item.view.*

class RocketlistAdapter(internal var result: ArrayList<GetRocketResponse>?) : RecyclerView.Adapter<RocketlistAdapter.ViewHolder>() {
    private var context: Context? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.raw_rocket_item, parent, false)
        context = parent?.context
        view.imag_rocket.clipToOutline = true
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = result?.get(position)

        holder.txtName.text = "Name: "+ items?.name
        holder.txtCountry.text = "Country: "+items?.country
        holder.txtCount.text = "Count: "+items?.first_stage!!.engines.toString()
        Glide.with(context!!).load(items?.flickr_images!!.get(0)).placeholder(R.drawable.ic_thumnail).into(holder.imgBanner)

    }

    fun getData(): ArrayList<GetRocketResponse>? {
        return result
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgBanner = v.imag_rocket
        val txtName = v.txtname
        val txtCountry = v.txtcountry
        val txtCount = v.txtcount

    }
}