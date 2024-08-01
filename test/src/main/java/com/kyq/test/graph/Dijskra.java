package com.kyq.test.graph;

import java.util.*;

public class Dijskra {
    public static List<RNode> nodeList = new ArrayList<>();
    public static List<List<RNode>> adjacencyList = new ArrayList<>();

    static {
        RNode node1 = new RNode(11305);
        RNode node2 = new RNode(11306);
        RNode node3 = new RNode(11307);
        RNode node4 = new RNode(11308);
        RNode node5 = new RNode(11309);
        RNode node6 = new RNode(11310);
        RNode node7 = new RNode(11311);
        nodeList.addAll(Arrays.asList(node1, node2, node3, node4, node5, node6, node7));
        adjacencyList.add(Arrays.asList(node2,node4,node6));
        adjacencyList.add(Arrays.asList(node1,node4,node7));
        adjacencyList.add(Arrays.asList(node4,node5));
        adjacencyList.add(Arrays.asList(node1,node2,node3,node5,node6));
        adjacencyList.add(Arrays.asList(node3,node4));
        adjacencyList.add(Arrays.asList(node1,node4));
        adjacencyList.add(Arrays.asList(node2));
    }

    public static void main(String[] args){
        int[][] nums = {
                {0,3,-1,-1,-1},
                {3,0,-1,10,20},
                {-1,-1,0,4,-1},
                {-1,10,4,0,7},
                {-1,20,-1,7,-1}
        };
//        getShortestPath2(nums, 0, 2);
        getShortestPath3(nodeList.get(5), nodeList.get(4));
    }

    public static void getShortestPath3(RNode src, RNode des){
        Map<RNode, Integer> result = new HashMap<>();
        Map<RNode, List<RNode>> pMap = new HashMap<>();
        Map<RNode, Boolean> visited = new HashMap<>();
        ArrayList<RNode> path = new ArrayList<>();
        result.put(src, 0);

        Queue<RNode> queue = new LinkedList<>();
        queue.add(src);
        while (!queue.isEmpty()){
            RNode curNode = queue.poll();

            if(visited.get(curNode)!=null && visited.get(curNode)){
                continue;
            }
            visited.put(curNode, true);
            path.add(curNode);

            //获取相邻的节点
            int nodeIdx = nodeList.indexOf(curNode);
            List<RNode> nearNodeList = adjacencyList.get(nodeIdx);

            for(RNode near : nearNodeList){
                Integer curW = result.get(curNode);
                Integer w = result.get(near);
                if(w == null || (curW != null && w > (curW + 1))){
                    //找到了路径
                    result.put(near, curW + 1);
                    List<RNode> tempPath = pMap.get(curNode);
                    if(tempPath == null){
                        tempPath = new ArrayList<>();
                    }
                    List<RNode> p = new ArrayList<>();
                    p.addAll(tempPath);
                    p.add(near);
                    pMap.put(near, p);
                    queue.add(near);
                }
            }

            if (curNode == des) {
                break;
            }
        }
        System.out.println(path);
    }

    public static int getShortestPath2(int[][] nums, int src, int des){
        int[] result = new int[nums.length];
        boolean[] visited = new boolean[nums.length];
        ArrayList<Integer> path = new ArrayList<>();

        Arrays.fill(result, 0x3f);  // 初始化为最大值
        result[src] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue();
        queue.add(src);
        while (!queue.isEmpty()){
            int nodeId = queue.poll();

            if(visited[nodeId]){
                continue;
            }

            visited[nodeId] = true;
            path.add(nodeId);
            for (int j = 0; j < nums.length; j++) {
                //节点curNode与节点j之间需要直接相连
                if (nums[nodeId][j] != -1) {
                    // 更新result[j]
                    if (result[j] > result[nodeId] + nums[nodeId][j]) {
                        result[j] = result[nodeId] + nums[nodeId][j];
                        queue.add(j);
                    }
                }
            }

            if (nodeId == des) {
                break;
            }
        }

        System.out.println("节点" + src + "到节点" + des + "经过的最短路径是：" + path);
        System.out.println("节点" + src + "到节点" + des + "之间的最短距离是：" + result[des]);
        return 0;
    }

    /**
     * Dijkstra算法，求任意两点之间的最短路径(朴素版本)
     *
     * @param nums：二维矩阵，代表两个节点之间的距离，其中-1代表两点之间无法直接相连
     * @param src：源节点
     * @param des：目的节点
     * @return 源节点到目的节点之间的最短距离
     */
    public static int getShortestPaths(int[][] nums, int src, int des) {
        int[] result = new int[nums.length]; // 用于存放节点src到其它节点的最短距离
        boolean[] visited = new boolean[nums.length]; // 用于判断节点是否被遍历
        ArrayList<Integer> path = new ArrayList<>(); // 用于存放(src, des)之间的最短路径

        // step1：初始化，将src节点设置为已经访问，并且初始化该节点到其他所有节点之间的距离
        visited[src] = true;
        path.add(src);

        for (int i = 0; i < nums.length; i++) {
            result[i] = nums[src][i];
        }

        while (true) {
            // step2：找到剩余未访问的节点中，距离src最近的一个节点，作为curNode
            int curNode = getNearestNode(nums, visited, result);

            // step3：更新所有节点距离src节点的最短距离（需要考虑curNode）
            update(nums, curNode, visited, result, path);

            System.out.println("选择节点 " + curNode + " 更新后的result为：" + Arrays.toString(result));
            if (curNode == des) {
                break;
            }
        }

        System.out.println("节点" + src + "到节点" + des + "经过的最短路径是：" + path);
        System.out.println("节点" + src + "到节点" + des + "之间的最短距离是：" + result[des]);
        return result[des];
    }

    // 找到剩余未访问的节点中，距离源节点最近的一个节点编号
    public static int getNearestNode(int[][] nums, boolean[] visited, int[] result) {
        int minIndex = -1;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i] && result[i] != -1 && result[i] < minLength) {
                minIndex = i;
                minLength = result[i];
            }
        }

        return minIndex;
    }

    // 更新与curNode节点直接相连接的未被访问的节点距离，专业术语叫 “松弛”
    public static void update(int[][] nums, int curNode, boolean[] visited, int[] result, ArrayList<Integer> path) {
        visited[curNode] = true;
        path.add(curNode);

        for (int j = 0; j < nums.length; j++) {
            // 节点j未访问过 && 节点curNode与节点j之间需要直接相连
            if (!visited[j] && nums[curNode][j] != -1) {
                // 更新result[j]
                if (result[j] > result[curNode] + nums[curNode][j] || result[j] == -1) {
                    result[j] = result[curNode] + nums[curNode][j];
                }
            }
        }
    }
}
