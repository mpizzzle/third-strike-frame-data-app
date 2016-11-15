package fd3s.framedata.moves;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fd3s.framedata.R;
import fd3s.framedata.sdo.CharSDO;
import fd3s.framedata.sdo.FrameHitBoxData;
import fd3s.framedata.sdo.GeneiJinSpecialSDO;
import fd3s.framedata.sdo.MoveSDO;
import fd3s.framedata.sdo.MoveWithOBarGainSDO;
import fd3s.framedata.sdo.NormalSDO;
import fd3s.framedata.sdo.NormalTypeSDO;
import fd3s.framedata.sdo.SpecialSDO;
import fd3s.framedata.sdo.SuperSDO;
import fd3s.framedata.utils.CharDataProvider;
import fd3s.framedata.utils.FrameDataProvider;
import fd3s.framedata.utils.MenuHandler;
import fd3s.framedata.views.HitBoxImageView;

import static fd3s.framedata.R.layout.*;
import static fd3s.framedata.utils.ResourceHelper.*;

public class ShowMoveDetailActivity extends ActionBarActivity {
    private ShowMoveDetailActivity ref = this;

    private static int frameId;
    private static int characterId;
    private static int listId;
    private static ListIds listType;
    private static int moveId;
    private static CharSDO charSDO;
    private static FrameDataProvider fdProvider;

    private enum btnActions {
        FIRST, PREV, PLAY, NEXT, LAST
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(move_detail_layout);

        characterId = getIntent().getIntExtra(ResourceIds.CHARACTER_ID.name(), 0);
        listId = getIntent().getIntExtra(ResourceIds.LIST_ID.name(), 0);
        listType = ListIds.values()[listId];
        moveId = getIntent().getIntExtra(listType.name(), 0);
        charSDO = CharDataProvider.getInstance(characterId, this).getCharSDO();
        fdProvider = new FrameDataProvider(this);

        setupViews(listType, characterId);

