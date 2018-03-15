package in.rhub.rhub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by durga on 22-02-2018.
 */

public class MyHolder extends RecyclerView.ViewHolder {
    LinearLayout indiLayout;
    TextView name;
    TextView phone;
    TextView status;

    public MyHolder(View itemView) {
        super(itemView);
        name   = itemView.findViewById(R.id.name);
        phone  = itemView.findViewById(R.id.phone);
        status = itemView.findViewById(R.id.status);
        indiLayout = itemView.findViewById(R.id.indi_layout);
    }
}