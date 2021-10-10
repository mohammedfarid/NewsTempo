package fit.tempoexample.newstempo.presentation.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import fit.tempoexample.newstempo.R
import fit.tempoexample.newstempo.data.entities.response.ArticlesItem
import fit.tempoexample.newstempo.databinding.FragmentNewsDetailsBinding
import fit.tempoexample.newstempo.presentation.HomeActivity
import fit.tempoexample.newstempo.presentation.bases.BaseFragment
import fit.tempoexample.newstempo.presentation.customui.RoundedCornersTransformation
import fit.tempoexample.newstempo.utils.ConvertDateTimeUtils
import fit.tempoexample.newstempo.utils.ConvertDimenUtils
import fit.tempoexample.newstempo.utils.RegexUtils


@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment(R.layout.fragment_news_details), View.OnClickListener {
    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding
    var articlesItem: ArticlesItem? = null
    override fun onStart() {
        super.onStart()
        (activity as HomeActivity).showBack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    (activity as HomeActivity).onBackPress(1)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        // The callback can be enabled or disabled here or in handleOnBackPressed()
        arguments?.let {
            articlesItem = it.getParcelable(ARTICLES_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsDetailsBinding.bind(view)

        binding?.tvSource?.text = articlesItem?.source?.name
        binding?.tvAuthor?.text = "${getString(R.string.by)} ${articlesItem?.author}"
        binding?.tvTitle?.text = articlesItem?.title
        binding?.tvDate?.text = ConvertDateTimeUtils.getTimeAsDateEnglish(
            articlesItem?.publishedAt,
            ConvertDateTimeUtils.SERVER_FORMAT,
            ConvertDateTimeUtils.SHOW_FORMAT
        )
        binding?.tvDescription?.text = articlesItem?.description
        binding?.ivNews?.let {
            Glide.with(requireContext()).load(articlesItem?.urlToImage)
                .placeholder(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_default
                    )
                ).apply(
                    RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(
                            context,
                            ConvertDimenUtils.dpToPx(10),
                            0,
                            RoundedCornersTransformation.CornerType.ALL
                        )
                    )
                ).into(it)
        }
        binding?.tvContent?.text =
            articlesItem?.content?.replace(RegexUtils.REMOVE_PATTERN.toRegex(), "")

        binding?.btnMore?.setOnClickListener(this)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    companion object {
        private val TAG = NewsDetailsFragment::class.java.simpleName
        const val ARTICLES_KEY = "articles_key"
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.btnMore -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articlesItem?.url))
                startActivity(browserIntent)
            }
        }
    }
}