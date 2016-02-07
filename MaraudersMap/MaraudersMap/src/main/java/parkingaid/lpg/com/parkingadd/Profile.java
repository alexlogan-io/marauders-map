package parkingaid.lpg.com.parkingadd;

import android.location.Location;

/**
 * Created by George on 18/03/2015.
 */
public class Profile {

    private String school;
    private String username;
    private Location latestLocation;

    public Profile()
    {
    }

    public Profile(String school, String username, Location latestLocation)
    {
        this.school = school;
        this.username = username;
        this.latestLocation = latestLocation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Location getLatestLocation() {
        return latestLocation;
    }

    public void setLatestLocation(Location latestLocation) {
        this.latestLocation = latestLocation;
    }
}

