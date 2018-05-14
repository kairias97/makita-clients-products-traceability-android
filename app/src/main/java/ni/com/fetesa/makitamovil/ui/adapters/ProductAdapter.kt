package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.Product

/**
 * Created by dusti on 14/05/2018.
 */
class ProductAdapter: RecyclerView.Adapter<ProductsViewHolder> {
    private var products: MutableList<Product>
    private var listener: OnProductSelecteListener

    interface OnProductSelecteListener{
        fun onProductSelected(product: Product)
    }
    constructor(products: MutableList<Product>, listener: OnProductSelecteListener){
        this.products = products
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: ProductsViewHolder?, position: Int) {
        (holder as ProductsViewHolder).bindData(products[position],this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_product
    }
    fun setProductsList(list: MutableList<Product>){
        this.products = list
    }
}