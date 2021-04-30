package jdk;

import java.util.List;

import org.junit.jupiter.api.Test;

import jdk.etc.ClassInit;
import jdk.etc.StaticInit;

public class InitTest {

    private int int1;
    private String string1;
    private List<String> stringList1;

    @Test
    public void testInit() {
        // ----- local variable -----
        int int1;
        String string1;
        List<String> stringList1;
        //
        System.out.println(this.int1);
        System.out.println(this.string1);
        System.out.println(this.stringList1);
        // ----- object variable -----
        ClassInit classInit = new ClassInit();
        System.out.println(classInit.getInt1());
        System.out.println(classInit.getString1());
        System.out.println(classInit.getStringList1());
        // ----- static object variable -----
        System.out.println(StaticInit.int1);
        System.out.println(StaticInit.string1);
        System.out.println(StaticInit.stringList1);

    }
}
