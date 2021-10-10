package fit.tempoexample.newstempo.presentation.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fit.tempoexample.newstempo.R
import fit.tempoexample.newstempo.data.entities.response.ArticlesItem
import fit.tempoexample.newstempo.databinding.ItemNewsBinding
import fit.tempoexample.newstempo.presentation.customui.RoundedCornersTransformation
import fit.tempoexample.newstempo.utils.ConvertDimenUtils

/**
 * Created by Mohammed Farid on 10/9/2021
 * Contact me : m.farid.shawky@gmail.com
 */
class NewsAdapter(var context: Context, var onCLick: (ArticlesItem?) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var currents: ArrayList<ArticlesItem?>? = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(currents?.get(position))
        holder.binding.clNews.setOnClickListener { onCLick(currents?.get(position)) }
    }

    override fun getItemCount(): Int {
        return currents?.size ?: 0
    }

    inner class ViewHolder(var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem?) {
            binding.tvSource.text = item?.source?.name?.trim()
            binding.tvShortDescription.text = item?.description?.trim()
            Glide.with(context).load(item?.urlToImage)
                .apply(
                    RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(
                            context,
                            ConvertDimenUtils.dpToPx(10),
                            0,
                            RoundedCornersTransformation.CornerType.TOP
                        )
                    )
                ).placeholder(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_default
                    )
                ).into(binding.ivNews)
        }
    }

    fun addData(updates: ArrayList<ArticlesItem?>?) {
        if (updates != null) {
            currents?.addAll(updates)
        }
        notifyDataSetChanged()
    }
}