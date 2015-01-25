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
            R.drawable.yang_face, R.drawable.gill_face,
            R.drawable.esn_face
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
        LIST_ID
    }

    public static enum ListIds {
        NORMAL_ID("Normals"),
        SPECIAL_ID("Specials"),
        SUPER_ID("Supers"),
        OTHER_ID("Others"),
        GJ_NORMAL_ID("GJ Normals"),
        GJ_SPECIAL_ID("GJ Specials");
        private String title;
        public String getTitle(){return title;}
        private ListIds(String title){this.title = title;}
    }

    public static final String SelectScreenTitle = "SF3:3S Frame Data";
}
