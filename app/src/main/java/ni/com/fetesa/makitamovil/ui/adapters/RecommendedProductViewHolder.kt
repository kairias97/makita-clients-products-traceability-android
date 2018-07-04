package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_recommended_product.view.*
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.RecommendedProduct

/**
 * Created by dusti on 04/07/2018.
 */
class RecommendedProductViewHolder: RecyclerView.ViewHolder {
    private val mTextViewName: TextView
    private val mTextViewDescription: TextView
    private val mTextViewAdditionalInfo: TextView

    constructor(itemView: View): super(itemView){
        mTextViewName = itemView.findViewById(R.id.textView_name)
        mTextViewDescription = itemView.findViewById(R.id.textView_description)
        mTextViewAdditionalInfo = itemView.findViewById(R.id.textView_additionalInfo)
    }

    fun bindData(recommendation: RecommendedProduct, listener: RecommendedProductAdapter.OnRecommendationSelectedListener){
        mTextViewName.text = recommendation.name
        mTextViewDescription.text = recommendation.description
        mTextViewAdditionalInfo.text = recommendation.additionalInfo

        itemView.setOnClickListener {
            listener.onRecommendationSelected(recommendation)
        }
    }
}