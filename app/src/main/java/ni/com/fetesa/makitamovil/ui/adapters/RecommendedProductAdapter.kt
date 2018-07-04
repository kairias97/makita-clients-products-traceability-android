package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.RecommendedProduct

/**
 * Created by dusti on 04/07/2018.
 */
class RecommendedProductAdapter:RecyclerView.Adapter<RecommendedProductViewHolder> {
    private var recommendations: MutableList<RecommendedProduct>
    private var listener: OnRecommendationSelectedListener

    interface OnRecommendationSelectedListener{
        fun onRecommendationSelected(recommendation: RecommendedProduct)
    }

    constructor(recommendations: MutableList<RecommendedProduct>, listener: OnRecommendationSelectedListener){
        this.recommendations = recommendations
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecommendedProductViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return RecommendedProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommendations.count()
    }

    override fun onBindViewHolder(holder: RecommendedProductViewHolder?, position: Int) {
        (holder as RecommendedProductViewHolder).bindData(recommendations[position], listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_recommended_product
    }

    fun setRecommendationsList(list: MutableList<RecommendedProduct>){
        this.recommendations = list
    }
}