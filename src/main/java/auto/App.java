package auto;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
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

    public static void textToSpeech(String text) {
        ActiveXComponent ax = null;
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");

            // 运行时输出语音内容
            Dispatch spVoice = ax.getObject();
            // 音量 0-100
            ax.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            ax.setProperty("Rate", new Variant(5));
            // 执行朗读
            Dispatch.call(spVoice, "Speak", new Variant(text));

            // 下面是构建文件流把生成语音文件

            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();

            // 设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            // 设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            // 调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("./text.wav"), new Variant(3), new Variant(true));
            // 设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            // 设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            // 设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(100));
            // 开始朗读
            Dispatch.call(spVoice, "Speak", new Variant(text));

            // 关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);

            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readJp(String jp) {
        // 创建与微软应用程序的新连接。传入的参数是注册表中注册的程序的名称。
        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");

        try {

            // 音量 0-100
            sap.setProperty("Volume", new Variant(100));

            // 语音朗读速度 -10 到 +10
            sap.setProperty("Rate", new Variant(2));


            // 获取执行对象
            Dispatch sapo = sap.getObject();

            // 执行朗读
            Dispatch.call(sapo, "Speak", new Variant(jp));

            // 关闭执行对象
            sapo.safeRelease();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

// 关闭应用程序连接

            sap.safeRelease();

        }

    }

//    java -classpath ".;E:\code\general\*" auto.App
    public static void main( String[] args ) throws InterruptedException {

//        while (1==1) {
//            Scanner scan = new Scanner(System.in);
//            String read = scan.nextLine();
//            readJp(read);
//
//            if (1 == 0) {
//                break;
//            }
//        }



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

        Date begin = new Date();
        int i = 0;
        while (true) {
            // 生产单词
            i++;
            System.out.println(i);
            QA qa = generalQA();
            System.out.print(qa.question);

            if (_prop.getProperty("read").equals("1")) {
                readJp(qa.readq);
            }
//            readJp(qa.readq);
//            Thread.sleep(1000);


            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();;
            System.out.flush();

            if (!_wordType.equals(WordType.ALL))  {
                System.out.println(qa.answer);
            }

//            Thread.sleep(1000);
//            command = scan.nextLine();
            if (command.equals("exit")) {
                Date end = new Date();
                System.out.println((end.getTime() - begin.getTime()) / 1000);
            }


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

            qa.readq = curPolite.getName() + curTime.getName() + curYesNo.getName();

            qa.question = "";
            if (curPolite.equals(PoliteType.POLITE)) {
                qa.question += "对老师说:";
            }
            if (curPolite.equals(PoliteType.NORMAL)) {
                qa.question += "对同学说:";
            }

            if (curTime.equals(TimeType.NOW)) {
                qa.question += "现在";
            }
            if (curTime.equals(TimeType.PAST)) {
                qa.question += "过去";
            }

            if (curYesNo.equals(YesNoType.YES)) {
                qa.question += "是";
            }

            if (curYesNo.equals(YesNoType.NO)) {
                qa.question += "不是";
            }
            qa.readq = qa.question;
            qa.question += word;
//            qa.question = question;

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

        if (_wordType.equals(WordType.DOSI))  {
            int wordIndex = _random.nextInt(Dict.verbs.size());
            Verb word = Dict.verbs.get(wordIndex);
            question += word.verb + " ";
            question += curPolite.getName();
            question += " " + curTime.getName();
            question += "" + curYesNo.getName();

            qa.readq = curPolite.getName() + curTime.getName() + curYesNo.getName();

            qa.question = question;

            qa.question = "";
            if (curPolite.equals(PoliteType.POLITE)) {
                qa.question += "对老师说:";
            }
            if (curPolite.equals(PoliteType.NORMAL)) {
                qa.question += "对同学说:";
            }

            if (curTime.equals(TimeType.NOW)) {
                qa.question += "现在";
            }
            if (curTime.equals(TimeType.PAST)) {
                qa.question += "过去";
            }

            if (curYesNo.equals(YesNoType.YES)) {
                qa.question += "";
            }

            if (curYesNo.equals(YesNoType.NO)) {
                qa.question += "没有";
            }
            qa.readq = qa.question;
            qa.question += "[" + word.verb + "]";


            if (curPolite.equals(PoliteType.NORMAL)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word.verb;
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = VerbConvert.convertNai(word.verb, word.type) + "ない";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = VerbConvert.convertTa(word.verb, word.type);
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = VerbConvert.convertNai(word.verb, word.type) + "なかった";
                    }
                }
            }

            if (curPolite.equals(PoliteType.POLITE)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ます";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ません";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ました";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ませんでした";
                    }
                }
            }
            return qa;
        }

        if (_wordType.equals(WordType.SERU))  {
            int wordIndex = _random.nextInt(Dict.verbs.size());
            Verb word = Dict.verbs.get(wordIndex);
            String seruWord = VerbConvert.convertSeru(word.verb, word.type);

            Verb verb = new Verb();
            verb.verb = seruWord;
            verb.type = 2;

            qa = generalDosiQA(word.verb, verb, curPolite, curTime, curYesNo);
            qa.question = "";
            if (curPolite.equals(PoliteType.POLITE)) {
                qa.question += "对老师说:";
            }
            if (curPolite.equals(PoliteType.NORMAL)) {
                qa.question += "对同学说:";
            }

            if (curTime.equals(TimeType.NOW)) {
                qa.question += "现在";
            }
            if (curTime.equals(TimeType.PAST)) {
                qa.question += "过去";
            }

            if (curYesNo.equals(YesNoType.YES)) {
                qa.question += "让";
            }

            if (curYesNo.equals(YesNoType.NO)) {
                qa.question += "没有让";
            }
            qa.readq = qa.question;
            qa.question += "[" + word.verb + "]";

            return qa;
        }


        if (_wordType.equals(WordType.KEYOSI))  {
            int wordIndex = _random.nextInt(Dict.keiyous.size());
            String word = Dict.keiyous.get(wordIndex);
            question += word + " ";
            question += curPolite.getName();
            question += " " + curTime.getName();
            question += "" + curYesNo.getName();

            qa.readq = curPolite.getName() + curTime.getName() + curYesNo.getName();

            qa.question = "";
            if (curPolite.equals(PoliteType.POLITE)) {
                qa.question += "对老师说:";
            }
            if (curPolite.equals(PoliteType.NORMAL)) {
                qa.question += "对同学说:";
            }

            if (curTime.equals(TimeType.NOW)) {
                qa.question += "";
            }
            if (curTime.equals(TimeType.PAST)) {
                qa.question += "过去";
            }

            if (curYesNo.equals(YesNoType.YES)) {
                qa.question += "";
            }

            if (curYesNo.equals(YesNoType.NO)) {
                qa.question += "不 ";
            }
            qa.readq = qa.question;
            qa.question += word;
//            qa.question = question;

            String removeWord = word.substring(0, word.length() -1);
            if (curPolite.equals(PoliteType.NORMAL)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word;
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = removeWord + "くない";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = removeWord + "かった";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = removeWord + "くなかった";
                    }
                }
            }

            if (curPolite.equals(PoliteType.POLITE)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + "です";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = removeWord + "くないです";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = removeWord + "かったです";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = removeWord + "くなかったです";
                    }
                }
            }
            return qa;
        }
        if (_wordType.equals(WordType.ALL))  {
            while (true) {
                int wordType = _random.nextInt(4);
                String word = null;
                if (wordType == 0) {
                    if (Dict.mesis.size() > 0) {
                        int wordIndex = _random.nextInt(Dict.mesis.size());
                        word = Dict.mesis.get(wordIndex);
                    }
                }

                if (wordType == 1) {
                    if (Dict.verbs.size() > 0) {
                        int wordIndex = _random.nextInt(Dict.verbs.size());
                        word = Dict.verbs.get(wordIndex).verb;
                    }
                }

                if (wordType == 2) {
                    if (Dict.keiyous.size() > 0) {
                        int wordIndex = _random.nextInt(Dict.keiyous.size());
                        word = Dict.keiyous.get(wordIndex);
                    }
                }

                if (wordType == 3) {
                    if (Dict.keiyoudosis.size() > 0) {
                        int wordIndex = _random.nextInt(Dict.keiyoudosis.size());
                        word = Dict.keiyoudosis.get(wordIndex);
                    }
                }

                if (word == null) {
                    continue;
                }

                question += word + " ";
                question += curPolite.getName();
                question += " " + curTime.getName();
                question += "" + curYesNo.getName();

                qa.readq = curPolite.getName() + curTime.getName() + curYesNo.getName();

                qa.question = "";
//                if (curPolite.equals(PoliteType.POLITE)) {
//                    qa.question += "对老师说:";
//                }
//                if (curPolite.equals(PoliteType.NORMAL)) {
//                    qa.question += "对同学说:";
//                }
//
//                if (curTime.equals(TimeType.NOW)) {
//                    qa.question += "";
//                }
//                if (curTime.equals(TimeType.PAST)) {
//                    qa.question += "过去";
//                }
//
//                if (curYesNo.equals(YesNoType.YES)) {
//                    qa.question += "";
//                }
//
//                if (curYesNo.equals(YesNoType.NO)) {
//                    qa.question += "不 ";
//                }
                qa.readq = qa.question;
                qa.question = word;
                qa.answer = word;
                return qa;
            }

        }
        if (_wordType.equals(WordType.KEYODOSI))  {
            int wordIndex = _random.nextInt(Dict.keiyoudosis.size());
            String word = Dict.keiyoudosis.get(wordIndex);
            question += word + " ";
            question += curPolite.getName();
            question += " " + curTime.getName();
            question += "" + curYesNo.getName();

            qa.readq = curPolite.getName() + curTime.getName() + curYesNo.getName();

            qa.question = "";
            if (curPolite.equals(PoliteType.POLITE)) {
                qa.question += "对老师说:";
            }
            if (curPolite.equals(PoliteType.NORMAL)) {
                qa.question += "对同学说:";
            }

            if (curTime.equals(TimeType.NOW)) {
                qa.question += "";
            }
            if (curTime.equals(TimeType.PAST)) {
                qa.question += "过去";
            }

            if (curYesNo.equals(YesNoType.YES)) {
                qa.question += "";
            }

            if (curYesNo.equals(YesNoType.NO)) {
                qa.question += "不 ";
            }
            qa.readq = qa.question;
            qa.question += word;
//            qa.question = question;

            if (curPolite.equals(PoliteType.NORMAL)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + "だ";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + "ではない";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + "だった";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + "ではなかった";
                    }
                }
            }

            if (curPolite.equals(PoliteType.POLITE)) {
                if (curTime.equals(TimeType.NOW)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + "です";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + "ではありません";
                    }
                }

                if (curTime.equals(TimeType.PAST)) {
                    if (curYesNo.equals(YesNoType.YES)) {
                        qa.answer = word + "でした";
                    }
                    if (curYesNo.equals(YesNoType.NO)) {
                        qa.answer = word + "ではありませんでした";
                    }
                }
            }
            return qa;
        }

        return  null;
    }

    public static QA generalDosiQA(String originWord, Verb word, PoliteType curPolite, TimeType curTime, YesNoType curYesNo) {
        QA qa = new QA();

        String question = "";
        question += originWord + " ";
        question += curPolite.getName();
        question += " " + curTime.getName();
        question += "" + curYesNo.getName();

        qa.readq = curPolite.getName() + curTime.getName() + curYesNo.getName();

        qa.question = question;

        if (curPolite.equals(PoliteType.NORMAL)) {
            if (curTime.equals(TimeType.NOW)) {
                if (curYesNo.equals(YesNoType.YES)) {
                    qa.answer = word.verb;
                }
                if (curYesNo.equals(YesNoType.NO)) {
                    qa.answer = VerbConvert.convertNai(word.verb, word.type) + "ない";
                }
            }

            if (curTime.equals(TimeType.PAST)) {
                if (curYesNo.equals(YesNoType.YES)) {
                    qa.answer = VerbConvert.convertTa(word.verb, word.type);
                }
                if (curYesNo.equals(YesNoType.NO)) {
                    qa.answer = VerbConvert.convertNai(word.verb, word.type) + "なかった";
                }
            }
        }

        if (curPolite.equals(PoliteType.POLITE)) {
            if (curTime.equals(TimeType.NOW)) {
                if (curYesNo.equals(YesNoType.YES)) {
                    qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ます";
                }
                if (curYesNo.equals(YesNoType.NO)) {
                    qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ません";
                }
            }

            if (curTime.equals(TimeType.PAST)) {
                if (curYesNo.equals(YesNoType.YES)) {
                    qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ました";
                }
                if (curYesNo.equals(YesNoType.NO)) {
                    qa.answer = VerbConvert.convertMasu(word.verb, word.type) + "ませんでした";
                }
            }
        }

        return qa;
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
