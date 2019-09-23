package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends BaseSprite implements Moveable, Drawable {

    public List<Image> imageList = new ArrayList<>();
    Random random = new Random();
    private Image image;
    private int speed = FrameConstant.GAME_SPEED * 9;
    private boolean collision;

    public int bosshp = FrameConstant.EQ_HP*100;
    public  boolean right=true;


    public Boss() {
        this(0,0,ImageMap.get("boss1"));
        for (int i = 1; i < 10; i++) {
            imageList.add(ImageMap.get("boss" + i));
        }
    }

    public Boss(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    int index = 0;

    @Override
    public void draw(Graphics g) {

        fire();
        move();
        borderTesting();
        g.drawImage(imageList.get(index++), getX(), getY(), imageList.get(0).getWidth(null),
                imageList.get(0).getHeight(null), null);
        if (index >= 9) {
            index = 0;
        }
    }

    @Override
    public void move() {
        if (right){
        setX(getX() + speed);
        }
        else if (!right){
            setX(getX() - speed);}

    }


    public void fire() {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 965) {
            gameFrame.bossBulletList.add(new BossBullet(
                    getX() + (image.getWidth(null) / 2) - (ImageMap.get("epb02").getWidth(null) / 2),
                    getY() + image.getHeight(null),
                    ImageMap.get("epb03")
            ));
        }
    }

    public void borderTesting(){
        if (getY() > FrameConstant.FRAME_HEIGHT){
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bossBulletList.remove(this);
        }

        if (getX() <= 0) {
            right=true;
        }
        if (getX() > FrameConstant.FRAME_WIDTh-300) {
           right=false;
        }
//        if (getY() > FrameConstant.FRAME_HEIGHT-image.getHeight(null)){
//            setY(30);
//        }

    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

//    //boss与我方飞机碰撞
//    public void collisonTesting(Plane plane){
//        GameFrame gameFrame = DataStore.get("gameFrame");
//        if (plane.getRectangle().intersects(this.getRectangle())){
//            gameFrame.gameOver = true;
////            System.out.println(1);
//            if (plane.hp > 0){
//                plane.hp-=5;
////                System.out.println(2);
//
//            }else{
//                gameFrame.gameOver = true;
////                System.out.println(3);
//            }

//        }
//    }


}
