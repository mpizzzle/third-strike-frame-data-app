package fd3s.framedata3s.utils;

import android.content.Context;
import android.content.Intent;

import fd3s.framedata3s.R;
import fd3s.framedata3s.appmenu.UserSettingActivity;

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
