package parkingaid.lpg.com.parkingadd.framework;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexlogan on 14/04/15.
 */
public class SchoolOfInterest implements Parcelable{

    String schoolOfInterest;
    String[] schools;


    public SchoolOfInterest(String schoolOfInterest, String[] schools) {
        this.schoolOfInterest = schoolOfInterest;
        this.schools = schools;
    }

    public String getSchoolOfInterest() {
        return schoolOfInterest;
    }

    public void setSchoolOfInterest(String schoolOfInterest) {
        this.schoolOfInterest = schoolOfInterest;
    }

    public String[] getSchools() {
        return schools;
    }

    public void setSchools(String[] schools) {
        this.schools = schools;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
