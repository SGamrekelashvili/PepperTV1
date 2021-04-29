package ge.android.gis.tv1.activity.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ge.android.gis.tv1.R

object FragmentUtils {

    var fragmentManager: FragmentManager? = null
    private var currentFragment: String? = null


    fun setFragment(fragment: Fragment, back: Boolean) {
        currentFragment = fragment.javaClass.simpleName
//        val topicName: String = currentFragment!!.toLowerCase().replace("fragment", "")


        Log.d("TAG", "Transaction for fragment : " + fragment.javaClass.simpleName)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

        if (back) {
            transaction.setCustomAnimations(
                R.anim.enter_fade_in_left,
                R.anim.exit_fade_out_right,
                R.anim.enter_fade_in_right,
                R.anim.exit_fade_out_left
            )
        } else transaction.setCustomAnimations(
            R.anim.enter_fade_in_right,
            R.anim.exit_fade_out_left,
            R.anim.enter_fade_in_left,
            R.anim.exit_fade_out_right
        )


        transaction.replace(R.id.fragment_container, fragment, "currentFragment")
        transaction.addToBackStack(null)
        transaction.commit()


    }

    fun getFragment(): Fragment? {
        return fragmentManager!!.findFragmentByTag("currentFragment")
    }


}