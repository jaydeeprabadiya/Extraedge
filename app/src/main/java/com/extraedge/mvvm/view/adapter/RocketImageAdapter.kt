package com.extraedge.mvvm.view.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.extraedge.R
import com.extraedge.mvvm.model.response.GetRocketDetailsResponse
import kotlinx.android.synthetic.main.slidingimages_layout.view.*


class RocketImageAdapter(
    internal var context: Context,
    internal var imagesArray: GetRocketDetailsResponse
) : PagerAdapter() {

    var inflater = LayoutInflater.from(context);

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout: View =
            inflater.inflate(R.layout.slidingimages_layout, view, false)!!

        imageLayout.image.clipToOutline = true
        Glide.with(context)
            .load(imagesArray.flickr_images!!.get(position)).placeholder(R.drawable.ic_thumnail)
            .into(imageLayout.image)

        view.addView(imageLayout, 0)

        return imageLayout!!
    }

    override fun getCount(): Int {
        return imagesArray.flickr_images!!.size!!
    }

}