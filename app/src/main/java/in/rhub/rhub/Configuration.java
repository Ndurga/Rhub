package in.rhub.rhub;

import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by durga on 13-02-2018.
 */

public class Configuration {
    public static SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "myPreferences" ;
    public static final String userName = "userNameKey";
    public static final String passWord = "passWordKey";

    public static final String baseUrl = "http://rhub.freshersschool.com/";
    public static String userPhoneNumberGlobal = "";
    public static TextView navigationUserNameGlobal;
    public static MenuItem menuItemLoginGlobal;

}
