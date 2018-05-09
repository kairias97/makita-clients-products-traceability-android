package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.presenter.ISplashPresenter
import ni.com.fetesa.makitamovil.ui.views.ISplashView
import ni.com.fetesa.makitamovil.utils.TimedTaskHandler

/**
 * Created by dusti on 09/05/2018.
 */
class SplashPresenterImpl: ISplashPresenter {
    private val mSplashTimeOut: Int = 1000
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSplashView: ISplashView
    private val mSharedPrefManager: SharedPrefManager

    constructor(view: ISplashView, sharedPref: SharedPrefManager, makitaRemoteDataSource: MakitaRemoteDataSource){
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSplashView = view
        this.mSharedPrefManager = sharedPref
    }

    private fun isSessionSaved(): Boolean{
        val token = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN)
        return token != ""
    }
    private fun setupSessionInstance(){
        val token = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN)
        val user = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.EMAIL)

        MakitaUserSession.instance.email = user
        MakitaUserSession.instance.authToken = token
    }
    private fun redirectToMain() {
        val timedTask = TimedTaskHandler()
        timedTask.executeTimedTask({ mSplashView.navigateToMain() }, mSplashTimeOut.toLong())
    }
    private fun redirectToLogin() {
        val timedTask = TimedTaskHandler()
        timedTask.executeTimedTask({ mSplashView.navigateToLogin() }, mSplashTimeOut.toLong())
    }

    override fun checkSession() {
        if(isSessionSaved()){
            setupSessionInstance()
            redirectToMain()
        }
        else{
            redirectToLogin()
        }
    }
}