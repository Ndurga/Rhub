package in.rhub.rhub;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Satish on 2/5/2018.
 */

public class HomeFragment extends Fragment {
    ImageView iview;
    int[] plots = new int[]{
            R.drawable.rhub_img1,
            R.drawable.rhub_img2,
            R.drawable.rhub_img3,
            R.drawable.rhub_img4,
            R.drawable.rhub_img5,
            R.drawable.rhub_img6
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home,container,false);

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<6;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("plot", Integer.toString(plots[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "plot" };

        // Ids of views in listview_layout
        int[] to = { R.id.lviewlayout};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(v.getContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) v.findViewById(R.id.lView1);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);


        return v;
    }


}
