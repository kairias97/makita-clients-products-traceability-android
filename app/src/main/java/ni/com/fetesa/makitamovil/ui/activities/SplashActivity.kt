package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.ISplashPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.SplashPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.ISplashView

class SplashActivity : AppCompatActivity(), ISplashView {

    private lateinit var mSplashPresenter: ISplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        mSplashPresenter = SplashPresenterImpl(this,SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ), MakitaRemoteDataSource.instance)
        mSplashPresenter.checkSession()
    }

    override fun navigateToLogin() {
        val intentTarget = Intent(this, LoginActivity::class.java)
        intentTarget.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentTarget)
    }

    override fun navigateToMain() {
       val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
