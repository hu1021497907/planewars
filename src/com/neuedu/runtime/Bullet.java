package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;


import java.awt.*;
import java.util.List;
import java.util.Random;

public class Bullet extends BaseSprite implements Moveable, Drawable {

    private Image image;
    private Image image2;
    private Image image3;

    Random random = new Random();

    public  int type;

    private int speed = FrameConstant.GAME_SPEED * 8;

    public Bullet() {
        this(0,0, ImageMap.get("mb01"),1);
    }

    public Bullet(int x, int y, Image image,int type) {
        super(x, y);
        this.type = type ;
        this.image = ImageMap.get("mb01");
        this.image2 = ImageMap.get("mb02");
        this.image3 = ImageMap.get("mb03");
    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        if (type == 1){
            g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        }
        if (type == 2){
            g.drawImage(image2,getX(),getY(),image2.getWidth(null),image2.getHeight(null),null);
        }
        if (type == 3) {
            g.drawImage(image3,getX(),getY(),image3.getWidth(null),image3.getHeight(null),null);
        }

    }

    @Override
    public void move() {
        setY(getY() - speed);
    }

    public void borderTesting(){
        if (getY() < 30 -image.getHeight(null)) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bulletList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    //我方子弹与敌方飞机碰撞，双方都会消失
    public void collisonTesting(List<EnemyPlane> enemyPlaneList){
        GameFrame gameFrame = DataStore.get("gameFrame");
        for (EnemyPlane enemyPlane : enemyPlaneList) {
            if (enemyPlane.getRectangle().intersects(this.getRectangle())){
                gameFrame.bulletList.remove(this);
                if (enemyPlane.hp <= 0 ){
                    enemyPlaneList.remove(enemyPlane);
                    gameFrame.score += enemyPlane.getType()*10;
                }else {

                    enemyPlane.hp-=3;
                }
                if (random.nextInt(1000) > 222 && random.nextInt(1000) < 225){
                    gameFrame.propList.add(new Prop(enemyPlane.getX(),enemyPlane.getY(),ImageMap.get("pr01"),1));
                }else if (random.nextInt(1000) > 600 && random.nextInt(1000) < 602){
                    gameFrame.propList.add(new Prop(enemyPlane.getX(),enemyPlane.getY(),ImageMap.get("pr02"),2));
//                }else if (random.nextInt(1000) > 850 && random.nextInt(1000) < 855){
//                    gameFrame.propList.add(new Prop(enemyPlane.getX(),enemyPlane.getY(),3));
                }
            }
        }
    }

    //我方子弹与boss碰撞
    public void collisonTesting2(Boss boss){
        GameFrame gameFrame = DataStore.get("gameFrame");

            if (boss.getRectangle().intersects(this.getRectangle())){
                boss.bosshp -= 6;
                if (boss.bosshp <= 0){
                    gameFrame.bossList.remove(boss);
                    gameFrame.gameOver = true;
                }
                gameFrame.bulletList.remove(this);
                gameFrame.score += 5;
            }

    }

    //我方子弹与敌方子弹碰撞，双方子弹都会消失
    public void collisonTesting1(List<EnemyBullet> enemyBulletList){
        GameFrame gameFrame = DataStore.get("gameFrame");
        for (EnemyBullet enemyBullet : enemyBulletList) {
            if (enemyBullet.getRectangle().intersects(this.getRectangle())){
                enemyBulletList.remove(enemyBullet);
                gameFrame.bulletList.remove(this);
            }
        }
    }

}
