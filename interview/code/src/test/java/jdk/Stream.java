package jdk;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Stream {

    private List<Integer> intList1;
    private List<Integer> intList2;
    private List<String> strList1;
    private List<String> strList2;

    @BeforeEach
    public void beforeEach() {
        this.intList1 = new ArrayList<>(){{add(0);add(0);add(1);add(2);add(3);add(4);add(5);}};
        this.intList2 = new ArrayList<>(){{add(0);add(4);add(2);add(9);add(1);add(3);add(6);}};
        this.strList1 = new ArrayList<>(){{add("str1");add("str2");add("str3");add("str4");add("str5");}};
        this.strList2 = new ArrayList<>(){{add("str1");add("str2");add("str3");add("str4");add("str5");}};
    }

    @Test
    public void testMatch() {
        boolean r1 = this.strList1.stream().anyMatch(str -> str=="str1");
        Assertions.assertEquals(Boolean.TRUE, r1);
        boolean r2 = this.strList1.stream().noneMatch(str -> str=="strNull");
        Assertions.assertEquals(Boolean.TRUE, r2);
        boolean r3 = this.strList1.stream().allMatch(str -> str == "strNull");
        Assertions.assertEquals(Boolean.FALSE, r3);

    }

    @Test
    public void testFilter() {
        long c1 = this.intList1.stream().filter(i->i>2).count();
        Assertions.assertEquals(3, c1);
    }

    @Test
    public void testNullAndEmptu() {
        List<String> emptyList = new ArrayList<>();
        List<String> nullList = null;
        emptyList.stream().forEach(elem -> System.out.println(elem));
        this.strList1.stream().forEach(elem -> System.out.println(elem));
    }
}
