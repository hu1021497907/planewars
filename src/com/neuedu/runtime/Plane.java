package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Plane extends BaseSprite implements Moveable, Drawable {

    private Image image;
    private Image image2;

    private boolean up,rigth,down,left;

    private boolean fire;

    public int type;

    public int hp = FrameConstant.MY_HP;

    public int speed = FrameConstant.GAME_SPEED * 8;

    public Plane() {
        this((FrameConstant.FRAME_WIDTh-ImageMap.get("my01").getWidth(null)) / 2,
                FrameConstant.FRAME_HEIGHT-ImageMap.get("my01").getHeight(null),
                ImageMap.get("my01"),1);
    }

    public Plane(int x, int y, Image image,int type) {
        super(x, y);
        this.type = type;
        this.image = ImageMap.get("my01");
        this.image2 = ImageMap.get("my02");

    }


    @Override
    public void draw(Graphics g) {
        move();
        if (type == 1){
            g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        }
        if (type == 2){
            g.drawImage(image2,getX(),getY(),image2.getWidth(null),image2.getHeight(null),null);
        }

//        fire();
//        if (fire){
//            index++;
//            if (index >= 10){
//                index = 0;
//            }
//        }
    }

//    private int index = 0;

    /**
     * 开火方法
     * 判断开关是否打开
     * 创建一个子弹对象放入gameFrame里的子弹集合中
     */
    public void fire(){
        if (type == 1){
            if (fire ){
                GameFrame gameFrame = DataStore.get("gameFrame");
                gameFrame.bulletList.add(new Bullet(
                        getX() + (image.getWidth(null) /2) - (ImageMap.get("mb01").getWidth(null) / 2),
                        getY() - ImageMap.get("mb01").getHeight(null),
                        ImageMap.get("mb01"),1
                ));
            }
        }
        if (type == 2){
            if (fire ){
                GameFrame gameFrame = DataStore.get("gameFrame");
                gameFrame.bulletList.add(new Bullet(
                        getX() + (image.getWidth(null) /2) - (ImageMap.get("mb02").getWidth(null) / 2),
                        getY() - ImageMap.get("mb02").getHeight(null),
                        ImageMap.get("mb02"),2
                ));
            }
        }
        if (type == 3){
            if (fire ){
                GameFrame gameFrame = DataStore.get("gameFrame");
                gameFrame.bulletList.add(new Bullet(
                        getX() + (image.getWidth(null) /2) - (ImageMap.get("mb03").getWidth(null) / 2),
                        getY() - ImageMap.get("mb03").getHeight(null),
                        ImageMap.get("mb03"),3
                ));
            }
        }

    }

    /**
     * 移动方法
     */
    @Override
    public void move() {
        if (up) {
            setY(getY() - speed);
        }
        if (rigth) {
            setX(getX() + speed);
        }
        if (down) {
            setY(getY() + speed);
        }
        if (left) {
            setX(getX() - speed);
        }
        borderTesting();
    }

    /**
     * 边缘检测
     */
    public void borderTesting(){
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTh-image.getWidth(null)){
            setX(FrameConstant.FRAME_WIDTh-image.getWidth(null));
        }
        if (getY() < 30){
            setY(30);
        }
        if (getY() > FrameConstant.FRAME_HEIGHT-image.getHeight(null)){
            setY(FrameConstant.FRAME_HEIGHT-image.getHeight(null));
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            rigth = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_J){

            fire = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            rigth = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_J){
            fire();
            fire = false;
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    //我方飞机撞boss
    public void collisonTesting(Boss boss) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (boss.getRectangle().intersects(this.getRectangle())) {
            if (hp > 0) {
                hp -= 1;
            }else {
                gameFrame.gameOver = true;
            }
        }
    }

}
