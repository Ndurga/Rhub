package in.rhub.rhub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by durga on 15-02-2018.
 */

public class OtpConfirmationScreen extends Fragment {

    private String userPhone;
    private Button resetPasswordNext;
    private EditText passwordNext;

    void setUserPhone(String phone){
        userPhone = phone;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otp_page, container, false);

        passwordNext = view.findViewById(R.id.otp);
        resetPasswordNext = view.findViewById(R.id.otp_next);

        resetPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //confirm otp match here
                String otpTxt =  passwordNext.getText().toString();

                if (!otpTxt.isEmpty()){
                    FragmentManager mgr = getFragmentManager();
                    FragmentTransaction tx = mgr.beginTransaction();
                    ResetPassword resetPasswordScreen = new ResetPassword();
                    resetPasswordScreen.setUserPhone(userPhone);
                    tx.replace(R.id.frag, resetPasswordScreen);
                    tx.addToBackStack("");
                    tx.commit();
                }
                else{
                    passwordNext.setHintTextColor(Color.RED);
                }
            }
        });

        return view;
    }

}
