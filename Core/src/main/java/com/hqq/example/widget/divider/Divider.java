package com.hqq.example.widget.divider;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.widget.divider
 * @FileName :   Divider
 * @Date : 2018/7/5 0005  下午 5:14
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class Divider {

    public SideLine leftSideLine;
    public SideLine topSideLine;
    public SideLine rightSideLine;
    public SideLine bottomSideLine;


    public Divider(SideLine leftSideLine, SideLine topSideLine, SideLine rightSideLine, SideLine bottomSideLine) {
        this.leftSideLine = leftSideLine;
        this.topSideLine = topSideLine;
        this.rightSideLine = rightSideLine;
        this.bottomSideLine = bottomSideLine;
    }

    public SideLine getLeftSideLine() {
        return leftSideLine;
    }

    public void setLeftSideLine(SideLine leftSideLine) {
        this.leftSideLine = leftSideLine;
    }

    public SideLine getTopSideLine() {
        return topSideLine;
    }

    public void setTopSideLine(SideLine topSideLine) {
        this.topSideLine = topSideLine;
    }

    public SideLine getRightSideLine() {
        return rightSideLine;
    }

    public void setRightSideLine(SideLine rightSideLine) {
        this.rightSideLine = rightSideLine;
    }

    public SideLine getBottomSideLine() {
        return bottomSideLine;
    }

    public void setBottomSideLine(SideLine bottomSideLine) {
        this.bottomSideLine = bottomSideLine;
    }
}
