package droiddevelopers254.droidconke.models;

public class TravelInfoModel {
    private String shuttleInfo;
    private String publicTransportationInfo;
    private String carpoolingParkingInfo;
    private String bikingInfo;
    private String rideSharingInfo;

    public String getShuttleInfo() {
        return shuttleInfo;
    }

    public void setShuttleInfo(String shuttleInfo) {
        this.shuttleInfo = shuttleInfo;
    }

    public String getPublicTransportationInfo() {
        return publicTransportationInfo;
    }

    public void setPublicTransportationInfo(String publicTransportationInfo) {
        this.publicTransportationInfo = publicTransportationInfo;
    }

    public String getCarpoolingParkingInfo() {
        return carpoolingParkingInfo;
    }

    public void setCarpoolingParkingInfo(String carpoolingParkingInfo) {
        this.carpoolingParkingInfo = carpoolingParkingInfo;
    }

    public String getBikingInfo() {
        return bikingInfo;
    }

    public void setBikingInfo(String bikingInfo) {
        this.bikingInfo = bikingInfo;
    }

    public String getRideSharingInfo() {
        return rideSharingInfo;
    }

    public void setRideSharingInfo(String rideSharingInfo) {
        this.rideSharingInfo = rideSharingInfo;
    }

    @Override
    public String toString() {
        return "TravelInfoModel{" +
                "shuttleInfo='" + shuttleInfo + '\'' +
                ", publicTransportationInfo='" + publicTransportationInfo + '\'' +
                ", carpoolingParkingInfo='" + carpoolingParkingInfo + '\'' +
                '}';
    }

    public TravelInfoModel(String shuttleInfo, String publicTransportationInfo, String carpoolingParkingInfo, String rideSharingInfo) {
        this.shuttleInfo = shuttleInfo;
        this.publicTransportationInfo = publicTransportationInfo;
        this.carpoolingParkingInfo = carpoolingParkingInfo;
        this.rideSharingInfo = rideSharingInfo;
    }

    public TravelInfoModel() {
    }
}
