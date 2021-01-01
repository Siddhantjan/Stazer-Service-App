package stazer.user.androidstazerserviceapp.Model;

public class CoursesBookingDetailsModel {
    private String ClassCategory;
    private String studentName;
    private String Subject;
    private String id;
    private String ServiceAddress;

    public CoursesBookingDetailsModel() {
    }

    public CoursesBookingDetailsModel(String classCategory, String studentName, String subject, String id, String serviceAddress) {
        this.ClassCategory = classCategory;
        this.studentName = studentName;
        this.Subject = subject;
        this.id = id;
        this.ServiceAddress = serviceAddress;
    }

    public String getClassCategory() {
        return ClassCategory;
    }

    public void setClassCategory(String classCategory) {
        ClassCategory = classCategory;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceAddress() {
        return ServiceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        ServiceAddress = serviceAddress;
    }
}
