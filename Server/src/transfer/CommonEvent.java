package transfer;
import java.io.Serializable;

public class CommonEvent<T>implements Serializable {

    private static final long serialVersionUID=544271018L;
    private String type;
    private T t;

    public CommonEvent(String type, T t) {
        this.type = type;
        this.t = t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
