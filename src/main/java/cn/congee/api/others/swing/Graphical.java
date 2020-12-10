package cn.congee.api.others.swing;

import java.util.Scanner;

/**
 * 图形生成
 *
 * @Author: yang
 * @Date: 2020-12-10 12:39
 */
public class Graphical {

    /**
     * 生成平行四边形
     *
     */
    public static void parallelogram(){
        //首先引用Scanner，
        Scanner sc = new Scanner(System.in);
        //先定义一个变量为整形
        int Length,height;
        for (int a = 0; a < 1; a++) {
            System.out.println("请输入平行四边形的长：");
            Length = sc.nextInt();
            System.out.println("高：");
            height = sc.nextInt();
            System.out.println("长为" + Length + "高为" + height + "的平行四边形如图：");
            System.out.println("=================================parallelogram start==================================");
            for (int i = 1; i <= height; i++) {
                for (int j = 1; j <= height - i; j++) {
                    System.out.print("  ");
                }
                for (int j = 1; j <= Length; j++) {
                    System.out.print(" * ");
                }
                System.out.println();
            }
        }
        System.out.println("====================================parallelogram end=====================================");
    }

    /**
     * 生成三角形
     *
     */
    public static void regularTriangle(){
        //首先引用Scanner，
        Scanner sc = new Scanner(System.in);
        //先定义一个变量为整形
        int Length,height;
        for (int a = 0; a < 1; a++) {
            System.out.println("请输入三角形的高：");
            height = sc.nextInt();
            System.out.println("高为" + height + "的三角形如图：");
            System.out.println("=================================regularTriangle start==================================");
            for (int i = 1; i <= height; i++) {
                for (int j = 1; j <= height - i; j++) {
                    System.out.print("  ");
                }
                for (int j = 1; j <= 2 * i - 1; j++) {
                    System.out.print("* ");
                }
                System.out.println();
            }
        }
        System.out.println("===================================regularTriangle end====================================");
    }

    /**
     * 生成菱形
     *
     */
    public static void diamond(){
        //首先引用Scanner，
        Scanner sc = new Scanner(System.in);
        //先定义一个变量为整形
        int Length,height;
        for (int a = 1; a <2 ; a++) {
            System.out.println("请输入菱形的边长：");
            Length = sc.nextInt();
            System.out.println("边长为" + Length + "的菱形如图：");
            System.out.println("====================================diamond start=====================================");
            for (int i = 1; i <=Length ; i++) {
                for (int j = 1; j <=Length-i ; j++) {
                    System.out.print("  ");
                }
                for (int j = 1; j <=2*i-1 ; j++) {
                    System.out.print("* ");
                }
                System.out.println();
            }
            for (int i = 1; i <=Length-1 ; i++) {
                for (int j = 1; j <=i ; j++) {
                    System.out.print("  ");
                }
                for (int j = 1; j <=2*Length-2*i-1 ; j++) {
                    System.out.print("* ");
                }
                System.out.println();
            }
        }
        System.out.println("=======================================diamond end========================================");
    }

    /**
     * 生成空心菱形
     *
     */
    public static void diamondHollow(){
        //首先引用Scanner，
        Scanner sc = new Scanner(System.in);
        //先定义一个变量为整形
        int Length,height;
        for (int a = 0; a < 1; a++) {
            System.out.println("请输入空心菱形的边长：");
            Length = sc.nextInt();
            System.out.println("边长为" + Length + "的空心菱形如图：");
            System.out.println("==================================diamondHollow start===================================");
            for (int i = 1; i <= Length; i++) {
                for (int j = 1; j <= Length - i; j++) {
                    System.out.print("  ");
                }
                for (int j = 1; j <= 2 * i - 1; j++) {
                    if (j == 1 || j == 2 * i - 1) {
                        System.out.print("* ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            for (int i = 1; i < Length; i++) {
                for (int j = 1; j <= i; j++) {
                    System.out.print("  ");
                }
                for (int j = 1; j <= 2 * Length - 2 * i - 1; j++) {
                    if (j == 1 || j == 2 * Length - 2 * i - 1) {
                        System.out.print("* ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("====================================diamondHollow end=====================================");
    }

    public static void main(String[] args) {
        //平行四边形
        Graphical.parallelogram();
        //三角形
        Graphical.regularTriangle();
        //菱形
        Graphical.diamond();
        //空心菱形
        Graphical.diamondHollow();
    }

}
