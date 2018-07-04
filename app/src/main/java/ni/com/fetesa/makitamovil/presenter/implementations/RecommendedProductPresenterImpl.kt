package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.RecommendedProduct
import ni.com.fetesa.makitamovil.presenter.IRecommendedProductPresenter
import ni.com.fetesa.makitamovil.ui.views.IRecommendedProductView

/**
 * Created by dusti on 04/07/2018.
 */
class RecommendedProductPresenterImpl: IRecommendedProductPresenter {
    private val mRecommendedProductView: IRecommendedProductView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(recommendedProductView: IRecommendedProductView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mRecommendedProductView = recommendedProductView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun getRecommendedProducts() {
        mRecommendedProductView.showLoadingRecommendedProducts()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getRecommendedProducts(token, object: IMakitaResponseCallback<List<RecommendedProduct>>{
            override fun onSuccess(response: List<RecommendedProduct>) {
                mRecommendedProductView.hideLoadingRecommendedProducts()
                mRecommendedProductView.setRecommendationsList(response.toMutableList())
            }

            override fun onNetworkFailure() {
                mRecommendedProductView.hideLoadingRecommendedProducts()
                mRecommendedProductView.showError()
            }

            override fun onCustomMessage(message: String) {
                mRecommendedProductView.hideLoadingRecommendedProducts()
                mRecommendedProductView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mRecommendedProductView.hideLoadingRecommendedProducts()
                mRecommendedProductView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mRecommendedProductView.navigateToLogin()
            }
        })
    }
}