package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.Random;

public class EnemyPlane extends BaseSprite implements Moveable, Drawable {


    private Image image;
    private Image image2;
    private Image image3;

    private int speed = FrameConstant.GAME_SPEED * 2;

    private Random random = new Random();

    //敌方飞机的种类
    private int type;

    public int hp = FrameConstant.EQ_HP;

    public int getType() {
        return type;
    }

    public EnemyPlane() {
        this(0,0,1);
    }

    public EnemyPlane(int x, int y,int type) {
        super(x, y);
        this.type = type;
        this.image = ImageMap.get("ep01");
        this.image2 = ImageMap.get("ep02");
        this.image3 = ImageMap.get("ep03");
        init();
    }

    void init(){
        if (type == 1){
            hp *= 3 ;
        }else if (type == 2){
            hp *= 4;
        }else if (type == 3){
            hp *= 5;
        }
    }

    @Override
    public void draw(Graphics g) {
        move();
        fire();
        if (type == 1){
            g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        }
        if (type == 2){
            g.drawImage(image2,getX(),getY(),image2.getWidth(null),image2.getHeight(null),null);
        }
        if (type == 3){
            g.drawImage(image3,getX(),getY(),image3.getWidth(null),image3.getHeight(null),null);
        }



    }

    public void fire(){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 990) {
            if (getY() >= 30) {
                if (type == 1) {
                    gameFrame.enemyBulletList.add(new EnemyBullet(
                            getX() + (image.getWidth(null) / 2) - (ImageMap.get("epb01").getWidth(null) / 2),
                            getY() + image.getHeight(null),
                            ImageMap.get("epb01")
                    ));
                }
                if (type == 2) {
                    gameFrame.enemyBulletList.add(new EnemyBullet(
                            getX() + (image2.getWidth(null) / 2) - (ImageMap.get("epb01").getWidth(null) / 2),
                            getY() + image2.getHeight(null),
                            ImageMap.get("epb01")
                    ));
                }
                if (type == 3) {
                    gameFrame.enemyBulletList.add(new EnemyBullet(
                            getX() + (image3.getWidth(null) / 2) - (ImageMap.get("epb01").getWidth(null) / 2),
                            getY() + image3.getHeight(null),
                            ImageMap.get("epb02")
                    ));
                }
            }
        }
    }

    @Override
    public void move() {
        if (type == 1){
            setY(getY() + speed);
        }else if (type == 2){
            setY(getY() + speed );
            setX(getX() + speed);
        }else if (type == 3){
            setX(getX() - speed);
            setY(getY() + speed * 2);
        }
        borderTesting();

    }

    public void borderTesting(){
//        if (getY() > FrameConstant.FRAME_HEIGHT){
            GameFrame gameFrame = DataStore.get("gameFrame");
//            gameFrame.enemyPlaneList.remove(this);
//        }

        if (getX() < 0) {
            setX(FrameConstant.FRAME_WIDTh);
        }
        if (getX() > FrameConstant.FRAME_WIDTh) {
            setX(0);
        }
        if (getY() > FrameConstant.FRAME_HEIGHT-image.getHeight(null)){
            setY(30);
        }

    }


    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    //敌方飞机与我方飞机碰撞
    public void collisonTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            if (plane.hp > 0) {
                plane.hp -= 5;
                gameFrame.enemyPlaneList.remove(this);
                gameFrame.score += getType()*5;
            } else {
                gameFrame.gameOver = true;
            }
        }
    }

}


