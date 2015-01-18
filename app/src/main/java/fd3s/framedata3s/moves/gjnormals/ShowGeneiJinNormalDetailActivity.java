package fd3s.framedata3s.moves.gjnormals;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import fd3s.framedata3s.R;
import fd3s.framedata3s.sdo.CharSDO;
import fd3s.framedata3s.sdo.GeneiJinNormalSDO;
import fd3s.framedata3s.utils.CharDataProvider;
import fd3s.framedata3s.utils.ResourceHelper;

public class ShowGeneiJinNormalDetailActivity extends ActionBarActivity {
    private ShowGeneiJinNormalDetailActivity ref = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gjnormal_detail_layout);

        int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        int normalId = getIntent().getIntExtra(ResourceHelper.ResourceIds.GJ_NORMAL_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tv = (TextView)findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView)findViewById(R.id.char_image);

        tv.setText("Error loading normal");
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO != null) {
            GeneiJinNormalSDO normalSDO = charSDO.genei_jin_normals.get(normalId);
            if(normalSDO != null){
                tv.setText(normalSDO.name);
                ((TextView)findViewById(R.id.detail_startup)).setText(normalSDO.startup);
                ((TextView)findViewById(R.id.detail_hit)).setText(normalSDO.hit);
                ((TextView)findViewById(R.id.detail_recovery)).setText(normalSDO.recovery);
                ((TextView)findViewById(R.id.detail_block_adv)).setText(normalSDO.block_advantage);
                ((TextView)findViewById(R.id.detail_hit_adv)).setText(normalSDO.hit_advantage);
                ((TextView)findViewById(R.id.detail_crouch_hit_adv)).setText(normalSDO.crouch_hit_advantage);

                ((TextView)findViewById(R.id.detail_damage)).setText(normalSDO.damage);
                ((TextView)findViewById(R.id.detail_stun)).setText(normalSDO.stun);
                ((TextView)findViewById(R.id.detail_kara_range)).setText(normalSDO.kara_range);
                ((TextView)findViewById(R.id.detail_throw_range)).setText(normalSDO.throw_range);

                ((TextView)findViewById(R.id.detail_bgo_hit)).setText(normalSDO.bar_gain_opp.hit + "");
                ((TextView)findViewById(R.id.detail_bgo_block)).setText(normalSDO.bar_gain_opp.block + "");

                if(normalSDO.cancel._super){
                    ((TextView)findViewById(R.id.detail_cancel_super)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_super)).setTextColor(Color.BLACK);
                }
                if(normalSDO.cancel._chain){
                    ((TextView)findViewById(R.id.detail_cancel_chain)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_chain)).setTextColor(Color.BLACK);
                }
                if(normalSDO.cancel._dash){
                    ((TextView)findViewById(R.id.detail_cancel_dash)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_dash)).setTextColor(Color.BLACK);
                }
                if(normalSDO.cancel._self){
                    ((TextView)findViewById(R.id.detail_cancel_self)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_self)).setTextColor(Color.BLACK);
                }
                if(normalSDO.cancel._special){
                    ((TextView)findViewById(R.id.detail_cancel_special)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_special)).setTextColor(Color.BLACK);
                }
                if(normalSDO.cancel._sjump){
                    ((TextView)findViewById(R.id.detail_cancel_jump)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_jump)).setTextColor(Color.BLACK);
                }

                if(normalSDO.parry.high){
                    ((TextView)findViewById(R.id.detail_parry_high)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_parry_high)).setTextColor(Color.BLACK);
                }
                if(normalSDO.parry.low){
                    ((TextView)findViewById(R.id.detail_parry_low)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_parry_low)).setTextColor(Color.BLACK);
                }
            }
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
