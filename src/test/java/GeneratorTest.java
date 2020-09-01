import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * created by Kimone
 * date 2020/8/31
 */
public class GeneratorTest {
    @Test
    public void test_single() {
        assertEquals("select * from customer where (id='1')",SQLGenerator.generateSQL("(id='1')"));
        assertEquals("select * from customer where (contactName<>'aaa')",SQLGenerator.generateSQL("(contactName<>'aaa')"));
    }

    @Test
    public void test_AND() {
        assertEquals("select * from customer where ((id='1') and (region='cn'))",
                SQLGenerator.generateSQL("(id='1') AND (region='cn')"));
    }

    @Test
    public void test_OR() {
        assertEquals("select * from customer where ((id='1') or (region='CN'))",
                SQLGenerator.generateSQL("(id='1') OR (region='CN')"));
    }

    @Test
    public void test_NOT() {
        assertEquals("select * from customer where not (id='1')",
                SQLGenerator.generateSQL("!(id='1')"));
    }

    @Test
    public void test_complex() {
        assertEquals("select * from customer where (((id='1') and ((region='CN') or (companyName='HTSC'))) and not (postalCode=3))",
                SQLGenerator.generateSQL("(id='1') AND ((region='CN') OR (companyName='HTSC')) AND !(postalCode=3)"));
    }
}
