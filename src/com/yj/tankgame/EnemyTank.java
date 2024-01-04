package com.yj.tankgame;
/*
敌人的坦克
 */

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    //在敌人坦克类，使用Vector保存多个shot
    Vector<Shot> shots = new Vector<>();
    //增加成员，enemytank可以得到敌人坦克的Vector
    Vector<EnemyTank>enemyTanks=new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x,int y) {
        super(x, y);
    }
//提供一个方法，可以将MyPanel 成员Vector<EnemyTank> enemyTanks = new Vector<>()
//设置到enemytank的成员enemytanks
    public void setEnemyTanks(Vector<EnemyTank>enemyTanks){
        this.enemyTanks= enemyTanks;
    }
//编写方法，判断当前敌人坦克是否和enemytanks的其他坦克发生重叠或者碰撞
public boolean isTouchEnemyTank(){
//判断当前敌人坦克（this）方向
    switch (this.getDirect()){
        case 0://上
            //让当前敌人坦克和其他所有敌人坦克进行比较
            for (int i =0;i< enemyTanks.size();i++){
                //从Vector中取出一个敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                //不和自己比较
                if (enemyTank!=this){
                    //如果敌人坦克时上或下方向
                    if (enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60){
                            return true;
                        }
                        if (this.getX() +40 >= enemyTank.getX()
                                && this.getX() +40 <= enemyTank.getX()+40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60){
                            return true;
                        }
                    }
                    //如果敌人坦克右或左方向
                    if (enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                        if (this.getX()  >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40){
                            return true;
                        }
                        if (this.getX() +40 >= enemyTank.getX()
                                && this.getX() +40 <= enemyTank.getX()+60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40){
                            return true;
                        }
                    }
                }
            }
            break;
        case 1://右
            for (int i =0;i< enemyTanks.size();i++){
                //从Vector中取出一个敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                //不和自己比较
                if (enemyTank!=this){
                    //如果敌人坦克时上或下方向
                    if (enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                        if (this.getX()+60 >= enemyTank.getX()
                                && this.getX()+60 <= enemyTank.getX()+40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60){
                            return true;
                        }
                        if (this.getX() +60 >= enemyTank.getX()
                                && this.getX() +60 <= enemyTank.getX()+40
                                && this.getY()+40 >= enemyTank.getY()
                                && this.getY()+40 <= enemyTank.getY() + 60){
                            return true;
                        }
                    }
                    //如果敌人坦克右或左方向
                    if (enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                        if (this.getX() +60 >= enemyTank.getX()
                                && this.getX()+60 <= enemyTank.getX()+60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40){
                            return true;
                        }
                        if (this.getX() +60 >= enemyTank.getX()
                                && this.getX() +60 <= enemyTank.getX()+60
                                && this.getY()+40 >= enemyTank.getY()
                                && this.getY()+40 <= enemyTank.getY() + 40){
                            return true;
                        }
                    }
                }
            }
            break;
        case 2://下
            for (int i =0;i< enemyTanks.size();i++){
                //从Vector中取出一个敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                //不和自己比较
                if (enemyTank!=this){
                    //如果敌人坦克时上或下方向
                    if (enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+40
                                && this.getY()+60 >= enemyTank.getY()
                                && this.getY()+60 <= enemyTank.getY() + 60){
                            return true;
                        }
                        if (this.getX() +40 >= enemyTank.getX()
                                && this.getX() +40<= enemyTank.getX()+40
                                && this.getY()+60 >= enemyTank.getY()
                                && this.getY()+60 <= enemyTank.getY() + 60){
                            return true;
                        }
                    }
                    //如果敌人坦克右或左方向
                    if (enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                        if (this.getX()  >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+60
                                && this.getY()+60 >= enemyTank.getY()
                                && this.getY()+60 <= enemyTank.getY() + 40){
                            return true;
                        }
                        if (this.getX() +40 >= enemyTank.getX()
                                && this.getX() +40 <= enemyTank.getX()+60
                                && this.getY()+60 >= enemyTank.getY()
                                && this.getY() +60<= enemyTank.getY() + 40){
                            return true;
                        }
                    }
                }
            }
            break;
        case 3://左
            for (int i =0;i< enemyTanks.size();i++){
                //从Vector中取出一个敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                //不和自己比较
                if (enemyTank!=this){
                    //如果敌人坦克时上或下方向
                    if (enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60){
                            return true;
                        }
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+40
                                && this.getY()+40 >= enemyTank.getY()
                                && this.getY()+40<= enemyTank.getY() + 60){
                            return true;
                        }
                    }
                    //如果敌人坦克右或左方向
                    if (enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                        if (this.getX()  >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX()+60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40){
                            return true;
                        }
                        if (this.getX()  >= enemyTank.getX()
                                && this.getX()  <= enemyTank.getX()+60
                                && this.getY() +40>= enemyTank.getY()
                                && this.getY() +40<= enemyTank.getY() + 40){
                            return true;
                        }
                    }
                }
            }
        break;
    }
    return false;
}
    @Override
    public void run() {
        while (true){
            //如果shots size = 0,创建一颗子弹，放入到shot集合并启动
            if (isLive&&shots.size()==0){
                Shot s=null;

                //判断坦克的方向来创建对应子弹
                switch (getDirect()){
                    case 0:
                        s = new Shot(getX() + 20,getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60,getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX() +20,getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX() ,getY()+20,3);
                        break;
                }
                shots.add(s);
                //启动
                new Thread(s).start();
            }

            //根据坦克的方向来继续移动
            switch (getDirect()){
                case 0:  //向上
                    //让坦克保持一个方向，走30下
                    for (int i = 0;i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUP();
                        }
                        try {
                            Thread.sleep(50);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:  //向右
                    for (int i = 0;i < 30; i++) {
                        if (getX() + 60 <1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:  //向下
                    for (int i = 0;i < 30; i++) {
                        if (getY()+60<750 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:  //向左
                    for (int i = 0;i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    break;
            }


            //然后随机的改变坦克方向 0-3
            setDirect((int)(Math.random()*4));
            if (!isLive){
                break;//退出线程
            }
        }
    }
}
