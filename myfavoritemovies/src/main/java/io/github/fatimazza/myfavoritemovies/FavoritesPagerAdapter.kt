package io.github.fatimazza.myfavoritemovies

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.fatimazza.myfavoritemovies.movies.FavoriteMovieFragment
import io.github.fatimazza.myfavoritemovies.television.FavoriteTelevisionFragment

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
