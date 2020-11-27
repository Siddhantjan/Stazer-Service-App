package stazer.user.androidstazerserviceapp.Model;

public class FetchBookingDetailsModel {
    private String serviceType;
    private String serviceCategory;
    private String Time;
    private String Date;
    private String Status;

    public FetchBookingDetailsModel() {
    }

    public FetchBookingDetailsModel(String serviceType, String serviceCategory, String time, String date, String status) {
        this.serviceType = serviceType;
        this.serviceCategory = serviceCategory;
        Time = time;
        Date = date;
        Status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
