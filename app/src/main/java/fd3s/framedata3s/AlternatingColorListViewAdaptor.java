package fd3s.framedata3s;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.graphics.Color;

/**
 * Created by Waseem Suleman on 10/01/15.
 */
public class AlternatingColorListViewAdaptor extends ArrayAdapter {

    private Context mContext;
    private int id;
    private List<String> items;

    public AlternatingColorListViewAdaptor(Context context, int textViewResourceId, List<String> list) {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View mView = v;
        if (mView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.list_item);

        if (items.get(position) != null) {
            text.setTextColor(Color.WHITE);
            text.setText(items.get(position));
            text.setBackgroundColor(Color.RED);
            int color = (position % 2 == 0) ? Color.argb(200, 80, 150, 100) : Color.argb(200, 100, 170, 150);
            text.setBackgroundColor(color);

        }

        return mView;
    }
}
