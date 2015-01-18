package fd3s.framedata3s.moves;

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
import android.widget.Toast;

import java.util.ArrayList;

import fd3s.framedata3s.R;
import fd3s.framedata3s.adapters.AlternatingColorListViewAdapter;
import fd3s.framedata3s.moves.gjnormals.ShowGeneiJinNormalsActivity;
import fd3s.framedata3s.moves.gjspecials.ShowGeneiJinSpecialsActivity;
import fd3s.framedata3s.moves.normals.ShowNormalsActivity;
import fd3s.framedata3s.moves.others.ShowOthersActivity;
import fd3s.framedata3s.moves.specials.ShowSpecialsActivity;
import fd3s.framedata3s.moves.supers.ShowSupersActivity;
import fd3s.framedata3s.sdo.CharSDO;
import fd3s.framedata3s.utils.CharDataProvider;
import fd3s.framedata3s.utils.ResourceHelper;

public class ShowDataCategoriesActivity extends ActionBarActivity {
    private ShowDataCategoriesActivity ref = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        final int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tvCharName = (TextView)findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView)findViewById(R.id.char_image);

        tvCharName.setText(ResourceHelper.CharacterNames[characterId]);
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO != null) {

            final ListView lvMoves = (ListView) findViewById(R.id.move_names);
            ArrayList<String> aMoveTypes = new ArrayList<String>();

            aMoveTypes.add("Normals");
            aMoveTypes.add("Specials");
            aMoveTypes.add("Supers");
            aMoveTypes.add("Other");
            if(ResourceHelper.CharacterNames[characterId].equals("Yun")){
                aMoveTypes.add("Genei Jin Normals");
                aMoveTypes.add("Genei Jin Specials");
            }

            AlternatingColorListViewAdapter adapter = new AlternatingColorListViewAdapter(this,
                    R.layout.move_layout, aMoveTypes);

            lvMoves.setAdapter(adapter);

            lvMoves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent myIntent;
                    switch(position){
                        case 0:
                            myIntent = new Intent(ref, ShowNormalsActivity.class);
                            myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                            startActivity(myIntent);
                            break;
                        case 1:
                            myIntent = new Intent(ref, ShowSpecialsActivity.class);
                            myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                            startActivity(myIntent);
                            break;
                        case 2:
                            myIntent = new Intent(ref, ShowSupersActivity.class);
                            myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                            startActivity(myIntent);
                            break;
                        case 3:
                            myIntent = new Intent(ref, ShowOthersActivity.class);
                            myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                            startActivity(myIntent);
                            break;
                        case 4:
                            myIntent = new Intent(ref, ShowGeneiJinNormalsActivity.class);
                            myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                            startActivity(myIntent);
                            break;
                        case 5:
                            myIntent = new Intent(ref, ShowGeneiJinSpecialsActivity.class);
                            myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                            startActivity(myIntent);
                            break;
                        default:
                            String  itemValue    = (String) lvMoves.getItemAtPosition(position);
                            Toast.makeText(getApplicationContext(),
                                    "Position :"+position+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
                            break;
                    }
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
