package fd3s.framedata3s.utils;

import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fd3s.framedata3s.sdo.CharSDO;
import fd3s.framedata3s.sdo.FrameHitBoxData;
import fd3s.framedata3s.utils.ResourceHelper.ResourceIds;

/**
 * Created by Waseem Suleman on 18/01/15.
 */
public class FrameDataProvider {
    //https://s3-eu-west-1.amazonaws.com/3sfdfiles/hitBox/Alex/fd_normals/1/0.png
    private static String baseUrl = "https://s3-eu-west-1.amazonaws.com/3sfdfiles/hitBox/";
    private static final Map<String, String> miscNames = new HashMap<String, String>();
    static {
        miscNames.put("Neutral jump startup", "jumpNeutral");
        miscNames.put("Backward jump startup", "jumpBackward");
        miscNames.put("Forward jump startup", "jumpForward");
        miscNames.put("Neutral superjump startup", "superjumpNeutral");
        miscNames.put("Backward superjump startup", "superjumpBackward");
        miscNames.put("Forward superjump startup", "superjumpForward");
        miscNames.put("Dash backward (full animation)", "dashBackwardFull");
        miscNames.put("Dash backward (recovery cancelled)", "dashBackward");
        miscNames.put("Dash forward (full animation)", "dashForwardFull");
        miscNames.put("Dash forward (recovery cancelled)", "dashForward");
        miscNames.put("Wakeup", "wakeUp");
        miscNames.put("Wakeup (quick roll)", "wakeUpQuickRoll");
    }

    private static String[] moveTypeList = {
            "fd_normals","fd_specials","fd_supers","fd_gj_normals","fd_gj_specials","fd_misc"
    };

    public static FrameHitBoxData getMoveFrame(CharSDO charSDO, int charId, ResourceIds type, int moveId, int frameId){
        int properId = moveId+1;
        int typeId = 0;
        switch (type) {
            case NORMAL_ID:
                typeId = 0;
                break;
            case SPECIAL_ID:
                typeId = 1;
                properId += charSDO.normals.size();
                break;
            case SUPER_ID:
                typeId = 2;
                properId += charSDO.normals.size() + charSDO.specials.size();
                break;
            case GJ_NORMAL_ID:
                typeId = 3;
                properId += charSDO.normals.size() + charSDO.specials.size() + charSDO.supers.size();
                break;
            case GJ_SPECIAL_ID:
                typeId = 4;
                properId += charSDO.normals.size() + charSDO.specials.size() + charSDO.supers.size() + charSDO.genei_jin_normals.size();
                break;
            default:
                throw new UnsupportedOperationException("Type not supported.");
        }

        String frameUrl = FrameDataProvider.baseUrl + ResourceHelper.CharacterNames[charId] + "/" + moveTypeList[typeId] + "/" + properId + "/" + frameId;

        FrameHitBoxData frame = new FrameHitBoxData();
        frame.json = loadStringFromWebOperations(frameUrl + ".txt");
        frame.sprite = loadImageFromWebOperations(frameUrl + ".png");
        return frame;
    }

    public static FrameHitBoxData getMiscFrame(int charId, String key, int frameId){
        String properId = miscNames.get(key);
        if (properId == null) {
            throw new UnsupportedOperationException("Misc frame not supported.");
        }
        String frameUrl = FrameDataProvider.baseUrl + ResourceHelper.CharacterNames[charId] + "/" + moveTypeList[5] + "/" + properId + "/" + frameId;

        FrameHitBoxData frame = new FrameHitBoxData();
        frame.json = loadStringFromWebOperations(frameUrl + ".txt");
        frame.sprite = loadImageFromWebOperations(frameUrl + ".png");
        return frame;
    }

    private static Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static String loadStringFromWebOperations(String weblink) {
        String stringText = "";
        URL textUrl;
        try {
            textUrl = new URL(weblink);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
            String StringBuffer;
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            stringText = e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            stringText = e.toString();
        }
        return stringText;
    }
}
