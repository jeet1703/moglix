import java.util.ArrayDeque;
import java.util.Deque;

public class LongestValidParentheses {

    // Stack of indices: O(n) time, O(n) space.
    public static int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        String[] inputs   = {"(()", ")()())", "", "(((", ")))", "()(()", "(()())", "()(())"};
        int[]    expected = {  2,      4,     0,   0,     0,      2,       6,        6   };

        boolean allPassed = true;
        for (int k = 0; k < inputs.length; k++) {
            int got = longestValidParentheses(inputs[k]);
            boolean ok = got == expected[k];
            allPassed &= ok;
            System.out.printf("%-10s -> %d expected=%d %s%n",
                    "\"" + inputs[k] + "\"", got, expected[k], ok ? "PASS" : "FAIL");
        }
        System.out.println(allPassed ? "All tests passed." : "Some tests FAILED.");
    }
}
