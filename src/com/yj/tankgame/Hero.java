package com.yj.tankgame;

import java.util.Vector;

/**
 *自己的坦克
 */
public class Hero extends Tank {
    //定义一个shot对象,表示一个射击（线程）
    Shot shot = null;
    //可以发射多可子弹
    Vector<Shot> shots = new Vector<>();
    public Hero(int x,int y){
        super(x,y);
    }
    //射击
    public void shotEnemyTank(){

        //控制面板最多只能发射五颗子弹
        if (shots.size()==5){
            return;
        }
        //创建shot对象，根据当前Hero对象的位置和方向来创建shot
        switch (getDirect()){//得到hero对象方向
            case 0://向上
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1://向右
                shot = new Shot(getX() + 60,getY() + 20,1);
                break;
            case 2://向下
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3://向左
                shot = new Shot(getX() ,getY() + 20,3);
                break;
        }
        //把新建的子弹放入到集合
        shots.add(shot);
        //启动我们的shot线程
        new Thread(shot).start();

    }



}
