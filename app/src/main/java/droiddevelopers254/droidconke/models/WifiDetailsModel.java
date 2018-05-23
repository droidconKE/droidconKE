package droiddevelopers254.droidconke.models;

public class WifiDetailsModel {
    String wifiSsid;
    String wifiPassword;

    public WifiDetailsModel() {
    }

    public String getWifiSsid() {
        return wifiSsid;
    }

    public void setWifiSsid(String wifiSsid) {
        this.wifiSsid = wifiSsid;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public WifiDetailsModel(String wifiSsid, String wifiPassword) {
        this.wifiSsid = wifiSsid;
        this.wifiPassword = wifiPassword;
    }
}
