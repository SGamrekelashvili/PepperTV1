package ge.android.gis.tv1.activity.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.android.pepper.fragments.presentation_vp.ImageModel
import com.android.pepper.fragments.presentation_vp.PresentationVPAdapter
import ge.android.gis.tv1.R
import ge.android.gis.tv1.databinding.FragmentPresentationBinding

class PresentationFragment : Fragment() {

    lateinit var binding: FragmentPresentationBinding
    lateinit var handler: Handler
    var nextPosition = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPresentationBinding.inflate(inflater, container, false)

        initViewPager()
        return binding.root
    }

    private fun initViewPager() {
        val imageItems = mutableListOf<ImageModel>()
        imageItems.add(ImageModel(R.drawable.first_page))
        imageItems.add(ImageModel(R.drawable.second_page))
        imageItems.add(ImageModel(R.drawable.third_page))
        imageItems.add(ImageModel(R.drawable.fourth_page))
        imageItems.add(ImageModel(R.drawable.fifth_page))
        imageItems.add(ImageModel(R.drawable.sixth_page))
        imageItems.add(ImageModel(R.drawable.seventh_page))
        imageItems.add(ImageModel(R.drawable.eigth_page))
        imageItems.add(ImageModel(R.drawable.ningth_page))
        imageItems.add(ImageModel(R.drawable.tenth_page))
        imageItems.add(ImageModel(R.drawable.eleventh_page))
        val adapter = PresentationVPAdapter(imageItems)
        binding.homeViewPager.adapter = adapter
        binding.homeDotsIndicator.setViewPager(binding.homeViewPager)
        handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            binding.homeViewPager.currentItem = nextPosition
        }
        handler.postDelayed(runnable, 5000)

        binding.homeViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(runnable)
                if (imageItems.size - 1 == position) {
                    nextPosition = 0
                } else {
                    nextPosition = position + 1
                }
                handler.postDelayed(runnable, 5000)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        binding.backBtn.setOnClickListener {
            FragmentUtils.setFragment(MainFragment(), true)
        }
    }

}