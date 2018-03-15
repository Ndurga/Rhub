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
 * Created by durga on 12-02-2018.
 */

public class RegisterFragment extends Fragment {
    private EditText userName;
    private EditText userEmail;
    private EditText userPhone;
    private EditText userPassword;
    private EditText userRePassword;
    private TextView messageView;
    private Button registerUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);

        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);
        userPhone = view.findViewById(R.id.user_phone);
        userPassword = view.findViewById(R.id.user_password);
        userRePassword = view.findViewById(R.id.user_re_password);
        registerUser = view.findViewById(R.id.user_register);
        messageView = view.findViewById(R.id.message_id);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        return view;
    }

    void setMessage(String message){
        if (messageView != null){
            messageView.setText(message);
        }
    }

    public void registerUser(){

        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String phone = userPhone.getText().toString();
        String pass = userPassword.getText().toString();
        String rePass = userRePassword.getText().toString();

        if (pass.equals(rePass) && !pass.isEmpty()){
           if( !phone.isEmpty() && !pass.isEmpty() ){
               Retrofit retrofit = new Retrofit.Builder()
                       .baseUrl(baseUrl)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();
               RhubAPIS api = retrofit.create(RhubAPIS.class);
               Call<Response> response = api.registerUser(name, email, phone, pass);

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
                           messageView.setText(res.getMsg());
                       }
                   }

                   @Override
                   public void onFailure(Call<Response> call, Throwable t){
                   }
               });
           }else{
               if (pass.isEmpty()){
                   userPassword.setHintTextColor(Color.RED);
               }
               if (rePass.isEmpty()){
                   userRePassword.setHintTextColor(Color.RED);
               }
               if (phone.isEmpty()){
                   userPhone.setHintTextColor(Color.RED);
               }
           }
        }
        else if(phone.isEmpty() || pass.isEmpty() || rePass.isEmpty())
        {
            if (phone.isEmpty()){
                setMessage("");
                userPhone.setHintTextColor(Color.RED);
            }
            if (pass.isEmpty()){
                setMessage("");
                userPassword.setHintTextColor(Color.RED);
            }
            if (rePass.isEmpty()){
                setMessage("");
                userRePassword.setHintTextColor(Color.RED);
            }
        }
        else if (!pass.equals(rePass)){
            setMessage("Error : Passwords must be equal");
        }
    }
}