        if (charSDO != null) {
            MoveSDO moveSDO = getMoveSDO(charSDO, listType, moveId);
            if(moveSDO != null){
                final int firstActiveFrame = getNumber(moveSDO.startup)+1;
                new AsyncTask<Void, Void, Void>(){
                    private FrameHitBoxData frame;
                    @Override
                    protected Void doInBackground(Void... params){
                        frame = fdProvider.getMoveFrame(charSDO, characterId, listType, moveId, firstActiveFrame);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result){
                        TextView stv = (TextView)findViewById(R.id.sprite_heading);
                        HitBoxImageView ivSpriteImage = (HitBoxImageView)findViewById(R.id.sprite_image);
                        int cutOff = frame.json.length() > 10 ? 10 : frame.json.length();
                        stv.setText(frame.json.substring(0,cutOff));
                        ivSpriteImage.setImageDrawable(frame.sprite);
                        ivSpriteImage.setImageViewHitboxes(frame.json);
                    }
                }.execute();
                displayMotion(listType, moveSDO);
                displayMoveDetails(moveSDO);
                setupFrameControls(moveSDO);
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

        if (MenuHandler.onOptionsItemSelected(this, id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private MoveSDO getMoveSDO(CharSDO charSDO, ListIds listType, int moveId){
        MoveSDO moveSDO = null;
        switch(listType){
            case NORMAL_ID:
                return charSDO.normals.get(moveId);
            case SPECIAL_ID:
                return charSDO.specials.get(moveId);
            case SUPER_ID:
                return charSDO.supers.get(moveId);
            case GJ_NORMAL_ID:
                return charSDO.genei_jin_normals.get(moveId);
            case GJ_SPECIAL_ID:
                return charSDO.genei_jin_specials.get(moveId);
        }
        return moveSDO;
    }

    private void setupViews(ListIds listType, int characterId){
        this.setTitle(CharacterNames[characterId] + " Frame Data");
        ((TextView)findViewById(R.id.page_heading)).setText("Error loading move");
        ((ImageView)findViewById(R.id.char_image)).setImageResource(ThumbIds[characterId]);

        TextView tvHitLabel = ((TextView) findViewById(R.id.detail_hit_adv_label));
        TextView tvCrouchLabel = ((TextView) findViewById(R.id.detail_crouch_hit_adv_label));
        TextView tvHit = ((TextView) findViewById(R.id.detail_hit_adv));
        TextView tvCrouch = ((TextView) findViewById(R.id.detail_crouch_hit_adv));

        tvHit.setVisibility(View.GONE);
        tvCrouch.setVisibility(View.GONE);
        tvHitLabel.setVisibility(View.GONE);
        tvCrouchLabel.setVisibility(View.GONE);

        LinearLayout otherContainer = ((LinearLayout) findViewById(R.id.detail_other_container));
        LinearLayout bgsContainer = ((LinearLayout) findViewById(R.id.detail_bgs_container));
        LinearLayout bgoContainer = ((LinearLayout) findViewById(R.id.detail_bgo_container));
        LinearLayout motionContainer = ((LinearLayout) findViewById(R.id.move_motion_container));
        View withKaraOther = getLayoutInflater().inflate(R.layout.detail_other_with_kara, null);
        View withoutKaraOther = getLayoutInflater().inflate(R.layout.detail_other_without_kara, null);
        switch(listType){
            case NORMAL_ID:
                otherContainer.addView(withKaraOther);
                tvHit.setVisibility(View.VISIBLE);
                tvCrouch.setVisibility(View.VISIBLE);
                tvHitLabel.setVisibility(View.VISIBLE);
                tvCrouchLabel.setVisibility(View.VISIBLE);
                motionContainer.setVisibility(View.GONE);
                break;
            case SPECIAL_ID:
                otherContainer.addView(withoutKaraOther);
                break;
            case SUPER_ID:
                otherContainer.addView(withoutKaraOther);
                bgsContainer.setVisibility(View.GONE);
                bgoContainer.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.detail_bgs_label)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.detail_bgo_label)).setVisibility(View.GONE);
                break;
            case GJ_NORMAL_ID:
                otherContainer.addView(withKaraOther);
                tvHit.setVisibility(View.VISIBLE);
                tvCrouch.setVisibility(View.VISIBLE);
                tvHitLabel.setVisibility(View.VISIBLE);
                tvCrouchLabel.setVisibility(View.VISIBLE);
                bgsContainer.setVisibility(View.GONE);
                motionContainer.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.detail_bgs_label)).setVisibility(View.GONE);
                break;
            case GJ_SPECIAL_ID:
                otherContainer.addView(withoutKaraOther);
                bgsContainer.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.detail_bgs_label)).setVisibility(View.GONE);
                break;
        }
    }

    private void displayMotion(ListIds listType, MoveSDO moveSDO){
        switch(listType){
            case SPECIAL_ID:
                ((TextView) findViewById(R.id.detail_motion)).setText(((SpecialSDO) moveSDO).motion);
                break;
            case SUPER_ID:
                ((TextView) findViewById(R.id.detail_motion)).setText(((SuperSDO) moveSDO).motion);
                break;
            case GJ_SPECIAL_ID:
                ((TextView) findViewById(R.id.detail_motion)).setText(((GeneiJinSpecialSDO) moveSDO).motion);
                break;
        }
    }

    private void displayMoveDetails(MoveSDO moveSDO){
        ((TextView)findViewById(R.id.page_heading)).setText(moveSDO.name);
        ((TextView)findViewById(R.id.detail_startup)).setText(moveSDO.startup);
        ((TextView)findViewById(R.id.detail_hit)).setText(moveSDO.hit);
        ((TextView)findViewById(R.id.detail_recovery)).setText(moveSDO.recovery);
        ((TextView)findViewById(R.id.detail_block_adv)).setText(moveSDO.block_advantage);

        if(moveSDO instanceof NormalTypeSDO) {
            NormalTypeSDO normalTypeSDO = (NormalTypeSDO) moveSDO;

            TextView tvHit = ((TextView) findViewById(R.id.detail_hit_adv));
            TextView tvCrouch = ((TextView) findViewById(R.id.detail_crouch_hit_adv));
            TextView tvKara = ((TextView) findViewById(R.id.detail_kara_range));

            tvHit.setVisibility(View.VISIBLE);
            tvKara.setVisibility(View.VISIBLE);
            tvCrouch.setVisibility(View.VISIBLE);

            tvHit.setText(normalTypeSDO.hit_advantage);
            tvCrouch.setText(normalTypeSDO.crouch_hit_advantage);
            tvKara.setText(normalTypeSDO.kara_range);
        }

        ((TextView)findViewById(R.id.detail_damage)).setText(moveSDO.damage);
        ((TextView)findViewById(R.id.detail_stun)).setText(moveSDO.stun);
        ((TextView)findViewById(R.id.detail_throw_range)).setText(moveSDO.throw_range);

        if(moveSDO instanceof MoveWithOBarGainSDO) {
            MoveWithOBarGainSDO moveWithBarGainSDO = (MoveWithOBarGainSDO) moveSDO;
            ((TextView) findViewById(R.id.detail_bgo_hit)).setText(moveWithBarGainSDO.bar_gain_opp.hit + "");
            ((TextView) findViewById(R.id.detail_bgo_block)).setText(moveWithBarGainSDO.bar_gain_opp.block + "");
        }

        if(moveSDO instanceof NormalSDO){
            NormalSDO normalSDO = (NormalSDO) moveSDO;
            ((TextView)findViewById(R.id.detail_bgs_wiff)).setText(normalSDO.bar_gain_self.wiff + "");
            ((TextView)findViewById(R.id.detail_bgs_hit)).setText(normalSDO.bar_gain_self.hit + "");
            ((TextView)findViewById(R.id.detail_bgs_block)).setText(normalSDO.bar_gain_self.block + "");
        } else if(moveSDO instanceof SpecialSDO){
            SpecialSDO specialSDO = (SpecialSDO) moveSDO;
            ((TextView)findViewById(R.id.detail_bgs_wiff)).setText(specialSDO.bar_gain_self.wiff + "");
            ((TextView)findViewById(R.id.detail_bgs_hit)).setText(specialSDO.bar_gain_self.hit + "");
            ((TextView)findViewById(R.id.detail_bgs_block)).setText(specialSDO.bar_gain_self.block + "");
        }

        if(moveSDO.cancel._super){
            ((TextView)findViewById(R.id.detail_cancel_super)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_cancel_super)).setTextColor(Color.BLACK);
        }
        if(moveSDO.cancel._chain){
            ((TextView)findViewById(R.id.detail_cancel_chain)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_cancel_chain)).setTextColor(Color.BLACK);
        }
        if(moveSDO.cancel._dash){
            ((TextView)findViewById(R.id.detail_cancel_dash)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_cancel_dash)).setTextColor(Color.BLACK);
        }
        if(moveSDO.cancel._self){
            ((TextView)findViewById(R.id.detail_cancel_self)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_cancel_self)).setTextColor(Color.BLACK);
        }
        if(moveSDO.cancel._special){
            ((TextView)findViewById(R.id.detail_cancel_special)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_cancel_special)).setTextColor(Color.BLACK);
        }
        if(moveSDO.cancel._sjump){
            ((TextView)findViewById(R.id.detail_cancel_jump)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_cancel_jump)).setTextColor(Color.BLACK);
        }

        if(moveSDO.parry.high){
            ((TextView)findViewById(R.id.detail_parry_high)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_parry_high)).setTextColor(Color.BLACK);
        }
        if(moveSDO.parry.low){
            ((TextView)findViewById(R.id.detail_parry_low)).setBackgroundColor(Color.YELLOW);
            ((TextView)findViewById(R.id.detail_parry_low)).setTextColor(Color.BLACK);
        }
    }

    private void setupFrameControls(MoveSDO moveSDO){
        Button btnFirst = (Button)findViewById(R.id.btn_frame_first);
        Button btnPrev = (Button)findViewById(R.id.btn_frame_prev);
        Button btnPlay = (Button)findViewById(R.id.btn_frame_play);
        Button btnNext = (Button)findViewById(R.id.btn_frame_next);
        Button btnLast = (Button)findViewById(R.id.btn_frame_last);

        int startup = getNumber(moveSDO.startup);
        int active = getNumber(moveSDO.hit);
        int recovery = getNumber(moveSDO.recovery);

        if(moveSDO instanceof SuperSDO){
            startup += 52;
        }

        int lastFrameGuess = startup+active+recovery-2;

        setupClickListener(btnFirst, btnActions.FIRST, lastFrameGuess);
        setupClickListener(btnPrev, btnActions.PREV, lastFrameGuess);
        setupClickListener(btnPlay, btnActions.PLAY, lastFrameGuess);
        setupClickListener(btnNext, btnActions.NEXT, lastFrameGuess);
        setupClickListener(btnLast, btnActions.LAST, lastFrameGuess);
    }

    private void setupClickListener(Button btn, final btnActions action, final int lastFrame){
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>(){
                    private FrameHitBoxData frame;
                    @Override
                    protected Void doInBackground(Void... params){
                        switch (action){
                            case FIRST:frameId = 0;break;
                            case PREV:frameId = frameId == 0 ? lastFrame : frameId-1;break;
                            case PLAY:break;
                            case NEXT:frameId =  frameId == lastFrame ? 0 :frameId+1; break;
                            case LAST:frameId = lastFrame;break;
                        }
                        frame = fdProvider.getMoveFrame(charSDO, characterId, listType, moveId, frameId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result){
                        TextView stv = (TextView)findViewById(R.id.sprite_heading);
                        HitBoxImageView ivSpriteImage = (HitBoxImageView)findViewById(R.id.sprite_image);
                        int cutOff = frame.json.length() > 10 ? 10 : frame.json.length();
                        stv.setText(frame.json.substring(0,cutOff));
                        ivSpriteImage.setImageDrawable(frame.sprite);
                        ivSpriteImage.setImageViewHitboxes(frame.json);
                    }
                }.execute();
            }
        });
    }

    private int getNumber(String s){
        try{
            int i = Integer.parseInt(s);
            return i;
        }catch(NumberFormatException e){
            return 0;
        }
    }
}
