package in.rhub.rhub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
 * Created by durga on 16-02-2018.
 */

public class CustomerInformation extends Fragment {

    EditText customerName;
    EditText customerPhone;
    EditText interstedCity;
    EditText interstedArea;
    EditText customerEmail;
    EditText customerStatus;
    TextView errorMessage;
    Button editCustomerInfo;

    String cName;
    String cPhone;
    String cEmail;
    String cityInterest;
    String areaInterest;
    String cStatus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_information, container, false);

        customerName = view.findViewById(R.id.name_id);
        customerPhone = view.findViewById(R.id.phone_id);
        interstedCity = view.findViewById(R.id.intersted_city);
        interstedArea = view.findViewById(R.id.intersted_area);
        customerEmail = view.findViewById(R.id.email_id);
        customerStatus = view.findViewById(R.id.status_id);
        errorMessage = view.findViewById(R.id.error_msg);
        editCustomerInfo = view.findViewById(R.id.edit_info);

        setCustomerData();

        editCustomerInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        }
        );

        return view;
    }

    public void setCustomerName(String name){
        cName = name;
    }

    public void setCustomerPhone(String phone){
        cPhone = phone;
    }

    public void setInterstedCity(String city){
        cityInterest = city;
    }

    public void setInterstedArea(String area){
        areaInterest = area;
    }

    public void setCustomerEmail(String email){
        cEmail = email;
    }

    public void setCustomerStatus(String status){
        cStatus = status;
    }

    public void setCustomerData(){
        if (customerName != null){
            customerName.setText(cName);
        }
        if (customerPhone != null){
            customerPhone.setText(cPhone);
        }
        if (interstedCity != null){
            interstedCity.setText(cityInterest);
        }
        if (interstedArea != null){
            interstedArea.setText(areaInterest);
        }
        if (customerEmail != null){
            customerEmail.setText(cEmail);
        }
        if (customerStatus != null){
            customerStatus.setText(cStatus);
        }
    }

    void updateInfo(){
        String name = customerName.getText().toString();
        String phone = customerPhone.getText().toString();
        String email = customerEmail.getText().toString();
        String cityInterest = interstedCity.getText().toString();
        String areaInterest = interstedArea.getText().toString();
        String status = customerStatus.getText().toString();
        String userPhone = userPhoneNumberGlobal;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RhubAPIS api = retrofit.create(RhubAPIS.class);
        Call<Response> response = api.updateCustomerInformation(name, email, phone, userPhone, cityInterest, areaInterest);

        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response res = response.body();
                if(1 == res.getStatus()){
                    errorMessage.setText("");
                    FragmentManager mgr = getFragmentManager();
                    FragmentTransaction tx = mgr.beginTransaction();
                    tx.replace(R.id.frag, new UserProfilePage());
                    tx.addToBackStack("");
                    tx.commit();
                }
                else {
                    errorMessage.setText("Error : " + res.getMsg());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }
}
