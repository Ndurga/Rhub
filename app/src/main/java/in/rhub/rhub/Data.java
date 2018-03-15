
package in.rhub.rhub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Reffered By")
    @Expose
    private String refferedBy;
    @SerializedName("City Of Interest")
    @Expose
    private String cityOfInterest;
    @SerializedName("Area Of Interest")
    @Expose
    private String areaOfInterest;
    @SerializedName("Created Date")
    @Expose
    private String createdDate;
    @SerializedName("Last Contacted")
    @Expose
    private Object lastContacted;
    @SerializedName("Response")
    @Expose
    private Object response;
    @SerializedName("Available")
    @Expose
    private Object available;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public String getCityOfInterest() {
        return cityOfInterest;
    }

    public void setCityOfInterest(String cityOfInterest) {
        this.cityOfInterest = cityOfInterest;
    }

    public String getAreaOfInterest() {
        return areaOfInterest;
    }

    public void setAreaOfInterest(String areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getLastContacted() {
        return lastContacted;
    }

    public void setLastContacted(Object lastContacted) {
        this.lastContacted = lastContacted;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Object getAvailable() {
        return available;
    }

    public void setAvailable(Object available) {
        this.available = available;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
