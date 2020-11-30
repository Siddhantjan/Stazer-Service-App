package stazer.user.androidstazerserviceapp.Model;

public class ServiceCompletedModel {
    private String ServiceName;
    private String Amount;

    public ServiceCompletedModel() {
    }

    public ServiceCompletedModel(String serviceName, String amount) {
        ServiceName = serviceName;
        Amount = amount;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
