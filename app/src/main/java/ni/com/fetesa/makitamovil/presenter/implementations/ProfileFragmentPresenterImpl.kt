package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaGetProfileCallBack
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaProfile
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.presenter.IProfileFragmentPresenter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IProfileFragmentView

/**
 * Created by dusti on 11/05/2018.
 */
class ProfileFragmentPresenterImpl:IProfileFragmentPresenter {

    private val mProfileFragmentView: IProfileFragmentView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(profileFragmentView: IProfileFragmentView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mProfileFragmentView = profileFragmentView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun getProfile() {
        mProfileFragmentView.showLoadingProfile()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getProfile(token, object: IMakitaGetProfileCallBack{
            override fun onSuccess(response: MakitaProfile) {
                mProfileFragmentView.loadProfile(response)
                MakitaUserSession.instance.makitaProfile = response
                mProfileFragmentView.hideLoadingProfile()
            }

            override fun onFailure() {
                mProfileFragmentView.hideLoadingProfile()
                mProfileFragmentView.showError()
            }

            override fun onUnauthorized(response: MakitaProfile) {
                mProfileFragmentView.hideLoadingProfile()
                mSharedPrefManager.clearPreferences()
                mProfileFragmentView.showCustomError(response.message)
            }
        })
    }

}