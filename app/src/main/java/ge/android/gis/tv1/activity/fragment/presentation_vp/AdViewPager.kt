package com.android.pepper.fragments.presentation_vp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import ge.android.gis.tv1.R

class PresentationVPAdapter(private val items: MutableList<ImageModel>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() = items.size


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView =
            LayoutInflater.from(container.context).inflate(R.layout.presentation_viewpager_layout, container, false)
        val model = items[position]
        itemView.findViewById<ImageView>(R.id.presentationImageView).setImageResource(model.image)

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}