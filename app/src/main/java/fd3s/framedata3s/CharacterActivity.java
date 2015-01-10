package fd3s.framedata3s;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import fd3s.framedata3s.sdo.CharSDO;

public class CharacterActivity extends ActionBarActivity {

    /* this method should probably go somewhere else. */

    public static CharSDO getCharDataFromFile (String filename, Context context) {
         AssetManager manager = context.getAssets();

        byte[] fileInput = null;
        String sJson = "";
        CharSDO charSDO = new CharSDO();

        try {
            InputStream file = manager.open(filename);
            fileInput = new byte[file.available()];
            file.read(fileInput);
            file.close();
            sJson = new String(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            charSDO = gson.fromJson(sJson, CharSDO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return charSDO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId]);
        CharSDO charSDO = getCharDataFromFile(ResourceHelper.CharacterNames[characterId] + ".txt", this);

        if (charSDO != null) {
            TextView tv = (TextView)findViewById(R.id.character_json_text);
            tv.setText(charSDO.normals.get(0).name);
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
