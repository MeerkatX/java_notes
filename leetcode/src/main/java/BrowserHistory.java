import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/7
 * @Description:
 */
public class BrowserHistory {


    public static void main(String[] args) {
//        BrowserHistory browserHistor2 = new BrowserHistory("icpbj.com");
//        System.out.println(browserHistor2.back(1));
//        System.out.println(browserHistor2.back(10));
//        browserHistor2.visit("xbepk.com");
//        System.out.println(browserHistor2.forward(8));
//        browserHistor2.visit("kls.com");
//        browserHistor2.visit("dlkw.com");
//        browserHistor2.visit("pnep.com");
//        System.out.println(browserHistor2.back(9));
//        browserHistor2.visit("rmis.com");
//        browserHistor2.visit("bxf.com");
//        browserHistor2.visit("dz.com");
//        System.out.println(browserHistor2.back(2));
//        browserHistor2.visit("acuqsax.com");
//        browserHistor2.visit("dcvo.com");
//        browserHistor2.visit("ijbg.com");
//        browserHistor2.visit("nlott.com");
//        System.out.println(browserHistor2.back(7));
//        browserHistor2.visit("dd.com");
//        browserHistor2.visit("vssnq.com");
//        browserHistor2.visit("teur.com");
//        browserHistor2.visit("pn.com");
//        browserHistor2.visit("szi.com");
//        browserHistor2.visit("brhldg.com");
//        browserHistor2.visit("yulyoqv.com");
//        System.out.println(browserHistor2.back(4));
//        System.out.println(browserHistor2.forward(10));
//        System.out.println(browserHistor2.back(8));
//        System.out.println(browserHistor2.forward(5));
//        browserHistor2.visit("av.com");
//        System.out.println(browserHistor2.back(3));


        BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
        browserHistory.visit("google.com");
        browserHistory.visit("facebook.com");
        browserHistory.visit("youtube.com");
        System.out.println(browserHistory.back(1));
        System.out.println(browserHistory.back(1));
        System.out.println(browserHistory.forward(1));
        browserHistory.visit("linkedin.com");
        System.out.println(browserHistory.forward(2));
        System.out.println(browserHistory.back(2));
        System.out.println(browserHistory.back(7));
    }

    Deque<String> f = new ArrayDeque<>();
    Deque<String> b = new ArrayDeque<>();

    public BrowserHistory(String homepage) {
        b.addLast(homepage);
    }

    public void visit(String url) {
        b.addLast(url);
        f.clear();
    }

    public String back(int steps) {
        for (int i = 0; i < steps; i++) {
            if (b.size() == 1) break;
            f.addLast(b.pollLast());
        }
        return b.peekLast();
    }

    public String forward(int steps) {
        for (int i = 0; i < steps; i++) {
            if (f.size() == 0) break;
            b.addLast(f.pollLast());
        }
        return b.peekLast();
    }
}
