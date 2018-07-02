package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.PasswordReset
import ni.com.fetesa.makitamovil.model.PasswordResetResponse
import ni.com.fetesa.makitamovil.presenter.IForgotPasswordPresenter
import ni.com.fetesa.makitamovil.ui.views.IForgotPasswordView

/**
 * Created by dusti on 01/07/2018.
 */
class ForgotPasswordPresenterImpl: IForgotPasswordPresenter {

    private val mForgotPasswordView: IForgotPasswordView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(forgotPasswordView: IForgotPasswordView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mForgotPasswordView = forgotPasswordView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun verifyUser(user: String) {
        mForgotPasswordView.showValidationProgress()
        mMakitaRemoteDataSource.resetPassword(PasswordReset(user), object: IMakitaResponseCallback<PasswordResetResponse>{
            override fun onSuccess(response: PasswordResetResponse) {
                mForgotPasswordView.hideValidationProgress()
                mForgotPasswordView.showCustomMessage(response.message)
                if(response.isSuccesful){
                    mForgotPasswordView.navigateToVerificationCode()
                }
            }

            override fun onNetworkFailure() {
                mForgotPasswordView.hideValidationProgress()
                mForgotPasswordView.showError()
            }

            override fun onCustomMessage(message: String) {
                mForgotPasswordView.hideValidationProgress()
                mForgotPasswordView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mForgotPasswordView.hideValidationProgress()
                mForgotPasswordView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mForgotPasswordView.navigateToLogin()
            }
        })
    }

}