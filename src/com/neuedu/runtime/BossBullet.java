package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

import java.awt.*;
import java.util.Random;

public class BossBullet extends BaseSprite implements Moveable, Drawable {

    private Image image;
    private int speed = FrameConstant.GAME_SPEED * 6;
    Random random = new Random();

    public BossBullet() {
    }

    public BossBullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);
    }

    @Override
    public void move() {
        if (random.nextInt(1000) > 980) {
            setY(getY() + speed);
        }
        if (random.nextInt(1000) > 300 && random.nextInt(1000) < 600) {
            setY(getY() + speed);
            setX(getX() + speed);
        }
        if (random.nextInt(1000) > 600 && random.nextInt(1000) < 620) {
            setY(getY() + speed);
            setX(getX() - speed);
        }
    }

    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bossBulletList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {

        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }

    //boss子弹碰撞我方飞机
    public void collisonTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.bossBulletList.remove(this);
            plane.hp -= 5;
            if (plane.hp <= 0) {
                gameFrame.gameOver = true;
            }

//            System.out.println(1);
//            if (plane.hp > 0){
//                plane.hp--;
//                System.out.println(2);
//                gameFrame.enemyBulletList.remove(this);
//            }else{
//
//
//                System.out.println(3);
//            }
        }

    }
}
