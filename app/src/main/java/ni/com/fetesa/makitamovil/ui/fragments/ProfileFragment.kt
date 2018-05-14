package ni.com.fetesa.makitamovil.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaProfile
import ni.com.fetesa.makitamovil.model.UserFidelizationPoints
import ni.com.fetesa.makitamovil.presenter.IProfileFragmentPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.ProfileFragmentPresenterImpl
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
class ProfileFragment : Fragment(), IProfileFragmentView {



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
        mCheckPointsButton.setOnClickListener {
            mProfileFragmentPresenter.getMakitaPoints()
        }
        mEditFab.setOnClickListener {
            activity.toast("Esta es una prueba del fab")
        }


        mProfileFragmentPresenter = ProfileFragmentPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                activity.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        activity.title = getString(R.string.title_fragment_profile)

        mProfileFragmentPresenter.getProfile()
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

    private fun loadData(data: MakitaProfile){
        mTxtUsername.setText(data.username)
        mTxtIdentificationNumber.setText(data.identificationNumber)
        mTxtFirstName.setText(data.firstName)
        mTxtMiddleName.setText(data.middleName)
        mTxtLastName.setText(data.lastName)
        mTxtSecondLastName.setText(data.secondLastName)
        if(data.birthDate != null){
            DateUtil.parseDateStringToFormat(data.birthDate,"yyyy-mm-dd","dd-mm-yy")
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


    companion object {
        fun newInstance(listener: ProfileFragmentListener): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.mListener = listener
            return fragment
        }
    }
}// Required empty public constructor
