package auto;

public class VerbConvert {

    public static String endChar(String str) {
        return str.substring(str.length()-1, str.length());
    }

    // type 0,自动判断 1,一类动词 2,二类动词 3，三类动词
    // verb 动词原形
    public static String convertMasu(String word, int type) {
        if (type == 1) {
            String endSound = VerbConvert.endChar(word);
            String newWord = word.substring(0, word.length() - 1) + VerbSound.getMasu(endSound);
            return newWord;
        }

        if (type == 2) {
            return word.substring(0, word.length() - 1);
        }

        if (type == 3) {
            if (word.equals("来る")||word.equals("くる")) {
                return "き";
            }

            if (word.endsWith("する")) {
                return word.substring(0, word.length() - 2) + "し";
            }
        }

        return null;
    }




    // type 0,自动判断 1,一类动词 2,二类动词 3，三类动词
    // verb 动词原形
    public static String convertTa(String word, int type) {
        if (type == 1) {
            String endSound = VerbConvert.endChar(word);
            String newWord = word.substring(0, word.length() - 1) + VerbSound.getTA(endSound);
            return newWord;
        }

        if (type == 2) {
            return word.substring(0, word.length() - 1) + "た";
        }

        if (type == 3) {
            if (word.equals("来る")||word.equals("くる")) {
                return "きた";
            }

            if (word.endsWith("する")) {
                return word.substring(0, word.length() - 2) + "した";
            }
        }

        return null;
    }

    // type 0,自动判断 1,一类动词 2,二类动词 3，三类动词
    // verb 动词原形
    public static String convertNai(String word, int type) {
        if (type == 1) {
            String endSound = VerbConvert.endChar(word);
            String newWord = word.substring(0, word.length() - 1) + VerbSound.getA(endSound);
            return newWord;
        }

        if (type == 2) {
            return word.substring(0, word.length() - 1);
        }

        if (type == 3) {
            if (word.equals("来る")||word.equals("くる")) {
                return "こ";
            }

            if (word.endsWith("する")) {
                return word.substring(0, word.length() - 2) + "し";
            }
        }

        return null;
    }

    // type 0,自动判断 1,一类动词 2,二类动词 3，三类动词
    // verb 动词原形
    public static String convertSeru(String word, int type) {
        if (type == 1) {
            String endSound = VerbConvert.endChar(word);
            String newWord = word.substring(0, word.length() - 1) + VerbSound.getA(endSound) + "せる";
            return newWord;
        }

        if (type == 2) {
            return word.substring(0, word.length() - 2)+"させる";
        }

        if (type == 3) {
            if (word.equals("来る")||word.equals("くる")) {
                return "こさせる";
            }

            if (word.endsWith("する")) {
                return word.substring(0, word.length() - 2) + "させる";
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String str = VerbConvert.convertMasu("帰る", 1);
        System.out.println(str);
        str = VerbConvert.convertMasu("起きる", 2);
        System.out.println(str);
        str = VerbConvert.convertMasu("くる", 3);
        System.out.println(str);
        str = VerbConvert.convertMasu("勉強する", 3);
        System.out.println(str);
        str = null;
    }
}
