package stazer.user.androidstazerserviceapp.HeplerClasses.homeScreenAds;

public class homeScreenAdsRecyclerViewHelperClass {
    int adImage;
    String adTitle,adRate1,adRate2;

    public homeScreenAdsRecyclerViewHelperClass(int adImage, String adTitle, String adRate1, String adRate2) {
        this.adImage = adImage;
        this.adTitle = adTitle;
        this.adRate1 = adRate1;
        this.adRate2 = adRate2;
    }

    public int getAdImage() {
        return adImage;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public String getAdRate1() {
        return adRate1;
    }

    public String getAdRate2() {
        return adRate2;
    }
}
