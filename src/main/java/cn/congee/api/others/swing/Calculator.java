package cn.congee.api.others.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Calculator类，继承JFrame框架，实现事件监听器接口
 *
 * @Author: yang
 * @Date: 2020-12-10 12:37
 */
public class Calculator extends JFrame implements ActionListener {

    /**
     * 1、可实现基本四则运算及平方、开方、求余运算。
     * 2、运算表达式可显示于输入界面并保存于历史记录栏
     * 3、输入界面和历史记录栏皆可实现不断字自动换行功能以及滚动条功能
     * 4、不足之处：进行平方和开方运算时其保存在历史记录中的表达式会出现两个等号及两个结果。
     *
     */

    private String[] KEYS = { "7", "8", "9", "AC", "4", "5", "6", "-", "1", "2", "3", "+", "0", "e", "pi", "/", "sqrt",
            "%", "x*x", "*", "(", ")", ".", "=" };
    private JButton keys[] = new JButton[KEYS.length];
    // 文本域组件TextArea可容纳多行文本；文本框内容初始值设为0.0
    private JTextArea resultText = new JTextArea("0.0");
    // 历史记录文本框初始值设为空
    private JTextArea History = new JTextArea();
    private JPanel jp1=new JPanel();
    private JPanel jp2=new JPanel();
    //给输入显示屏文本域新建一个垂直滚动滑条
    private JScrollPane gdt1=new JScrollPane(resultText);
    //给历史记录文本域新建一个垂直滚动滑条
    private JScrollPane gdt2=new JScrollPane(History);
    //给历史记录文本域新建一个水平滚动滑条
    // private JScrollPane gdt3=new JScrollPane(History);
    private JLabel label = new JLabel("历史记录");
    private String b = "";

