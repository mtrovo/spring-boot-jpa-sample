package mtrovo.shorturl;


import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Base58RandomStringSupplier implements Supplier<String> {
    private static final String DICT = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    private final int stringLength;
    private final Random rand;

    public Base58RandomStringSupplier(int stringLength) {
        Assert.isTrue(stringLength > 0, "must have a valid string length");

        this.stringLength = stringLength;
        this.rand = new SecureRandom();
    }

    @Override
    public String get() {
        String randString = this.rand.ints(0, DICT.length())
                .mapToObj(DICT::charAt)
                .map(Object::toString)
                .limit(this.stringLength)
                .collect(Collectors.joining());
        return randString;
    }
}
