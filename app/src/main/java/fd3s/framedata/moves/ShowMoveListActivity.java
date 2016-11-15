package fd3s.framedata.moves;

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

import fd3s.framedata.R;
import fd3s.framedata.adapters.AlternatingColorListViewAdapter;
import fd3s.framedata.sdo.CharSDO;
import fd3s.framedata.utils.CharDataProvider;
import fd3s.framedata.utils.MenuHandler;
import fd3s.framedata.utils.ResourceHelper;

public class ShowMoveListActivity extends ActionBarActivity {
    private ShowMoveListActivity ref = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        final int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        final int listId = getIntent().getIntExtra(ResourceHelper.ResourceIds.LIST_ID.name(), 0);
        final ResourceHelper.ListIds listType = ResourceHelper.ListIds.values()[listId];

        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tv = (TextView)findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView)findViewById(R.id.char_image);

        tv.setText(listType.getTitle());
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO != null) {

            final ListView lvMoves = (ListView) findViewById(R.id.move_names);

            ArrayList<String> aMoveNames = getMoveListItems(listType, charSDO);

            AlternatingColorListViewAdapter adapter = new AlternatingColorListViewAdapter(this,
                    R.layout.move_layout, aMoveNames);

            lvMoves.setAdapter(adapter);

            lvMoves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent myIntent = new Intent(ref, ShowMoveDetailActivity.class);
                    myIntent.putExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), characterId);
                    myIntent.putExtra(ResourceHelper.ResourceIds.LIST_ID.name(), listId);
                    myIntent.putExtra(listType.name(), position);
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

    private ArrayList<String> getMoveListItems(ResourceHelper.ListIds listType, CharSDO charSDO){
        ArrayList<String> aMoveNames = new ArrayList<>();
        switch(listType){
            case NORMAL_ID:
                for(int i = 0; i < charSDO.normals.size(); i++){
                    aMoveNames.add(charSDO.normals.get(i).name);
                }
                return aMoveNames;
            case SPECIAL_ID:
                for(int i = 0; i < charSDO.specials.size(); i++){
                    aMoveNames.add(charSDO.specials.get(i).name);
                }
                return aMoveNames;
            case SUPER_ID:
                for(int i = 0; i < charSDO.supers.size(); i++){
                    aMoveNames.add(charSDO.supers.get(i).name);
                }
                return aMoveNames;
            case OTHER_ID:
                throw new UnsupportedOperationException("Other ID is not supported with this activity.");
            case GJ_NORMAL_ID:
                for(int i = 0; i < charSDO.genei_jin_normals.size(); i++){
                    aMoveNames.add(charSDO.genei_jin_normals.get(i).name);
                }
                return aMoveNames;
            case GJ_SPECIAL_ID:
                for(int i = 0; i < charSDO.genei_jin_specials.size(); i++){
                    aMoveNames.add(charSDO.genei_jin_specials.get(i).name);
                }
                return aMoveNames;
        }
        return aMoveNames;
    }
}
