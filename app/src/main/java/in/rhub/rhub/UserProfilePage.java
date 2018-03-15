package in.rhub.rhub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.rhub.rhub.Configuration.baseUrl;
import static in.rhub.rhub.Configuration.userPhoneNumberGlobal;

/**
 * Created by durga on 12-02-2018.
 */

public class UserProfilePage extends Fragment {

    Button  getCustomersList;
    Button  getCustomerInformation;
    Button  getCustomerStatus;
    EditText customerPhone;
    TextView resultView;
    TextView welcomeView;
    FloatingActionButton addUserBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile, container, false);

        getCustomersList = view.findViewById(R.id.customers_list);
        getCustomerInformation = view.findViewById(R.id.customer_info);
        getCustomerStatus = view.findViewById(R.id.customer_status);
        customerPhone = view.findViewById(R.id.customer_id);
        resultView = view.findViewById(R.id.result_id);
        welcomeView = view.findViewById(R.id.user_id);
        addUserBtn = (FloatingActionButton) view.findViewById(R.id.add_user);

        welcomeView.setText("Welcome " + userPhoneNumberGlobal);


        addUserBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  addingUser();
              }
          }
        );

        getCustomersList.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    showAllCustomers();
                }
            }
        );

        getCustomerInformation.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getCustomerInformation();
                }
            }
        );
        getCustomerStatus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getCustomerStatus();
                }
            }
        );

        return view;
    }

    public void addingUser(){
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction tx = mgr.beginTransaction();
        tx.replace(R.id.frag, new AddUser());
        tx.addToBackStack("");
        tx.commit();
    }

    public void getCustomerStatus(){

        String customerPhoneNumber = customerPhone.getText().toString();
        String userPhoneNumber = userPhoneNumberGlobal;

        if (!customerPhoneNumber.isEmpty())
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RhubAPIS api = retrofit.create(RhubAPIS.class);
            Call<Response> response = api.getCustomerStatus(customerPhoneNumber, userPhoneNumber);

            response.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response res = response.body();
                    if(1 == res.getStatus())
                    {
                        String status = res.getMsg();
                        if (resultView != null){
                            resultView.setTextColor(Color.DKGRAY);
                            resultView.setText(" Status : " + status);
                        }
                    }
                    else {
                        resultView.setTextColor(Color.RED);
                        resultView.setText("Error : " + res.getMsg());
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    resultView.setText("Error : Unexpected failure, contact app admin.");
                }
            });
        }
        else{
            customerPhone.setHintTextColor(Color.RED);
        }

    }
    public void getCustomerInformation(){

        String customerPhoneNumber = customerPhone.getText().toString();
        String userPhoneNumber = userPhoneNumberGlobal;

        if (!customerPhoneNumber.isEmpty())
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RhubAPIS api = retrofit.create(RhubAPIS.class);
            Call<CustomerInformationResponse> response = api.getCustomerInformation(customerPhoneNumber, userPhoneNumber);

            response.enqueue(new Callback<CustomerInformationResponse>() {
                @Override
                public void onResponse(Call<CustomerInformationResponse> call, retrofit2.Response<CustomerInformationResponse> response) {
                    CustomerInformationResponse res = response.body();
                    if(1 == res.getStatus())
                    {
                        String name = res.getData().getName();
                        String phone = res.getData().getPhone();
                        String email = res.getData().getEmail();
                        String cityInterest = res.getData().getCityOfInterest();
                        String areaInterest = res.getData().getAreaOfInterest();
                        String status = res.getData().getStatus();

                        FragmentManager mgr = getFragmentManager();
                        FragmentTransaction tx = mgr.beginTransaction();
                        CustomerInformation customerInfo =  new CustomerInformation();

                        customerInfo.setCustomerName(name);
                        customerInfo.setCustomerPhone(phone);
                        customerInfo.setCustomerEmail(email);
                        customerInfo.setInterstedCity(cityInterest);
                        customerInfo.setInterstedArea(areaInterest);
                        customerInfo.setCustomerStatus(status);

                        tx.replace(R.id.frag, customerInfo);
                        tx.addToBackStack("");
                        tx.commit();
                    }
                    else {
                        resultView.setTextColor(Color.RED);
                        resultView.setText("Error in getting customer details" );
                    }
                }

                @Override
                public void onFailure(Call<CustomerInformationResponse> call, Throwable t) {
                    resultView.setText("Error : Unexpected failure, contact app admin.");
                }
            });
        }
        else{
            customerPhone.setHintTextColor(Color.RED);
        }
    }

    public void showAllCustomers(){

        //String customerPhoneNumber = customerPhone.getText().toString();
        String userPhoneNumber = userPhoneNumberGlobal;

        if (!userPhoneNumber.isEmpty())
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RhubAPIS api = retrofit.create(RhubAPIS.class);
            Call<CustomersBasicInformation> response = api.getAllCustomersBasicInfo(userPhoneNumber);

            response.enqueue(new Callback<CustomersBasicInformation>() {
                @Override
                public void onResponse(Call<CustomersBasicInformation> call, retrofit2.Response<CustomersBasicInformation> response) {
                    CustomersBasicInformation res = response.body();
                    if(1 == res.getStatus())
                    {
                        List<Datum> customersBasicInfo = res.getData() ;
                        FragmentManager mgr = getFragmentManager();
                        FragmentTransaction tx = mgr.beginTransaction();
                        CustomersList customersList =  new CustomersList();
                        customersList.setCustomersList(customersBasicInfo);
                        tx.replace(R.id.frag, customersList);
                        tx.addToBackStack("");
                        tx.commit();
                    }
                    else {
                        resultView.setTextColor(Color.RED);
                        resultView.setText("Error in getting customers details : " + res.getMsg());
                    }
                }

                @Override
                public void onFailure(Call<CustomersBasicInformation> call, Throwable t) {
                    resultView.setText("Error : Unexpected failure, contact app admin.");
                }
            });
        }
        else{
            customerPhone.setHintTextColor(Color.RED);
        }
    }
}
