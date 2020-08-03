package annotation;

import jdk.Exported;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/30
 * @Description:
 */
@MyAnnotation
@Exported
public class Main {
    /*

      #24 = Utf8               Lannotation/MyAnnotation;  #24 = Utf8               Lannotation/MyAnnotation;
      #30 = Utf8               Ljdk/Exported;

      方法注解会在方法下加入
      RuntimeVisibleAnnotations:
      0: #24()
     */
    @MyAnnotation
    public static void main(String[] args) throws Exception {
        /*
        类注解会在字节码文件中加入 RUNTIME
        RuntimeVisibleAnnotations:
          0: #24()
          1: #30()

        RuntimeVisibleAnnotations:
            0: #31()
        RuntimeInvisibleAnnotations: CLASS
            0: #24()
         */
        Class clazz = Class.forName("annotation.Main");
        MyAnnotation myAnnotation = (MyAnnotation) clazz.getAnnotation(MyAnnotation.class);
        System.out.println(myAnnotation);
    }
}
