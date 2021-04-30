package jdk;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BasicDataType {
    /**
     * 源码: 人类大脑容易理解
     * [+1] : 0000 0001
     * [-1] : 1000 0001
     */
    /**
     * 反码: 正数为本体, 负数符号为不变, 各位取反
     * [+1] : 0000 0001
     * [-1] : 1111 1110
     */
    /**
     * 补码: 正数为本体, 负数为反码+1 !!!! 计算机使用补码存储，好处有: 1. 可以不进行减法运算 2.表示范围多一位
     * [+1] : 0000 0001
     * [-1] : 1111 1111
     */

    // 整数溢出

    @Test
    public void temp1() {
        int int1 = 1;
        int int2 = -1;
        System.out.println(Integer.toBinaryString(int1));
        System.out.println(Integer.toBinaryString(int1>>1));
        System.out.println(Integer.toBinaryString(int1<<1));
        System.out.println(Integer.toBinaryString(int2));
    }

    @Test
    public void testByte() {
        // & | ^ >> << >>>

        // ^: XOR "1^0=1" "1^1=0"
        // a^b=c a^c=b b^c=a
        int a =10;
        byte b = (byte) a;
        String intToBinaryString = Integer.toBinaryString(a);
        char[] intToBinaryCharArray = intToBinaryString.toCharArray();
        byte[] intToByteArray = new byte[intToBinaryCharArray.length];
        for(int i = 0;i<intToBinaryCharArray.length;i++) {
            intToByteArray[i] = Byte.valueOf(String.valueOf(intToBinaryCharArray[i]));
        }
        System.out.println(Arrays.toString(intToByteArray));
        System.out.println(Integer.toBinaryString(~10));
        System.out.println(Integer.toBinaryString(-10));
    }
}
