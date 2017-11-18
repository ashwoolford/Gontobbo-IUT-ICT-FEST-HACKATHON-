package bd.com.thedebuggers.gontobbo;

/**
 * Created by ash on 10/14/2017.
 */

public class ClaimData {

    String date , description , name , subject , vehicle_id , url;

    public ClaimData() {
    }

    public ClaimData(String date, String description, String name, String subject, String vehicle_id , String url) {
        this.date = date;
        this.description = description;
        this.name = name;
        this.subject = subject;
        this.vehicle_id = vehicle_id;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
