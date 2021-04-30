package algorithm.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackApplication {

    public static void main(String[] args) {
        StackApplication stackApplication = new StackApplication();
        stackApplication.trigger();
    }

    public void trigger() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("a");
        stringBuffer.append("b");
        stringBuffer.append("c");
        System.out.println(stringBuffer);
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        System.out.println(stringBuffer);
        stringBuffer.deleteCharAt(0);
        System.out.println(stringBuffer);

        ArrayList<String> strArrayList = new ArrayList<>();
        strArrayList.add("a");
        strArrayList.add("b");
        strArrayList.add("c");
        System.out.println(strArrayList);
        strArrayList.remove(strArrayList.size()-1);
        System.out.println(strArrayList);
        strArrayList.remove(0);
        System.out.println(strArrayList);

        String[] strArray = {"a", "b", "c"};
        System.out.println(strArray);
        System.out.println(strArrayList.toArray());

        ArrayList<Integer> intArrayList = new ArrayList<Integer>();
        intArrayList.add(1);
        intArrayList.add(2);
        intArrayList.add(3);
        System.out.println(intArrayList);

        int[] intArray = {1,2,3};
        int[] intArray2 = new int[10];
        intArray2[0] = 1;
        System.out.println( intArray2[0]);
        intArray2[0] = 2;
        System.out.println( intArray2[0]);

        List<String> strList = Arrays.asList(strArray);
//        strList.add()
        strArrayList.add("c");
        strArrayList.add("d");
        for(String s : strArrayList) {
            System.out.println(s);
        }

        for(int i: intArray) {
            System.out.println(i);
        }



        // stack
            // in(from end), out(from end)
        // queue
            // in(end), out(from 0)

    }

}
