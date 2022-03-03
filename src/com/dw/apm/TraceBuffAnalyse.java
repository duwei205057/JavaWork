package com.dw.apm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * 原逻辑问题
 * 1. 没有out的方法，耗时时间不准确
 * 2. treenode结构中部分节点parent不对
 */
public class TraceBuffAnalyse {

    static String[] methodNames;

    public static void main(String[] args) {
        try {
            File f = new File("src/amp.txt");
            DataInputStream dis = new DataInputStream(new FileInputStream(f));

            int size = dis.readInt();
            long buf[] = new long[size];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = dis.readLong();
            }
            int methodNameSize = dis.readInt();
            methodNames = new String[methodNameSize];
            for (int i = 0; i < methodNameSize; i++) {
                methodNames[i] = dis.read
            }

            System.out.println("end");
            dis.close();

            TraceBuffAnalyse traceBuffAnalyse = new TraceBuffAnalyse();
            traceBuffAnalyse.print(buf);
            traceBuffAnalyse.analyse(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isIn(long trueId) {
        return ((trueId >> 63) & 0x1) == 1;
    }

    private int getMethodId(long trueId) {
        return (int) ((trueId >> 43) & 0xFFFFFL);
    }

    private long getTime(long trueId) {
        return trueId & 0x7FFFFFFFFFFL;
    }

    //针对循环的处理，若与上一个函数相同，则时间累计
    private void addMethodItem(LinkedList<MethodItem> resultStack, MethodItem methodItem) {
        MethodItem lastMethodItem = null;
        if (!resultStack.isEmpty()) {
            lastMethodItem = resultStack.peek();
        }
        if (null != lastMethodItem && lastMethodItem.equals(methodItem)) {
            lastMethodItem.addMore(methodItem.durTime);
        } else {
            resultStack.push(methodItem);
        }
    }

    private TreeNode stackToTree(LinkedList<MethodItem> resultStack,LinkedList<MethodItem> topMethodStack) {
        TreeNode root = new TreeNode(null, null);
        TreeNode lastNode = null;
        ListIterator<MethodItem> iterator = resultStack.listIterator(0);

        while (iterator.hasNext()) {
            MethodItem item = iterator.next();
            TreeNode node = new TreeNode(item, lastNode);
            if (null == lastNode && node.depth() != 0) {
                break;
            }
            // ApmLog.i(TAG, "node :%s", node.mItem.toString());
            int depth = node.depth();
            if (depth == 0) {
                topMethodStack.push(item);
                root.push(node);
                node.mFather = root;
            } else if (null != lastNode && lastNode.depth() >= depth) {
                while (lastNode.depth() > depth) {
                    lastNode = lastNode.mFather;
                    // ApmLog.e(TAG, "node :%s lastNode:%s", node.depth(), lastNode.depth());
                }
                if (lastNode.mFather != null) {
                    node.mFather = lastNode.mFather;
                    lastNode.mFather.push(node);
                }

            } else if (null != lastNode && lastNode.depth() < depth) {
                lastNode.push(node);
            }
            lastNode = node;
        }
        return root;
    }

    public void print(long[] buffer) {
        try {
            File f = new File("src/buf-print.txt");
            FileWriter fw = new FileWriter(f);
            for (long trueId : buffer) {
                int methodId = getMethodId(trueId);
                if (methodId < 500) {
                    fw.write("message ---------- " + methodId);
                }
                fw.write("in ? " + isIn(trueId) + " methodId : " + getMethodId(trueId) + " time : " + getTime(trueId) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void analyse(long[] buffer) {
        LinkedList<Long> rawData = new LinkedList<>();
        LinkedList<MethodItem> resultStack = new LinkedList<>();

        for (long trueId : buffer) {
            if (isIn(trueId)) {
                rawData.push(trueId);
            } else {
                int methodId = getMethodId(trueId);
                if (!rawData.isEmpty()) {
                    long in = rawData.pop();
                    while (getMethodId(in) != methodId) {
                        //complete out for not found in
                        System.out.println("complete out for not found in");
//                        long outTime = analyseExtraInfo.happenTime;
//                        long inTime = getTime(in);
//                        long during = outTime - inTime;
                        long during = -1;

                        int methodId2 = getMethodId(in);
                        MethodItem methodItem = new MethodItem(methodId2, (int) during, rawData.size());
                        addMethodItem(resultStack, methodItem);

                        if (!rawData.isEmpty()) {
                            in = rawData.pop();
                        } else {
                            in = 0;
                            break;
                        }
                    }
                    long outTime = getTime(trueId);
                    long inTime = getTime(in);
                    long during = outTime - inTime;
                    MethodItem methodItem = new MethodItem(methodId, (int) during, rawData.size());
                    addMethodItem(resultStack, methodItem);
                } else {
                }
            }
        }
        boolean missOut = false;
        // maybe ANR or Full
        while (!rawData.isEmpty()) {
            long trueId = rawData.pop();
            int methodId = getMethodId(trueId);
//            long inTime = getTime(trueId);
//            MethodItem methodItem = new MethodItem(methodId, Math.min((int) (analyseExtraInfo.happenTime - inTime), Constants.DEFAULT_ANR), rawData.size());
//            addMethodItem(resultStack, methodItem);
//            missOut = true;
        }
        LinkedList<MethodItem> finalResultStack = new LinkedList<>();
        LinkedList<MethodItem> topMethodStack = new LinkedList<>();//存储depth=0的方法节点
//        List<Map.Entry<Integer, Integer>> topRepeatMethods = getTopRepeatMethods(resultStack, 20);
//        String topRepeatsMethodsInfo = combineMethodsInfo(topRepeatMethods);
        int originStackCount = resultStack.size();
        TreeNode root = stackToTree(resultStack,topMethodStack);
        findNode(root);

        int delCount = 0;

        int round = 1;
        boolean ret = false;//trim后的标记，ret为true时表示过滤后方法已经没有了，这时将所有根节点上报

        trimResultStack(root, null);
        int remainCount = getChildCount(root);
        delCount = originStackCount - remainCount;
        System.out.println("total " + originStackCount + " node , del " + delCount + " node" + " , remainCount = " + remainCount);

        updateTree(root);
        System.out.println("create tree");
//        if(!ret) {
//            makeKey(root, analyseExtraInfo);
//            preTraversalTree(root, finalResultStack);
//        }else {
//            finalResultStack = topMethodStack;
//        }
//        if (finalResultStack.isEmpty()) {
//            return;
//        }
//        ListIterator<MethodItem> listIterator = finalResultStack.listIterator();
//        StringBuilder printBuilder = new StringBuilder("\n"); // print to logcat
//        StringBuilder reportBuilder = new StringBuilder(); // report result
//        while (listIterator.hasNext()) {
//            MethodItem item = listIterator.next();
//            printBuilder.append(item.print()).append('\n');
//            reportBuilder.append(item.toString()).append('\n');
//        }
//        String key;
//        StringBuilder keyBuilder = new StringBuilder(); // key for cache
//        if(!ret) {
//            getKey(root, keyBuilder);
//        }else {
//            getKey2(finalResultStack,keyBuilder);
//        }
//        key = keyBuilder.toString();
    }

    private void updateTree(TreeNode root) {
        try {
            HashMap<String, String> methodMap = new HashMap<>();
            File f = new File("src/Debug.methodmap");
            BufferedReader fileReader = new BufferedReader(new FileReader(f));
            String str;
            while ((str = fileReader.readLine()) != null) {
                int firstIndex = str.indexOf(",");
                int lastIndex = str.lastIndexOf(",");
                String num = str.substring(0, firstIndex);
                String name = str.substring(lastIndex + 1);
                methodMap.put(num, name);
            }
            fileReader.close();

            LinkedList<TreeNode> l = new LinkedList<TreeNode>();
            l.add(root);
            while (!l.isEmpty()) {
                TreeNode node = l.pop();
                int subTotalCost = 0;
                Iterator<TreeNode> iterator = node.mChildNodes.iterator();
                while (iterator.hasNext()) {
                    TreeNode subNode = iterator.next();
                    subTotalCost += subNode.mItem.durTime;
                    l.add(subNode);
                }
                if (node.mItem != null) {
                    String name = methodMap.get(node.mItem.methodId + "");
                    if (name == null) {
                        name = "";
                    }
                    node.setDesc(name + " " + node.mItem.methodId + " " + node.mItem.durTime + " sub:" + subTotalCost);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final class TreeNode {
        MethodItem mItem;
        TreeNode mFather;
        String desc = "";

        LinkedList<TreeNode> mChildNodes = new LinkedList<>();

        TreeNode(MethodItem item, TreeNode father) {
            this.mItem = item;
            this.mFather = father;
        }

        private int depth() {
            if (null == mItem) {
                return 0;
            } else {
                return mItem.depth;
            }
        }

        public void setDesc(String s) {
            desc = s;
        }

        private void push(TreeNode node) {
            mChildNodes.push(node);
        }

        private boolean isLeaf() {
            return mChildNodes.isEmpty();
        }

        public String toString() {
            return desc;
        }
    }

    private static final class MethodItem {
        int methodId;
        int durTime;
        int depth;
        int count = 1;

        MethodItem(int methodId, int durTime, int depth) {
            this.methodId = methodId;
            this.durTime = durTime;
            this.depth = depth;
        }

        @Override
        public String toString() {
            return depth + "," + methodId + "," + count + "," + durTime;
        }

        public String getKey() {
            return depth + "," + methodId + "," + count;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MethodItem) {
                MethodItem leftObj = (MethodItem) obj;
                if (leftObj.methodId == methodId && leftObj.depth == depth) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        public void addMore(long cost) {
            count++;
            durTime += cost;
        }

        public String print() {
            StringBuffer inner = new StringBuffer();
            for (int i = 0; i < depth; i++) {
                inner.append('.');
            }
            return inner.toString() + methodId + " " + count + " " + durTime;
        }
    }

    private boolean trimResultStack(TreeNode node, TreeNode parentNode) {
        long durTime = node.mItem == null ? -1 : node.mItem.durTime;

        boolean removed = false;
        if (parentNode != null && durTime >= 0) {
            long parentCost = parentNode.mItem == null ? -1 : parentNode.mItem.durTime;
            long minCost = (long) (parentCost * 0.05);  //will be 0.1, 0.2, 0.3
            if (durTime <= minCost) {
                removed = true;
            }
        }

        if (!removed) {
            Iterator<TreeNode> iterator = node.mChildNodes.iterator();
            while (iterator.hasNext()) {
                TreeNode tmpNode = iterator.next();
                boolean ret = trimResultStack(tmpNode, node);
                if (ret) {
                    iterator.remove();
                }
            }
        }

        return removed;
    }

    private int getChildCount(TreeNode node) {
        int count = node.mChildNodes.size();
        Iterator<TreeNode> iterator = node.mChildNodes.iterator();
        while (iterator.hasNext()) {
            TreeNode tmpNode = iterator.next();
            count += getChildCount(tmpNode);
        }
        return count;
    }

    private void findNode (TreeNode root) {
        LinkedList<TreeNode> l = new LinkedList<TreeNode>();
        l.add(root);
        while (!l.isEmpty()) {
            TreeNode node = l.pop();
            if (node.mChildNodes != null && node.mChildNodes.size() > 0) {
                l.addAll(node.mChildNodes);
            }
            if (node.mItem != null && node.mItem.durTime < 0) {
                StringBuilder sb = new StringBuilder();
                while (node != null && node.mItem != null) {
                    sb.append(node.mItem.methodId + "-------");
                    node = node.mFather;
                }
                System.out.println(sb);
            }
        }
    }
}
