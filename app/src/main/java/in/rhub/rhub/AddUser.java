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
import static in.rhub.rhub.Configuration.userPhoneNumberGlobal;


/**
 * Created by durga on 13-02-2018.
 */

public class AddUser extends Fragment {

        EditText customerName;
        EditText customerPhone;
        EditText customerInterstedinArea;
        TextView messageView;
        Button addCustomer;
        String   userPhone;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.add_user, container, false);
            customerName = view.findViewById(R.id.customer_name);
            customerPhone = view.findViewById(R.id.customer_phone);
            customerInterstedinArea = view.findViewById(R.id.customer_intersted_area);
            addCustomer = view.findViewById(R.id.add_user);
            messageView = view.findViewById(R.id.message_id);

            addCustomer.setOnClickListener(new View.OnClickListener(){
                   @Override
                   public void onClick(View v) {
                        addUser();
                   }
               }
            );

            return view;
        }

        public void addUser(){

            if (customerName == null || customerPhone == null || customerInterstedinArea == null){
                messageView.setText("Error : something went wrong, contact app admin");
                return;
            }

            final String cName = customerName.getText().toString();
            String cEmail = "";
            String cPhone = customerPhone.getText().toString().trim();
            String cInterestCity = "Hyderabad";
            String cInterstedArea = customerInterstedinArea.getText().toString();
            String userPhone = userPhoneNumberGlobal;
            userPhone = userPhone.trim();

            if (!cPhone.isEmpty() && !userPhone.isEmpty()){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RhubAPIS api = retrofit.create(RhubAPIS.class);
                Call<Response> response = api.registerCustomer(cName, cEmail, cPhone, userPhone, cInterestCity, cInterstedArea);

                response.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response res = response.body();
                        if(1 == res.getStatus()){
                            FragmentManager fMgr = getFragmentManager();
                            FragmentTransaction tx = fMgr.beginTransaction();
                            tx.replace(R.id.frag, new UserProfilePage());
                            tx.commit();
                        }
                        else {
                            messageView.setText(res.getMsg());
                        }
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }
            else{
               if (cPhone.isEmpty()){
                   messageView.setText("");
                    customerPhone.setHintTextColor(Color.RED);
                }
            }
        }
}

