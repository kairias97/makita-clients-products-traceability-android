package ni.com.fetesa.makitamovil.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import ni.com.fetesa.makitamovil.R

class LoginActivity : BaseActivity() {

    private lateinit var mRegisterTextView: TextView
    private lateinit var mForgotPasswordTextView: TextView
    private lateinit var mButtonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()

        mRegisterTextView = findViewById<TextView>(R.id.textView_register)
        mForgotPasswordTextView = findViewById<TextView>(R.id.textView_forgot_password)
        mButtonLogin = findViewById<Button>(R.id.button_login)

        mRegisterTextView.setOnClickListener {
            val intentTarget = Intent(this, RegisterActivity::class.java)
            startActivity(intentTarget)
        }
        mForgotPasswordTextView.setOnClickListener {
            val intentTarget = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intentTarget)
        }
        mButtonLogin.setOnClickListener {

        }
    }
}
