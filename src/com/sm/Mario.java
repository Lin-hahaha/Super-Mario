package com.sm;

import javazoom.jl.decoder.JavaLayerException;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public class Mario implements Runnable {
    // 马里奥属性
    private int x;
    private int y;
    // 用于表示当前状态
    private String status;
    // 用于显示当前状态对于图像
    private BufferedImage show = null;
    // 定义一个background对象,用来获取障碍物信息
    private BackGround backGround = new BackGround();
    // 用来实现马里奥动作
    private Thread thread = null;
    // 用来表示马里奥的移动速度
    private int xSpeed;
    // 用来表示马里奥的跳跃速度
    private int ySpeed;

    // 定义一个索引
    private int index;
    // 用于表示马里奥方向
    private boolean face_to = true;
    // 表示马里奥上升时间
    private int upTime = 0;
    // 判断马里奥是否走到城堡门口
    private boolean isOk;
    // 判断马里奥死亡
    public boolean isDeath = false;
    private int score = 0;

    public Mario() {
    }

    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        show = GameUtil.stand_R;
        this.status = "stand-right";
        thread = new Thread(this);
        thread.start();
    }

    public void death() {
        isDeath = true;
    }

    // 马里奥向左移动
    public void leftMove() {
        // 改变马里奥速度
        xSpeed = -5;
        // 判断马里奥是否碰到旗帜
        if (backGround.isReach()) {
            xSpeed = 0;
        }
        // 判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "move--left";
        }
    }

    public void rightMove() {
        // 改变马里奥速度
        xSpeed = 5;
        if (backGround.isReach()) {
            xSpeed = 0;
        }
        // 判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "move--right";
        }
    }

    public void leftStop() {
        xSpeed = 0;
        // 判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "stop--left";
        }
    }

    public void rightStop() {
        xSpeed = 0;
        // 判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "stop--right";
        }
    }

    // 马里奥跳跃方法
    public void jump() {
        if (status.indexOf("jump") == -1) {
            if (status.indexOf("left") != -1) {
                status = "jump--left";
            } else {
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 8;
        }
        if (backGround.isReach()) {
            ySpeed = 0;
        }
    }

    // 马里奥下落方法
    public void fall() {
        if (status.indexOf("left") != -1) {
            status = "jump--left";
        } else {
            status = "jump--right";
        }
        ySpeed = 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getStatus() {
        return status;
    }

    public BufferedImage getShow() {
        return show;
    }

    public BackGround getBackGround() {
        return backGround;
    }

    public Thread getThread() {
        return thread;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public int getIndex() {
        return index;
    }

    public boolean isFace_to() {
        return face_to;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFace_to(boolean face_to) {
        this.face_to = face_to;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void run() {
        // 判断是否处于障碍物
        while (true) {

            boolean onObstacle = false;
            // 判断是否可以往右走
            boolean canRight = true;
            // 判断是否可以往左走
            boolean canLeft = true;
            // 判断马里奥是否到达旗杆位置
            if (backGround.isFlag() && this.x >= 500) {
                this.backGround.setReach(true);
                // 判断旗帜是否下落
                if (this.backGround.isBase()) {
                    status = "move--right";
                    if (x < 690) {
                        x += 5;
                    } else {
                        isOk = true;
                    }
                } else {
                    if (y < 395) {
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    if (y > 395) {
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            } else {
                // 遍历当前场景里所有的障碍物
                for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                    Obstacle ob = backGround.getObstacleList().get(i);
                    // 判断马里奥是否位于障碍物上
                    if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        onObstacle = true;
                    }

                    // 判断是否跳起来顶到砖块
                    if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20)
                            && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        if (ob.getType() == 0) {
                            backGround.getObstacleList().remove(ob);
                            score++;
                        }
                        upTime = 0;
                    }

                    // 判断是否可以往右走
                    if (ob.getX() == this.x + 25 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                        canRight = false;
                    }

                    // 判断是否可以往左走
                    if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                        canLeft = false;
                    }

                }
                for (int i = 0; i < backGround.getEnemyList().size(); i++) {
                    Enemy e = backGround.getEnemyList().get(i);
                    if (e.getY() == this.y + 20 && (e.getX() - 25 < this.x && e.getX() + 35 >= this.x)) {
                        if (e.getType() == 1) {
                            e.death();
                            score += 2;
                            upTime = 3;
                            ySpeed = -10;
                        } else if (e.getType() == 2) {
                            // 马里奥死亡
                            death();
                        }
                    }
                    if (e.getX() + 35 > this.x && e.getX() - 25 < this.x
                            && (e.getY() + 35 > this.y && e.getY() - 20 < this.y)) {
                        // 马里奥死亡
                        death();
                    }
                }

                // 进行马里奥跳跃的操作
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("left") != -1) {
                        if (xSpeed != 0) {
                            status = "move--left";
                        } else {
                            status = "stop--left";
                        }
                    } else {
                        if (xSpeed != 0) {
                            status = "move--right";
                        } else {
                            status = "stop--right";
                        }
                    }
                } else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        fall();
                    }
                    y += ySpeed;
                }
            }
            if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)) {
                x += xSpeed;
                // 判断马里奥是否到了最左边
                if (x < 0) {
                    x = 0;
                }
            }
            // 判断马里奥是否掉入深坑
            if (y > 550) {
                death();
            }
            // 判断当前是否是移动状态
            if (status.contains("move")) {
                index = index == 0 ? 1 : 0;
            }
            // 判断是否向左移动
            if ("move--left".equals(status)) {
                show = GameUtil.run_L.get(index);
            }
            // 判断是否向右移动
            if ("move--right".equals(status)) {
                show = GameUtil.run_R.get(index);
            }
            // 判断是否向左停止
            if ("stop--left".equals(status)) {
                show = GameUtil.stand_L;
            }
            // 判断是否向右停止
            if ("stop--right".equals(status)) {
                show = GameUtil.stand_R;
            }
            // 判断是否向左跳跃
            if ("jump--left".equals(status)) {
                show = GameUtil.jump_L;
            }
            // 判断是否向右跳跃
            if ("jump--right".equals(status)) {
                show = GameUtil.jump_R;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
