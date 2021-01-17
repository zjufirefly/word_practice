package auto;

import java.util.ArrayList;
import java.util.Properties;

public class Dict {

    public static ArrayList<String> mesis = new ArrayList<>();

    public static ArrayList<Verb> verbs = new ArrayList<>();

    public static ArrayList<String> keiyous = new ArrayList<>();

    public static ArrayList<String> keiyoudosis = new ArrayList<>();

    public void init(Properties prop) {

        String lesson = prop.getProperty("lesson");

        String[] lessons = lesson.split(";");
        for (String les:lessons) {
            if (les.startsWith("n")) {
                String word = prop.getProperty(les);
                String[] words = word.split(";");
                for (String w: words) {
                    mesis.add(w);
                }
            }

            if (les.startsWith("v")) {
                String word = prop.getProperty(les);
                String[] words = word.split(";");
                for (String w: words) {
                    Verb v = new Verb();
                    v.verb = w.substring(0, w.length() -1);
                    v.type = Integer.parseInt(w.substring(w.length() -1, w.length()));
                    verbs.add(v);
                }
            }

            if (les.startsWith("a")) {
                String word = prop.getProperty(les);
                String[] words = word.split(";");
                for (String w: words) {
                    keiyous.add(w);
                }
            }

            if (les.startsWith("d")) {
                String word = prop.getProperty(les);
                String[] words = word.split(";");
                for (String w: words) {
                    keiyoudosis.add(w);
                }
            }
        }
    }
}
