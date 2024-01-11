
package utils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Emoji {


      String id;
      String imagepath;

    public Emoji( String imagepath,String id) {
        this.id = id;
        this.imagepath = imagepath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}


