package stazer.user.androidstazerserviceapp.Model;

public class RateModel {
    private String SparePartName;
    private String PartCost;
    private String LabourCost;
    private String id;

    public RateModel() {
    }

    public RateModel(String sparePartName, String partCost, String labourCost, String id) {
        SparePartName = sparePartName;
        PartCost = partCost;
        LabourCost = labourCost;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSparePartName() {
        return SparePartName;
    }

    public void setSparePartName(String sparePartName) {
        SparePartName = sparePartName;
    }

    public String getPartCost() {
        return PartCost;
    }

    public void setPartCost(String partCost) {
        PartCost = partCost;
    }

    public String getLabourCost() {
        return LabourCost;
    }

    public void setLabourCost(String labourCost) {
        LabourCost = labourCost;
    }
}
