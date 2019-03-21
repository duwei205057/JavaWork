package com.dw.math;

import java.util.Stack;

public class TraversalUtils {


    private static void preOrder(Node n) {
        Stack<Node> s = new Stack();
        while (n != null || !s.empty()) {
            if ( n != null) {
                System.out.print(n);
                s.push(n);
                n = n.getLeft();
            } else {
                n = s.pop();
                n = n.getRight();
            }
        }
        System.out.println();
    }

    private static void midOrder(Node n) {
        Stack<Node> s = new Stack();
        while (n != null || !s.empty()) {
            if ( n != null) {
                s.push(n);
                n = n.getLeft();
            } else {
                n = s.pop();
                System.out.print(n);
                n = n.getRight();
            }
        }
        System.out.println();
    }

    private static void postOrder(Node n) {
        Stack<Node> s = new Stack();
        Node lastVisit = null;
        while (n != null || !s.empty()) {
            if ( n != null) {
                s.push(n);
                n = n.getLeft();
            } else {
                n = s.pop();
                Node r = n.getRight();
                if ( r == null || lastVisit == r) {
                    System.out.print(n);
                    lastVisit = n;
                    n = null;
                } else {
                    s.push(n);
                    n = r;
                }
            }
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Node a = new Node("A");
        a.append("B","C");
        a.getLeft().appendLeft("D");
        a.getRight().append("E","F");

        preOrder(a);
        midOrder(a);
        postOrder(a);
    }


    static class Node {
        String value;
        Node left;
        Node right;

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        Node append(String l , String r){
            this.left = new Node(l);
            this.right = new Node(r);
            return this;
        }

        Node appendLeft(String l){
            this.left = new Node(l);
            return this;
        }
        Node appendRigth(String r){
            this.right = new Node(r);
            return this;
        }

        Node (String value){
            this.value = value;
        }

        public String toString(){
            return value;
        }
    }
}
