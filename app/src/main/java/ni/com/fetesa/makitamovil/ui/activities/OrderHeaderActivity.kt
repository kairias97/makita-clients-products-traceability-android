package ni.com.fetesa.makitamovil.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.OrderDetail
import ni.com.fetesa.makitamovil.model.OrderHeader
import ni.com.fetesa.makitamovil.ui.views.IOrderHeaderView
import ni.com.fetesa.makitamovil.utils.DateUtil
import ni.com.fetesa.makitamovil.utils.QUOTED_ORDER_ID

class OrderHeaderActivity : BaseActivity(), IOrderHeaderView {

    private lateinit var mOrder: OrderHeader
    private lateinit var mDetails: List<OrderDetail>

    private lateinit var mTextViewOrderID: TextView
    private lateinit var mTextViewDistibutionCenter: TextView
    private lateinit var mTextViewClientNumber: TextView
    private lateinit var mTextViewClientName: TextView
    private lateinit var mTextViewEquipmentCode: TextView
    private lateinit var mTextViewService: TextView
    private lateinit var mTextViewEquipmentDescription: TextView
    private lateinit var mTextViewWarrantyDocument: TextView
    private lateinit var mTextViewEquipmentSerialNumber: TextView
    private lateinit var mTextViewReceptionObservation: TextView
    private lateinit var mTextViewDeliveryObservation: TextView
    private lateinit var mTextViewPhoneNumber: TextView
    private lateinit var mTextViewReceivedAccesories: TextView
    private lateinit var mTextViewCreatedDate: TextView
    private lateinit var mTextViewDescription: TextView
    private lateinit var mTextViewOrderStatus: TextView
    private lateinit var mTextViewOrderStatusDescription:TextView

    private lateinit var mButtonNavToDetails: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_header)

        if(intent.extras != null){
            mOrder = intent.getParcelableExtra("order")
            mDetails = intent.getParcelableArrayListExtra("details")
        }

        mTextViewOrderID = findViewById(R.id.textView_label_orderID)
        mTextViewDistibutionCenter = findViewById(R.id.textView_label_distributionCenter)
        mTextViewClientNumber = findViewById(R.id.textView_label_clientNumber)
        mTextViewClientName = findViewById(R.id.textView_label_clientName)
        mTextViewEquipmentCode = findViewById(R.id.textView_label_equipmentCode)
        mTextViewService = findViewById(R.id.textView_label_service)
        mTextViewEquipmentDescription = findViewById(R.id.textView_label_equipmentDescription)
        mTextViewWarrantyDocument = findViewById(R.id.textView_label_warrantyDocument)
        mTextViewEquipmentSerialNumber = findViewById(R.id.textView_label_equipmentSerialNumber)
        mTextViewReceptionObservation = findViewById(R.id.textView_label_receptionObservation)
        mTextViewDeliveryObservation = findViewById(R.id.textView_label_deliveryObservation)
        mTextViewPhoneNumber = findViewById(R.id.textView_label_phoneNumber)
        mTextViewReceivedAccesories = findViewById(R.id.textView_label_receivedAccessories)
        mTextViewCreatedDate = findViewById(R.id.textView_label_createdDate)
        mTextViewDescription = findViewById(R.id.textView_label_description)
        mTextViewOrderStatus = findViewById(R.id.textView_label_orderStatus)
        mTextViewOrderStatusDescription = findViewById(R.id.textView_label_orderStatusDescription)

        mButtonNavToDetails = findViewById(R.id.button_show_details)
        mButtonNavToDetails.visibility = View.GONE

        mButtonNavToDetails.setOnClickListener {
            navigateToOrderDetails()
        }

        bindData()

        supportActionBar!!.title = "Detalles de orden"

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
        finish()
    }

    override fun onBackPressed() {
        returnTop()
    }

    private fun bindData(){
        mTextViewOrderID.text = mOrder.fetesaOrderID.toString()
        mTextViewDistibutionCenter.text = mOrder.distributionCenter
        mTextViewClientNumber.text = mOrder.clientNumber.toString()
        mTextViewClientName.text = mOrder.clientName
        mTextViewEquipmentCode.text = mOrder.equipmentCode
        mTextViewService.text = mOrder.service
        mTextViewEquipmentDescription.text = mOrder.equipmentDescription
        mTextViewWarrantyDocument.text = mOrder.warrantyDocument
        mTextViewEquipmentSerialNumber.text = mOrder.equipmentSerialNumber
        if(mOrder.receptionObservation != ""){
            mTextViewReceptionObservation.text = mOrder.receptionObservation
        }
        else{
            mTextViewReceptionObservation.text = "Ninguna"
        }
        if(mOrder.deliveryObservation != ""){
            mTextViewDeliveryObservation.text = mOrder.deliveryObservation
        }
        else{
            mTextViewDeliveryObservation.text = "Ninguna"
        }
        mTextViewPhoneNumber.text = mOrder.phoneNumber
        if(mOrder.receivedAccessories != ""){
            mTextViewReceivedAccesories.text = mOrder.receivedAccessories
        }
        else{
            mTextViewReceivedAccesories.text = "Ninguno"
        }
        mTextViewCreatedDate.text = DateUtil.parseDateStringToFormat(mOrder.createdDate.substring(0,10),"yyyy-mm-dd","dd/mm/yy")
        mTextViewDescription.text = mOrder.description
        mTextViewOrderStatus.text = mOrder.orderStatus.name
        mTextViewOrderStatusDescription.text = mOrder.orderStatus.description

        if(mOrder.orderStatus.NAFCode == QUOTED_ORDER_ID){
            mButtonNavToDetails.visibility = View.VISIBLE
        }
    }

    override fun navigateToOrderDetails() {
        val intent = Intent(this,OrderDetailActivity::class.java)
        intent.putExtra("order", mOrder)
        intent.putParcelableArrayListExtra("details", ArrayList(mDetails))
        startActivity(intent)
    }
}
