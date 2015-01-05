package fd3s.framedata3s;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class CharacterActivity extends ActionBarActivity {

    /* this method should probably go somewhere else. */

    public static JSONObject loadJSONFile (String filename, Context context) {
         AssetManager manager = context.getAssets();

        byte[] fileInput = null;

        try {
            InputStream file = manager.open(filename);
            fileInput = new byte[file.available()];
            file.read(fileInput);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = null;

        try {
            json = new JSONObject(new String(fileInput));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId]);
        JSONObject json = loadJSONFile(ResourceHelper.CharacterNames[characterId] + ".txt", this);

        if (json != null) {
            TextView tv = (TextView)findViewById(R.id.character_json_text);
            tv.setText(json.toString());
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.argb(128, 0, 0, 0));
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
