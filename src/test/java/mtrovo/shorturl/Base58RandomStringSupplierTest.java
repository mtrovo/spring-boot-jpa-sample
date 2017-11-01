package mtrovo.shorturl;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class Base58RandomStringSupplierTest {
    @Test
    public void testStringSize() throws Exception {
        Base58RandomStringSupplier sup = new Base58RandomStringSupplier(10);
        assertEquals(10, sup.get().length());
    }

    @Test
    public void testIsRandom() throws Exception {
        Base58RandomStringSupplier sup = new Base58RandomStringSupplier(30);
        Set<String> strings = Stream.generate(sup).limit(1000).collect(Collectors.toSet());

        assertEquals(1000, strings.size());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testZeroLength() throws Exception {
        Base58RandomStringSupplier sup = new Base58RandomStringSupplier(0);
        fail("should have failed");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNegativeLength() throws Exception {
        Base58RandomStringSupplier sup = new Base58RandomStringSupplier(-10);
        fail("should have failed");
    }

    @Test
    public void validateContent() {
        Base58RandomStringSupplier sup = new Base58RandomStringSupplier(30);
        Stream.generate(sup).limit(10).forEach(System.out::println);
    }
}