package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.ui.fragments.ProductsFragment
import ni.com.fetesa.makitamovil.ui.fragments.ProfileFragment
import ni.com.fetesa.makitamovil.utils.toast

class MainActivity : BaseActivity() {

    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.navigation)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragmentTransaction(ProductsFragment.newInstance())

        navigationView.selectedItemId = R.id.navigation_home
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment = Fragment()
        val view = this
        when (item.itemId) {
            R.id.navigation_home -> {
                fragment = ProductsFragment.newInstance()
                //message.setText(R.string.title_home)
            }
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
            }
            R.id.navigation_notifications -> {
                //message.setText(R.string.title_notifications)
            }
            R.id.navigation_profile -> {
                fragment = ProfileFragment.newInstance(object: ProfileFragment.ProfileFragmentListener{
                    override fun onProfileLoading() {
                        view.showProgressDialog(getString(R.string.progress_dialog_profile_loading))
                    }

                    override fun onProfileLoadingError() {
                        view.toast(getString(R.string.generic_500_error))
                    }

                    override fun onProfileLoadingFinished() {
                        view.hideProgressDialog()
                    }

                    override fun onProfileLoadingCustomError(error: String) {
                        view.toast(error)
                    }
                })
            }
        }
        fragmentTransaction(fragment)
        true
    }

    private fun fragmentTransaction(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_fragments, fragment)
                .commit()
    }
}
