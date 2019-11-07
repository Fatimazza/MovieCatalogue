package io.github.fatimazza.moviecatalogue.fragment


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.adapter.ListMovieAdapter
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.utils.getFormattedLanguage
import io.github.fatimazza.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_list_movie.*

class ListMovieFragment : Fragment(), ListMovieAdapter.OnItemClickCallback,
    SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private val listMovie: RecyclerView
        get() = rv_movie

    private lateinit var listMovieAdapter: ListMovieAdapter

    private lateinit var movieViewModel: MovieViewModel

    private lateinit var locale: String

    private lateinit var searchView: SearchView

    companion object {
        private const val STATE_LOCALE = "state_locale"
        private const val STATE_LIST_MOVIE = "state_list_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLanguage()
        initMovieViewModel()
        setupListMovieAdapter()
        setItemClickListener()
        setClickListener()

        if (savedInstanceState == null) {
            fetchMovieData()
        } else {
            val stateLocale = savedInstanceState.getString(STATE_LOCALE)
            if (stateLocale != locale) {
                fetchMovieData()
            } else {
                val stateList =
                    savedInstanceState.getParcelableArrayList<MovieResponse>(STATE_LIST_MOVIE)
                if (stateList != null) {
                    if (stateList.isNotEmpty()) {
                        listMovieAdapter.setData(stateList)
                    } else {
                        showFailedLoad(true)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_LIST_MOVIE, listMovieAdapter.getData())
        outState.putString(STATE_LOCALE, locale)
    }

    private fun initMovieViewModel() {
        movieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MovieViewModel::class.java)
    }

    private fun setupListMovieAdapter() {
        listMovie.layoutManager = LinearLayoutManager(requireContext())
        listMovieAdapter = ListMovieAdapter()
        listMovieAdapter.notifyDataSetChanged()
        listMovie.adapter = listMovieAdapter
    }

    private fun fetchMovieData() {
        showLoading(true)
        listMovie.visibility = View.GONE
        llMovieFailed.visibility = View.GONE

        movieViewModel.getMovieData(locale.getFormattedLanguage()).observe(this, Observer { movie ->
            if (movie != null) {
                listMovieAdapter.setData(movie)
                showLoading(false)
            } else {
                showLoading(false)
                showFailedLoad(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbLoadingMovie.visibility = View.VISIBLE
        } else {
            pbLoadingMovie.visibility = View.GONE
        }
        showFailedLoad(false)
    }

    private fun showFailedLoad(state: Boolean) {
        if (state) {
            llMovieFailed.visibility = View.VISIBLE
            listMovie.visibility = View.GONE
        } else {
            llMovieFailed.visibility = View.GONE
            listMovie.visibility = View.VISIBLE
        }
    }

    private fun setClickListener() {
        btnRetryMovie.setOnClickListener {
            fetchMovieData()
        }
    }

    private fun setItemClickListener() {
        listMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: MovieResponse) {
        val intentMovie = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE, data.id.toString())
        }
        startActivity(intentMovie)
    }

    private fun setLanguage() {
        locale = resources.configuration.locale.toLanguageTag()
    }

    private fun fetchMovieSearchData(query: String) {
        showLoading(true)
        listMovie.visibility = View.GONE
        llMovieFailed.visibility = View.GONE

        movieViewModel.searchMovie(locale.getFormattedLanguage(), query)
            .observe(this, Observer { movie ->
                if (movie != null) {
                    listMovieAdapter.setData(movie)
                    showLoading(false)
                } else {
                    showLoading(false)
                    showFailedLoad(true)
                }
            })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            fetchMovieSearchData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.searchview_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)

        searchItem.setOnActionExpandListener(this)

        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onMenuItemActionExpand(menuItem: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(menuItem: MenuItem?): Boolean {
        fetchMovieData()
        return true
    }
}
