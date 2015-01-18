package fd3s.framedata3s.moves.supers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fd3s.framedata3s.R;
import fd3s.framedata3s.adapters.AlternatingColorListViewAdapter;
import fd3s.framedata3s.sdo.CharSDO;
import fd3s.framedata3s.utils.CharDataProvider;
import fd3s.framedata3s.utils.ResourceHelper;

public class ShowSupersActivity extends ActionBarActivity {
    private ShowSupersActivity ref = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        final int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tv = (TextView)findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView)findViewById(R.id.char_image);

        tv.setText("Supers");
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO != null) {

            final ListView lvMoves = (ListView) findViewById(R.id.move_names);
            ArrayList<String> aMoveNames = new ArrayList<String>();

            for(int i = 0; i < charSDO.supers.size(); i++){
                aMoveNames.add(charSDO.supers.get(i).name);
            }

            AlternatingColorListViewAdapter adapter = new AlternatingColorListViewAdapter(this,
                    R.layout.move_layout, aMoveNames);

            lvMoves.setAdapter(adapter);

            lvMoves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent myIntent = new Intent(ref, ShowSuperDetailActivity.class);
                    myIntent.putExtra(ResourceHelper.ResourceIds.SUPER_ID.name(), position);
                    myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                    startActivity(myIntent);
                    /*
                    String  itemValue    = (String) lvMoves.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),
                    "Position :"+position+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
                    */
                }
            });
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
