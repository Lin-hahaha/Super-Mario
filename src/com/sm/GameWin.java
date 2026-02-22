package com.sm;

import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame implements Runnable, KeyListener {
    // 储存所有背景
    private List<BackGround> allbg = new ArrayList<>();
    // 存储当前背景
    private BackGround nowbg = new BackGround();
    // 用于双缓存
    private Image offScreenImage = null;
    private Mario mario = new Mario();
    // 当前关卡
    private int currentLevel = 1;
    // 实现马里奥移动
    private Thread thread = new Thread(this);

    public void launch() {
        // 设置窗口大小
        this.setSize(800, 600);
        // 设置窗口剧中显示
        this.setLocationRelativeTo(null);
        // 设置窗口可见性
        this.setVisible(true);
        // 设置窗口关闭
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 设置窗口不可变
        this.setResizable(false);
        // 向窗口加入键盘监听
        this.addKeyListener(this);
        // 设置窗口名称
        this.setTitle("超级玛丽");

        // 初始化图片
        GameUtil.init();
        // 初始化马里奥
        mario = new Mario(10, 355);
        // 创建所有场景
        for (int i = 1; i <= 3; i++) {
            allbg.add(new BackGround(i, i == 3 ? true : false));
        }
        // 将第一个场景设置为当前场景
        nowbg = allbg.get(0);
        mario.setBackGround(nowbg);
        // 绘制图像
        repaint();
        thread.start();
        try {
            new Music();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(800, 600);
        }
        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0, 0, 800, 600);
        // 绘制背景
        graphics.drawImage(nowbg.getBgImage(), 0, 0, this);
        // 绘制敌人
        for (Enemy e : nowbg.getEnemyList()) {
            graphics.drawImage(e.getShow(), e.getX(), e.getY(), this);
        }
        // 绘制障碍物
        for (Obstacle ob : nowbg.getObstacleList()) {
            graphics.drawImage(ob.getShow(), ob.getX(), ob.getY(), this);
        }
        // 绘制城堡
        if (nowbg.getTower() != null) {
            graphics.drawImage(nowbg.getTower(), 620, 270, this);
        }
        // 绘制旗杆
        if (nowbg.getGan() != null) {
            graphics.drawImage(nowbg.getGan(), 500, 220, this);
        }
        // 绘制马里奥
        graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), this);
        // 绘制分数和关卡
        Color c = graphics.getColor();
        graphics.setColor(Color.pink);
        graphics.setFont(new Font("黑体", Font.BOLD, 25));
        graphics.drawString("第" + currentLevel + "关  分数:" + mario.getScore(), 250, 100);
        graphics.setColor(c);
        // 绘制的背景添加到窗口中
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(50);
                // 判断马里奥是否进入下一个场景
                if (mario.getX() >= 775) {
                    int currentIndex = allbg.indexOf(nowbg);
                    if (currentIndex + 1 < allbg.size()) {
                        nowbg = allbg.get(currentIndex + 1);
                        mario.setBackGround(nowbg);
                        mario.setX(10);
                        mario.setY(395);
                    }
                }
                // 判断马里奥是否死亡
                if (mario.isDeath) {
                    JOptionPane.showMessageDialog(this, "马里奥死亡");
                    System.exit(0);
                }
                // 判断游戏是否结束
                if (mario.isOk()) {
                    if (currentLevel == 1) {
                        // 第一关通过，直接进入第二关
                        currentLevel = 2;
                        allbg.clear();
                        for (int i = 4; i <= 6; i++) {
                            allbg.add(new BackGround(i, i == 6));
                        }
                        nowbg = allbg.get(0);
                        mario.setBackGround(nowbg);
                        mario.setX(10);
                        mario.setY(355);
                        mario.setOk(false);
                        mario.setxSpeed(0);
                        mario.setySpeed(0);
                        mario.setStatus("stand-right");
                        mario.setShow(GameUtil.stand_R);
                    } else {
                        JOptionPane.showMessageDialog(this, "恭喜你,全部通关!");
                        System.exit(0);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 按下键盘调用
    @Override
    public void keyPressed(KeyEvent e) {
        // 向右移动
        if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
            mario.rightMove();
        }
        // 向左移动
        if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
            mario.leftMove();
        }
        // 跳跃
        if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
            mario.jump();
        }
    }

    // 松开键盘调用
    @Override
    public void keyReleased(KeyEvent e) {
        // 向左停止
        if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
            mario.leftStop();
        }
        if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
            mario.rightStop();
        }
    }
}
