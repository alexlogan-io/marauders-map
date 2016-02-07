package parkingaid.lpg.com.parkingadd;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import parkingaid.lpg.com.parkingadd.framework.MapController;
import parkingaid.lpg.com.parkingadd.framework.MapMarker;

/**
 * Fragment containing the Google Maps API
 */
public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private GoogleMap.OnMarkerClickListener clickListener;
    private MapView mMapView;
    private MapController mapController = new MapController();
    private Location currentMapLocation = new Location("X");
    private ArrayList<MapMarker> mapMarkers;
    private static final String argMap = "MAP_MARKERS";
    private static MapFragment fragment;

    /**
     * Creates a new instance of MapFragment using an ArrayList of MapMarkers
     * @param mapMarkers the current parking event
     * @return the MapFragment
     */
    public static MapFragment newInstance(ArrayList<MapMarker> mapMarkers) {
        if(fragment == null) {
            fragment = new MapFragment();
            Bundle markers = new Bundle();
            markers.putParcelableArrayList(argMap, mapMarkers);
            fragment.setArguments(markers);
        }
        return fragment;
    }

    /**
     * Creates the MapFragment and ensures that the savedInstanceState is never null when
     * it's created
     * @param savedInstanceState the Bundle of arguments required
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mapMarkers = getArguments().getParcelableArrayList(argMap);
        } else {
            mapMarkers = savedInstanceState.getParcelableArrayList(argMap);
            currentMapLocation = savedInstanceState.getParcelable("mapLocation");
        }
    }

    /**
     * Retrieves Markers to be added to the map via the map controller
     * @param inflater for inflating the views in the fragment
     * @param container contains the views
     * @param savedInstanceState the Bundle of arguments required
     * @return the updated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_map_page, container, false);

        mMapView = mapController.getMapView(getActivity(), view, R.id.location_map, savedInstanceState);
        mMap = mapController.getGoogleMap(mMapView);
        clickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String d = makeFragmentName(R.id.pager, 2);
                DirectionViewFragment directionViewFragment = (DirectionViewFragment) getActivity().getSupportFragmentManager().findFragmentByTag(d);
                Location location = new Location("X");
                location.setLatitude(marker.getPosition().latitude);
                location.setLongitude(marker.getPosition().longitude);
                currentMapLocation.setLongitude(marker.getPosition().longitude);
                currentMapLocation.setLatitude(marker.getPosition().latitude);
                directionViewFragment.updateTargetLocation(location);
                directionViewFragment.updateCurrentLecturer(marker.getTitle());
                return false;
            }
        };
        mMap.setOnMarkerClickListener(clickListener);
        //mapMarkers = lecturerController.getMapMarkers(getActivity(),lecturers);
        mapController.addMultipleMarkers(mMap, mapMarkers);
        mapController.setLocation(mMap);
        if (savedInstanceState == null) {
            mapController.centreCameraOnLocation(mMap, mapMarkers.get(0).getLocation());
        }else{
            mapController.centreCameraOnLocation(mMap, currentMapLocation);
        }

        return view;
    }

    /**
     * Update the MapMarkers ArrayList
     * @param mapMarkers
     */
    public void update(ArrayList<MapMarker> mapMarkers){
        mMap.clear();
        mapController.addMultipleMarkers(mMap,mapMarkers);
        mapController.centreCameraOnLocation(mMap,mapMarkers.get(0).getLocation());
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
     * Required method that handles onMarkerClicks
     * @param marker the marker clicked
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * Saves the latest information from the fragment
     * @param outState the Bundle containing the information
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(argMap, mapMarkers);
        outState.putParcelable("mapLocation",currentMapLocation);
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
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }
}
