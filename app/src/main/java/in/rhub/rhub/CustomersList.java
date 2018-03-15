package in.rhub.rhub;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by durga on 20-02-2018.
 */

public class CustomersList extends Fragment {

    List<Datum> customersBasicInfo;
    RecyclerView recycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.customer_list, container, false);
        recycleView = view.findViewById(R.id.customer_r_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(new MyAdapter(this, customersBasicInfo));

        return view;
    }

    void setCustomersList(List<Datum> customers){
        customersBasicInfo = customers;
    }

}
