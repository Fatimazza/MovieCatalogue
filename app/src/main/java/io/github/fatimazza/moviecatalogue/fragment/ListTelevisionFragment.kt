package io.github.fatimazza.moviecatalogue.fragment


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.adapter.ListTelevisionAdapter
import io.github.fatimazza.moviecatalogue.model.TvShowResponse
import io.github.fatimazza.moviecatalogue.utils.getFormattedLanguage
import io.github.fatimazza.moviecatalogue.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_list_television.*

class ListTelevisionFragment : Fragment(), ListTelevisionAdapter.OnItemClickCallback,
    SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private val listTelevision: RecyclerView
        get() = rv_tvshow

    private lateinit var listTelevisionAdapter: ListTelevisionAdapter

    private lateinit var tvShowViewModel: TvShowViewModel

    private lateinit var locale: String

    private lateinit var searchView: SearchView

    private var stateIsSearching = false
    private var stateSearchQuery = ""

    companion object {
        private const val STATE_LOCALE = "state_locale"
        private const val STATE_LIST_TV = "state_list_tv"
        private const val STATE_IS_SEARCHING = "state_is_searching"
        private const val STATE_SEARCH_QUERY = "state_search_query"
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
        return inflater.inflate(R.layout.fragment_list_television, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLanguage()
        initTvViewModel()
        setupListTelevisionAdapter()
        setItemClickListener()
        setClickListener()

        if (savedInstanceState == null) {
            fetchTelevisionData()
        } else {
            val stateLocale = savedInstanceState.getString(STATE_LOCALE)
            stateIsSearching = savedInstanceState.getBoolean(STATE_IS_SEARCHING, false)
            stateSearchQuery = savedInstanceState.getString(STATE_SEARCH_QUERY, "")

            if (stateLocale != locale) {
                fetchTelevisionData()
            } else {
                val stateList =
                    savedInstanceState.getParcelableArrayList<TvShowResponse>(STATE_LIST_TV)
                if (stateList != null) {
                    if (stateList.isNotEmpty()) {
                        listTelevisionAdapter.setData(stateList)
                    } else {
                        showFailedLoad(true)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_LIST_TV, listTelevisionAdapter.getData())
        outState.putString(STATE_LOCALE, locale)
        outState.putBoolean(STATE_IS_SEARCHING, !searchView.isIconified)
        outState.putString(STATE_SEARCH_QUERY, searchView.query.toString())
    }

    private fun initTvViewModel() {
        tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(TvShowViewModel::class.java)
    }

    private fun setupListTelevisionAdapter() {
        listTelevision.layoutManager = LinearLayoutManager(requireContext())
        listTelevisionAdapter = ListTelevisionAdapter()
        listTelevisionAdapter.notifyDataSetChanged()
        listTelevision.adapter = listTelevisionAdapter
    }

    private fun fetchTelevisionData() {
        showLoading(true)
        listTelevision.visibility = View.GONE
        llTelevisionFailed.visibility = View.GONE

        tvShowViewModel.getTvShowData(locale.getFormattedLanguage())
            .observe(this, Observer { tvShow ->
            if (tvShow != null) {
                listTelevisionAdapter.setData(tvShow)
                showLoading(false)
            } else {
                showLoading(false)
                showFailedLoad(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbLoadingTelevision.visibility = View.VISIBLE
        } else {
            pbLoadingTelevision.visibility = View.GONE
        }
        showFailedLoad(false)
    }

    private fun showFailedLoad(state: Boolean) {
        if (state) {
            llTelevisionFailed.visibility = View.VISIBLE
            listTelevision.visibility = View.GONE
        } else {
            llTelevisionFailed.visibility = View.GONE
            listTelevision.visibility = View.VISIBLE
        }
    }

    private fun setClickListener() {
        btnRetryTelevision.setOnClickListener {
            if (searchView.isIconified) {
                fetchTelevisionData()
            } else {
                fetchTelevisionSearchData(searchView.query.toString())
            }
        }
    }

    private fun setItemClickListener() {
        listTelevisionAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: TvShowResponse) {
        val intentTelevision = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_TELEVISION, data.id.toString())
        }
        startActivity(intentTelevision)
    }

    private fun setLanguage() {
        locale = resources.configuration.locale.toLanguageTag()
    }

    private fun fetchTelevisionSearchData(query: String) {
        showLoading(true)
        listTelevision.visibility = View.GONE
        llTelevisionFailed.visibility = View.GONE

        tvShowViewModel.searchTvShow(locale.getFormattedLanguage(), query)
            .observe(this, Observer { tvShow ->
                if (tvShow != null) {
                    listTelevisionAdapter.setData(tvShow)
                    showLoading(false)
                } else {
                    showLoading(false)
                    showFailedLoad(true)
                }
            })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            fetchTelevisionSearchData(query)
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

        val options = searchView.imeOptions
        searchView.imeOptions = options or EditorInfo.IME_FLAG_NO_EXTRACT_UI

        if (stateIsSearching) {
            searchItem.expandActionView()
            searchView.setQuery(stateSearchQuery, false)
            searchView.clearFocus()
        }

        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onMenuItemActionExpand(menuItem: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(menuItem: MenuItem?): Boolean {
        fetchTelevisionData()
        return true
    }
}
