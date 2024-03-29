package ni.com.fetesa.makitamovil.ui.fragments

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaProfile
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.UserFidelizationPoints
import ni.com.fetesa.makitamovil.presenter.IProfileFragmentPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.ProfileFragmentPresenterImpl
import ni.com.fetesa.makitamovil.ui.activities.LoginActivity
import ni.com.fetesa.makitamovil.ui.fragmentViews.IProfileFragmentView
import ni.com.fetesa.makitamovil.utils.DateUtil
import ni.com.fetesa.makitamovil.utils.toast

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), IProfileFragmentView, DatePickerFragment.DatePickerListener {

    private lateinit var mListener: ProfileFragmentListener

    private lateinit var mTxtUsername: EditText
    private lateinit var mTxtIdentificationNumber: EditText
    private lateinit var mTxtFirstName: EditText
    private lateinit var mTxtMiddleName: EditText
    private lateinit var mTxtLastName: EditText
    private lateinit var mTxtSecondLastName: EditText
    private lateinit var mTxtBirthDate: EditText
    private lateinit var mRadioBtnGroup: RadioGroup
    private lateinit var mRadioBtnMale: RadioButton
    private lateinit var mRadioBtnFemale: RadioButton
    private lateinit var mTxtFirstEmail: EditText
    private lateinit var mTxtSecondEmail: EditText
    private lateinit var mTxtCellphone: EditText
    private lateinit var mCheckPointsButton: Button
    private lateinit var mEditFab: FloatingActionButton
    private lateinit var mSaveFab: FloatingActionButton

    private lateinit var mLayoutFirstName: TextInputLayout
    private lateinit var mLayoutMiddleName: TextInputLayout
    private lateinit var mLayoutLastName: TextInputLayout
    private lateinit var mLayoutSecondLastName: TextInputLayout
    private lateinit var mLayoutBirthDate: TextInputLayout
    private lateinit var mLayoutFirstEmail: TextInputLayout
    private lateinit var mLayoutSecondEmail: TextInputLayout
    private lateinit var mLayoutCellPhone: TextInputLayout
    private var mFirstTime: Boolean = false

    private lateinit var mProfileFragmentPresenter: IProfileFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_profile, container, false)

        mTxtUsername = view.findViewById(R.id.edit_text_profile_username)
        mTxtIdentificationNumber = view.findViewById(R.id.edit_text_profile_identificationNumber)
        mTxtFirstName = view.findViewById(R.id.edit_text_profile_firstName)
        mTxtMiddleName = view.findViewById(R.id.edit_text_profile_secondName)
        mTxtLastName = view.findViewById(R.id.edit_text_profile_lastName)
        mTxtSecondLastName = view.findViewById(R.id.edit_text_profile_secondLastName)
        mTxtBirthDate = view.findViewById(R.id.edit_text_profile_birthDate)
        mRadioBtnGroup = view.findViewById(R.id.radio_btn_group_profile)
        mRadioBtnMale = view.findViewById(R.id.radio_btn_profile_male)
        mRadioBtnFemale = view.findViewById(R.id.radio_btn_profile_female)
        mTxtFirstEmail = view.findViewById(R.id.edit_text_profile_firstEmail)
        mTxtSecondEmail = view.findViewById(R.id.edit_text_profile_secondEmail)
        mTxtCellphone = view.findViewById(R.id.edit_text_profile_cellphone)
        mCheckPointsButton = view.findViewById(R.id.btn_check_points)
        mEditFab = view.findViewById(R.id.fab_edit_profile)
        mSaveFab = view.findViewById(R.id.fab_save_profile)

        mLayoutFirstName = view.findViewById(R.id.text_input_layout_profile_firstName)
        mLayoutMiddleName = view.findViewById(R.id.text_input_layout_profile_secondName)
        mLayoutLastName = view.findViewById(R.id.text_input_layout_profile_lastName)
        mLayoutSecondLastName = view.findViewById(R.id.text_input_layout_profile_secondLastName)
        mLayoutBirthDate = view.findViewById(R.id.text_input_layout_profile_birth_date)
        mLayoutFirstEmail = view.findViewById(R.id.text_input_layout_profile_firstEmail)
        mLayoutSecondEmail = view.findViewById(R.id.text_input_layout_profile_secondEmail)
        mLayoutCellPhone = view.findViewById(R.id.text_input_layout_profile_cellphone)

        mFirstTime = false

        mCheckPointsButton.setOnClickListener {
            mProfileFragmentPresenter.getMakitaPoints()
        }
        mEditFab.setOnClickListener {
            //activity.toast("Esta es una prueba del fab")
            mEditFab.visibility = View.GONE
            mSaveFab.visibility = View.VISIBLE
            lockUnlockInputs(true)
        }
        mSaveFab.setOnClickListener {
            if(validateText(mTxtFirstName) && validateText(mTxtMiddleName) && validateText(mTxtLastName)
            && validateText(mTxtSecondLastName) && validateText(mTxtBirthDate) && validateText(mTxtFirstEmail)
            && validateText(mTxtSecondEmail) && validateText(mTxtCellphone) && validateSex(mRadioBtnMale,mRadioBtnFemale)){
                val username = mTxtUsername.text.toString()
                val identificationNumber = mTxtIdentificationNumber.text.toString()
                val firstName = mTxtFirstName.text.toString()
                val middleName = mTxtMiddleName.text.toString()
                val lastName = mTxtLastName.text.toString()
                val secondLastName = mTxtSecondLastName.text.toString()
                val birthDate = DateUtil.parseDateStringToFormat(mTxtBirthDate.text.toString(),"dd/mm/yy","yyyy-mm-dd")
                val isMale = mRadioBtnMale.isChecked
                val firstEmail = mTxtFirstEmail.text.toString()
                val secondEmail = mTxtSecondEmail.text.toString()
                val cellPhone = mTxtCellphone.text.toString()
                val profile = MakitaProfile(username, MakitaUserSession.instance.makitaProfile.fetesaAlias, MakitaUserSession.instance.makitaProfile.clientFetesaCode,
                        MakitaUserSession.instance.makitaProfile.documentTypeID, identificationNumber, firstName, middleName, lastName, secondLastName, birthDate, isMale,
                        firstEmail, secondEmail, cellPhone,"")
                if(profile != MakitaUserSession.instance.makitaProfile){
                    mProfileFragmentPresenter.updateProfile(profile)
                }
                else{
                    savedProfileSuccessful()
                }
            }
        }

        mTxtFirstName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtFirstName)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtMiddleName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtMiddleName)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtLastName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtLastName)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtSecondLastName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtSecondLastName)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtBirthDate.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtBirthDate)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtFirstEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtFirstEmail)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtSecondEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtSecondEmail)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtCellphone.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtCellphone)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtBirthDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment.newInstance(this)
            datePickerFragment.show(fragmentManager.beginTransaction(), "datePicker")
        }


        mProfileFragmentPresenter = ProfileFragmentPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                activity.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        activity.title = getString(R.string.title_fragment_profile)

        mProfileFragmentPresenter.getProfile()
        lockUnlockInputs(false)
        return view
    }

    private fun validateSex(s1: RadioButton, s2: RadioButton): Boolean{
        if(s1.isChecked || s2.isChecked){
            return true
        }
        else{
            return false
        }
    }

    private fun validateText(s: EditText): Boolean{
        when(s.id){
            R.id.edit_text_profile_firstName -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutFirstName.error = null
                    return true
                }
                else{
                    mLayoutFirstName.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_secondName -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutMiddleName.error = null
                    return true
                }
                else{
                    mLayoutMiddleName.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_lastName -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutLastName.error = null
                    return true
                }
                else{
                    mLayoutLastName.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_secondLastName -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutSecondLastName.error = null
                    return true
                }
                else{
                    mLayoutSecondLastName.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_birthDate -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutBirthDate.error = null
                    return true
                }
                else{
                    mLayoutBirthDate.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_firstEmail -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutFirstEmail.error = null
                    return true
                }
                else{
                    mLayoutFirstEmail.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_secondEmail -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutSecondEmail.error = null
                    return true
                }
                else{
                    mLayoutSecondEmail.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_profile_cellphone -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutCellPhone.error = null
                    return true
                }
                else{
                    mLayoutCellPhone.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            else -> {
                return false
            }
        }
    }

    interface ProfileFragmentListener{
        fun onProfileLoading()
        fun onMakitaPointsLoading()
        fun onMakitaPointsFinished()
        fun onProfileLoadingFinished()
        fun onProfileLoadingError()
        fun onCustomMessage(message: String)
        fun onMakitaPointsChecked(points: UserFidelizationPoints)
        fun onSavingProfileLoading()
    }


    override fun showLoadingMakitaPoints() {
        mListener?.onMakitaPointsLoading()
    }

    override fun showMakitaPoints(points: UserFidelizationPoints) {
        mListener?.onMakitaPointsChecked(points)
    }
    override fun hideLoadingMakitaPoints() {
        mListener?.onMakitaPointsFinished()
    }
    override fun showLoadingProfile() {
        mListener.onProfileLoading()
    }

    override fun hideLoadingProfile() {
        mListener.onProfileLoadingFinished()
    }

    override fun showError() {
        mListener.onProfileLoadingError()
    }

    override fun showCustomMessage(message: String) {
        mListener.onCustomMessage(message)
    }

    override fun loadProfile(data: MakitaProfile) {
        loadData(data)
    }

    override fun savedProfileSuccessful() {
        mSaveFab.visibility = View.GONE
        mEditFab.visibility = View.VISIBLE
        lockUnlockInputs(false)
        mListener.onCustomMessage(getString(R.string.message_profile_updated))
    }

    override fun showSavingProfileProgress() {
        mListener.onSavingProfileLoading()
    }

    override fun hideSavingProfileProgress() {
        mListener.onProfileLoadingFinished()
    }

    override fun OnDateSelected(year: Int, month: Int, day: Int) {
        var birthDate = DateUtil.parseDateToFormat(year, month, day, "dd/MM/yyyy")
        mTxtBirthDate.setText(birthDate)
    }
    override fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun loadData(data: MakitaProfile){
        mTxtUsername.setText(data.username)
        mTxtIdentificationNumber.setText(data.identificationNumber)
        mTxtFirstName.setText(data.firstName)
        mTxtMiddleName.setText(data.middleName)
        mTxtLastName.setText(data.lastName)
        mTxtSecondLastName.setText(data.secondLastName)
        if(data.birthDate != null){
            mTxtBirthDate.setText(DateUtil.parseDateStringToFormat(data.birthDate,"yyyy-mm-dd","dd/mm/yy"))
        }
        if(data.isMale != null){
            if(data.isMale){
                mRadioBtnMale.isChecked = true
            }
            else{
                mRadioBtnFemale.isChecked = true
            }
        }
        mTxtFirstEmail.setText(data.primaryEmail)
        mTxtSecondEmail.setText(data.secondaryEmail)
        mTxtCellphone.setText(data.cellPhone)
    }

    private fun lockUnlockInputs(value: Boolean){
        mTxtUsername.isEnabled = false
        mTxtIdentificationNumber.isEnabled = false
        mTxtFirstName.isEnabled = value
        mTxtMiddleName.isEnabled = value
        mTxtLastName.isEnabled = value
        mTxtSecondLastName.isEnabled = value
        mTxtBirthDate.isEnabled = value
        mRadioBtnMale.isEnabled = value
        mRadioBtnFemale.isEnabled = value
        mTxtFirstEmail.isEnabled = value
        mTxtSecondEmail.isEnabled = value
        mTxtCellphone.isEnabled = value
    }

    companion object {
        fun newInstance(listener: ProfileFragmentListener): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.mListener = listener
            return fragment
        }
    }
}// Required empty public constructor
