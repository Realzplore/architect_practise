package com.practise.algorithm.firstSearch;

import java.util.*;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/17
 * 广度优先搜索算法
 * 用一个队列来存储已查询到的但还未搜索的节点。
 * 从起点开始入列，然后起点出列，遍历起点所连接的节点并入列，然后依次对队列的节点进行遍历，直到队列为空。
 */
public class FirstSearch {
    private static Node node1 = new Node("起点", 1);
    private static Node node2 = new Node("第二节点", 2);
    private static Node node3 = new Node("第三节点", 3);
    private static Node node4 = new Node("第四节点", 4);
    private static Node node5 = new Node("第五节点", 5);

    static {
        Set<Node> set = new HashSet<>();
        set.add(node2);
        set.add(node3);
        set.add(node4);
        node1.setNodeSet(set);

        Set<Node> set2 = new HashSet<>();
        set2.add(node3);
        set2.add(node5);
        node2.setNodeSet(set2);

        Set<Node> set3 = new HashSet<>();
        set3.add(node5);
        node3.setNodeSet(set3);
    }

    public static void main(String[] args) {
        List<Node> result = new ArrayList<>();

//        result = bfs(node1);
        dfs(node1, result);

        for (Node node:result) {
            System.out.println(node);
        }
    }

    /**
     * 广度算法
     * @param node
     * @return
     */
    private static List<Node> bfs(Node node) {
        List<Node> result = new ArrayList<>();
        Queue<Node> queue = new PriorityQueue<>();
        queue.add(node);
        while (queue.size() > 0) {
            Node temp = queue.poll();
            if (result.contains(temp) || queue.contains(temp)) {
                continue;
            }
            if (node.getNodeSet() != null) {
                Iterator<Node> iterator = temp.getNodeSet().iterator();
                while (iterator.hasNext()) {
                    Node next = iterator.next();
                    queue.add(next);
                }
            }
            result.add(temp);
        }
        return result;
    }

    private static void dfs(Node node, List<Node> list) {
        if (list.contains(node)) {
            return;
        }
        list.add(node);
        if (node.getNodeSet() != null) {
            Iterator<Node> iterator = node.getNodeSet().iterator();
            while (iterator.hasNext()) {
                dfs(iterator.next(), list);
            }
        }
    }

    static class Node implements Comparable{
        private String name;
        private Integer value;
        private Set<Node> nodeSet;

        public Node(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Set<Node> getNodeSet() {
            return nodeSet;
        }

        public void setNodeSet(Set<Node> nodeSet) {
            this.nodeSet = nodeSet;
        }

        @Override
        public int compareTo(Object o) {
            if (this.value > ((Node) o).getValue()) {
                return 1;
            } else if (this.value < ((Node) o).getValue()) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Node(name=" + this.name + ",value=" + this.value + ")";
        }
    }

}