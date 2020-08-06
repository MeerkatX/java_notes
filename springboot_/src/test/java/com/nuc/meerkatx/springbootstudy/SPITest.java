package com.nuc.meerkatx.springbootstudy;


import org.junit.jupiter.api.Test;

import javax.servlet.ServletContainerInitializer;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @ClassName: SPITest
 * @Auther: MeerkatX
 * @Date: 2020-08-06 11:32
 * @Description:
 */
public class SPITest {


    @Test
    public void test() {

        //    private static final String PREFIX = "META-INF/services/";
        ServiceLoader<ServletContainerInitializer> loadedDrivers = ServiceLoader.load(ServletContainerInitializer.class);
        Iterator<ServletContainerInitializer> driversIterator = loadedDrivers.iterator();
        while (driversIterator.hasNext()) {
            System.out.println(driversIterator.next().toString());
        }
    }
}
