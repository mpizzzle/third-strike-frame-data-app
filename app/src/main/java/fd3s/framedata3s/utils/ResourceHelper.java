package fd3s.framedata3s.utils;

import fd3s.framedata3s.R;

public class ResourceHelper {

    /*Note that ordering of characters in ThumbIds and CharacterNames
     are the same at the moment. Might be worth pairing these values in a
     separate class*/

    public static final Integer[] ThumbIds = {
            R.drawable.gouki_face, R.drawable.yun_face,
            R.drawable.ryu_face, R.drawable.urien_face,
            R.drawable.remy_face, R.drawable.oro_face,
            R.drawable.necro_face, R.drawable.q_face,
            R.drawable.dudley_face, R.drawable.ibuki_face,
            R.drawable.chun_li_face, R.drawable.elena_face,
            R.drawable.sean_face, R.drawable.makoto_face,
            R.drawable.hugo_face, R.drawable.alex_face,
            R.drawable.twelve_face, R.drawable.ken_face,
            R.drawable.yang_face, R.drawable.gill_face
    };

    public static final String[] CharacterNames = {
            "Gouki", "Yun",
            "Ryu", "Urien",
            "Remy", "Oro",
            "Necro", "Q",
            "Dudley", "Ibuki",
            "Chun Li", "Elena",
            "Sean", "Makoto",
            "Hugo", "Alex",
            "Twelve", "Ken",
            "Yang", "Gill"
    };

    public static enum ResourceIds {
        CHARACTER_ID,
        NORMAL_ID,
        SPECIAL_ID,
        SUPER_ID,
        OTHER_ID,
        GJ_NORMAL_ID,
        GJ_SPECIAL_ID
    }

    public static final String SelectScreenTitle = "SF3:3S Frame Data";
}
