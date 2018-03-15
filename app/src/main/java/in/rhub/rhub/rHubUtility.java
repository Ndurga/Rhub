package in.rhub.rhub;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by Satish on 1/30/2018.
 */

public class rHubUtility extends AppCompatActivity {

    SharedPreferences sfe;
    SQLiteDatabase dBase;

    public SQLiteDatabase createDB()
    {
        if(dBase == null) {
            dBase = openOrCreateDatabase("RHub", MODE_PRIVATE, null);
            dBase.execSQL("create table  if not exists tCustomer(id_phone number,name varchar(100),email varchar(100),password varchar(100))");
        }
        return dBase;
    }

    public Cursor getLoginData()
    {
        if(dBase == null) {
            dBase = createDB();
        }

        Cursor cursor = dBase.query("tCustomer",null,
                null,
                null,
                null,null,
                null);

        return cursor;
    }

    public SharedPreferences.Editor setLoginInfo(String Phone,String Pwd)
    {
        final String MyPREFERENCES = "userLogin" ;

        sfe = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sfe.edit();
        editor.putString(Phone,Pwd);
        editor.commit();

        return editor;
    }




}
