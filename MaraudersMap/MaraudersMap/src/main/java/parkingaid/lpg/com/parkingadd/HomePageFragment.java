package parkingaid.lpg.com.parkingadd;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import parkingaid.lpg.com.parkingadd.framework.SchoolOfInterest;

/**
 * Fragment that deals with displaying app specific user information
 */
public class HomePageFragment extends android.support.v4.app.Fragment {

    private OnSchoolSelectedListener schoolSelectedListener;
    private SchoolOfInterest schoolOfInterest;
    private Spinner schoolSpinner;
    private TextView noOfLecturers;
    private ArrayList<StaffLecturer> lecturers;
    private static final String schoolKey = "SCHOOL_OF_INTEREST";
    private static final String lecturerKey = "LECTURERS";
    private static HomePageFragment fragment;

    /**
     * Creates a new instance of HomePageFragment, stores any input information and
     * acts as a Constructor
     * @param schoolOfInterest the current SchoolOfInterest object
     * @param lecturers ArrayList object containing the current lecturers
     * @return a HomePageFragment
     */
    public static HomePageFragment newInstance(SchoolOfInterest schoolOfInterest, ArrayList<StaffLecturer> lecturers) {

        if (fragment == null) {
            fragment = new HomePageFragment();
            Bundle schoolInfo = new Bundle();
            schoolInfo.putParcelable(schoolKey, schoolOfInterest);
            schoolInfo.putParcelableArrayList(lecturerKey, lecturers);
            fragment.setArguments(schoolInfo);
        }
        return fragment;
    }

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the HomepageFragment and ensures that the savedInstanceState is never null when
     * it's created
     * @param savedInstanceState the Bundle of arguments required
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            schoolOfInterest = getArguments().getParcelable(schoolKey);
            lecturers = getArguments().getParcelableArrayList(lecturerKey);
        }
        else {
            schoolOfInterest = savedInstanceState.getParcelable(schoolKey);
            lecturers = savedInstanceState.getParcelableArrayList(lecturerKey);
        }
    }

    /**
     * Creates the view containing the user's information and sets up listeners to deal with
     * button clicks, list clicks or form entries
     * @param inflater for inflating the views in the fragment
     * @param container contains the views
     * @param savedInstanceState the Bundle of arguments required
     * @return the updated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        schoolSpinner = (Spinner) view.findViewById(R.id.schoolSpinner);
        String[] schools = schoolOfInterest.getSchools();
        ArrayAdapter<String> schoolAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,schools);
        schoolSpinner.setAdapter(schoolAdapter);
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoolSelectedListener.onSchoolSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schoolSpinner.setSelection(getIndex(schoolSpinner, schoolOfInterest.getSchoolOfInterest()));
        noOfLecturers = (TextView) view.findViewById(R.id.noOfLecturers);
        Integer l = lecturers.size();
        noOfLecturers.setText(l.toString());
        return view;
    }

    /**
     * Method to attach the fragment to the MainActivity
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            schoolSelectedListener = (OnSchoolSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Required method to remove fragment from the MainActivity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        schoolSelectedListener = null;
    }

    /**
     * Interface to implement the OnSchoolSelectedListner in the MainActivity
     */
    public interface OnSchoolSelectedListener {
        public void onSchoolSelected(int position);
    }

    /**
     * Gets the index of an item in a spinner
     * @param spinner spinner to get item from
     * @param myString string to search for
     * @return index of item in spinner
     */
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    /**
     * Update the key class objects
     * @param schoolOfInterest the current SchoolOfInterest object
     * @param lecturers the current Lecturers object
     */
    public void update(SchoolOfInterest schoolOfInterest, ArrayList<StaffLecturer> lecturers){
        this.schoolOfInterest = schoolOfInterest;
        this.lecturers = lecturers;
        Integer l = lecturers.size();
        noOfLecturers.setText("Number of Lecturers/Staff Active: "+ l.toString());
    }

    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable(schoolKey, schoolOfInterest);
        outState.putParcelableArrayList(lecturerKey, lecturers);
        super.onSaveInstanceState(outState);
    }

    /**
     * Stops fragment orientation changing
     * @param isVisibleToUser boolean for whether an orientation is visible
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}

