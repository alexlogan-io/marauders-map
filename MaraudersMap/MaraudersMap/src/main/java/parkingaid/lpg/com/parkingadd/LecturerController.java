package parkingaid.lpg.com.parkingadd;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;

import parkingaid.lpg.com.parkingadd.framework.MapMarker;

/**
 * Controller that mimicks real world use where many lecturers are signed up
 */
public class LecturerController {

    ArrayList<StaffLecturer> allLecturers;

    /**
     * constructor for Lecturer Controller
     */
    public LecturerController() {
        allLecturers = new ArrayList<>();
    }

    /**
     * Creates a set of lecturer objects with details
     */
    public void setUpLecturers(){
        Location l = new Location("a"), l1 = new Location("a"), l2 = new Location("b"), l3 = new Location("c"), l4 = new Location("d"),
                l5 = new Location("e"),l6 = new Location("e"),l7 = new Location("e"),l8 = new Location("e"),l9 = new Location("e");
        l.setLatitude(52.632348f);
        l.setLongitude(1.277855f);
        l1.setLatitude(52.632711f);
        l1.setLongitude(1.277367f);
        l2.setLatitude(52.632629f);
        l2.setLongitude(1.276860f);
        l3.setLatitude(52.631921f);
        l3.setLongitude(1.276571f);
        l4.setLatitude(52.633312f);
        l4.setLongitude(1.276062f);
        l5.setLatitude(52.622641f);
        l5.setLongitude(1.240135f);
        l6.setLatitude(52.621762f);
        l6.setLongitude(1.235694f);
        l7.setLatitude(52.621892f);
        l7.setLongitude(1.241401f);
        l8.setLatitude(52.621085f);
        l8.setLongitude(1.241906f);
        l9.setLatitude(52.621085f);
        l9.setLongitude(1.241037f);
        StaffLecturer coxy = new StaffLecturer("Prof. Stephen Cox", "CMP",l,"Lecturer","Lecturer in Computer Science",true,"password");
        allLecturers.add(coxy);
        StaffLecturer tony = new StaffLecturer("Dr Tony Bagnall", "CMP",l1,"Lecturer","Lecturer in Computer Science",true,"password");
        allLecturers.add(tony);
        StaffLecturer piere = new StaffLecturer("Pierre Chardaire","CMP",l2,"Staff","I am here to help. Please talk to me.",true,"password");
        allLecturers.add(piere);
        StaffLecturer joost = new StaffLecturer("Dr Joost Noppen","CMP",l3,"Lecturer","I am a lecturer in computer science ",true,"password");
        allLecturers.add(joost);
        StaffLecturer hill = new StaffLecturer("Prof. Clint Hill", "BIO",l4,"Lecturer","I am a lecturer in biology",true,"password");
        allLecturers.add(hill);
        StaffLecturer bebe = new StaffLecturer("Bebe", "BIO",l5,"Staff","I am here to help. Please talk to me.",true,"password");
        allLecturers.add(bebe);
        StaffLecturer alex = new StaffLecturer("Alex Logan", "LPG", l6,"Staff","I am here to help. Please talk to me.",true,"password");
        allLecturers.add(alex);
        StaffLecturer g = new StaffLecturer("Prof. George Mabey", "LPG", l7,"Lecturer","I am a lecturer in Lollipop Guild Science",true,"password");
        allLecturers.add(g);
        StaffLecturer luke = new StaffLecturer("Prof. Luke Sapiets", "LPG", l8,"Lecturer","I am a lecturer in Lollipop Guild Science",true,"password");
        allLecturers.add(luke);
        StaffLecturer jon = new StaffLecturer("Jonathon Westwood","LPG",l9,"Staff","I am here to help. Please talk to me.",true,"password");
        allLecturers.add(jon);
    }

    /**
     * Gets an ArrayList containing MapMarker objects for lecturers
     * @param context context of activity
     * @param lecturers lecturers to get MapMarkers from
     * @return
     */
    public ArrayList<MapMarker> getMapMarkers(Context context, ArrayList<StaffLecturer> lecturers){
        ArrayList<MapMarker> mapMarkers = new ArrayList<>();
        for (StaffLecturer lecturer : lecturers){
            MapMarker mapMarker = new MapMarker(context,lecturer.getLatestLocation(),lecturer.getUsername(),lecturer.getType(),lecturer.getSnippit());
            mapMarkers.add(mapMarker);
        }
        return mapMarkers;
    }

    /**
     * Gets an ArrayList containing lecturers marked as belonging to a specific school
     * @param school school of lecturers
     * @return ArrayList of lecturers
     */
    public ArrayList<StaffLecturer> getLecturers(String school){
        ArrayList<StaffLecturer> lecturers = new ArrayList<>();
        for (StaffLecturer lec : allLecturers){
            if(lec.getSchool().equals(school)){
                lecturers.add(lec);
            }
        }
        return lecturers;
    }
}
