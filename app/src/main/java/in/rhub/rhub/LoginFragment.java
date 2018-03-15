package in.rhub.rhub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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

import static in.rhub.rhub.Configuration.MyPREFERENCES;
import static in.rhub.rhub.Configuration.baseUrl;
import static in.rhub.rhub.Configuration.menuItemLoginGlobal;
import static in.rhub.rhub.Configuration.navigationUserNameGlobal;
import static in.rhub.rhub.Configuration.passWord;
import static in.rhub.rhub.Configuration.sharedpreferences;
import static in.rhub.rhub.Configuration.userName;
import static in.rhub.rhub.Configuration.userPhoneNumberGlobal;

/**
 * Created by durga on 11-02-2018.
 */

public class LoginFragment extends Fragment {

    private EditText loginTxt;
    private EditText passwordTxt;
    private Button loginBtn;
    private Button registerBtn;
    private TextView messageView;
    private Button forgotPasswordBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        loginTxt = view.findViewById(R.id.user_loin_id);
        passwordTxt = view.findViewById(R.id.user_password_id);
        loginBtn = view.findViewById(R.id.login);
        registerBtn = view.findViewById(R.id.register);
        forgotPasswordBtn = view.findViewById(R.id.forgot_passwd);
        messageView = view.findViewById(R.id.message_id);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View v) {
                                                     forgotPassword();
                                                 }
                                             }

        );

        return view;
    }

    public void logIn(){
        if (loginTxt != null && passwordTxt != null){
            final String user = loginTxt.getText().toString();
            String pass = passwordTxt.getText().toString();

            if (!user.isEmpty() && !pass.isEmpty()){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RhubAPIS api = retrofit.create(RhubAPIS.class);
                Call<Response> response = api.authenticateUser(user, pass);

                response.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response res = response.body();
                        if(1 == res.getStatus()){
                            userPhoneNumberGlobal = user;
                            navigationUserNameGlobal.setText(userPhoneNumberGlobal);
                            if(menuItemLoginGlobal != null){
                                menuItemLoginGlobal.setTitle("Logout");
                            }

                            sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(userName, loginTxt.getText().toString());
                            editor.putString(passWord, passwordTxt.getText().toString());
                            editor.commit();

                            //#satish - insert this data into shared ptrefferences

                            FragmentManager fMgr = getFragmentManager();
                            FragmentTransaction tx = fMgr.beginTransaction();
                            tx.replace(R.id.frag, new HomeFragment());
                            tx.commit();
                        }
                        else {
                            setMessage("Login failed : " + res.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                    }
                });
            }
            else{
                if (user.isEmpty()){
                    setMessage("");
                    loginTxt.setHintTextColor(Color.RED);
                }
                if (pass.isEmpty()){
                    setMessage("");
                    passwordTxt.setHintTextColor(Color.RED);
                }
            }
        }
    }

    public void register(){
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction tx = mgr.beginTransaction();
        tx.replace(R.id.frag, new RegisterFragment());
        tx.addToBackStack("");
        tx.commit();
    }

    public void forgotPassword(){
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction tx = mgr.beginTransaction();
        tx.replace(R.id.frag, new ForgotPassword());
        tx.addToBackStack("");
        tx.commit();
    }

    public void setMessage(String message){
        if (messageView != null){
            messageView.setText(message);
        }
    }
}
