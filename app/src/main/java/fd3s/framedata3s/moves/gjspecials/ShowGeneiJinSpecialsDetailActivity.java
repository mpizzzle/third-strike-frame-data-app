package fd3s.framedata3s.moves.gjspecials;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import fd3s.framedata3s.R;
import fd3s.framedata3s.sdo.CharSDO;
import fd3s.framedata3s.sdo.GeneiJinSpecialSDO;
import fd3s.framedata3s.sdo.SpecialSDO;
import fd3s.framedata3s.utils.FrameDataProvider;
import fd3s.framedata3s.utils.ResourceHelper;

public class ShowGeneiJinSpecialsDetailActivity extends ActionBarActivity {
    private ShowGeneiJinSpecialsDetailActivity ref = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gjspecial_detail_layout);

        int characterId = getIntent().getIntExtra(ResourceHelper.ResourceIds.CHARACTER_ID.name(), 0);
        int specialId = getIntent().getIntExtra(ResourceHelper.ResourceIds.GJ_SPECIAL_ID.name(), 0);
        this.setTitle(ResourceHelper.CharacterNames[characterId] + " Frame Data");
        CharSDO charSDO = FrameDataProvider.getInstance(characterId, this).getCharSDO();

        TextView tv = (TextView)findViewById(R.id.page_heading);
        ImageView ivCharImage = (ImageView)findViewById(R.id.char_image);

        tv.setText("Error loading normal");
        ivCharImage.setImageResource(ResourceHelper.ThumbIds[characterId]);

        if (charSDO != null) {
            GeneiJinSpecialSDO actionSDO = charSDO.genei_jin_specials.get(specialId);
            if(actionSDO != null){
                tv.setText(actionSDO.name);
                ((TextView)findViewById(R.id.detail_motion)).setText(actionSDO.motion);
                
                ((TextView)findViewById(R.id.detail_startup)).setText(actionSDO.startup);
                ((TextView)findViewById(R.id.detail_hit)).setText(actionSDO.hit);
                ((TextView)findViewById(R.id.detail_recovery)).setText(actionSDO.recovery);
                ((TextView)findViewById(R.id.detail_block_adv)).setText(actionSDO.block_advantage);

                ((TextView)findViewById(R.id.detail_damage)).setText(actionSDO.damage);
                ((TextView)findViewById(R.id.detail_stun)).setText(actionSDO.stun);
                ((TextView)findViewById(R.id.detail_throw_range)).setText(actionSDO.throw_range);

                ((TextView)findViewById(R.id.detail_bgo_hit)).setText(actionSDO.bar_gain_opp.hit + "");
                ((TextView)findViewById(R.id.detail_bgo_block)).setText(actionSDO.bar_gain_opp.block + "");

                if(actionSDO.cancel._super){
                    ((TextView)findViewById(R.id.detail_cancel_super)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_super)).setTextColor(Color.BLACK);
                }
                if(actionSDO.cancel._chain){
                    ((TextView)findViewById(R.id.detail_cancel_chain)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_chain)).setTextColor(Color.BLACK);
                }
                if(actionSDO.cancel._dash){
                    ((TextView)findViewById(R.id.detail_cancel_dash)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_dash)).setTextColor(Color.BLACK);
                }
                if(actionSDO.cancel._self){
                    ((TextView)findViewById(R.id.detail_cancel_self)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_self)).setTextColor(Color.BLACK);
                }
                if(actionSDO.cancel._special){
                    ((TextView)findViewById(R.id.detail_cancel_special)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_special)).setTextColor(Color.BLACK);
                }
                if(actionSDO.cancel._sjump){
                    ((TextView)findViewById(R.id.detail_cancel_jump)).setBackgroundColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.detail_cancel_jump)).setTextColor(Color.BLACK);
                }

                if(actionSDO.parry.high){
                    ((TextView)findViewById(R.id.detail_parry_high)).setBackgroundColor(Color.argb(0xFF,0x77, 0xCC, 0xBB));
                    ((TextView)findViewById(R.id.detail_parry_high)).setTextColor(Color.BLACK);
                }
                if(actionSDO.parry.low){
                    ((TextView)findViewById(R.id.detail_parry_low)).setBackgroundColor(Color.argb(0xFF,0x77, 0xCC, 0xBB));
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
