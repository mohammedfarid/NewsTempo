package fit.tempoexample.newstempo.presentation.news

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import fit.tempoexample.newstempo.R
import fit.tempoexample.newstempo.data.entities.response.ArticlesItem
import fit.tempoexample.newstempo.data.entities.response.NewsResponse
import fit.tempoexample.newstempo.databinding.NewsFragmentBinding
import fit.tempoexample.newstempo.presentation.HomeActivity
import fit.tempoexample.newstempo.presentation.bases.BaseFragment
import fit.tempoexample.newstempo.presentation.news.NewsDetailsFragment.Companion.ARTICLES_KEY
import fit.tempoexample.newstempo.presentation.news.adapter.NewsAdapter
import fit.tempoexample.newstempo.utils.Dialog
import fit.tempoexample.newstempo.utils.KeyboardUtils
import fit.tempoexample.newstempo.utils.State

@AndroidEntryPoint
class NewsFragment : BaseFragment(R.layout.news_fragment), TextView.OnEditorActionListener {
    private val newsViewModel: NewsViewModel by viewModels()

    private var _binding: NewsFragmentBinding? = null
    private val binding get() = _binding
    var dialog: AlertDialog? = null

    var newsAdapter: NewsAdapter? = null
    var newsResponse: NewsResponse? = null

    var firstTime = true

    var isLoading = false
    var currentPage: Int = 1
    var wordSearch: String? = null

    var isFinish = false

    override fun onStart() {
        super.onStart()
        (activity as HomeActivity).hideBack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = NewsFragmentBinding.bind(view)
        dialog = Dialog.loadingDialog(requireContext())

        wordSearch?.let {
            callSearchResult(it,1)
        }
        binding?.baseContent?.setOnTouchListener { v, event ->
            if (event != null && event.action === MotionEvent.ACTION_MOVE) {
                KeyboardUtils.hideKeyboard(v, requireActivity())
            }
            return@setOnTouchListener false
        }

        binding?.edSearch?.setOnEditorActionListener(this)
        newsAdapter = NewsAdapter(requireContext()) { item ->
            val bundle = bundleOf(ARTICLES_KEY to item)
            findNavController().navigate(R.id.action_newsFragment_to_newsDetailsFragment, bundle)
        }
        binding?.rvNews?.adapter = newsAdapter

        binding?.rvNews?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager: LinearLayoutManager =
                    binding?.rvNews?.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                @Suppress("IMPLICIT_CAST_TO_ANY")
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isFinish) {
                    if ((visibleItemCount + firstVisibleItemPosition >= totalItemCount) && firstVisibleItemPosition > 0) {
                        wordSearch?.let { wordSearch ->
                            callSearchResult(wordSearch, page = currentPage)
                        }
                    }
                }

            }
        })

        if (firstTime) {
            binding?.tvFirstMsg?.visibility = View.VISIBLE
            binding?.tvEmpty?.visibility = View.GONE
            binding?.rvNews?.visibility = View.GONE
        } else {
            newsResponse?.let {
                checkEmpty()
            }
        }
    }

    private fun checkEmpty() {
        binding?.tvFirstMsg?.visibility = View.GONE
        if (newsResponse?.totalResults == 0) {
            binding?.tvEmpty?.visibility = View.VISIBLE
        } else {
            binding?.rvNews?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        when (p1) {
            EditorInfo.IME_ACTION_SEARCH -> {
                if (binding?.edSearch?.text?.trim()?.toString()?.isBlank() == false) {
                    wordSearch = binding?.edSearch?.text?.trim()?.toString()
                    firstTime = false
                    wordSearch?.let {
                        callSearchResult(it)
                    }
                } else {
                    binding?.tvEmpty?.visibility = View.GONE
                    binding?.rvNews?.visibility = View.GONE
                    binding?.tvFirstMsg?.visibility = View.VISIBLE
                    firstTime = true
                }
            }
        }
        return false
    }

    private fun callSearchResult(wordSearch: String, page: Int = 1) {
        newsViewModel.getNewsResponse(wordSearch, page).observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.LoadingState -> {
                    dialog?.show()
                    isLoading = true
                }
                is State.ErrorState -> {
                    Log.e("Error", state.exception.message ?: "")
                    Dialog.errorDialog(requireContext(), state.exception.message ?: "")
                    isLoading = false
                    isFinish = true
                    dialog?.cancel()
                }
                is State.DataState -> {
                    newsResponse = Gson().fromJson(state.data, NewsResponse::class.java)
                    binding?.tvFirstMsg?.visibility = View.GONE
                    if (newsResponse?.totalResults == 0) {
                        binding?.tvEmpty?.visibility = View.VISIBLE
                        binding?.rvNews?.visibility = View.GONE
                    } else {
                        binding?.rvNews?.visibility = View.VISIBLE
                        binding?.tvEmpty?.visibility = View.GONE
                    }
                    newsAdapter?.addData(newsResponse?.articles as ArrayList<ArticlesItem?>?)
                    currentPage = currentPage.plus(1)
                    isLoading = false
                    isFinish = false
                    dialog?.cancel()
                }
            }

        }
    }

    companion object {
        private val TAG = NewsFragment::class.java.simpleName
        const val EDIT_TEXT_KEY = "edit_text_key"
        const val PAGE_KEY = "page_key"
        const val LOADING_KEY = "loading_key"
        const val FINISH_KEY = "finish_key"
        const val NEWS_KEY = "news_key"
    }
}