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

import java.util.ArrayList;

import fd3s.framedata3s.R;
import fd3s.framedata3s.adapters.AlternatingColorListViewAdapter;
import fd3s.framedata3s.sdo.CharSDO;
import fd3s.framedata3s.utils.CharDataProvider;
import fd3s.framedata3s.utils.MenuHandler;
import fd3s.framedata3s.utils.ResourceHelper;
import fd3s.framedata3s.utils.ResourceHelper.ListIds;
import fd3s.framedata3s.utils.ResourceHelper.ResourceIds;

public class ShowMoveCategoriesActivity extends ActionBarActivity {
    private ShowMoveCategoriesActivity ref = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        final int characterId = getIntent().getIntExtra(ResourceIds.CHARACTER_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tvCharName = (TextView)findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView)findViewById(R.id.char_image);

        tvCharName.setText(ResourceHelper.CharacterNames[characterId]);
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO != null) {

            final ListView lvMoves = (ListView) findViewById(R.id.move_names);
            ArrayList<String> aMoveTypes = new ArrayList<String>();

            aMoveTypes.add(ListIds.values()[0].getTitle());
            aMoveTypes.add(ListIds.values()[1].getTitle());
            aMoveTypes.add(ListIds.values()[2].getTitle());
            aMoveTypes.add(ListIds.values()[3].getTitle());
            if(ResourceHelper.CharacterNames[characterId].equals("Yun")){
                aMoveTypes.add(ListIds.values()[4].getTitle());
                aMoveTypes.add(ListIds.values()[5].getTitle());
            }

            AlternatingColorListViewAdapter adapter = new AlternatingColorListViewAdapter(this,
                    R.layout.move_layout, aMoveTypes);

            lvMoves.setAdapter(adapter);

            lvMoves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent myIntent;

                    if(ListIds.values()[position].getTitle().equals(ListIds.OTHER_ID.getTitle())){
                        myIntent = new Intent(ref, ShowOthersActivity.class);
                    }else{
                        myIntent = new Intent(ref, ShowMoveListActivity.class);
                    }

                    myIntent.putExtra(ResourceIds.CHARACTER_ID.name(), characterId);
                    myIntent.putExtra(ResourceIds.LIST_ID.name(), position);
                    startActivity(myIntent);
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

        if (MenuHandler.onOptionsItemSelected(this, id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
