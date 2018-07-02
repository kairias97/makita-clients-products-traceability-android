package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.PasswordUpdate
import ni.com.fetesa.makitamovil.model.PasswordUpdateResponse
import ni.com.fetesa.makitamovil.presenter.IPasswordUpdatePresenter
import ni.com.fetesa.makitamovil.ui.views.IPasswordUpdateView

/**
 * Created by dusti on 01/07/2018.
 */
class PasswordUpdatePresenterImpl: IPasswordUpdatePresenter {

    private val mPasswordUpdateView: IPasswordUpdateView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(passwordUpdateView: IPasswordUpdateView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mPasswordUpdateView = passwordUpdateView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun updatePassword(oldPassword: String, newPassword: String) {
        mPasswordUpdateView.showUpdatingPasswordProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.updatePassword(token, PasswordUpdate(oldPassword, newPassword), object: IMakitaResponseCallback<PasswordUpdateResponse>{
            override fun onSuccess(response: PasswordUpdateResponse) {
                mPasswordUpdateView.hideUpdatingPasswordProgress()
                mPasswordUpdateView.showCustomMessage(response.message)
                if(response.isSuccessful){
                    mPasswordUpdateView.navigateToMain()
                }
            }

            override fun onNetworkFailure() {
                mPasswordUpdateView.hideUpdatingPasswordProgress()
                mPasswordUpdateView.showError()
            }

            override fun onCustomMessage(message: String) {
                mPasswordUpdateView.hideUpdatingPasswordProgress()
                mPasswordUpdateView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mPasswordUpdateView.hideUpdatingPasswordProgress()
                mPasswordUpdateView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mPasswordUpdateView.navigateToLogin()
            }
        })
    }
}