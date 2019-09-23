package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.*;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameFrame extends Frame {

    //创建背景对象
    private Background background = new Background();

    //创建飞机对象
    private Plane plane = new Plane();

    private Bullet bullet = new Bullet();

    //创建子弹集合
    public final List<Bullet> bulletList = new CopyOnWriteArrayList<>();
    
    //创建敌方子弹集合
    public final List<EnemyBullet> enemyBulletList = new CopyOnWriteArrayList<>();
    
    //创建敌方飞机集合
    public final  List<EnemyPlane> enemyPlaneList = new CopyOnWriteArrayList<>();

    //创建boss子弹集合
    public final List<BossBullet> bossBulletList = new CopyOnWriteArrayList<>();

    public boolean gameOver = false;

    //创建道具对象
    public final  List<Prop> propList = new CopyOnWriteArrayList<>();

    //积分
    public int score = 0;

    //创建道具增益
    public Integer gain = 0;

    //boss
    public final List<Boss> bossList = new CopyOnWriteArrayList<>();

    private Boss boss = new Boss();


    @Override
    public void paint(Graphics g) {
        if (!gameOver){
            background.draw(g);

            if (score >= 200){
                boss.draw(g);
            }

            plane.draw(g);



            g.setFont(new Font("楷体",Font.BOLD,36));
            g.setColor(Color.red);
            g.drawString("得分："+ score,550,100);

            //画血量
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            g.setColor(Color.red);
            g.setFont(new Font("楷体",Font.BOLD,36));
            g.drawRect(650, 150, 100, 20);
            g.fillRect(650, 150, plane.hp, 20);
            g.drawString("血量:",550,175);

            if (score >= 200){
                g.setFont(new Font("楷体",Font.BOLD,36));
                g.drawRect(200, 150, 200, 20);
                g.fillRect(200, 150, boss.bosshp, 20);
                g.drawString("boss血量:",20,175);
            }


            //道具
            for (Prop prop : propList) {
                prop.draw(g);
            }

            //我方子弹
            for (Bullet bullet : bulletList) {
                bullet.draw(g);
            }

            for (Boss boss1 : bossList) {
                boss1.draw(g);
            }

            //敌方子弹
            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.draw(g);
            }

            //boss子弹
            for (BossBullet bossBullet : bossBulletList) {
                bossBullet.draw(g);
            }

            //敌方飞机
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.draw(g);
            }

            //我方子弹碰撞敌方飞机
            for (Bullet bullet : bulletList) {
                bullet.collisonTesting(enemyPlaneList);
            }

            //我方子弹碰撞boss
            for (Bullet bullet : bulletList) {
                if (score > 200)
                bullet.collisonTesting2(boss);
            }

            //我方飞机撞boss
            if (score >= 200 ){
                plane.collisonTesting(boss);
            }
            //我方子弹碰撞子弹
//            if (plane.hp <= 20){
//                for (Bullet bullet : bulletList) {
//                    bullet.collisonTesting1(enemyBulletList);
//                }
//            }


            //碰道具
            for (Prop prop : propList) {
                prop.collisonTesting(plane, bullet);
            }

            //敌方子弹碰撞我方飞机
            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.collisonTesting(plane);
            }

            //boss子弹碰撞我方飞机
            for (BossBullet bossBullet : bossBulletList) {
                bossBullet.collisonTesting(plane);
            }


            //敌方飞机碰撞我方飞机
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.collisonTesting(plane);
            }



            g.setColor(Color.RED);
            g.drawString(""+ enemyPlaneList.size(),300,100);


        } else if (boss.bosshp <= 0){
            g.setFont(new Font("行楷",Font.BOLD,60));
            g.setColor(Color.red);
            g.drawString("GAME WINS",270,600);
        }else if (plane.hp <= 0 ){
            g.setFont(new Font("行楷",Font.BOLD,60));
            g.setColor(Color.red);
            g.drawString("GAME OVER",240,600);
        }

    }

    /**
     * 使用这个方法初始化窗口
     */
    public void init(){

        //设置窗口名称
        setTitle("飞机大战");

        //设置窗口宽高
        setSize(FrameConstant.FRAME_WIDTh, FrameConstant.FRAME_HEIGHT);

        //设置居中
        setLocationRelativeTo(null);

        //输入法关闭操作
        enableInputMethods(false);

        //设置窗口不可改变大小
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });

        //添加键盘监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //游戏初始时添加一些敌方飞机
        enemyPlaneList.add(new EnemyPlane(100,30,1));
        enemyPlaneList.add(new EnemyPlane(300,-270,1));
        enemyPlaneList.add(new EnemyPlane(450,30,1));
        enemyPlaneList.add(new EnemyPlane(650,-270,1));
        enemyPlaneList.add(new EnemyPlane(100,-600,1));
        enemyPlaneList.add(new EnemyPlane(300,-650,1));
        enemyPlaneList.add(new EnemyPlane(450,-800,2));
        enemyPlaneList.add(new EnemyPlane(100,-800,2));
        enemyPlaneList.add(new EnemyPlane(300,-1050,2));
        enemyPlaneList.add(new EnemyPlane(650,-1050,2));
        enemyPlaneList.add(new EnemyPlane(100,-2030,3));
        enemyPlaneList.add(new EnemyPlane(300,-2530,3));
        enemyPlaneList.add(new EnemyPlane(450,-2050,3));
        enemyPlaneList.add(new EnemyPlane(650,-2500,3));
        enemyPlaneList.add(new EnemyPlane(650,-1550,2));
        enemyPlaneList.add(new EnemyPlane(650,-1550,2));




        //道具
//        propList.add(new Prop(100,200,1));
//        propList.add(new Prop(400,-200,2));
        propList.add(new Prop(400,-600, ImageMap.get("pr03"),3));






        //设置窗口可见
        setVisible(true);
    }


    private Image offScreenImage = null;//创建缓冲区

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = this.createImage(FrameConstant.FRAME_WIDTh,FrameConstant.FRAME_HEIGHT);
        }
        Graphics goff = offScreenImage.getGraphics();//创建离线图片的实例，在图片缓冲区绘图
        paint(goff);
        g.drawImage(offScreenImage,0,0,null);//将缓冲区图片绘制到窗口目标
    }
}
