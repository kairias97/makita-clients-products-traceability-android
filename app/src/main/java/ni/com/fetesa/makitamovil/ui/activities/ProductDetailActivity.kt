package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.text.format.DateUtils
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.model.Warranty
import ni.com.fetesa.makitamovil.presenter.IProductDetailPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.ProductDetailPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IProductDetailView
import ni.com.fetesa.makitamovil.utils.DateUtil
import ni.com.fetesa.makitamovil.utils.toast
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ProductDetailActivity : BaseActivity(), IProductDetailView {

    private lateinit var mTxtDescription: EditText
    private lateinit var mTxtSerial: EditText
    private lateinit var mTxtPrice: EditText
    private lateinit var mTxtMeasureUnit: EditText
    private lateinit var mTxtPartNumber: EditText
    private lateinit var mTxtStore: EditText
    private lateinit var mTxtType: EditText
    private lateinit var mTxtPhysicalInvoice: EditText
    private lateinit var mTxtDate: EditText
    private lateinit var mTxtWeight: EditText

    private lateinit var mBtnSerial: Button
    private lateinit var mBtnWarranty: Button
    private lateinit var mBtnSave: Button
    private lateinit var mProduct: Product

    private lateinit var mProductDetailPresenter: IProductDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        mTxtDescription = findViewById(R.id.edit_text_productDetail_description)
        mTxtSerial = findViewById(R.id.edit_text_productDetail_serialNumber)
        mTxtPrice = findViewById(R.id.edit_text_productDetail_price)
        mTxtMeasureUnit = findViewById(R.id.edit_text_productDetail_measureUnit)
        mTxtPartNumber = findViewById(R.id.edit_text_productDetail_partNumber)
        mTxtStore = findViewById(R.id.edit_text_productDetail_fetesaStore)
        mTxtType = findViewById(R.id.edit_text_productDetail_type)
        mTxtPhysicalInvoice = findViewById(R.id.edit_text_productDetail_physicalInvoiceNumber)
        mTxtDate = findViewById(R.id.edit_text_productDetail_date)
        mTxtWeight = findViewById(R.id.edit_text_productDetail_kgWeight)

        mBtnSerial = findViewById(R.id.btn_product_serial)
        mBtnWarranty = findViewById(R.id.btn_product_warranty)
        mBtnSave = findViewById(R.id.btn_product_serial_save)

        mProductDetailPresenter = ProductDetailPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        if(intent.extras != null){
            mProduct = intent.extras.getParcelable("product")
        }

        if(mProduct.serialNumber == null || mProduct.serialNumber == ""){
            mBtnSerial.visibility = View.VISIBLE
            mBtnWarranty.visibility = View.GONE
            mBtnSave.visibility = View.GONE
        }
        else{
            mTxtSerial.setText(mProduct.serialNumber)
            mBtnSerial.visibility = View.GONE
            mBtnWarranty.visibility = View.VISIBLE
            mBtnSave.visibility = View.GONE
        }
        mTxtDescription.setText(mProduct.description)
        val price = "CS ${mProduct.purchasePrice}"
        mTxtPrice.setText(price)
        mTxtMeasureUnit.setText(mProduct.measureUnit)
        mTxtPartNumber.setText(mProduct.partNumber)
        mTxtStore.setText(mProduct.purchaseStore)
        mTxtType.setText(mProduct.purchaseType)
        mTxtPhysicalInvoice.setText(mProduct.physicalInvoice)
        mTxtDate.setText(DateUtil.parseDateStringToFormat(mProduct.purchaseDate,"yyyy-mm-dd","dd/mm/yy"))
        val weight = "${mProduct.kgWeight} KG"
        mTxtWeight.setText(weight)

        lockUnlockFields(false)

        mBtnSerial.setOnClickListener {
            lockUnlockFields(true)
            mBtnSerial.visibility = View.GONE
            mBtnSave.visibility = View.VISIBLE
            mTxtSerial.requestFocus()
        }
        mBtnSave.setOnClickListener {
            if(mTxtSerial.text.toString() != ""){
                mProductDetailPresenter.saveSerial(mTxtSerial.text.toString(), mProduct.id)
            }
            else{
                showCustomMessage(getString(R.string.error_register_empty_field))
            }
        }
        mBtnWarranty.setOnClickListener {
            mProductDetailPresenter.getWarranty(mProduct.id)
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        supportActionBar!!.title = "Detalle de producto"
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }
    private fun returnTop(){
        //NavUtils.navigateUpFromSameTask(this)
        finish()
    }

    private fun lockUnlockFields(value: Boolean){
        mTxtDescription.isEnabled = false
        mTxtSerial.isEnabled = value
        mTxtPrice.isEnabled = false
        mTxtMeasureUnit.isEnabled = false
        mTxtPartNumber.isEnabled = false
        mTxtStore.isEnabled = false
        mTxtType.isEnabled = false
        mTxtPhysicalInvoice.isEnabled = false
        mTxtDate.isEnabled = false
        mTxtWeight.isEnabled = false
    }

    override fun showSavingSerialProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_product_saving_warranty))
    }

    override fun hideSavingSerialProgress() {
        this.hideProgressDialog()
    }

    override fun showGettingWarrantyProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_getting_warranty))
    }

    override fun hideGettingWarrantyProgress() {
        this.hideProgressDialog()
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun savingSerialSuccessful() {
        mBtnWarranty.visibility = View.VISIBLE
        mBtnSave.visibility = View.GONE
        lockUnlockFields(false)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showWarranty(warranty: Warranty) {
        val sdf = SimpleDateFormat("dd/mm/yy")
        val date1 = DateUtil.parseDateStringToFormat(warranty.endDate,"yyyy-mm-dd","dd/mm/yy")

        this.toast("La garantía finaliza el día: $date1")
    }
}
