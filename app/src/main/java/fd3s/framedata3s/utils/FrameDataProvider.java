package fd3s.framedata3s.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import fd3s.framedata3s.sdo.CharSDO;

/**
 * Single access point for retrieving the frame data,
 * using the Singleton pattern as there only needs to be one set of char data
 * in context.
 *
 * Created by Waseem Suleman on 10/01/15.
 */
public class FrameDataProvider {
    private static FrameDataProvider me;
    private static int characterId;
    private CharSDO charSDO;

    public static FrameDataProvider getInstance(int characterId, Context context){
        if(me == null || FrameDataProvider.characterId != characterId){
            FrameDataProvider.characterId = characterId;
            me = new FrameDataProvider(ResourceHelper.CharacterNames[characterId] + ".txt", context);
        }

        return me;
    }

    private FrameDataProvider(String filename, Context context){
        this.charSDO = getCharDataFromFile(filename, context);
    }

    private CharSDO getCharDataFromFile (String filename, Context context) {
        AssetManager manager = context.getAssets();

        byte[] fileInput = null;
        String sJson = "";
        CharSDO charSDO = new CharSDO();

        try {
            InputStream file = manager.open(filename);
            fileInput = new byte[file.available()];
            file.read(fileInput);
            file.close();
            sJson = new String(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            charSDO = gson.fromJson(sJson, CharSDO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return charSDO;
    }

    public CharSDO getCharSDO(){
        return charSDO;
    }
}
