package com.hqq.core.app.ui.data;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.databing
 * @FileName :   User
 * @Date : 2019/10/22 0022  下午 1:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class User {
    private String name;
    private int score;
    private int level;
    private int avatar;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public int getLevel() { return level; }

    public int getAvatar() { return avatar; }

    public void setAvatar(int avatar) { this.avatar = avatar; }

    public void setLevel(int level) { this.level = level; }

    public static User newInstance() {
        User user = new User();
        user.setName("王大锤:" + (int)(Math.random() * 10));
        user.setScore((int) (Math.random() * 999));
        user.setLevel((int) (Math.random() * 77));
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", level=" + level +
                ", avatar=" + avatar +
                '}';
    }
}
