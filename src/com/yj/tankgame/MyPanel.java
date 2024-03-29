package com.yj.tankgame;

import javafx.scene.chart.ScatterChart;
import sun.security.mscapi.CPublicKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 *坦克大战的绘图区域
 */
//为了监听 键盘事件，实现KeyListener
//为了让Panel 不停的重绘子弹，需要将MyPanel 实现Runnable，当做一个线程使用
public class MyPanel extends JPanel implements KeyListener,Runnable{
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克，放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector 用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个vector，用于存放炸弹
    //说明，当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel(String key){
        nodes = Recorder.getNodesAndEnemyTankrec();
        //将MyPanel对象的enemytanks设置给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(500,100);//初始化自己坦克
        switch (key) {
            case "1":
                //初始化敌人坦克
                for (int i = 0;i<enemyTankSize;i++){
                    //创建一个敌人的坦克
                    EnemyTank enemyTank=new EnemyTank((100*(i+1)),0);
                    //将enemytanks 设置给enemytank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程，使其动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shot shot= new Shot(enemyTank.getX() +20,enemyTank.getY() + 60,enemyTank.getDirect());
                    //加入enenmyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }


                break;
            case "2"://继续上局游戏
                //初始化敌人坦克
                for (int i = 0;i< nodes.size();i++){
                    Node node = nodes.get(i);
                    //创建一个敌人的坦克
                    EnemyTank enemyTank=new EnemyTank(node.getX(), node.getY());
                    //将enemytanks 设置给enemytank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程，使其动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shot shot= new Shot(enemyTank.getX() +20,enemyTank.getY() + 60,enemyTank.getDirect());
                    //加入enenmyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误");
        }




        hero.setSpeed(1);

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
  //播放背景音乐
        new AePlayWave("d:\\111.wav").start();
    }

    //编写方法 显示我方击毁敌方坦克信息
    public void showInfo(Graphics g){
        //画出玩家总成绩
        g.setColor(Color.BLACK);
       Font font = new Font("宋体",Font.BOLD,25);
       g.setFont(font);

       g.drawString("您累计击毁敌方坦克",1020,30);
       drawTank(1020,60,g,0,0);//画出一个敌方坦克
        g.setColor(Color.BLACK);//颜色重新设置为黑色
        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认黑色
        showInfo(g);
        if (hero != null && hero.isLive) {
            //画出坦克-封装方法
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //画出hero射击的子弹
        //将hero的子弹集合shots，遍历取出绘制
      //  if (hero.shot!=null && hero.shot.isLive== true){
            //g.fill3DRect(hero.shot.x,hero.shot.y,1,1,false);
            //g.draw3DRect(hero.shot.x,hero.shot.y,1,1,false);
      //  }
        for (int i = 0;i<hero.shots.size();i++){
            Shot shot = hero.shots.get(i);
            if (shot!=null && shot.isLive== true) {
                g.fill3DRect(shot.x, shot.y, 1, 1, false);
            }else {//如果该shot对象已经无效，就从shots集合中拿掉
                hero.shots.remove(shot);
            }
        }

        //如果bombs 集合中有对象，就画出
        for (int i =0;i< bombs.size();i++){
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应图片
            if (bomb.life>6){
                g.drawImage(image1, bomb.x, bomb.y, 60,60,this);
            } else if (bomb.life >3) {
                g.drawImage(image2, bomb.x, bomb.y, 60,60,this);
            }else {
                g.drawImage(image3, bomb.x, bomb.y, 60,60,this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life为0，就从bombs的集合中删除
            if (bomb.life==0){
                bombs.remove(bomb);
            }
        }

        //画出敌人坦克，遍历Vector
        for (int i = 0;i<enemyTanks.size();i++){
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否还存活
            if (enemyTank.isLive) {//当敌人坦克是存活的，才画出该坦克
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出enemyTank 所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive ) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从Vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }
    //编写方法 画出坦克

    /**
     *
     * @param x 坦克左上角x坐标
     * @param y 坦克左上角y坐标
     * @param g 画笔
     * @param direct 坦克方向（上下左右）
     * @param type 坦克类型
     */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        //根据不同类型坦克设置不同颜色
        switch (type){
            case 0://敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1://我们的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，来绘制对应形状坦克
        //direct 表示方向(0：向上 1：向右 2：向下 3；向左)
        //
        switch (direct){
            case 0://表示向上
                g.fill3DRect(x,y,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//画出坦克盖子
                g.fillOval(x+10,y+20,20,20);//画出圆盖
                g.drawLine(x+20,y+30,x+20,y);//画出炮筒
                break;
            case 1://表示向右
                g.fill3DRect(x,y,60,10,false);//画出坦克上边轮子
                g.fill3DRect(x,y+30,60,10,false);//画出坦克下边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//画出坦克盖子
                g.fillOval(x+20,y+10,20,20);//画出圆盖
                g.drawLine(x+30,y+20,x+60,y+20);//画出炮筒
                break;
            case 2://表示向下
                g.fill3DRect(x,y,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//画出坦克盖子
                g.fillOval(x+10,y+20,20,20);//画出圆盖
                g.drawLine(x+20,y+30,x+20,y+60);//画出炮筒
                break;
            case 3://表示向左
                g.fill3DRect(x,y,60,10,false);//画出坦克上边轮子
                g.fill3DRect(x,y+30,60,10,false);//画出坦克下边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//画出坦克盖子
                g.fillOval(x+20,y+10,20,20);//画出圆盖
                g.drawLine(x+30,y+20,x,y+20);//画出炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }
//如果坦克可以发射多颗子弹
//在判断我方子弹是否击中敌人坦克时，需要把子弹集合中所有的子弹，都取出和敌人的所有坦克进行判断
 public void hitEnemyTank(){


        //遍历我方子弹
     for (int j = 0;j<hero.shots.size();j++){
         Shot shot = hero.shots.get(j);
     //判断是否击中敌人坦克
     if (shot!=null && shot.isLive) {//当我的子弹还存活
         //遍历敌人所有的tank
         for (int i = 0; i < enemyTanks.size(); i++) {
             EnemyTank enemyTank = enemyTanks.get(i);
             hitTank(shot, enemyTank);
         }
     }
     }

 }
    //编写方法，判短敌人坦克是否击中我方坦克;
    public void hithero(){
        //遍历敌人的所有坦克
        for (int i = 0;i<enemyTanks.size();i++){
         //取出敌人坦克
         EnemyTank enemyTank = enemyTanks.get(i);
         //遍历enemyTank的所有子弹
        for (int j = 0;j<enemyTank.shots.size();j++){
            //取出子弹
          Shot shot = enemyTank.shots.get(j);
          //判断shot是否击中hero
            if (hero.isLive && shot.isLive){
                hitTank(shot,hero);
            }
        }
        }
    }





//编写方法，判断我方子弹是否击中敌人坦克
//run方法判断有无坦克被击中
//可将enemytank改为tank
    public void hitTank(Shot s,Tank enemyTank){
        //判断s击中坦克
        switch (enemyTank.getDirect()){
            case 0://坦克向上
            case 2://坦克向下
                if (s.x > enemyTank.getX() && s.x< enemyTank.getX()+40 && s.y>enemyTank.getY() && s.y<enemyTank.getY()+60){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当我的子弹击中敌人坦克后，将enemytank从Vector中拿掉
                    enemyTanks.remove(enemyTank);
                    //当我方击毁一个敌人坦克时，对数据allEnemytankNum++
                    //因为enemytank可以是hero也可以是Enemytank
                    if (enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                   Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if (s.x > enemyTank.getX() && s.x< enemyTank.getX()+60 && s.y>enemyTank.getY() && s.y<enemyTank.getY()+40){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    //当我方击毁一个敌人坦克时，对数据allEnemytankNum++
                    //因为enemytank可以是hero也可以是Enemytank
                    if (enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wdsa 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下w键
            //改变坦克方向
            hero.setDirect(0);
            //修改坦克坐标y-=1
            if (hero.getY() > 0) {
                hero.moveUP();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//d键,向右移动
            hero.setDirect(1);
            if (hero.getX() +60 <1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//s键
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//a键
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        //如果用户按下的是J，就发射
        if (e.getKeyCode() == KeyEvent.VK_J){
            //判断hero的子弹是否销毁
          //  if (hero.shot == null || !hero.shot.isLive) {
             //   hero.shotEnemyTank();
           // }
            //发射多颗子弹
            hero.shotEnemyTank();
        }
        this.repaint();
    }


    //让面板重绘


        @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100毫秒，重绘区域,刷新绘图区域，子弹就移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hithero();
            hitEnemyTank();
            this.repaint();
        }
    }
}
