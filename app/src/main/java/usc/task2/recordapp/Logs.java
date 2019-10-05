package usc.task2.recordapp;

public class Logs {
    String title;
    String place;
    String details;
    String logDt;
    String longitude;
    String latitude;
    String imagePath;

    public Logs(){

    }

    public Logs(String title, String place, String details, String logDt, String longitude, String latitude, String imagePath) {
        this.title = title;
        this.place = place;
        this.details = details;
        this.logDt = logDt;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLogDt() {
        return logDt;
    }

    public void setLogDt(String logDt) {
        this.logDt = logDt;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
