import java.util.ArrayDeque;
import java.util.Deque;

public class LongestValidParentheses {

    public static int usingStack(String s) {
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

    public static int usingDP(String s) {
        int n = s.length(), max = 0;
        int[] dp = new int[n];

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else {
                    int j = i - dp[i - 1] - 1;
                    if (j >= 0 && s.charAt(j) == '(') {
                        dp[i] = dp[i - 1] + 2 + (j >= 1 ? dp[j - 1] : 0);
                    }
                }
                max = Math.max(max, dp[i]);
            }
        }
        return max;
    }

    public static int usingCounters(String s) {
        int open = 0, close = 0, max = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') open++; else close++;
            if (open == close) max = Math.max(max, 2 * close);
            else if (close > open) open = close = 0;
        }

        open = close = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') open++; else close++;
            if (open == close) max = Math.max(max, 2 * open);
            else if (open > close) open = close = 0;
        }
        return max;
    }

    public static void main(String[] args) {
        String[] inputs   = {"(()", ")()())", "", "(((", ")))", "()(()", "(()())", "()(())"};
        int[]    expected = {  2,      4,     0,   0,     0,      2,       6,        6   };

        boolean allPassed = true;
        for (int k = 0; k < inputs.length; k++) {
            String s = inputs[k];
            int a = usingStack(s), b = usingDP(s), c = usingCounters(s);
            boolean ok = a == expected[k] && b == expected[k] && c == expected[k];
            allPassed &= ok;
            System.out.printf("%-10s -> stack=%d dp=%d counters=%d expected=%d %s%n",
                    "\"" + s + "\"", a, b, c, expected[k], ok ? "PASS" : "FAIL");
        }
        System.out.println(allPassed ? "All tests passed." : "Some tests FAILED.");
    }
}
