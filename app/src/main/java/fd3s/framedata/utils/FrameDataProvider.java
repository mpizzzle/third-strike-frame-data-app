package fd3s.framedata.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import fd3s.framedata.R;
import fd3s.framedata.sdo.CharSDO;
import fd3s.framedata.sdo.FrameHitBoxData;

/**
 * Created by Waseem Suleman on 18/01/15.
 */
public class FrameDataProvider {
    //https://s3-eu-west-1.amazonaws.com/3sfdfiles/hitBox/Alex/fd_normals/1/0.png
    private static String baseUrl = "https://s3-eu-west-1.amazonaws.com/3sfdfiles/hitBox/";
    private static final Map<String, String> miscNames = new HashMap<String, String>();
    private static Context context;
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

    public FrameDataProvider(Context context){
        FrameDataProvider.context = context.getApplicationContext();
    }

    public FrameHitBoxData getMoveFrame(CharSDO charSDO, int charId, ResourceHelper.ListIds type, int moveId, int frameId){
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

        String frameUrl = FrameDataProvider.baseUrl + (ResourceHelper.CharacterNames[charId].replace(' ', '+')) + "/" + moveTypeList[typeId] + "/" + properId + "/" + frameId;
        String filename = ResourceHelper.CharacterNames[charId] + "-" + moveTypeList[typeId] + "-" + properId + "-" + frameId;
        return getFrameHitBoxData(frameUrl, filename);
    }

    public FrameHitBoxData getMiscFrame(int charId, String key, int frameId){
        String properId = miscNames.get(key);
        if (properId == null) {
            throw new UnsupportedOperationException("Misc frame not supported.");
        }
        String frameUrl = FrameDataProvider.baseUrl + ResourceHelper.CharacterNames[charId] + "/" + moveTypeList[5] + "/" + properId + "/" + frameId;
        String filename = ResourceHelper.CharacterNames[charId] + "-" + moveTypeList[5] + "-" + properId + "-" + frameId;
        return getFrameHitBoxData(frameUrl, filename);
    }

    private FrameHitBoxData getFrameHitBoxData(String frameUrl, String filename){
        FrameHitBoxData frame = new FrameHitBoxData();

        frame.json = getTextFromFile(filename + ".txt");
        if(frame.json == null || frame.json.equals("")){
            loadStringFromWebOperations(filename + ".txt", frameUrl + ".txt");
            frame.json = getTextFromFile(filename + ".txt");
        }

        if(frame.json == null || frame.json.equals("")){
            frame.json = "Could not retrieve data";
        }

        frame.sprite = getDrawableFromFile(filename + ".png");
        if(frame.sprite == null){
            loadImageFromWebOperations(filename + ".png", frameUrl + ".png");
            frame.sprite = getDrawableFromFile(filename + ".png");
        }

        if(frame.sprite == null){
            frame.sprite = context.getResources().getDrawable(R.drawable.not_found);
        }

        return frame;
    }

    private void loadStringFromWebOperations(String filename, String weblink) {
        if(!isConnectedToNet()){
            return;
        }
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
            persistBytesToFile(filename, (stringText.toString()).getBytes());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            stringText = e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            stringText = e.toString();
        }
    }

    private String getTextFromFile(String filename){
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = context.openFileInput(filename);
            if(fis != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                if (fis != null) {
                    String read;
                    while ((read = reader.readLine()) != null) {
                        buffer.append(read + "\n");
                    }
                    fis.close();
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private Drawable getDrawableFromFile(String filename){
        Drawable sprite;
        File filePath = context.getFileStreamPath(filename);
        if(filePath != null) {
            sprite = Drawable.createFromPath(filePath.toString());
            return sprite;
        }
        return null;
    }

    public void loadImageFromWebOperations(String filename, String weblink) {
        if(!isConnectedToNet()){
            return;
        }
        try {
            URL u = new URL(weblink);
            URLConnection uc = u.openConnection();
            if(uc == null){
                return;
            }
            String contentType = uc.getContentType();
            if(contentType == null){
                return;
            }
            int contentLength = uc.getContentLength();
            if (contentType.startsWith("text/") || contentLength == -1) {
                throw new IOException("This is not a binary file.");
            }
            InputStream raw = uc.getInputStream();
            InputStream in = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int bytesRead = 0;
            int offset = 0;
            while (offset < contentLength) {
                bytesRead = in.read(data, offset, data.length - offset);
                if (bytesRead == -1)
                    break;
                offset += bytesRead;
            }
            in.close();

            if (offset != contentLength) {
                throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
            }
            persistBytesToFile(filename, data);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean persistBytesToFile(String filename, byte[] data){
        try {
            FileOutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            if(out != null) {
                out.write(data);
                out.flush();
                out.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private boolean isConnectedToNet(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        }

        if(prefs.getBoolean("WIFIONLY", false)){
            return false;
        }

        return connManager.getActiveNetworkInfo() != null;
    }
}
