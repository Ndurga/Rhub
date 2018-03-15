package in.rhub.rhub;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Satish on 2/1/2018.
 */

public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home,container,false);
        return v;
    }
}


