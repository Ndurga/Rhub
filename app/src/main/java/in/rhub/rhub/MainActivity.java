package in.rhub.rhub;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText m_etName,m_etPwd,m_etRePwd,m_etEmail, m_etPhone, m_etUNameLogin, m_etPwdLogin;
    SQLiteDatabase dBase;

    public void callFragment(Fragment fmt){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction tx = fManager.beginTransaction();
        tx.replace(R.id.frag1,fmt);
        tx.addToBackStack("");
        tx.commit();
    }

    public void createDB() {
        dBase=openOrCreateDatabase("RHub",MODE_PRIVATE,null);
        dBase.execSQL("create table  if not exists tCustomer(id_phone number,name varchar(100),email varchar(100),password varchar(100), repassword varchar(100))");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callFragment(new LoginFragment());

    }


    public void registerUser(View v){
        callFragment(new RegisterUserFragment());
    }

    public void submitRegisterData(View v){

        m_etName  = findViewById(R.id.etName);
        m_etEmail = findViewById(R.id.etEmail);
        m_etPhone = findViewById(R.id.etPhone);
        m_etPwd   = findViewById(R.id.etPwd);
        m_etRePwd = findViewById(R.id.etRePwd);

        if((!m_etName.getText().toString().isEmpty())  &&
                (!m_etEmail.getText().toString().isEmpty()) &&
                (!m_etPhone.getText().toString().isEmpty()) &&
                (!m_etPwd.getText().toString().isEmpty()) &&
                (!m_etRePwd.getText().toString().isEmpty())) {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(m_etEmail.getText().toString()).matches()){
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }
            else if(!android.util.Patterns.PHONE.matcher(m_etPhone.getText().toString()).matches()){
                Toast.makeText(this,"Enter Valid Phone", Toast.LENGTH_SHORT).show();
            }
            else if(!m_etPwd.getText().toString().equals(m_etRePwd.getText().toString())){
                Toast.makeText(this,"Passwords are Mismatch", Toast.LENGTH_SHORT).show();
            }
            else{
                //DB Logic needs to be implement
                createDB();
                ContentValues cv=new ContentValues();
                cv.put("id_phone",Integer.parseInt(m_etPhone.getText().toString()));
                cv.put("name",m_etName.getText().toString());
                cv.put("email",m_etEmail.getText().toString());
                cv.put("password",m_etPwd.getText().toString());
                cv.put("repassword",m_etRePwd.getText().toString());
                long status=dBase.insert("tCustomer",null,cv);
                if(status!=-1){
                    Toast.makeText(this,"Record Inserted Successfully",
                            Toast.LENGTH_LONG).show();
                    m_etPhone.setText("");
                    m_etName.setText("");
                    m_etEmail.setText("");
                    m_etPwd.setText("");
                    m_etRePwd.setText("");
                }else{
                    Toast.makeText(this,"Record Insertion Failed",
                            Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    public void loginUser(View v){
        //Toast.makeText(this,"Login clicked..", Toast.LENGTH_SHORT).show();
        //DB Logic needs to be implement
        /*String table, String[] columns, String selection, String[] selectionArgs,
         String groupBy, String having, String orderBy */
        m_etUNameLogin = findViewById(R.id.etUName);
        m_etPwdLogin   = findViewById(R.id.etPwdLogin);

      /*  Cursor c= dBase.query("tCustomer",null,
                "id_phone=? and password=?",
                new String[]{m_etUNameLogin.getText().toString(),m_etPwdLogin.getText().toString()},
                null,null,
                null); */
        dBase=openOrCreateDatabase("RHub",MODE_PRIVATE,null);
        Cursor c= dBase.query("tCustomer",null,
                null,
                null,
                null,null,
                null);
        boolean bSuccess = false;
        while (c.moveToNext()){
            String sUnameDB = c.getInt(0) + "";
            String sPwdDB   = c.getString(3);

            if(m_etUNameLogin.getText().toString().equals(sUnameDB) && m_etPwdLogin.getText().toString().equals(sPwdDB)){
                Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show();
                bSuccess = true;
                break;
            }
        }

        if(!bSuccess){
            Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendOTP(View v){
        Toast.makeText(this,"Send OTP clicked..", Toast.LENGTH_SHORT).show();
        //DB Logic needs to be implement

    }

    public void submitOTP(View v){
        Toast.makeText(this,"submit OTP clicked..", Toast.LENGTH_SHORT).show();
        //DB Logic needs to be implement
        callFragment(new ResetPwdFragment());

    }

    public void resetPWD(View v){
        Toast.makeText(this,"reset PWD clicked..", Toast.LENGTH_SHORT).show();
        //DB Logic needs to be implement

    }

    public void forgotPwdUser(View v){
        Toast.makeText(this,"ForGot PWD clicked..", Toast.LENGTH_SHORT).show();
        callFragment(new ForgotPwdFragment());
    }

}
