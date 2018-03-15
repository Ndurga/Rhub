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

public class ForgotPassword extends Fragment {

        private EditText userPhone;
        private Button resetPasswordNext;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.forgot_password, container, false);

            userPhone = view.findViewById(R.id.user_phone);
            resetPasswordNext = view.findViewById(R.id.reset_next);

            resetPasswordNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String userPhoneNumber = userPhone.getText().toString();

                    if (!userPhoneNumber.isEmpty()){
                        FragmentManager mgr = getFragmentManager();
                        FragmentTransaction tx = mgr.beginTransaction();
                        OtpConfirmationScreen otpScreen = new OtpConfirmationScreen();
                        otpScreen.setUserPhone(userPhoneNumber);
                        tx.replace(R.id.frag, otpScreen);
                        tx.addToBackStack("");
                        tx.commit();
                    }
                    else{
                        userPhone.setHintTextColor(Color.RED);
                    }
                }
            });

            return view;
        }
}
