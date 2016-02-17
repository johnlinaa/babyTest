package www.khh.domain.babytest.apputility;

/**
 * Created by User on 2016/2/17.
 */
public class Baby {
    private  int babyID;
    private  int userID;
    private String babyName;

    public int getBabyID() {
        return babyID;
    }

    public void setBabyID(int babyID) {
        this.babyID = babyID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }
}
