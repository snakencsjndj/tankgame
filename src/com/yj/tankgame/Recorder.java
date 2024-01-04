package com.yj.tankgame;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

/*
该类用于记录相关信息 通过io流和文件交互
 */
public class Recorder {

    //定义变量 记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    //定义io对象,准备写数据到文件中
    private static BufferedWriter bw =null;
    private static BufferedReader br = null;
    private static String recordFile  = "d:\\myRecord.txt";
    //定义Vector 指向MyPanel 对象的敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;
    //定义一个Node 的Vector 用于保存敌人的信息node
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
   //增加一个方法，用于读取recorFile 恢复相关信息
   //该方法在继续上局的时候调用即可
    public static Vector<Node> getNodesAndEnemyTankrec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum =Integer.parseInt(br.readLine()) ;
            //循环读取文件 生成nodes集合
            String line = "";
            while ((line = br.readLine()) != null){
                String[] xyd= line.split(" ");
              Node node = new Node(Integer.parseInt(xyd[0]),Integer.parseInt(xyd[1]),Integer.parseInt(xyd[2]));
               nodes.add(node);//放入nodes Vector
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return nodes;
    }




    //增加一个方法 当游戏退出时，将allEnemyTankNum 保存到 recordFile
    //保存敌人坦克坐标和方向

    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum +"\r\n");
   //遍历敌人坦克的Vector 根据情况保存
   //定义一个属性 通过set方法得到敌人坦克的Vector
        for (int i = 0;i < enemyTanks.size();i++){
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive){
                //保存该enemytank信息
                String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                //写入到文件
                bw.write(record+"\r\n");
            }//判断敌人坦克是否存活
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    //当我方坦克击毁一个敌人坦克，就应当给击毁坦克数++
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
