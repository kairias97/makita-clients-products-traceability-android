package ni.com.fetesa.makitamovil.ui.fragments

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
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
