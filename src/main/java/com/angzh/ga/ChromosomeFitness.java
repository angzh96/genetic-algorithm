package com.angzh.ga;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * 个体适应度计算
 * Fitness function, which calculates difference between chromosomes vector
 * and target vector
 */

public class ChromosomeFitness {

    public Double calculate(Chromosome chromosome) {
        int OBSTACLES_NUMS = 7; //障碍物个数
        Rectangle[] Obstacles = new Rectangle[OBSTACLES_NUMS]; //Rectangle类数组,(x,y)为左上角
        Obstacles[0] = new Rectangle(0, 3, 6, 2);
        Obstacles[1] = new Rectangle(11, 4, 3, 3);
        Obstacles[2] = new Rectangle(0, 10, 2, 3);
        Obstacles[3] = new Rectangle(5, 10, 4, 4);
        Obstacles[4] = new Rectangle(13, 8, 4, 2);
        Obstacles[5] = new Rectangle(2, 14, 3, 3);
        Obstacles[6] = new Rectangle(9, 14, 7, 2);

        int[] vec = chromosome.getVector();
        int pointLen = vec.length + 2;
        // 网格序列对应的点序列
        Point[] points = new Point[pointLen];
        // 增加起点（0，0）和终点（19，19）
        points[0] = new Point(0.5, 0.5);
        points[pointLen - 1] = new Point(19.5, 19.5);
        for(int i = 1; i < pointLen - 1; i++) {
            points[i] = new Point(vec[i-1] % 20 + 0.5, vec[i-1] / 20 + 0.5);
        }
        //计算总距离
        double totalDistance = 0;
        for(int i = 0; i < pointLen - 1; i++) {
            totalDistance += distance(points[i+1], points[i]);
        }

        //判断是否与障碍物相交，并计算相交线段个数
        int intersectNum = 0;
        for(int i = 0; i < pointLen - 1; i++) {
            for(int j = 0; j < OBSTACLES_NUMS; j++) {
                if(isIntersect(points[i+1], points[i], Obstacles[j])) {
                    intersectNum ++;
                }
            }
        }
//        System.out.println(intersectNum);
        double fitness = totalDistance + intersectNum * 20;
        return fitness;
    }

    // 计算平方
    private double pow(double x) {
        return x * x;
    }

    // 两点间距离
    private double distance(Point p1, Point p2) {
        double temp = pow(p1.getX() - p2.getX()) + pow(p1.getY() - p2.getY());
        return temp;
    }

    // 两点组成的线段是否和障碍物相交
    private boolean isIntersect(Point p1, Point p2, Rectangle rec) {
        // 分别判断两点组成的线段是否和矩形的四条线段相交
        if(Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x, rec.y, rec.x+rec.width, rec.y)) {
            return true;
        }
        else if(Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x+rec.width, rec.y, rec.x+rec.width, rec.y-rec.height)) {
            return true;
        }
        else if(Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x+rec.width, rec.y-rec.height, rec.x, rec.y-rec.height)) {
            return true;
        }
        else if(Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x, rec.y-rec.height, rec.x, rec.y)) {
            return true;
        }
        else {
            return false;
        }
    }
}
