package auto;

import java.util.HashMap;

public class VerbSound {

    public static HashMap<String, VerbSound> verbSounds = new HashMap<>();

    static {
        VerbSound verbSound = null;
        verbSound = new VerbSound();
        verbSound.A = "わ";
        verbSound.I = "い";
        verbSound.U = "う";
        verbSound.E = "え";
        verbSound.O = "お";
        verbSound.TE = "って";
        verbSound.TA = "った";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "か";
        verbSound.I = "き";
        verbSound.U = "く";
        verbSound.E = "け";
        verbSound.O = "こ";
        verbSound.TE = "いて";
        verbSound.TA = "いた";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "が";
        verbSound.I = "ぎ";
        verbSound.U = "ぐ";
        verbSound.E = "げ";
        verbSound.O = "ご";
        verbSound.TE = "いで";
        verbSound.TA = "いだ";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "さ";
        verbSound.I = "し";
        verbSound.U = "す";
        verbSound.E = "せ";
        verbSound.O = "そ";
        verbSound.TE = "して";
        verbSound.TA = "した";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "た";
        verbSound.I = "ち";
        verbSound.U = "つ";
        verbSound.E = "て";
        verbSound.O = "と";
        verbSound.TE = "って";
        verbSound.TA = "った";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "な";
        verbSound.I = "に";
        verbSound.U = "ぬ";
        verbSound.E = "ね";
        verbSound.O = "の";
        verbSound.TE = "んて";
        verbSound.TA = "んた";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "ば";
        verbSound.I = "び";
        verbSound.U = "ぶ";
        verbSound.E = "べ";
        verbSound.O = "ぼ";
        verbSound.TE = "んで";
        verbSound.TA = "んだ";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "ま";
        verbSound.I = "み";
        verbSound.U = "む";
        verbSound.E = "め";
        verbSound.O = "も";
        verbSound.TE = "んで";
        verbSound.TA = "んだ";
        verbSounds.put(verbSound.U, verbSound);

        verbSound = new VerbSound();
        verbSound.A = "ら";
        verbSound.I = "り";
        verbSound.U = "る";
        verbSound.E = "れ";
        verbSound.O = "ろ";
        verbSound.TE = "って";
        verbSound.TA = "った";
        verbSounds.put(verbSound.U, verbSound);
    }

    public static String getMasu(String sound) {
        return verbSounds.get(sound).I;
    }

    public static String getTA(String sound) {
        return verbSounds.get(sound).TA;
    }

    public static String getA(String sound) {
        return verbSounds.get(sound).A;
    }

    public String A;

    public String I;

    public String U;

    public String E;

    public String O;

    public String TE;

    public String TA;
}
