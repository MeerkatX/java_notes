package SPI;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @ClassName: SPIDemo
 * @Auther: MeerkatX
 * @Date: 2020-08-06 11:23
 * @Description:
 */
public class SPIDemo {
    public static void main(String[] args) {

        ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
        Iterator<Driver> driversIterator = loadedDrivers.iterator();
        while (driversIterator.hasNext()){
            System.out.println(driversIterator.next().toString());
        }
    }
}
