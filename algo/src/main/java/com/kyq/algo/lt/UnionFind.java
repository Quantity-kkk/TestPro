package com.kyq.algo.lt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * Description： com.kyq.algo.lt
 * 并查集算法实现；并查集适用于分组问题；例：亲戚关系查找；
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-10-13 11:16
 */
public class UnionFind {
    /**
     * 记录节点元素和该元素所在分组的标识
     * */
    private int[] fa;

    /**
     * 记录节点元素树有多少层；
     * */
    private int[] rank;

    /**
     * 记录有多少集合；
     * */
    private int count;

    /**
     * 构造函数初始化
     * */
    public UnionFind(int num){
        this.count = num;
        this.fa = new int[num+1];
        this.rank = new int[num+1];

        for(int i = 1; i < fa.length; i++){
            fa[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int i){
        return i == fa[i] ? i : (fa[i] = find(fa[i]));
    }

    public void union(int i, int j){
        int x = find(i),  y = find(j);
        if( x != y){
            //进行了元素合并，集合数量相应减少；
            this.count--;
        }

        //按秩合并，将小集合合并到大集合中；
        if(rank[x] <= rank[y]){
            fa[x] = y;
        }else {
            fa[y] = x;
        }

        //如果深度相同但根节点不同，则新的根节点的深度+1；
        if(rank[x] == rank[y] && x != y){
            rank[y]++;
        }
    }

    @Override
    public String toString() {
        return "UnionFind{" +
                "fa=" + Arrays.toString(fa) +
                ", rank=" + Arrays.toString(rank) +
                ", count=" + count +
                '}';
    }

    /**
     * 输入关系 分离集合9 7 1
     * (2,4) {2,4}{1} {3} {5} {6} {7} {8} {9}
     * (5,7) {2,4} {5,7} {1} {3} {6} {8} {9}
     * (1,3) {1,3} {2,4} {5,7}{6} {8} {9}
     * (8,9) {1,3} {2,4} {5,7} {8,9}{6}
     * (1,2) {1,2,3,4} {5,7} {8,9}{6}
     * (5,6) {1,2,3,4} {5,6,7} {8,9}
     * (2,3) {1,2,3,4} {5,6,7} {8,9}
     * (1,9) No
     * */
    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        System.out.println("请输入参数n m p，使用空格分隔，表示n个人，m个亲戚关系，询问p对亲戚关系：");
        while (st.nextToken() != StreamTokenizer.TT_EOF){
            //n个人，m个亲戚关系，询问p对亲戚关系
            int n = (int) st.nval;
            st.nextToken();
            int m = (int) st.nval;
            st.nextToken();
            int p = (int)st.nval;

            UnionFind uf = new UnionFind(n);
            for(int i = 0; i < m; i++){
                System.out.println("请输入第"+(i+1)+"组亲戚关系x,y，使用空格分隔，表示x和y具有亲戚关系：");
                st.nextToken();
                int x = (int)st.nval;
                st.nextToken();
                int y = (int)st.nval;
                uf.union(x, y);
            }

            for(int j = 0; j < p; j++){
                System.out.println("询问第"+(j+1)+"组亲戚关系a,b，使用空格分隔，表示询问a和b是否具有亲戚关系：");
                st.nextToken();
                int a = (int)st.nval;
                st.nextToken();
                int b = (int)st.nval;
                a = uf.find(a);
                b = uf.find(b);
                System.out.println(a == b ? "Yes" : "No");
                System.out.println(uf);
            }
            System.out.println("请输入参数n m p，使用空格分隔，表示n个人，m个亲戚关系，询问p对亲戚关系(Ctrl+D结束)：");
        }
    }
}
