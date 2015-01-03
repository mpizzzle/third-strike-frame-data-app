package fd3s.framedata3s;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SelectScreenImageAdapter extends BaseAdapter {
    private Context mContext;

    public SelectScreenImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
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
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(Color.argb(128, 0, 0, 0));

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    private Integer[] mThumbIds = {
            R.drawable.gouki_face, R.drawable.yun_face,
            R.drawable.ryu_face, R.drawable.urien_face,
            R.drawable.remy_face, R.drawable.oro_face,
            R.drawable.necro_face, R.drawable.q_face,
            R.drawable.dudley_face, R.drawable.ibuki_face,
            R.drawable.chun_li_face, R.drawable.elena_face,
            R.drawable.sean_face, R.drawable.makoto_face,
            R.drawable.hugo_face, R.drawable.alex_face,
            R.drawable.twelve_face, R.drawable.ken_face,
            R.drawable.yang_face, R.drawable.gill_face
    };
}