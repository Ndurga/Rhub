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
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.rhub.rhub.Configuration.baseUrl;

/**
 * Created by durga on 15-02-2018.
 */

public class ResetPassword extends Fragment {

    private EditText password;
    private EditText rePassword;
    private String userPhone;
    private Button resetPassword;
    private TextView messageView;

    void setUserPhone(String phone){
        userPhone = phone;
    }

    void setMessage(String message){
        if (messageView != null){
            messageView.setText(message);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_password, container, false);

        resetPassword = view.findViewById(R.id.reset);
        password = view.findViewById(R.id.reset_pwd);
        rePassword = view.findViewById(R.id.reset_pwd_2);
        messageView = view.findViewById(R.id.message_id);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1 = password.getText().toString();
                String password2 = rePassword.getText().toString();
                resetPassword(password1, password2);
            }
        });

        return view;
    }

    public void resetPassword(String pass1, String pass2){
        if(pass1.equals(pass2) && !pass1.isEmpty()){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RhubAPIS api = retrofit.create(RhubAPIS.class);
            Call<Response> response = api.resetPassword(userPhone, pass1);

            response.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response res = response.body();
                    if(1 == res.getStatus()){
                        FragmentManager fMgr = getFragmentManager();
                        FragmentTransaction tx = fMgr.beginTransaction();
                        tx.replace(R.id.frag, new in.rhub.rhub.LoginFragment());
                        tx.commit();
                    }
                    else {
                        setMessage("Error : " + res.getMsg());
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {

                }
            });
        }else{

            if (pass1.isEmpty() && pass2.isEmpty()){
                setMessage("");
                password.setHintTextColor(Color.RED);
                rePassword.setHintTextColor(Color.RED);
            }
            else if (pass1.isEmpty()){
                setMessage("");
                password.setHintTextColor(Color.RED);
            }
            else if (pass2.isEmpty()){
                setMessage("");
                rePassword.setHintTextColor(Color.RED);
            }
            else if (!pass1.equals(pass2)){
                setMessage("Error : Passwords must match");
            }
        }
    }


}



