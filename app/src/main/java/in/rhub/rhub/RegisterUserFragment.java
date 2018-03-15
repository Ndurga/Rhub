package in.rhub.rhub;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Satish on 1/20/2018.
 */

public class RegisterUserFragment extends Fragment {
    EditText et_Name,et_Email, et_Phone, et_Pwd, et_RePwd, et_OTP;
    Button btn_submit, btn_verify;
    SQLiteDatabase dBase;
    TextView tvStatustxt;
    String strStatus = null , strOTP = null;
    boolean bOTPVerified = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registeruser,container,false);

        et_Name     = v.findViewById(R.id.reg_etName);
        et_Email    = v.findViewById(R.id.reg_etEmail);
        et_Phone    = v.findViewById(R.id.reg_etPhone);
        et_Pwd      = v.findViewById(R.id.reg_etPwd);
        et_RePwd    = v.findViewById(R.id.reg_etRePwd);
        et_OTP      = v.findViewById(R.id.reg_etOTP);
        btn_verify  = v.findViewById(R.id.reg_bVerify);
        btn_submit  = v.findViewById(R.id.reg_bSubit);
        tvStatustxt = v.findViewById(R.id.reg_tvStatusText);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((et_Name.getText().toString().isEmpty())  ||
                        (et_Email.getText().toString().isEmpty()) ||
                        (et_Phone.getText().toString().isEmpty()) ||
                        (et_Pwd.getText().toString().isEmpty()) ||
                        (et_RePwd.getText().toString().isEmpty()))
                {
                    strStatus = "fill all mandatory(*) fields";
                    tvStatustxt.setText(strStatus);
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_Email.getText().toString()).matches()){
                    strStatus = "Please enter valid email";
                    tvStatustxt.setText(strStatus);
                }
                else if(!android.util.Patterns.PHONE.matcher(et_Phone.getText().toString()).matches()){
                    strStatus = "Please enter valid phone number";
                    tvStatustxt.setText(strStatus);
                }
                else if(!et_Pwd.getText().toString().equals(et_RePwd.getText().toString())){
                    strStatus = "Passwords are mismatch";
                    tvStatustxt.setText(strStatus);
                }
                else {
                    strOTP = "0445";
                    et_OTP.setEnabled(true);
                    et_OTP.setText(strOTP);
                    btn_verify.setEnabled(true);
                    strStatus = "";
                    tvStatustxt.setText(strStatus);

                }

            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_OTP.getText().toString().equals(strOTP))
                {
                    bOTPVerified = true;

                    if(bOTPVerified) {
                        ContentValues cv = new ContentValues();
                        cv.put("id_phone", Integer.parseInt(et_Phone.getText().toString()));
                        cv.put("name", et_Name.getText().toString());
                        cv.put("email", et_Email.getText().toString());
                        cv.put("password", et_Pwd.getText().toString());
                        dBase= getContext().openOrCreateDatabase("RHub", Context.MODE_PRIVATE,null);
                        dBase.execSQL("create table  if not exists tCustomer(id_phone number,name varchar(100),email varchar(100),password varchar(100))");
                        long status = dBase.insert("tCustomer", null, cv);

                        if(status!=-1){
                            et_Phone.setText("");
                            et_Name.setText("");
                            et_Email.setText("");
                            et_Pwd.setText("");
                            et_RePwd.setText("");
                            et_OTP.setEnabled(false);
                            btn_verify.setEnabled(false);
                            strStatus = "Successfully Registered";
                            tvStatustxt.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            tvStatustxt.setText(strStatus);

                        }else{
                            strStatus = "Record Insertion Failed..";
                            tvStatustxt.setText(strStatus);
                        }
                    }
                }
                else{
                    strStatus = "Invalid OTP..";
                    tvStatustxt.setText(strStatus);
                }
            }
        });

        return v;
    }
}

