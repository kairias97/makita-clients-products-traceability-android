package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.Warranty
import ni.com.fetesa.makitamovil.model.WarrantyBindingRequest
import ni.com.fetesa.makitamovil.presenter.IProductDetailPresenter
import ni.com.fetesa.makitamovil.ui.views.IProductDetailView

/**
 * Created by dusti on 15/05/2018.
 */
class ProductDetailPresenterImpl: IProductDetailPresenter {
    private val mProductDetailView: IProductDetailView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(productDetailView: IProductDetailView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mProductDetailView = productDetailView
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun saveSerial(serial: String, productID: Int) {
        mProductDetailView.showSavingSerialProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.saveProductWarranty(token, productID.toLong(), WarrantyBindingRequest(serial),object: IMakitaResponseCallback<Warranty>{
            override fun onSuccess(response: Warranty) {
                mProductDetailView.hideSavingSerialProgress()
                mProductDetailView.savingSerialSuccessful()
                mProductDetailView.showWarranty(response)
            }

            override fun onCustomMessage(message: String) {
                mProductDetailView.hideSavingSerialProgress()
                mProductDetailView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mProductDetailView.hideSavingSerialProgress()
                mProductDetailView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
            }

            override fun onNetworkFailure() {
                mProductDetailView.hideSavingSerialProgress()
                mProductDetailView.showError()
            }
        })
    }

    override fun getWarranty(productID: Int) {
        mProductDetailView.showGettingWarrantyProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getProductWarranty(token, productID.toLong(), object: IMakitaResponseCallback<Warranty>{
            override fun onSuccess(response: Warranty) {
                mProductDetailView.hideGettingWarrantyProgress()
                mProductDetailView.showWarranty(response)
            }

            override fun onCustomMessage(message: String) {
                mProductDetailView.hideGettingWarrantyProgress()
                mProductDetailView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mProductDetailView.hideGettingWarrantyProgress()
                mProductDetailView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
            }

            override fun onNetworkFailure() {
                mProductDetailView.hideGettingWarrantyProgress()
                mProductDetailView.showError()
            }
        })
    }
}