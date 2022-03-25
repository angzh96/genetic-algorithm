import com.angzh.ga.Point;

import java.awt.*;
import java.awt.geom.Line2D;

public class testIntersect {
    public static void main(String[] args) {
        int OBSTACLES_NUMS = 4; //障碍物个数
        Rectangle[] Obstacles = new Rectangle[OBSTACLES_NUMS]; //Rectangle类数组,(x,y)为左上角
        Obstacles[0] = new Rectangle(7, 5, 5, 3);
        Obstacles[1] = new Rectangle(8, 14, 4, 4);
        Obstacles[2] = new Rectangle(1, 19, 4, 4);
        Obstacles[3] = new Rectangle(16, 14, 3, 4);

        int[] vec = {41, 82, 123, 165, 227, 288, 311, 334, 356, 378};
        int pointLen = vec.length + 2;
        // 网格序列对应的点序列
        com.angzh.ga.Point[] points = new com.angzh.ga.Point[pointLen];
        // 增加起点（0，0）和终点（19，19）
        points[0] = new com.angzh.ga.Point(0.5, 0.5);
        points[pointLen - 1] = new com.angzh.ga.Point(19.5, 19.5);
        for(int i = 1; i < pointLen - 1; i++) {
            points[i] = new com.angzh.ga.Point(vec[i-1] % 20 + 0.5, vec[i-1] / 20 + 0.5);
        }

        int intersectNum = 0;
        for(int i = 0; i < pointLen - 1; i++) {
            for (int j = 0; j < OBSTACLES_NUMS; j++) {
                if (isIntersect(points[i + 1], points[i], Obstacles[j])) {
                    intersectNum++;
                }
            }
            System.out.println(intersectNum);
            intersectNum = 0;
        }
//        Point point0 = new Point(7, 11);
//        Point point1 = new Point(8, 14);
//        isIntersect(point0, point1, Obstacles[0]);
    }

    // 两点组成的线段是否和障碍物相交
    private static boolean isIntersect(com.angzh.ga.Point p1, Point p2, Rectangle rec) {
        // 判断两点组成的线段是否和矩形的四条线段相交
        if (Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x, rec.y, rec.x + rec.width, rec.y)) {
            return true;
        } else if (Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x + rec.width, rec.y, rec.x + rec.width, rec.y - rec.height)) {
            return true;
        } else if (Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x + rec.width, rec.y - rec.height, rec.x, rec.y - rec.height)) {
            return true;
        } else if (Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), rec.x, rec.y - rec.height, rec.x, rec.y)) {
            return true;
        } else {
            return false;
        }
    }
}
