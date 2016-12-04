package fd3s.framedata.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom image view which draws a frame and its hitbox data
 * automatically.
 */
public class HitBoxImageView extends ImageView {

    private Map<Paint,List<Rect>> hitboxes = Collections.EMPTY_MAP;
    private double scaleWidth = 1;
    private double scaleHeight = 1;

    public HitBoxImageView(Context context) {
        super(context);
    }

    public HitBoxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HitBoxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HitBoxImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        scaleWidth = getWidth() / 384.0;
        scaleHeight = getHeight() / 224.0;
        for (Map.Entry<Paint, List<Rect>> entry : hitboxes.entrySet()) {
            for(Rect hitbox : entry.getValue()){
                canvas.drawRect((int) Math.ceil(hitbox.left * scaleWidth),
                        (int) Math.ceil(hitbox.top * scaleHeight),
                        (int) Math.ceil(hitbox.right * scaleWidth),
                        (int) Math.ceil(hitbox.bottom * scaleHeight),
                        entry.getKey());
            }
        }
    }

    private List<Rect> getHBsToDraw(String json, String hbType){
        if(json == null || json.length() < 1){
            return Collections.emptyList();
        }

        List<Rect> boxes = new ArrayList<>();

// Try to parse JSON
        try {
            JSONObject jsonObjMain = new JSONObject(json);
            JSONObject jsonObjectP1 =(JSONObject) jsonObjMain.get("P1");
            JSONObject jsonObjectHitboxes =(JSONObject) jsonObjectP1.get("hitboxes");
            JSONArray jsonArrayX_HB =(JSONArray) jsonObjectHitboxes.get(hbType);
            JSONArray jsonArrayHB_To_Draw =(JSONArray) jsonObjectHitboxes.get(hbType + "_to_draw");

            for (int i = 0; i < jsonArrayX_HB.length(); i++) {
                String x_hb = jsonArrayX_HB.getString(i);
                if(!x_hb.equals("0,0,0,0")) {
                    Rect r = new Rect();
                    JSONArray jsonCoords = jsonArrayHB_To_Draw.getJSONArray(i);
                    r.set(jsonCoords.getInt(1), jsonCoords.getInt(2), jsonCoords.getInt(0), jsonCoords.getInt(3));
                    boxes.add(r);
                }
            }
            return boxes;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void setImageViewHitboxes(String json){
        hitboxes = new HashMap<Paint, List<Rect>>();
        if(json != null) {
            List<Rect> a_hbs = getHBsToDraw(json, "a_hb");
            Paint a_paint = new Paint();
            a_paint.setColor(Color.argb(128, 255, 0, 0));
            a_paint.setStrokeWidth(1);
            hitboxes.put(a_paint, a_hbs);

            List<Rect> pu_hbs = getHBsToDraw(json, "p_hb");
            Paint p_paint = new Paint();
            p_paint.setColor(Color.argb(128, 0, 0, 255));
            p_paint.setStrokeWidth(1);
            hitboxes.put(p_paint, pu_hbs);

            List<Rect> v_hbs = getHBsToDraw(json, "v_hb");
            Paint v_paint = new Paint();
            v_paint.setColor(Color.argb(128, 128, 128, 255));
            v_paint.setStrokeWidth(1);
            hitboxes.put(v_paint, v_hbs);
        }
    }
}
