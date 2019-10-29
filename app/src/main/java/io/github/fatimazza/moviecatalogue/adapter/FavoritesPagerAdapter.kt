package io.github.fatimazza.moviecatalogue.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.fragment.FavoriteMovieFragment
import io.github.fatimazza.moviecatalogue.fragment.FavoriteTelevisionFragment

class FavoritesPagerAdapter(fm: FragmentManager, context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val numberOfTab: Int = 2
    private var ctx: Context = context

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment()
            else -> {
                return FavoriteTelevisionFragment()
            }
        }
    }

    override fun getCount(): Int {
        return numberOfTab
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> ctx.resources.getString(R.string.menu_movie)
            else -> {
                ctx.resources.getString(R.string.menu_tv_show)
            }
        }
    }
}
