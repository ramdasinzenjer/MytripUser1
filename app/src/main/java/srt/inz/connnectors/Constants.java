package srt.inz.connnectors;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";
    String REGISTER_MESSAGE="Register in...";

    //Urls
 
    String REGISTER_URL="https://mytripuser.000webhostapp.com/user_api/mRegisterUser.php?";
    String LOGIN_URL="https://mytripuser.000webhostapp.com/user_api/mLoginUser.php?";
    String REQUESTLOC_URL="https://mytripuser.000webhostapp.com/user_api/mLocServices.php?";
    String REQUESTPROFILE_URL="https://mytripuser.000webhostapp.com/user_api/mProfilefetch.php?";
    String REQUESTTRANSPORT_URL="https://mytripuser.000webhostapp.com//user_api/mRequest.php?";
    String PROFILEUP_URL="https://mytripuser.000webhostapp.com/user_api/mProupdate.php?";
  //  String REQSTATUS_URL="http://192.168.1.6/smartmeter/driver_api/mReqstatUpdate.php?";
    
    String PROFILEFETCH_URL="https://mytripuser.000webhostapp.com/user_api/mTrip_profetch.php?";
    String MESSAGERECIEVE_URL="https://mytripuser.000webhostapp.com/mTrip_msgret.php?";
    String MESSAGESEND_URL="https://mytripuser.000webhostapp.com/mTrip/mTrip_msgsent.php?";
    
    //Details

    String ID="id";
    String NAME="Name";
    String PASSWORD="Password";
    String EMAIL="Email";
    String LOGINSTATUS="LoginStatus";
    String USERID="id";

    //SharedPreference

    String PREFERENCE_PARENT="Parent_Pref";

   
}
