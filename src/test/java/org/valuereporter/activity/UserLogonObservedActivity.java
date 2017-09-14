package org.valuereporter.activity;

/**
 * Created by baardl on 04.03.16.
 */
public class UserLogonObservedActivity extends org.valuereporter.agent.activity.ObservedActivity {
    public static final String USER_LOGON_ACTIVITY = "userLogon";
    private static final String USER_LOGON_USERID_DB_KEY = "userid";

    public UserLogonObservedActivity(String userid) {
        super(USER_LOGON_ACTIVITY, System.currentTimeMillis());
        put("userid", userid);
    }
}
