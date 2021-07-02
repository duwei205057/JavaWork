package com.dw;

import java.util.Stack;

public class LeeCode {

    /**
     * 解压缩Demo
     * A[3|B[2|CD]]FG ABCDCDBCDCDBCDCDFG
     * @param args
     */

    public static void main(String[] args) {
        String input = "AMN[3|B[2|CD[2|K]]]FG[4|H[2|IJ]]";
        char lBracket = '[';
        char rBracket = ']';
        char separate = '|';
        char[] inputChars = input.toCharArray();
        StringBuilder total = new StringBuilder();
        StringBuilder num = new StringBuilder();
        StringBuilder alphabet = new StringBuilder();
        Stack<CharSequence> stack = new Stack<>();
        int level = 0;
        boolean getNum = false;
        for (char c : inputChars) {
            if (c == lBracket) {
                level++;
                getNum = true;
                if (alphabet.length() > 0) {
                    stack.push(alphabet.toString());
                    alphabet.setLength(0);
                }
            } else if (c == separate) {
                getNum = false;
                stack.push(num.toString());
                num.setLength(0);
            }else if (c == rBracket) {
                level--;
                if (alphabet.length() > 0) {
                    stack.push(alphabet.toString());
                    alphabet.setLength(0);
                }
            } else {
                if (getNum) {
                    num.append(c);
                } else {
                    alphabet.append(c);
                }
            }
            if (level == 0) {
                StringBuilder append = new StringBuilder();
                boolean isNum = false;
                while(!stack.empty()) {
                    if (!isNum) {
                        CharSequence sequence = stack.pop();
                        append.insert(0, sequence);
                        isNum = true;
                    } else {
                        CharSequence sequence = stack.pop();
                        int n = Integer.parseInt(sequence.toString());
                        String v = append.toString();
                        append.setLength(0);
                        while(n-- > 0) {
                            append.append(v);
                        }
                        isNum = false;
                    }
                }
                total.append(append);
            }
        }
        System.out.println(total);
    }


}
