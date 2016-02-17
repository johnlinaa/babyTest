package www.khh.domain.babytest.apputility;

import java.io.Serializable;

/**
 * Created by User on 2016/2/15.
 */
public class TakeGroup implements Serializable {
   //照育討論區的新增標題
    private String UserID;
    private String UserName;

    private String MemoTitle;
    private String MemoID;

    //照育討論區的新增回復
    private String ContentID;
    private String Content_User;
    private String Content_MemoID;
    private String ContentRecord;
    private String ContentTime;

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String contentID) {
        ContentID = contentID;
    }

    public String getContent_User() {
        return Content_User;
    }

    public void setContent_User(String content_User) {
        Content_User = content_User;
    }

    public String getContent_MemoID() {
        return Content_MemoID;
    }

    public void setContent_MemoID(String content_MemoID) {
        Content_MemoID = content_MemoID;
    }

    public String getContentRecord() {
        return ContentRecord;
    }

    public void setContentRecord(String contentRecord) {
        ContentRecord = contentRecord;
    }

    public String getContentTime() {
        return ContentTime;
    }

    public void setContentTime(String contentTime) {
        ContentTime = contentTime;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }



    public String getMemoTitle() {
        return MemoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        MemoTitle = memoTitle;
    }

    public String getMemoID() {
        return MemoID;
    }

    public void setMemoID(String memoID) {
        MemoID = memoID;
    }
}
