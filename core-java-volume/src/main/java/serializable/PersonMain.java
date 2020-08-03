package serializable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/18
 * @Description:
 */
public class PersonMain {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("oos.txt"));
        Person person = new Person();
        Serializable serializable = new Person();
        oos.writeObject(person);
    }
}
