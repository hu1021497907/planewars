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

/**
 * 道具
 */
public class Prop extends BaseSprite implements Moveable, Drawable {

    //道具的图片
    private Image image;
    private Image image2;
    private Image image3;

    Random random = new Random();
    //道具的种类
    private int type;

    private int speed = FrameConstant.GAME_SPEED * 5;

//     Bullet bullet = new Bullet();
     EnemyPlane enemyPlane = new EnemyPlane();

    public Prop() {
        this(0,0,ImageMap.get("pr01"),1);
    }

    public Prop(int x, int y,Image image,int type) {
        super(x, y);
        this.type = type;
        this.image = ImageMap.get("pr01");
        this.image2 = ImageMap.get("pr02");
        this.image3 = ImageMap.get("pr03");
    }

    @Override
    public void draw(Graphics g) {
        move();
        if (type == 1){
            g.drawImage(image,getX(),getY(),image.getWidth(null)/2,image.getHeight(null)/2,null);
        }
        if (type == 2){
            g.drawImage(image2,getX(),getY(),image2.getWidth(null)/2,image2.getHeight(null)/2,null);
        }
        if (type == 3){
            g.drawImage(image3,getX(),getY(),image3.getWidth(null)/2,image3.getHeight(null)/2,null);
        }
    }

    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting();
    }

    public void borderTesting(){
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.enemyBulletList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    //碰道具
    public void collisonTesting(Plane plane,Bullet bullet){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())){
            gameFrame.propList.remove(this);
            if (type == 1){
                bullet.type = 2;
            }else if (type == 2){
                if (plane.hp >= 100){
                    plane.hp = 100;
                }else {
                    plane.hp+=5;
                }
            }else if (type == 3){
                plane.type = 2;
                plane.speed += 2;
                bullet.type = 3;

            }
        }

//        if (this.getRectangle().intersects(bullet.getRectangle()))
//        if (bullet.getRectangle().intersects(this.getRectangle())){
//            gameFrame.propList.remove(this);
//            if (type == 1){
//                bullet.type = 2;
//            }else if (type == 3){
//                plane.type = 2;
//                plane.speed += 2;
//                bullet.type = 3;
//            }
//        }
    }
}
