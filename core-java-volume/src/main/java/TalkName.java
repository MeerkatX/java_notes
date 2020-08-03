import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public interface TalkName {

    default String getName() {
        return "不知道";
    }
}
