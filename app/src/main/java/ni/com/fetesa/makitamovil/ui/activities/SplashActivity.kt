package ni.com.fetesa.makitamovil.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.ui.views.ISplashView

class SplashActivity : AppCompatActivity(), ISplashView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        navigateToLogin()
    }

    override fun navigateToLogin() {
        val intentTarget = Intent(this, LoginActivity::class.java)
        intentTarget.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentTarget)
    }

    override fun navigateToMain() {
        //por implementar
    }
}
