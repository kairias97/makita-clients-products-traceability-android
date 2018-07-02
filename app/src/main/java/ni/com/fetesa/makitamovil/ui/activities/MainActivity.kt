package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.model.Invoice
import ni.com.fetesa.makitamovil.model.UserFidelizationPoints
import ni.com.fetesa.makitamovil.presenter.IMainPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.MainPresenterImpl
import ni.com.fetesa.makitamovil.services.RegistrationIntentService
import ni.com.fetesa.makitamovil.ui.fragments.InvoicesFragment
import ni.com.fetesa.makitamovil.ui.fragments.ProductsFragment
import ni.com.fetesa.makitamovil.ui.fragments.ProfileFragment
import ni.com.fetesa.makitamovil.ui.views.IMainView
import ni.com.fetesa.makitamovil.utils.toast

class MainActivity : BaseActivity(), IMainView {

    private lateinit var navigationView: BottomNavigationView
    private lateinit var mMainPresenter: IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.navigation)

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mMainPresenter = MainPresenterImpl(this, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        fragmentTransaction(ProductsFragment.newInstance(object: ProductsFragment.OnFragmentInteractionListener {
            override fun onAddProductsSelected() {
                navigateToBindInvoice()
            }
            override fun onLoadingProducts() {
                showProgressDialog(getString(R.string.progress_dialog_products_loading))
            }

            override fun onLoadingProductsFinished() {
                hideProgressDialog()
            }

            override fun onCustomMessage(msg: String) {
                toast(msg)
            }

            override fun onError() {
                toast(R.string.generic_500_error)
            }

        }))

        navigationView.selectedItemId = R.id.navigation_products
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_password -> {
                this.prepareToNavigateToPasswordUpdate()
            }
            R.id.action_logout -> {
                this.confirmLogout()
            }
        }
        return true
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

                    override fun onLoadingProducts() {
                        activity.showProgressDialog(getString(R.string.progress_dialog_products_loading))
                    }

                    override fun onLoadingProductsFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onCustomMessage(msg: String) {
                        activity.toast(msg)
                    }

                    override fun onError() {
                        activity.toast(R.string.generic_500_error)
                    }

                })
                //message.setText(R.string.title_home)
            }
            R.id.navigation_invoices -> {
                fragment = InvoicesFragment.newInstance(object: InvoicesFragment.OnFragmentInteractionListener {
                    override fun onAddInvoicesSelected() {
                        navigateToBindInvoice()
                    }

                    override fun onLoadingInvoices() {
                        activity.showProgressDialog(getString(R.string.progress_dialog_invoice_loading))
                    }

                    override fun onLoadingInvoicesFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onCustomMessage(msg: String) {
                        activity.toast(msg)
                    }

                    override fun onError() {
                        activity.toast(R.string.generic_500_error)
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

                    override fun onSavingProfileLoading() {
                        activity.showProgressDialog(getString(R.string.progress_dialog_profile_saving))
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

    override fun prepareToNavigateToPasswordUpdate() {
        val intent = Intent(this, PasswordUpdateActivity::class.java)
        startActivity(intent)
    }

    override fun confirmLogout() {
        this.showConfirmDialog(titleResId = R.string.title_logout,
                iconResId = R.drawable.baseline_warning_black_48,
                messageResId = R.string.message_logout,
                positiveBtnResId = R.string.logout_accept,
                negativeBtnResId = R.string.logout_cancel,
                positiveListener = android.content.DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    this.executeLogout()
                },
                negativeListener = android.content.DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
    }

    override fun executeLogout() {
        mMainPresenter.executeLogout()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showLoggingOutProgress() {
        this.showProgressDialog(getString(R.string.message_logging_out))
    }

    override fun hideLoggingOutProgress() {
        this.hideProgressDialog()
    }
}
