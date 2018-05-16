package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.presenter.IProductsFragmentPresenter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IProductsFragmentView

/**
 * Created by dusti on 14/05/2018.
 */
class ProductsFragmentPresenterImpl: IProductsFragmentPresenter {

    private val mProductFragmentView: IProductsFragmentView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(productsFragmentView: IProductsFragmentView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mProductFragmentView = productsFragmentView
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun getProducts() {
        mProductFragmentView.showLoadingProducts()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getProductsByUser(token,object: IMakitaResponseCallback<List<Product>>{
            override fun onSuccess(response: List<Product>) {
                mProductFragmentView.setProductList(response.toMutableList())
                mProductFragmentView.hideLoadingProducts()
            }

            override fun onCustomMessage(message: String) {
                mProductFragmentView.hideLoadingProducts()
                mProductFragmentView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mProductFragmentView.hideLoadingProducts()
                mProductFragmentView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mProductFragmentView.navigateToLogin()
            }

            override fun onNetworkFailure() {
                mProductFragmentView.hideLoadingProducts()
                mProductFragmentView.showError()
            }
        })
    }
}