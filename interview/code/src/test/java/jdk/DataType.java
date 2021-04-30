package jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * int[]
 * Integer List<Integer>
 *
 * String StringBuffer
 * String[] List<String>
 *
 * Set, Map
 */
public class DataType {
    // Collections.singletonList()

    @Test
    public void intMath() {
        int a = -5;
        int b = 5;
        int c= 666;
        double d = 6.66;
        int t = (int) d;
        Assertions.assertEquals(5, Math.abs(a));
        Assertions.assertEquals(5, Math.max(a,b));
        Assertions.assertEquals(625, Math.pow(a,4));
    }

    @Test
    public void traverse() {
        int[] intArray = {0,1,2};
        Integer[] integerArray = {0,1,2};
        List<Integer> integerList = new ArrayList<>(){{add(0);add(1);add(2);}};
        Set<Integer> integerSet = new HashSet<>(){{add(0);add(1);add(2);}};
        Arrays.stream(intArray).max().getAsInt();

        // baseType <--boxed--> referenceType
        int[] integerListToIntArray = integerList.stream().mapToInt(Integer::intValue).toArray();
        int[] integerSetToIntArray = integerSet.stream().mapToInt(Integer::intValue).toArray();
        int[] integerArrayToIntArray = Arrays.stream(integerArray).mapToInt(Integer::intValue).toArray();
        Integer[] intArrayToIntegerArray = Arrays.stream(intArray).boxed().toArray(Integer[]::new);
        List<Integer> intArrayToIntegerList = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        Set<Integer> intArrayToIntegerSet = Arrays.stream(intArray).boxed().collect(Collectors.toSet());

        // Array <--> List (only referenceType)
        Integer[] integerListToIntegerArray = integerList.toArray(new Integer[integerList.size()]);
        Integer[] integerListToIntegerArray2 = integerList.stream().toArray(Integer[]::new);
        List<Integer> integerArrayToIntegerList = Arrays.asList(integerArray);
        List<Integer> integerArrayToIntegerList2 = Arrays.stream(integerArray).map(Integer::intValue).collect(Collectors.toList());
        List<Integer> integerArrayToIntegerList3 = new ArrayList<>();
        Collections.addAll(integerArrayToIntegerList3, integerArray);

        // Array <--> Set (only referenceType)
        Integer[] integerSetToIntegerArray = integerSet.toArray(new Integer[integerSet.size()]);
        Integer[] integerSetToIntegerArray2 = integerSet.stream().toArray(Integer[]::new);
        Set<Integer> integerArrayToIntegerSet = Arrays.stream(integerArray).map(Integer::intValue).collect(Collectors.toSet());

        // List <--> Set (only referenceType)
        Set<Integer> integerListToIntegerSet = new HashSet<>(integerList);
        List<Integer> integerSetToIntegerList = new ArrayList<>(integerSet);

    }

    // Basic & Reference Array : init, get, update
    @Test
    public void intArray() {
        int[] intArray1 = {0,1,2};
        int[] intArray2 = new int[3];
        int[] intArray3 = {3,1,2};
        intArray2[0] = 0; intArray2[1] = 1; intArray2[2] = 2;

        System.out.println(Arrays.toString(intArray3));
        Arrays.sort(intArray3);
        System.out.println(Arrays.toString(intArray3));

        int arrayLength = intArray1.length;
        int getValue = intArray1[1];
        String arrayString = Arrays.toString(intArray1);

        int[] mergeArray = new int[intArray1.length + intArray2.length];
        System.arraycopy(intArray1, 0, mergeArray, 0, intArray1.length);
        System.arraycopy(intArray2, 0, mergeArray, intArray1.length, intArray2.length);

        // Arrays.Stream
        int[] subArray = Arrays.copyOfRange(intArray1, 2,3);
        int maxValue = Arrays.stream(intArray1).max().getAsInt();
        int sumArray = Arrays.stream(intArray1).sum();
        double getAverage = Arrays.stream(intArray1).average().getAsDouble();
        Arrays.stream(intArray1).distinct();

        // ----
        int[][] tIntArray = {{1,2,3},{4,5,6},{7,8,9}};
        for(int i=0; i<tIntArray.length;i++) {
            for(int j=0; j<tIntArray[0].length/2; j++) {
                int rj = tIntArray[0].length - j - 1;
                int t = tIntArray[i][j];
                tIntArray[i][j] = tIntArray[i][rj];
                tIntArray[i][rj] = t;
            }
        }
        for(int[] i : tIntArray) {
            System.out.println(Arrays.toString(i));
        }

    }


