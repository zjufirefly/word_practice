package auto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Test {

    public static ArrayList<String> mesi = new ArrayList<>();

    public static void main(String[] args) {
        mesi.add("和老师说 我是学生");
        mesi.add("和老师说 我不是学生");
        mesi.add("和老师说 我过去是学生");
        mesi.add("和老师说 我过去不是学生");

        mesi.add("和同学说 我是学生");
        mesi.add("和同学说 我不是学生");
        mesi.add("和同学说 我过去是学生");
        mesi.add("和同学说 我过去不是学生");

        int i = 0;
        Random r = new Random();
        while (true) {
            i++;
            String str = mesi.get(r.nextInt(1000000)%8);
            System.out.print(i + str);

            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
        }
    }
}
