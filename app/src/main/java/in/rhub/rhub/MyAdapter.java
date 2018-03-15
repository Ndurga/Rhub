package in.rhub.rhub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.rhub.rhub.Configuration.baseUrl;
import static in.rhub.rhub.Configuration.userPhoneNumberGlobal;

/**
 * Created by durga on 22-02-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    private List<Datum> customersData = null;
    private Fragment fragment;

    public MyAdapter(Fragment fragment, List<Datum> customersInfo) {
        this.fragment = fragment;
        this.customersData=customersInfo;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.indiview, parent,false);
        MyHolder holder=new MyHolder(v);

        return holder;
    }
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.name.setText(customersData.get(position).getName());
        holder.phone.setText(customersData.get(position).getPhone());
        holder.status.setText(customersData.get(position).getStatus());

        holder.indiLayout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     TextView phone = v.findViewById(R.id.phone);
                     String customerPhone = phone.getText().toString();
                     String userPhone = userPhoneNumberGlobal;
                     showCustomerInformationOnItemClick(userPhone, customerPhone);
                 }
             }
        );
    }
    @Override
    public int getItemCount() {
        return customersData.size();
    }

    public void showCustomerInformationOnItemClick(String userPhone, String customerPhone){

        String customerPhoneNumber = customerPhone;
        String userPhoneNumber = userPhone;

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

                        FragmentManager mgr =  fragment.getFragmentManager();
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
                }

                @Override
                public void onFailure(Call<CustomerInformationResponse> call, Throwable t) {
                }
            });
        }
    }
}
