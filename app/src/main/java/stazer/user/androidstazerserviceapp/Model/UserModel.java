package stazer.user.androidstazerserviceapp.Model;

public class UserModel {
    private String firstName,lastName,PhoneNumber,flatNo,Area,Landmark;
    private String id;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String phoneNumber, String flatNo, String area, String landmark, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.flatNo = flatNo;
        this.Area = area;
        this.Landmark = landmark;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
