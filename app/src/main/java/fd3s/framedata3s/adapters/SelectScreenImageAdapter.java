package fd3s.framedata3s.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import fd3s.framedata3s.utils.ResourceHelper;

public class SelectScreenImageAdapter extends BaseAdapter {
    private Context myContext;

    public SelectScreenImageAdapter(Context c) {
        myContext = c;
    }

    public int getCount() {
        return ResourceHelper.ThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(myContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(Color.argb(128, 0, 0, 0));

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(ResourceHelper.ThumbIds[position]);
        return imageView;
    }
}