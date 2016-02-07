package parkingaid.lpg.com.parkingadd;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines a Staff/Lecturer object
 */
public class StaffLecturer extends Profile implements Parcelable {

    private String type;
    private String snippit;
    private String password;
    boolean available;

    /**
     * Constructor for a Staff/Lecturer
     * @param username username of lecturer
     * @param school school of lecturer
     * @param latestLocation lecturer's latest location
     * @param type define whether they're staff of lecturer
     * @param snippit used in the google map
     * @param available availability
     * @param password lecturer's password
     */
    public StaffLecturer(String username,String school,Location latestLocation,String type, String snippit, Boolean available, String password) {
        super(school,username,latestLocation);
        this.type = type;
        this.snippit = snippit;
        this.available = available;
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSnippit() {
        return snippit;
    }

    public void setSnippit(String snippit) {
        this.snippit = snippit;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
