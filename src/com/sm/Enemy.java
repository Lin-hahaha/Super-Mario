package com.sm;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {
    //储存当前坐标
    private int x,y;
    //储存敌人类型
    private int type;
    //判断敌人运动方向
    private boolean face_to=true;
    //用于显示敌人图像
    private BufferedImage show;
    //定义背景对象
    private BackGround bg;
    //食人花极限范围
    private int max_up=0;
    private int max_down=0;
    private Thread thread=new Thread(this);
    private  int image_type=0;
    //蘑菇的函数
    public Enemy(int x,int y,boolean face_to,int type,BackGround bg){
        this.x=x;
        this.y=y;
        this.face_to=face_to;
        this.type=type;
        this.bg=bg;
        show=GameUtil.mogu.get(0);
        thread.start();
    }
    public Enemy(int x,int y,boolean face_to,int type,int max_up,int max_down,BackGround bg){
        this.x=x;
        this.y=y;
        this.face_to=face_to;
        this.type=type;
        this.max_up=max_up;
        this.max_down=max_down;
        this.bg=bg;
        show=GameUtil.flower.get(0);
        thread.start();
    }
    //敌人死亡方法
    public void death(){
        show=GameUtil.mogu.get(2);
        this.bg.getEnemyList().remove(this);
    }

    @Override
    public void run() {
        while (true){
            //判断是否为蘑菇敌人
            if (type==1){
                if (face_to){
                    this.x-=2;
                }else {
                    this.x+=2;
                }
                image_type=image_type==1?0:1;
                show=GameUtil.mogu.get(image_type);
            }
            //定义两个值
            boolean canLeft=true;
            boolean canRight=true;
            for (int i = 1; i <bg.getObstacleList().size() ; i++) {
                Obstacle ob1=bg.getObstacleList().get(i);
                //判断是否可以向右
                if (ob1.getX()==this.x+36&&(ob1.getY()+65>this.y&&ob1.getY()-35<this.y)){
                    canRight=false;
                }
                //判断是否可以向左
                if (ob1.getX()==this.x-36&&(ob1.getY()+65>this.y&&ob1.getY()-35<this.y)){
                    canLeft=false;
                }
            }
            if(face_to&&!canLeft||this.x==0){
                face_to=false;
            } else if (!face_to&&!canRight||this.x==764) {
                face_to=true;
            }
            //判断食人花
            if (type==2){
                if (face_to){
                    this.y-=2;
                }else {
                    this.y+=2;
                }
                image_type=image_type==1?0:1;
                //食人花是否移动到极限位置
                if (face_to&&(this.y==max_up)){
                    face_to=false;
                }
                if (!face_to&&(this.y==max_down)){
                    face_to=true;
                }
                show=GameUtil.flower.get(image_type);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public int getType() {
        return type;
    }
}
