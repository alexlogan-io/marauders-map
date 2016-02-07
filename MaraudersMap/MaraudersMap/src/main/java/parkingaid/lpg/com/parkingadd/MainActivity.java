package parkingaid.lpg.com.parkingadd;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.google.gson.Gson;

import java.util.ArrayList;

import parkingaid.lpg.com.parkingadd.framework.AppPagerAdapter;
import parkingaid.lpg.com.parkingadd.framework.MapMarker;
import parkingaid.lpg.com.parkingadd.framework.SchoolOfInterest;
import parkingaid.lpg.com.parkingadd.framework.SetUpTabView;

/**
 * Controls all of the fragments and the data passed to and from each of them, also acts as a listener
 * for app specific information
 */
public class MainActivity extends android.support.v4.app.FragmentActivity implements ActionBar.TabListener,HomePageFragment.OnSchoolSelectedListener, android.location.LocationListener {

    private AppPagerAdapter mAppPagerAdapter;
    private ViewPager mViewPager;
    private Location _currentLocation;
    private SchoolOfInterest schoolOfInterest;
    private SharedPreferences prefs;
    private Gson gSon = new Gson();
    private LecturerController lecturerController = new LecturerController();
    private SetUpTabView setUpTabView;
    private ArrayList<StaffLecturer> lecturers;
    private ArrayList<MapMarker> mapMarkers = new ArrayList<>();

    /**
     * Deals with first time launching, ensuring no duplicate information. Sets up the location
     * manager for location tracking and attaches fragments to the action bar as tabs
     * @param savedInstanceState the Bundle of arguments required
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            String[] schools = new String[]{
                    "CMP", "BIO", "LPG"
            };

            String def = "CMP";
            schoolOfInterest = new SchoolOfInterest(def, schools);
        }else{
            schoolOfInterest = savedInstanceState.getParcelable("school");
        }
        setUpTabView = new SetUpTabView();
        lecturerController.setUpLecturers();
        lecturers = lecturerController.getLecturers(schoolOfInterest.getSchoolOfInterest());
        mapMarkers = lecturerController.getMapMarkers(this,lecturers);


        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,
                1, this);

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1, this);

        _currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(_currentLocation == null){
            _currentLocation = new Location("X");
            _currentLocation.setLatitude(0);
            _currentLocation.setLongitude(0);
        }

        mAppPagerAdapter = new AppPagerAdapter(getSupportFragmentManager());
        mAppPagerAdapter.addFragment(HomePageFragment.newInstance(schoolOfInterest,lecturers), "Home");
        mAppPagerAdapter.addFragment(MapFragment.newInstance(mapMarkers), "Map");
        mAppPagerAdapter.addFragment(DirectionViewFragment.newInstance(_currentLocation,lecturers.get(0)), "Direction");
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        ActionBar actionBar = getActionBar();

        actionBar = setUpTabView.newActionBar(actionBar);

        mViewPager = setUpTabView.newViewPager(view,R.id.pager,mAppPagerAdapter,actionBar);

        setUpTabView.addTabsToActionBar(mAppPagerAdapter,actionBar,this);
        }


    /**
     * Required method which informs the action bar when the tab is no longer selected
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Required method which informs the action bar which tab is selected
     * @param tab the current tab(fragment)
     * @param fragmentTransaction
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Required method which tells the action bar when the tab is reselected
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onSchoolSelected(int position) {
        String[] schools = schoolOfInterest.getSchools();
        String school = schools[position];
        this.schoolOfInterest = new SchoolOfInterest(school,schools);
        this.lecturers = lecturerController.getLecturers(school);
        this.mapMarkers = lecturerController.getMapMarkers(this,lecturers);

        String h = makeFragmentName(R.id.pager,0);
        HomePageFragment homePageFragment = (HomePageFragment) getSupportFragmentManager().findFragmentByTag(h);
        homePageFragment.update(schoolOfInterest,lecturers);

        String m = makeFragmentName(R.id.pager,1);
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(m);
        mapFragment.update(mapMarkers);

        String d = makeFragmentName(R.id.pager,2);
        DirectionViewFragment directionViewFragment = (DirectionViewFragment) getSupportFragmentManager().findFragmentByTag(d);
        directionViewFragment.updateTargetLocation(lecturers.get(0).getLatestLocation());
    }

    /**
     * Used to give a fragment a tag
     * @param viewPagerId the current view
     * @param index the position of the fragment
     * @return the tag
     */
    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    /**
     * A method that notifies and updates all fragments with the latest location information
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(Location location) {
        String d = makeFragmentName(R.id.pager,2);
        DirectionViewFragment directionViewFragment = (DirectionViewFragment) getSupportFragmentManager().findFragmentByTag(d);
        if (directionViewFragment != null) {
            directionViewFragment.updateCurrentLocation(location);
        }
        _currentLocation = location;
    }

    /**
     * Required override method for class of type LocationListener
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Required override method class of type LocationListener
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Required override method class of type LocationListener
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("school",schoolOfInterest);
        super.onSaveInstanceState(outState);
    }

    /**
     * @return the current location
     */
    public Location get_currentLocation() {
        return _currentLocation;
    }

    /**
     *
     * @return the lecturers
     */
    public ArrayList<StaffLecturer> getLecturers() {
        return lecturers;
    }
}
