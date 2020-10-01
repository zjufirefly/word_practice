package auto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Properties _prop = new Properties();

    private static WordType _wordType;

    private static TimeType _timeType;

    private static YesNoType _yesNoType;

    private static  PoliteType _politeType;

    private static Dict _dict;

    private static Random _random = new Random();

    public static void main( String[] args ) throws InterruptedException {
        // read cfg file
        readProperties();

        // choose word type
        App app = new App();
        _wordType = app.chooseWordType();

        _timeType = app.chooseTimeType();

        _yesNoType = app.chooseYesNoType();

        _politeType = app.choosePoliteType();


        // 加载单词
        loadWord();

        while (true) {
            // 生产单词
            QA qa = generalQA();
            System.out.println(qa.question);
            Thread.sleep(4000);


            Scanner scan = new Scanner(System.in);
            String command = null;
            System.out.flush();
            System.out.println(qa.answer);
            Thread.sleep(1000);


//            command = scan.nextLine();
//            if (command.equals("")) {
//                // print result
//                System.out.print(qa.answer);
//            }
//            command = scan.nextLine();
//            if (command.equals("")) {
//                // print result
//                continue;
//            }
//            if (command.equals("menu")) {
//
//            }
//            if (command.equals("exit")) {
//                return;
//            }
        }
    }

    public static QA generalQA() {
        QA qa = new QA();
        PoliteType curPolite = getPoliteType();
        TimeType curTime = getTimeType();
        YesNoType curYesNo = getYesNoType();

        String question = "";
        if (_wordType.equals(WordType.MESI))  {
            int wordIndex = _random.nextInt(Dict.mesis.size());
            String word = Dict.mesis.get(wordIndex);
            question += word + " ";
            question += curPolite.getName();
            question += " " + curTime.getName();
            question += "" + curYesNo.getName();

            qa.question = question;

            if (curPolite.equals(PoliteType.NORMAL)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + " だ";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + " ではない";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + " だった";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + " ではなかった";
                    }
                }
            }

            if (curPolite.equals(PoliteType.POLITE)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + " です";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + " ではありません";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + " でした";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + " ではありませんでした";
                    }
                }
            }
            return qa;
        }

        return  null;
    }

    public static PoliteType getPoliteType() {
        if (_politeType.equals(PoliteType.ALL)) {
            int bound = PoliteType.values().length -1;
            return PoliteType.getPoliteType(_random.nextInt(bound) + 1);
        } else {
            return _politeType;
        }
    }

    public static TimeType getTimeType() {
        if (_timeType.equals(TimeType.ALL)) {
            int bound = TimeType.values().length -1;
            return TimeType.getTimeType(_random.nextInt(bound) + 1);
        } else {
            return _timeType;
        }
    }

    public static YesNoType getYesNoType() {
        if (_yesNoType.equals(YesNoType.ALL)) {
            int bound = YesNoType.values().length -1;
            return YesNoType.getYesNoType(_random.nextInt(bound) + 1);
        } else {
            return _yesNoType;
        }
    }

    public static void loadWord() {
        _dict = new Dict();
        _dict.init(_prop);
    }

    public static void readProperties(){
        try {
            InputStream in = App.class.getResourceAsStream("/cfg.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));


            _prop.load(bf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public WordType chooseWordType() {

        while (true) {
            WordType[]  wordTypes = WordType.values();

            // default
            int idefaultType = 0;
            String strWordType = _prop.getProperty("wordType");
            try {
                idefaultType = Integer.parseInt(strWordType);
            } catch (Exception e) {
                idefaultType = 1;
            }

            WordType defaultType = WordType.getWordInfo(idefaultType);
            System.out.println("================================================================================");
            System.out.println("选择单词类型:");
            System.out.println("0.默认[" + defaultType.getName() + "]");

            for (WordType wordType: wordTypes) {
                System.out.println("" + wordType.getIndex() + "." + wordType.getName());
            }
            System.out.println("");

            Scanner scan = new Scanner(System.in);
            String type = null;
            System.out.print("$ ");
            type = scan.nextLine();


            Integer intType;
            if (type.equals("")) {
                intType = idefaultType;
            } else {
                intType = Integer.parseInt(type);
            }

            WordType wordType = WordType.getWordInfo(intType);
            if (wordType == null) {
                continue;
            }

            System.out.println();
            System.out.println();
            return wordType;
        }
    }

    public TimeType chooseTimeType() {

        while (true) {
            TimeType[]  timeTypes = TimeType.values();

            // default
            int idefaultType = 0;
            String strTimeType = _prop.getProperty("timeType");
            try {
                idefaultType = Integer.parseInt(strTimeType);
            } catch (Exception e) {
                idefaultType = 1;
            }

            TimeType defaultType = TimeType.getTimeType(idefaultType);

            System.out.println("================================================================================");
            System.out.println("选择时间类型:");
            System.out.println("0.默认[" + defaultType.getName() + "]");

            for (TimeType timeType: timeTypes) {
                System.out.println("" + timeType.getIndex() + "." + timeType.getName());
            }
            System.out.println("");

            Scanner scan = new Scanner(System.in);
            String type = null;
            System.out.print("$ ");
            type = scan.nextLine();

            if (type == null) {
                continue;
            }

            Integer intType;
            if (type.equals("")) {
                intType = idefaultType;
            } else {
                intType = Integer.parseInt(type);
            }


            TimeType timeType = TimeType.getTimeType(intType);
            if (timeType == null) {
                continue;
            }

            System.out.println();
            System.out.println();
            return timeType;
        }
    }

    public YesNoType chooseYesNoType() {

        while (true) {
            YesNoType[]  yesNoTypes = YesNoType.values();

            // default
            int idefaultType = 0;
            String strYesNoType = _prop.getProperty("yesNoType");
            try {
                idefaultType = Integer.parseInt(strYesNoType);
            } catch (Exception e) {
                idefaultType = 1;
            }

            YesNoType defaultType = YesNoType.getYesNoType(idefaultType);
            System.out.println("================================================================================");
            System.out.println("选择肯定否定类型:");
            System.out.println("0.默认[" + defaultType.getName() + "]");

            for (YesNoType yesNoType: yesNoTypes) {
                System.out.println("" + yesNoType.getIndex() + "." + yesNoType.getName());
            }
            System.out.println("");

            Scanner scan = new Scanner(System.in);
            String type = null;
            System.out.print("$ ");
            type = scan.nextLine();

            if (type == null) {
                continue;
            }
            Integer intType;
            if (type.equals("")) {
                intType = idefaultType;
            } else {
                intType = Integer.parseInt(type);
            }

            YesNoType yesNoType = YesNoType.getYesNoType(intType);
            if (yesNoType == null) {
                continue;
            }

            System.out.println();
            System.out.println();
            return yesNoType;
        }
    }


    public PoliteType choosePoliteType() {

        while (true) {
            PoliteType[]  politeTypes = PoliteType.values();

            // default
            int idefaultType = 0;
            String strPoliteType = _prop.getProperty("politeType");
            try {
                idefaultType = Integer.parseInt(strPoliteType);
            } catch (Exception e) {
                idefaultType = 1;
            }

            PoliteType defaultType = PoliteType.getPoliteType(idefaultType);

            System.out.println("================================================================================");
            System.out.println("选择普通礼貌类型:");
            System.out.println("0.默认[" + defaultType.getName() + "]");

            for (PoliteType politeType: politeTypes) {
                System.out.println("" + politeType.getIndex() + "." + politeType.getName());
            }
            System.out.println("");

            Scanner scan = new Scanner(System.in);
            String type = null;
            System.out.print("$ ");
            type = scan.nextLine();

            if (type == null) {
                continue;
            }
            Integer intType;
            if (type.equals("")) {
                intType = idefaultType;
            } else {
                intType = Integer.parseInt(type);
            }


            PoliteType politeType = PoliteType.getPoliteType(intType);
            if (politeType == null) {
                continue;
            }

            System.out.println();
            System.out.println();
            return politeType;
        }
    }
}
