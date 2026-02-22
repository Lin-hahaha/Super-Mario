package com.sm;

import jdk.dynalink.beans.StaticClass;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    // 当前场景要显示的图片
    private BufferedImage bgImage = null;
    // 记录场景是第几个
    private int sort;
    // 判断是否到达最后场景
    private boolean flag;
    // 用于存放障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();
    // 用于存放敌人
    private List<Enemy> enemyList = new ArrayList<>();
    // 用于存放旗杆
    private BufferedImage gan = null;
    // 用于存放城堡
    private BufferedImage tower = null;
    // 判断马里奥是否到达旗杆
    private boolean isReach = false;
    // 判断旗子是否落地
    private boolean isBase = false;

    public BackGround() {
    }

    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;
        if (flag || sort >= 4) {
            bgImage = GameUtil.bg_2;
        } else {
            bgImage = GameUtil.bg_1;
        }
        // 判断是否第一关
        if (sort == 1) {
            // 绘制地面,上地面type=2,下地面type=1
            for (int i = 0; i <= 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 2, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j <= 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 1, this));
                }
            }
            // 绘制方块
            for (int i = 120; i <= 150; i += 30) {
                obstacleList.add(new Obstacle(i, 300, 3, this));
            }
            for (int i = 300; i <= 570; i += 30) {
                if (i == 360 || i == 390 || i == 480 || i == 510 || i == 540) {
                    obstacleList.add(new Obstacle(i, 300, 3, this));

                } else {
                    obstacleList.add(new Obstacle(i, 300, 0, this));
                }
            }
            for (int i = 420; i <= 450; i += 30) {
                obstacleList.add(new Obstacle(i, 240, 3, this));
            }
            // 绘制水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 7, this));
                    obstacleList.add(new Obstacle(645, i, 8, this));
                }
            }
            // 绘制第一关的蘑菇敌人
            enemyList.add(new Enemy(580, 385, true, 1, this));
            // 绘制食人花敌人
            enemyList.add(new Enemy(635, 420, true, 2, 328, 428, this));
        }
        // 判断是否为第二关
        if (sort == 2) {
            // 绘制地面
            for (int i = 0; i <= 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 2, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j <= 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 1, this));
                }
            }
            // 绘制第一个水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(60, i, 5, this));
                    obstacleList.add(new Obstacle(85, i, 6, this));
                } else {
                    obstacleList.add(new Obstacle(60, i, 7, this));
                    obstacleList.add(new Obstacle(85, i, 8, this));
                }
            }
            // 绘制第二个水管
            for (int i = 380; i <= 600; i += 25) {
                if (i == 380) {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 7, this));
                    obstacleList.add(new Obstacle(645, i, 8, this));
                }
            }
            // 障碍物方块
            obstacleList.add(new Obstacle(300, 330, 0, this));
            for (int i = 270; i <= 330; i += 30) {
                if (i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i, 360, 0, this));
                } else {
                    obstacleList.add(new Obstacle(i, 360, 3, this));
                }
            }
            for (int i = 240; i <= 360; i += 30) {
                if (i == 240 || i == 360) {
                    obstacleList.add(new Obstacle(i, 390, 0, this));
                } else {
                    obstacleList.add(new Obstacle(i, 390, 3, this));
                }
            }
            obstacleList.add(new Obstacle(2400, 300, 0, this));
            for (int i = 360; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i, 270, 3, this));
            }
            // 绘制第二关第一个食人花敌人
            enemyList.add(new Enemy(75, 420, true, 2, 328, 418, this));
            // 绘制第二个食人花
            enemyList.add(new Enemy(635, 420, true, 2, 298, 388, this));
            // 绘制第一个蘑菇敌人
            enemyList.add(new Enemy(200, 385, true, 1, this));
            // 绘制第二个蘑菇敌人
            enemyList.add(new Enemy(500, 385, true, 1, this));
        }
        // 判断是否为第三关
        if (sort == 3) {
            // 绘制第三关地面
            for (int i = 0; i <= 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 2, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j <= 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 1, this));
                }
            }
            // 绘制砖块
            int temp = 290;
            for (int i = 390; i >= 270; i -= 30) {
                for (int j = temp; j <= 410; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 3, this));
                }
                temp += 30;
            }
            temp = 60;
            for (int i = 390; i >= 360; i -= 30) {
                for (int j = temp; j <= 90; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 3, this));
                }
                temp += 30;
            }

            // 绘制旗杆
            gan = GameUtil.gan;
            // 绘制城堡
            tower = GameUtil.tower;
            // 旗子到旗杆上
            obstacleList.add(new Obstacle(515, 220, 4, this));
            // 绘制第三关蘑菇敌人
            enemyList.add(new Enemy(150, 385, true, 1, this));
        }

        // ===================== 第二关（场景4-6，参照第一关风格设计）=====================

        // 场景4（第二关第一个场景，参照场景1的风格）
        if (sort == 4) {
            // 绘制地面
            for (int i = 0; i <= 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 2, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j <= 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 1, this));
                }
            }
            // 绘制方块（左侧一排）
            for (int i = 120; i <= 180; i += 30) {
                obstacleList.add(new Obstacle(i, 300, 0, this));
            }
            // 绘制方块（中间一排，可打碎和不可打碎混合）
            for (int i = 300; i <= 570; i += 30) {
                if (i == 330 || i == 420 || i == 510) {
                    obstacleList.add(new Obstacle(i, 300, 3, this));
                } else {
                    obstacleList.add(new Obstacle(i, 300, 0, this));
                }
            }
            // 绘制上层方块
            for (int i = 390; i <= 450; i += 30) {
                obstacleList.add(new Obstacle(i, 240, 3, this));
            }
            // 绘制水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 7, this));
                    obstacleList.add(new Obstacle(645, i, 8, this));
                }
            }
            // 绘制蘑菇敌人
            enemyList.add(new Enemy(200, 385, true, 1, this));
            enemyList.add(new Enemy(500, 385, true, 1, this));
            // 绘制食人花敌人
            enemyList.add(new Enemy(635, 420, true, 2, 328, 428, this));
        }

        // 场景5（第二关第二个场景，参照场景2的风格）
        if (sort == 5) {
            // 绘制地面
            for (int i = 0; i <= 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 2, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j <= 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 1, this));
                }
            }
            // 绘制第一个水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(60, i, 5, this));
                    obstacleList.add(new Obstacle(85, i, 6, this));
                } else {
                    obstacleList.add(new Obstacle(60, i, 7, this));
                    obstacleList.add(new Obstacle(85, i, 8, this));
                }
            }
            // 绘制第二个水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 7, this));
                    obstacleList.add(new Obstacle(645, i, 8, this));
                }
            }
            // 障碍物方块（金字塔结构）
            obstacleList.add(new Obstacle(300, 330, 0, this));
            for (int i = 270; i <= 330; i += 30) {
                if (i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i, 360, 0, this));
                } else {
                    obstacleList.add(new Obstacle(i, 360, 3, this));
                }
            }
            for (int i = 240; i <= 360; i += 30) {
                if (i == 240 || i == 360) {
                    obstacleList.add(new Obstacle(i, 390, 0, this));
                } else {
                    obstacleList.add(new Obstacle(i, 390, 3, this));
                }
            }
            // 绘制上方方块
            for (int i = 390; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i, 270, 3, this));
            }
            // 绘制蘑菇敌人
            enemyList.add(new Enemy(200, 385, true, 1, this));
            enemyList.add(new Enemy(420, 385, true, 1, this));
            enemyList.add(new Enemy(550, 385, true, 1, this));
            // 绘制食人花敌人
            enemyList.add(new Enemy(75, 420, true, 2, 328, 418, this));
            enemyList.add(new Enemy(635, 420, true, 2, 328, 418, this));
        }

        // 场景6（第二关最终场景，参照场景3的风格）
        if (sort == 6) {
            // 绘制地面
            for (int i = 0; i <= 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 2, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j <= 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 1, this));
                }
            }
            // 绘制阶梯（通向旗帜）
            int temp2 = 290;
            for (int i = 390; i >= 270; i -= 30) {
                for (int j = temp2; j <= 410; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 3, this));
                }
                temp2 += 30;
            }
            // 左侧小阶梯
            temp2 = 60;
            for (int i = 390; i >= 360; i -= 30) {
                for (int j = temp2; j <= 90; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 3, this));
                }
                temp2 += 30;
            }
            // 绘制旗杆
            gan = GameUtil.gan;
            // 绘制城堡
            tower = GameUtil.tower;
            // 旗子到旗杆上
            obstacleList.add(new Obstacle(515, 220, 4, this));
            // 绘制蘑菇敌人
            enemyList.add(new Enemy(150, 385, true, 1, this));
            enemyList.add(new Enemy(250, 385, true, 1, this));
        }
    }

    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public BufferedImage getTower() {
        return tower;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
