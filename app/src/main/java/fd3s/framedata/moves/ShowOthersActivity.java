package fd3s.framedata.moves;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

import fd3s.framedata.R;
import fd3s.framedata.sdo.CharSDO;
import fd3s.framedata.utils.CharDataProvider;
import fd3s.framedata.utils.MenuHandler;
import fd3s.framedata.utils.ResourceHelper;

public class ShowOthersActivity extends ActionBarActivity {

    private static final String JUMP_PATTERN = "jump";
    private static final String DASH_PATTERN = "Dash";
    private static final String WAKEUP_PATTERN = "Wakeup";

    private ShowOthersActivity ref = this;
    private LayoutInflater vi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_detail_layout);
        vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tv = (TextView) findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView) findViewById(R.id.char_image);

        tv.setText("Other");
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO.misc != null) {
            MapKeyValuePairsToLinearLayout(charSDO.misc, (LinearLayout) findViewById(R.id.jump_list_view), JUMP_PATTERN);
            MapKeyValuePairsToLinearLayout(charSDO.misc, (LinearLayout) findViewById(R.id.dash_list_view), DASH_PATTERN);
            MapKeyValuePairsToLinearLayout(charSDO.misc, (LinearLayout) findViewById(R.id.wake_up_list_view), WAKEUP_PATTERN);
        }
    }

    private void MapKeyValuePairsToLinearLayout(Map<String, String> map, LinearLayout root, String pattern) {
        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<String, String> thisEntry = (Map.Entry<String, String>) entries.next();
            String key = thisEntry.getKey();

            if (key.contains(pattern)) { //easier than formatting all the data, I'm lazy
                String value = thisEntry.getValue();
                addViewToLinearLayout(root, key, value);
            }
        }
    }

    private void addViewToLinearLayout(LinearLayout root, String key, String value) {
        View row = vi.inflate(R.layout.key_value_layout, null);

        TextView tv_key = (TextView) row.findViewById(R.id.key);
        tv_key.setText(key);

        TextView tv_value = (TextView) row.findViewById(R.id.value);
        tv_value.setText(value);

        root.addView(row);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (MenuHandler.onOptionsItemSelected(this, id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
