package io.github.fatimazza.myfavoritemovies.television


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.fatimazza.myfavoritemovies.R

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTelevisionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_television, container, false)
    }


}
