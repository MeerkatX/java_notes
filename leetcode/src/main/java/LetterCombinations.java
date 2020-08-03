

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/12
 * @Description:
 */
public class LetterCombinations {


    List<String> ans = new ArrayList<>();
    Map<String, String> code = new HashMap<>();

    public static void main(String[] args) {
        List<String> ans = new LetterCombinations().letterCombinations("234");
        ans.stream().forEach(System.out::println);
    }

    public List<String> letterCombinations(String digits) {
        int y = digits.length();
        code.put("2", "abc");
        code.put("3", "def");
        code.put("4", "ghi");
        code.put("5", "jkl");
        code.put("6", "mno");
        code.put("7", "pqrs");
        code.put("8", "tuv");
        code.put("9", "wxyz");
        List<Character> characters = new ArrayList<>();
        letterCombinationsHelper(digits, 0, new StringBuilder());
        return ans;
    }

    private void letterCombinationsHelper(String digits, int y, StringBuilder characters) {
        if (y == digits.length()) {
            ans.add(characters.toString());
            return;
        }
        char d = digits.charAt(y);
        String c = code.get(String.valueOf(d));
        for (int i = 0; i < c.length(); i++) {
            characters.insert(y, c.charAt(i));
            letterCombinationsHelper(digits, y + 1, characters);
            characters.deleteCharAt(y);
        }
    }


}
