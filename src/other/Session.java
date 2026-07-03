package other;

/*  Singleton Pattern */
public class Session {

   
    private static Session instance;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }


    private String plaka;
    private int ucret;
    private long girisZamani;
    private boolean parktaMi;

    public String getPlaka() {
        return plaka;
    }
    public void setPlaka(String p) {
        plaka = p;
    }

    public int getUcret() {
        return ucret;
    }
    public void setUcret(int u) {
        ucret = u;
    }

    public long getGirisZamani() {
        return girisZamani;
    }
    public void setGirisZamani(long t) {
        girisZamani = t;
    }

    public boolean isParktaMi() {
        return parktaMi;
    }
    public void setParktaMi(boolean b) {
        parktaMi = b;
    }
}
