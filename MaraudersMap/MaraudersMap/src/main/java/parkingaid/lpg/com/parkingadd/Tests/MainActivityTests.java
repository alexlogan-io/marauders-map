package parkingaid.lpg.com.parkingadd.Tests;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import java.util.ArrayList;

import parkingaid.lpg.com.parkingadd.HomePageFragment;
import parkingaid.lpg.com.parkingadd.MainActivity;
import parkingaid.lpg.com.parkingadd.R;
import parkingaid.lpg.com.parkingadd.StaffLecturer;

/**
 * Created by George on 27/04/2015.
 */
public class MainActivityTests
        extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mFirstTestActivity;
    private TextView mFirstTestText;

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testLocation() throws InterruptedException {
        assertNotNull(mFirstTestActivity.get_currentLocation());
    }

    public void testDirection() throws InterruptedException {
        ArrayList<StaffLecturer> a = new ArrayList<StaffLecturer>();


     a = mFirstTestActivity.getLecturers();

        for (int i = 0; i< a.size();i++)
        {
            assertEquals("CMP",a.get(i).getSchool());
        }


    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mFirstTestActivity = getActivity();
    }
}
