package fd3s.framedata.utils;

import android.content.Context;
import android.content.Intent;

import fd3s.framedata.R;
import fd3s.framedata.appmenu.UserSettingActivity;

/**
 * Created by vhd on 25/01/15.
 */
public class MenuHandler {
    public static boolean onOptionsItemSelected(Context ref, int id){
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(ref, UserSettingActivity.class);
            ref.startActivity(myIntent);
            return true;
        }
        return false;
    }
}
