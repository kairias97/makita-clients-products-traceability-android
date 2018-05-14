package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.Invoice
import ni.com.fetesa.makitamovil.model.UserFidelizationPoints
import ni.com.fetesa.makitamovil.ui.fragments.InvoicesFragment
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
        fragmentTransaction(ProductsFragment.newInstance(object: ProductsFragment.OnFragmentInteractionListener {
            override fun onAddProductsSelected() {
                navigateToBindInvoice()
            }

        }))

        navigationView.selectedItemId = R.id.navigation_products
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment = Fragment()
        val activity = this
        when (item.itemId) {
            R.id.navigation_products -> {
                fragment = ProductsFragment.newInstance(object: ProductsFragment.OnFragmentInteractionListener {
                    override fun onAddProductsSelected() {
                        navigateToBindInvoice()
                    }

                })
                //message.setText(R.string.title_home)
            }
            R.id.navigation_invoices -> {
                fragment = InvoicesFragment.newInstance(object: InvoicesFragment.OnFragmentInteractionListener {
                    override fun onAddInvoicesSelected() {
                        navigateToBindInvoice()
                    }

                })
            }
            R.id.navigation_workshop_orders -> {
                //message.setText(R.string.title_notifications)
            }
            R.id.navigation_profile -> {
                fragment = ProfileFragment.newInstance(object: ProfileFragment.ProfileFragmentListener{


                    override fun onMakitaPointsLoading() {
                        activity.showProgressDialog(getString(R.string.progress_dialog_fetching_points))
                    }

                    override fun onMakitaPointsFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onMakitaPointsChecked(points: UserFidelizationPoints) {
                        activity.toast(String.format(getString(R.string.makita_points_message), points.total,
                                points.expirationDate.substring(0,10)), 5000)
                    }

                    override fun onProfileLoading() {
                        activity.showProgressDialog(getString(R.string.progress_dialog_profile_loading))
                    }

                    override fun onProfileLoadingError() {
                        activity.toast(getString(R.string.generic_500_error))
                    }

                    override fun onProfileLoadingFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onCustomMessage(error: String) {
                        activity.toast(error)
                    }
                })
            }
        }
        fragmentTransaction(fragment)
        true
    }

    private fun navigateToBindInvoice() {
        val intent = Intent(this, InvoiceBindingActivity::class.java)
        startActivity(intent)
    }

    private fun fragmentTransaction(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_fragments, fragment)
                .commit()
    }
}
