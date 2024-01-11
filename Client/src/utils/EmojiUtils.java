package utils;
import java.util.ArrayList;

public class EmojiUtils {
    public static ArrayList<Emoji>array=new ArrayList<>();

    static{

        for(int i=1;i<=188;i++)
        {
            Emoji e=new Emoji("C:\\Users\\zplaz\\Documents\\tencent files\\3403784285\\filerecv\\expression\\static\\"+i+".png",+i+".png");
            array.add(e);

        }


    }
}
