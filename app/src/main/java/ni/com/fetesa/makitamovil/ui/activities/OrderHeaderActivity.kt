package ni.com.fetesa.makitamovil.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.OrderHeader

class OrderHeaderActivity : BaseActivity() {

    private lateinit var mOrder: OrderHeader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_header)

        if(intent.extras != null){
            mOrder = intent.getParcelableExtra("order")
        }

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
}