    // List : init, get, add, insert, update, remove
    @Test
    public void testList() {
        List<Integer> integerList1 = new ArrayList<>(){{add(0);add(1);add(2);}};
        List<Integer> integerList2 = new ArrayList<>();
        List<Integer> integerList4 = new ArrayList<>(){{add(3);add(1);add(2);}};
        integerList2.add(2); integerList2.add(3); integerList2.add(4);
        List<Integer> intersectionList = List.copyOf(integerList1);
        List<Integer> unionList = new ArrayList<>(){{add(0);add(1);add(2);}};
        List<Integer> complementList = new ArrayList<>(){{add(0);add(1);add(2);}};

        System.out.println(intersectionList.getClass());
        System.out.println(unionList.getClass());
        System.out.println(integerList4);
        integerList4.sort(Comparator.naturalOrder());
        System.out.println(integerList4);

        this.updateInIndex(integerList1, 2, new Integer(3));

        Integer getValue = integerList1.get(0);
        int listLength = integerList1.size();
        int getIntValue = integerList1.get(1).intValue();
        boolean isEmpty = integerList1.isEmpty();
        // modify List
        integerList1.add(0,2);
        integerList1.remove(2);
        integerList1.remove(Integer.valueOf(2));
        // intersection, union, complement
//        intersectionList.removeAll(integerList2);
        unionList.addAll(integerList2);
        complementList.retainAll(integerList2);
//        System.out.println(intersectionList);
//        System.out.println(unionList);
//        System.out.println(complementList);
        // traverse
        integerList1.stream().forEach(Int -> {System.out.println(Int);});
        System.out.println(integerList1);
        List<Integer> integerList3 = integerList1.stream().distinct().collect(Collectors.toList());
        System.out.println(integerList3);

        // =============

        List<Integer> integerList5 = new ArrayList<>(){{add(0);add(1);add(1);}};
        System.out.println(integerList5);
        integerList5.stream().distinct().collect(Collectors.toList());
        System.out.println(integerList5);
//        integerList5 = integerList5.Stream().distinct().collect(Collectors.toList());
        this.listDistinct(integerList5);
        System.out.println(integerList5);

    }

    private void listDistinct(List<Integer> list) {
        list = list.stream().distinct().collect(Collectors.toList());
    }

    private void updateInIndex(List<Integer> integerList, int index, Integer value) {
        integerList.remove(index);
        integerList.add(index, value);
    }

    // Set, init add(All) remove(All) contain(All), copy
    @Test
    public void testSet() {
        Set<Integer> integerHashSet1 = new HashSet<>(){{add(0);add(1);add(2);}};
        Set<Integer> integerHashSet2 = new HashSet<>(){{add(0);add(1);add(2);}};
        Set<Integer> integerLinkedHashSet1 = new LinkedHashSet<>(){{add(0);add(1);add(2);}};
        int hashSetSize = integerHashSet1.size();
        boolean isElemExist = integerHashSet1.contains(new Integer(1));
        integerHashSet1.remove(new Integer(1));
        // intersection 交
        // union 并
        Set<Integer> unionSet = Set.copyOf(integerHashSet1);
//        unionSet.addAll(integerHashSet2);
        System.out.println(integerHashSet1);
        System.out.println(unionSet);
//            integerHashSet1.addAll(integerLinkedHashSet1);
        // complement 补
        // integerHashSet1.retainAll()
//        integerLinkedHashSet1.
    }

    // Map
    @Test
    public void testMap() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "key2");
        String value1 = map1.get("key1");

        for(Map.Entry<String,String> entry : map1.entrySet()) {
            String key2 = entry.getKey();
            String value2 = entry.getValue();
        }
        for(String intKey : map1.keySet()) {
            String value3 = map1.get(intKey);
        }
    }

    // String, StringBuffer
    @Test
    public void stingStringBuffer() {
        char char1 = 'a';
        String string1 = "a,b,c";
        String string2 = "a";
        String string3 = "aabbbcccc";

        int stringLength = string1.length();
        String subString1 = string1.substring(2);
        char getChar = string1.charAt(1);
        boolean isContains = string2.contains("a");
        boolean isStartWith = string1.startsWith("a");
        boolean isEndWith = string1.endsWith("c");

        String[] stringArray1 = string1.split(",");
        char[] charArray = string1.toCharArray();

        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("a");
//        String string3 = stringBuffer1.toString();

        for(char i : string1.toCharArray()) {
            System.out.println(i);
        }
//        System.out.println(string1.getBytes());
//        System.out.println(string1.indexOf(1));
//
//        Assertions.assertEquals(Boolean.TRUE, str1 == "abc");
//        Assertions.assertEquals(Boolean.TRUE, str1.equals("abc"));
//        Assertions.assertEquals(Boolean.TRUE, str1 == str2);
//        Assertions.assertEquals(Boolean.TRUE, str1.equals(str2));
//
//        Assertions.assertEquals(Boolean.TRUE, str1 == strList.get(0));
//        Assertions.assertEquals(Boolean.TRUE, str1.equals(strList.get(0)));
//
//        System.out.println(str1.indexOf("b"));
//        System.out.println(str1.indexOf(1));
    }

}