    // 构造方法
    public Calculator() {
        //“超”关键字，表示调用父类的构造函数，
        super("Caculator");
        // 设置文本框大小
        resultText.setBounds(20, 18, 255, 115);
        // 文本框内容右对齐
        resultText.setAlignmentX(RIGHT_ALIGNMENT);
        // 文本框不允许修改结果
        resultText.setEditable(false);
        // 设置文本框大小
        History.setBounds(290, 40, 250,370);
        // 文本框内容右对齐
        History.setAlignmentX(LEFT_ALIGNMENT);
        // 文本框不允许修改结果
        History.setEditable(false);
        //设置标签位置及大小
        label.setBounds(300, 15, 100, 20);
        //设置面板窗口位置及大小
        jp2.setBounds(290,40,250,370);
        jp2.setLayout(new GridLayout());
        //设置面板窗口位置及大小
        jp1.setBounds(20,18,255,115);
        jp1.setLayout(new GridLayout());
        // 激活自动换行功能
        resultText.setLineWrap(true);
        // 激活断行不断字功能
        resultText.setWrapStyleWord(true);
        resultText.setSelectedTextColor(Color.RED);
        //自动换行
        History.setLineWrap(true);
        History.setWrapStyleWord(true);
        History.setSelectedTextColor(Color.blue);
        //使滚动条显示出来
        gdt1.setViewportView(resultText);
        gdt2.setViewportView(History);
        //设置让垂直滚动条一直显示
        gdt1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //设置让垂直滚动条一直显示
        gdt2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //设置让水平滚动条一直显示
        gdt2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //将滚动条添加入面板窗口中
        jp1.add(gdt1);
        jp2.add(gdt2);
        //将面板添加到总窗体中
        this.add(jp1);
        //将面板添加到总窗体中
        this.add(jp2);
        this.setLayout(null);
        // 新建“历史记录”标签
        this.add(label);
        // 新建文本框，该语句会添加进去一个新的JTextArea导致带有滚动条的文本无法显示或者发生覆盖
        //this.add(resultText);
        // 新建历史记录文本框，该语句会添加进去一个新的JTextArea导致带有滚动条的文本无法显示
        //this.add(History);

        // 放置按钮
        int x = 20, y = 150;
        for (int i = 0; i < KEYS.length; i++)
        {
            keys[i] = new JButton();
            keys[i].setText(KEYS[i]);
            keys[i].setBounds(x, y, 60, 40);
            if (x < 215) {
                x += 65;
            } else {
                x = 20;
                y += 45;
            }
            this.add(keys[i]);
        }
        // 每个按钮都注册事件监听器
        for (int i = 0; i < KEYS.length; i++)
        {
            keys[i].addActionListener(this);
        }
        this.setResizable(false);
        this.setBounds(500, 200, 567, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // 事件处理
    @Override
    public void actionPerformed(ActionEvent e) {
        //使输入的表达式显示在历史记录文本框中
        //History.setText(b);
        //获得事件源的标签
        String label=e.getActionCommand();
        if(label=="=") {
            resultText.setText(this.b);
            History.setText(History.getText()+resultText.getText());
            //调用计算方法，得出最终结果
            if(label=="=") {
                String s[]=houzhui(this.b);
                String result=Result(s);
                this.b=result+"";
                //更新文本框，当前结果在字符串b中，并未删除，下一次输入接着此结果以实现连续运算
                resultText.setText(this.b);
                History.setText(History.getText()+"="+resultText.getText()+"\n");
            }
            //清空按钮，消除显示屏文本框前面所有的输入和结果
        }else if(label == "AC"){
            this.b = "";
            //更新文本域的显示，显示初始值;
            resultText.setText("0");
        }else if(label == "sqrt"){
            String n = kfys(this.b);
            //使运算表达式显示在输入界面
            resultText.setText("sqrt"+"("+this.b+")"+"="+n);
            //获取输入界面的运算表达式并使其显示在历史记录文本框
            History.setText(History.getText()+"sqrt"+"("+this.b+")"+"=");
            this.b = n;
        }else if(label == "x*x"){
            String m = pfys(this.b);
            //使运算表达式显示在输入界面
            resultText.setText(this.b+"^2"+"="+m);
            //获取输入界面的运算表达式并使其显示在历史记录文本框
            History.setText(History.getText()+this.b+"^2"+"=");
            this.b = m;
        }else if(label == "e" || label == "pi"){
            if(label == "e"){
                //将e的值以字符串的形式传给m
                String m = String.valueOf(2.71828);
                //保留显示m之前输入的运算符或数字字符继续下一步运算
                this.b = this.b + m;
                resultText.setText(this.b);
                // History.setText(History.getText()+this.b);
            }if(label=="pi"){
                String m=String.valueOf(3.14159265);
                this.b=this.b+m;
                resultText.setText(this.b);
                // History.setText(History.getText()+this.b);
            }
        }else{
            this.b=this.b+label;
            resultText.setText(this.b);
            // History.setText(History.getText()+this.b);
        }
        //使输入的表达式显示在历史记录文本框中
        //History.setText(History.getText()+this.b);
    }

    //将中缀表达式转换为后缀表达式
    private String[] houzhui(String str) {
        // 用于承接多位数的字符串
        String s = "";
        // 静态栈,对用户输入的操作符进行处理，用于存储运算符
        char opStack[] = new char[100];
        // 后缀表达式字符串数组，为了将多位数存储为独立的字符串
        String postQueue[] = new String[100];
        // 静态指针top,控制变量j
        int top = -1, j = 0;
        for(int i = 0; i < str.length(); i++){
            // 遍历中缀表达式
            // indexof函数，返回字串首次出现的位置；charAt函数返回index位置处的字符；
            if("0123456789.".indexOf(str.charAt(i)) >= 0){
                // 遇到数字字符的情况
                // 作为承接字符，每次开始时都要清空
                s = "";
                for(; i < str.length() && "0123456789.".indexOf(str.charAt(i)) >= 0; i++){
                    s = s + str.charAt(i);
                }
                i--;
                // 数字字符直接加入后缀表达式
                postQueue[j] = s;
                j++;
            }else if("(".indexOf(str.charAt(i)) >= 0){
                // 遇到左括号
                top++;
                // 左括号入栈
                opStack[top] = str.charAt(i);
            }else if(")".indexOf(str.charAt(i)) >= 0){
                // 遇到右括号
                for(;;){
                    // 栈顶元素循环出栈，直到遇到左括号为止
                    if(opStack[top] != '('){
                        // 栈顶元素不是左括号
                        // 栈顶元素出栈
                        postQueue[j] = opStack[top] + "";
                        j++;
                        top--;
                    }else{
                        // 找到栈顶元素是左括号
                        // 删除栈顶左括号
                        top--;
                        // 循环结束
                        break;
                    }
                }
            }
            if("*%/".indexOf(str.charAt(i)) >= 0){
                // 遇到高优先级运算符
                if (top == -1) {
                    // 若栈为空则直接入栈
                    top++;
                    opStack[top] = str.charAt(i);
                }else{
                    // 栈不为空,把栈中弹出的元素入队，直到栈顶元素优先级小于x或者栈为空
                    if("*%/".indexOf(opStack[top]) >= 0){
                        // 栈顶元素也为高优先级运算符
                        // 栈顶元素出栈进入后缀表达式
                        postQueue[j] = opStack[top] + "";
                        j++;
                        // 当前运算符入栈
                        opStack[top] = str.charAt(i);
                    }else if("(".indexOf(opStack[top]) >= 0){
                        // 栈顶元素为左括号，当前运算符入栈
                        top++;
                        opStack[top] = str.charAt(i);
                    }else if("+-".indexOf(str.charAt(i)) >= 0){
                        // 遇到低优先级运算符
                        // 栈顶元素出栈进入后最表达式
                        postQueue[j] = opStack[top] + "";
                        j++;
                        // 当前元素入栈
                        opStack[top] = str.charAt(i);
                    }
                }
            }else if("+-".indexOf(str.charAt(i)) >= 0){
                if(top == -1){
                    top++;
                    opStack[top] = str.charAt(i);
                }else{
                    if("*%/".indexOf(opStack[top]) >= 0){
                        // 栈顶元素也为高优先级运算符
                        // 栈顶元素出栈进入后缀表达式
                        postQueue[j] = opStack[top] + "";
                        j++;
                        // 当前运算符入栈
                        opStack[top] = str.charAt(i);
                    }else if("(".indexOf(opStack[top]) >= 0){
                        // 栈顶元素为左括号，当前运算符入栈
                        top++;
                        opStack[top] = str.charAt(i);
                    }else if ("+-".indexOf(str.charAt(i)) >= 0){
                        // 遇到低优先级运算符
                        // 栈顶元素出栈进入后最表达式
                        postQueue[j] = opStack[top] + "";
                        j++;
                        // 当前元素入栈
                        opStack[top] = str.charAt(i);
                    }
                }
            }
        }
        // 遍历结束后将栈中剩余元素依次出栈进入后缀表达式
        for(; top != -1;){
            postQueue[j] = opStack[top] + "";
            j++;
            top--;
        }
        return postQueue;
    }

    //开方运算方法
    public String kfys(String str){
        String result = "";
        double a = Double.parseDouble(str), b = 0;
        b = Math.sqrt(a);
        //将运算结果转换为string类型并赋给string类型的变量result
        result = String.valueOf(b);
        return result;
    }

    //平方运算方法
    public String pfys(String str){
        String result = "";
        double a = Double.parseDouble(str), b = 0;
        b = Math.pow(a, 2);
        result = String.valueOf(b);
        return result;
    }

    // 计算后缀表达式，并返回最终结果
    public String Result(String str[]){
        // 顺序存储的栈，数据类型为字符串
        String Result[] = new String[100];
        // 静态指针Top
        int Top = -1;
        for(int i = 0; str[i] != null; i++){
            if("+-*%/".indexOf(str[i]) < 0){
                Top++;
                Result[Top] = str[i];
            }
            // 遇到运算符字符，将栈顶两个元素出栈计算并将结果返回栈顶
            if("+-*%/".indexOf(str[i]) >= 0){
                double x, y, n;
                // 顺序出栈两个数字字符串，并转换为double类型
                x = Double.parseDouble(Result[Top]);
                Top--;
                y = Double.parseDouble(Result[Top]);
                Top--;
                if("-".indexOf(str[i]) >= 0){
                    n = y - x;
                    Top++;
                    // 将运算结果重新入栈
                    Result[Top] = String.valueOf(n);
                }
                if("+".indexOf(str[i]) >= 0){
                    n = y + x;
                    Top++;
                    // 将运算结果重新入栈
                    Result[Top] = String.valueOf(n);
                }
                if("*".indexOf(str[i]) >= 0){
                    n = y * x;
                    Top++;
                    // 将运算结果重新入栈
                    Result[Top] = String.valueOf(n);

                }
                if("/".indexOf(str[i]) >= 0){
                    // 被除数不允许为0
                    if(x == 0){
                        String s = "error!";
                        return s;
                    }else{
                        n = y / x;
                        Top++;
                        // 将运算结果重新入栈
                        Result[Top] = String.valueOf(n);
                    }
                }if("%".indexOf(str[i]) >= 0){
                    // 被除数不允许为0
                    if (x == 0){
                        String s = "error!";
                        return s;
                    }else{
                        n = y % x;
                        Top++;
                        // 将运算结果重新入栈
                        Result[Top] = String.valueOf(n);
                    }
                }
            }
        }
        // 返回最终结果
        return Result[Top];
    }

    // 主函数
    public static void main(String[] args) {
        Calculator a = new Calculator();
    }


}
