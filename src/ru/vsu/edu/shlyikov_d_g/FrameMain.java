package ru.vsu.edu.shlyikov_d_g;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.w3c.dom.ls.LSOutput;
import ru.vsu.edu.shlyikov_d_g.util.JTableUtils;
import ru.vsu.edu.shlyikov_d_g.util.ArrayUtils;
import ru.vsu.edu.shlyikov_d_g.util.SwingUtils;

import static ru.vsu.edu.shlyikov_d_g.Main.printElems;


public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable task1_tableInput;
    private JButton buttonLoadInputFromFile;
    private JButton buttonOutput;
    private JButton buttonSolve;
    private JTable task1_tableOutput;
    private JComboBox taskChooser;
    private JPanel task1;
    private JScrollPane task1input;
    private JPanel tasks_panel;
    private JPanel task2;
    private JPanel buttonsPanel;
    private JScrollPane task1output;
    private JScrollPane task2output;
    private JTable task2_tableInput;
    private JTable task2_tableOutput;
    private JButton taskSelectorButton;
    private JButton buttonRandom;
    private JSpinner sizeSpinner;
    private JSpinner maxSpinner;
    private JLabel matrixSize;
    private JLabel matrixMax;
    private JPanel selectorPanel;
    private JPanel matrixSizePanel;
    private JPanel maxNumPanel;
    private JScrollPane task2input;
    private JPanel task3;
    private JLabel task2_A;
    private JLabel task2_A_1;
    private JTable task2_tableUnitMatrix;
    private JLabel task2_unitMatrix;
    private JScrollPane task3input;
    private JTable task3_tableInput;
    private JTable task3_tableInput2;
    private JScrollPane task3intput2;
    private JTable task3_tableOutput;
    private JScrollPane task3output;
    private JLabel task3_B;
    private JLabel task3_A;
    private JLabel task3_X;
    private JLabel task3_formula;
    private JButton buttonMultiply;
    private JPanel task4;
    private JTable kramer_tableInput;
    private JTable kramer_tableOutput;
    private JTextArea kramer_SLAU;
    private JTextArea kramer_Variables;
    private JPanel Kramer;
    private JPanel Gauss;
    private JButton changeGauss;
    private JButton changeKramer;
    private JTable gauss_tableInput;
    private JTable gauss_tableOutput;
    private JTextArea gauss_SLAU;
    private JTextArea gauss_Variables;
    private JScrollPane kramerInput;
    private JScrollPane gaussIn;
    private JPanel task5;
    private JScrollPane task5_Input;
    private JTable task5_tableInput;
    private JTextArea task5_gausses;
    private JTextArea task5_values;
    private JPanel task6;
    private JPanel task6_painter = null;
    private JPanel task6_paint;
    private JTextArea task6_inputA;
    private JTextArea task6_inputB;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    private static int a = 1;
    private static int b = 1;

    private CurrentTask curTask = CurrentTask.TASK1;

    public static void goToLayout(JPanel jf, String name) {
        CardLayout cardLayout = (CardLayout) jf.getLayout();
        cardLayout.show(jf, name);
    }

    private String vars = "xyzklmnqrpasdfghj";

    private String textSLAU(int[][] arr){
        StringBuilder sb = new StringBuilder();

        boolean flag = false;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (j == arr[0].length - 1) {
                    sb.append("= ");
                    sb.append(arr[i][j]);
                } else {
                    if (arr[i][j] != 0) {
                        if (flag) {
                            sb.append("+ ");
                        }
                    } else {
                        continue;
                    }
                    flag = true;
                    sb.append(arr[i][j]);
                    sb.append(vars.charAt(j));
                    sb.append(" ");
                }
            }
            flag = false;
            sb.append("\n");
        }
        return sb.toString();
    }

    private String textVariables(Element[] arr){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(vars.charAt(i));
            sb.append(" = ");
            sb.append(arr[i].print());
            sb.append("\n");
            }
            return sb.toString();
        }

        private String columnPrint(String[] arr){
            StringBuilder sb = new StringBuilder();
            for (String s : arr) {
                sb.append(s);
                sb.append("\n");
            }
            return sb.toString();
        }

    private String textVariables(Element[][] arr, int[] a){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                sb.append(vars.charAt(a[i]-1));
                sb.append(" = ");
                sb.append(arr[i][j].print());
                if (j < arr[0].length) {
                    sb.append(" | ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    class myPanel extends JPanel
    {
        myPanel()
        {
            this.setBorder(BorderFactory.createLineBorder(Color.red));
            this.setBackground(Color.white);
        }
        public Dimension getPreferredSize()
        {
            return new Dimension(390,250);
        }
        public void doDrawing(Graphics g){
            double x,y,x1,y1;
            Graphics2D gr;

            gr = (Graphics2D) g;

            // Делаем белый фон
            Rectangle2D rect = new Rectangle2D.Double(0,0,this.getWidth(),this.getHeight());
            gr.setPaint(Color.white);
            gr.fill(rect);
            gr.draw(rect);

            // Рисуем сетку
            gr.setPaint(Color.LIGHT_GRAY);
            gr.setStroke(new BasicStroke((float) 0.2));
            for(y=0;y<=this.getWidth();y+=50){
                gr.draw(new Line2D.Double(0,y  ,this.getWidth(),y));
                gr.draw(new Line2D.Double(y,0,y,this.getHeight()));
            }
            // Рисуем оси
            gr.setPaint(Color.GREEN);
            gr.setStroke(new BasicStroke((float) 2));
            int x0 = this.getWidth() / 2 + (50 - (this.getWidth() / 2) % 50);
            int y0 = this.getHeight() / 2 + (50 - (this.getHeight() / 2) % 50);
            gr.draw(new Line2D.Double(x0,0,x0,this.getHeight()));
            gr.draw(new Line2D.Double(0,y0,this.getWidth(),y0));

            gr.setPaint(Color.BLACK);
            gr.setStroke(new BasicStroke((float) 2));

            for(x=-this.getWidth();x<=this.getWidth();x += 0.001){
               y = Math.sqrt(0.01*x*x*x + a * x + b);
                x1=x-0.001;
                y1=Math.sqrt(0.01*x1*x1*x1 + a * x1 + b);
                gr.draw(new Line2D.Double(x+x0,y+y0,x1+x0,y1+y0));
                gr.draw(new Line2D.Double(x+x0,-y+y0,x1+x0,-y1+y0));
            }
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            doDrawing(g);
        }
    }

    public FrameMain() {
        this.setTitle("MLITA");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        task6_painter = new myPanel();
        task6_paint.add(task6_painter);
        this.pack();

        JTableUtils.initJTableForArray(task1_tableInput, 200, true, true, false, true);
        JTableUtils.initJTableForArray(task1_tableOutput, 1000, false, true, false, false);
        task1_tableInput.setRowHeight(30);
        task1_tableOutput.setRowHeight(30);

        JTableUtils.initJTableForArray(task2_tableInput, 60, true, true, false, true);
        JTableUtils.initJTableForArray(task2_tableOutput, 200, true, true, false, true);
        task2_tableInput.setRowHeight(30);
        task2_tableOutput.setRowHeight(30);

        JTableUtils.initJTableForArray(task2_tableUnitMatrix, 30, true, true, false, true);
        task2_tableUnitMatrix.setRowHeight(30);

        JTableUtils.initJTableForArray(task3_tableInput, 60, true, true, false, true);
        JTableUtils.initJTableForArray(task3_tableOutput, 100, false, false, false, false);
        task3_tableInput.setRowHeight(30);
        JTableUtils.REinitJTableForArray(task3_tableInput2, 60, false, true, false, true, 20, 6);
        task3_tableOutput.setRowHeight(30);
        task3_tableInput2.setRowHeight(30);

        JTableUtils.initJTableForArray(kramer_tableInput, 60, true, true, false, true);
        kramer_tableInput.setRowHeight(30);

        JTableUtils.REinitJTableForArray(gauss_tableInput, 60, true, true, true, true, 20, 6);
        gauss_tableInput.setRowHeight(30);

        JTableUtils.initJTableForArray(task5_tableInput, 60, true, true, false, true);
        kramer_tableInput.setRowHeight(30);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Custom");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        JTableUtils.writeArrayToJTable(task1_tableInput, new int[][] {{1,2,3},{3,4,4},{7, 9, 9}});
        JTableUtils.writeArrayToJTable(task1_tableInput, new int[][] {{2, 1, 0, 0, 2},{3, 2, 0, 0, 3},{1,1,3,4,4},{2,-1,2,3,5},{1,5,3,6,3}});
        JTableUtils.writeArrayToJTable(task2_tableInput, new int[][] {{1,2,3},{3,4,4},{7, 9, 9}});
        JTableUtils.writeArrayToJTable(task2_tableInput, new int[][] {{47,116,14}, {41,68,99}, {121, 46, 15}});
        JTableUtils.writeArrayToJTable(task3_tableInput, new int[][] {{1,2,3}, {3,4,4}, {7, 9, 9}});
        JTableUtils.writeArrayToJTable(task3_tableInput2, new int[][] {{1}, {0}, {0}});

        JTableUtils.writeArrayToJTable(kramer_tableInput, new int[][] {{1,2,3, 4},{3,4,4, 2},{7, 9, 9, 3}});
        JTableUtils.writeArrayToJTable(gauss_tableInput, new int[][] {{1,2,3, 4},{3,4,4, 2},{7, 9, 9, 3}});

        JTableUtils.writeArrayToJTable(task5_tableInput, new int[][] {{1,2},{5,4}});
//        JTableUtils.writeArrayToJTable(task5_tableInput, new int[][] {{8,-4,-2},{-2,6,-2},{2, -4, 4}});
        JTableUtils.writeArrayToJTable(task5_tableInput, new int[][] {{-1,-1,-2},{-8,-7,3},{-1,-1,-2}});

        try {
            kramer_SLAU.setText(textSLAU(Objects.requireNonNull(JTableUtils.readIntMatrixFromJTable(kramer_tableInput))));
            kramer_Variables.setText(textVariables(Main.kramer(Objects.requireNonNull(JTableUtils.readIntMatrixFromJTable(kramer_tableInput)))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.pack();

        task4.add(Kramer, "Kramer");
        task4.add(Gauss, "Gauss");

        goToLayout(task4, "Kramer");

        tasks_panel.add(task1, "Det A");
        tasks_panel.add(task2, "Inverse A");
        tasks_panel.add(task3, "A * X = B");
        tasks_panel.add(task4, "Kramer & Gauss");
        tasks_panel.add(task5, "Eigen-(values & vector)");
        tasks_panel.add(task6, "Third - order curves");

        taskChooser.insertItemAt("Det A", 0);
        taskChooser.insertItemAt("Inverse A", 1);
        taskChooser.insertItemAt("A * X = B", 2);
        taskChooser.insertItemAt("Kramer & Gauss", 3);
        taskChooser.insertItemAt("Eigen-(values & vector)", 4);
        taskChooser.insertItemAt("Third - order curves", 5);

        taskChooser.setSelectedIndex(0);

        taskSelectorButton.setText("Select task");

        taskSelectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String current = Objects.requireNonNull(taskChooser.getSelectedItem()).toString();
                goToLayout(tasks_panel, current);
                switch (current){
                    case "Det A":
                        curTask = CurrentTask.TASK1;
                        break;

                    case "Inverse A":
                        curTask = CurrentTask.TASK2;
                        break;
                    case "A * X = B":
                        curTask = CurrentTask.TASK3;
                        break;
                    case "Kramer & Gauss":
                        curTask = CurrentTask.TASK4;
                        break;
                    case "Eigen-(values & vector)":
                        curTask = CurrentTask.TASK5;
                        break;
                    case "Third - order curves":
                        curTask = CurrentTask.TASK6;
                        break;
                }
            }
        });

        changeGauss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goToLayout(task4, "Gauss");
            }
        });
        changeKramer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goToLayout(task4, "Kramer");
            }
        });


        buttonRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int max = (int) maxSpinner.getValue();
                int size = (int) sizeSpinner.getValue();
                if (size <= 0){
                    size = 1;
                }
                int[][] arr = Main.rnd(size, max);
                int[][] arrB = Main.rnd(size, task3_tableInput2.getColumnCount(), max);
                int[][] arrC = Main.rnd(size, kramer_tableInput.getColumnCount(), max);
                int[][] arrD = Main.rnd(size, gauss_tableInput.getColumnCount(), max);

                JTable input = task1_tableInput;

                switch (curTask){
                    case TASK1 -> input = task1_tableInput;
                    case TASK2 ->  input = task2_tableInput;
                    case TASK3 ->  {
                        input = task3_tableInput;
                        JTableUtils.writeArrayToJTable(task3_tableInput2, arrB);
                    }
                    case TASK4 ->  {
                        JTableUtils.writeArrayToJTable(kramer_tableInput, arrC);
                        JTableUtils.writeArrayToJTable(gauss_tableInput, arrD);
                    }
                }

                JTableUtils.writeArrayToJTable(input, arr);
            }
        });

        goToLayout(tasks_panel, "Det A");

        buttonMultiply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Element[][] input = JTableUtils.readElementMatrixFromJTable(task2_tableInput);
                    Element[][] output = JTableUtils.readElementMatrixFromJTable(task2_tableOutput);

                    JTableUtils.writeArrayToJTable(task2_tableUnitMatrix, printElems(Main.matrixMultiply(input, output)));
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        }
    });

        buttonSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (curTask == CurrentTask.TASK1) {
                        int[][] arr = JTableUtils.readIntMatrixFromJTable(task1_tableInput);
                        String[][] elms = printElems(new Element[][]{{Main.inDepthCount(Element.convert(arr))}});
                        System.out.println(Arrays.deepToString(elms));
                        JTableUtils.writeArrayToJTable(task1_tableOutput, elms);
                    }
                    else if (curTask == CurrentTask.TASK2) {
                        int[][] arr = JTableUtils.readIntMatrixFromJTable(task2_tableInput);
                        System.out.println(Arrays.deepToString(arr));
                        String[][] elms = printElems(Main.inDepthCountAlg(Element.convert(arr)));
                        System.out.println(Arrays.deepToString(elms));
                        JTableUtils.writeArrayToJTable(task2_tableOutput, elms);
                    }
                    else if (curTask == CurrentTask.TASK3) {
                        int[][] arr = JTableUtils.readIntMatrixFromJTable(task3_tableInput);
                        int[][] arrB = JTableUtils.readIntMatrixFromJTable(task3_tableInput2);
                        System.out.println(Arrays.deepToString(arr));
                        System.out.println(Arrays.deepToString(arrB));
                        String[][] elms = printElems(Main.matrixMultiply(Main.inDepthCountAlg(Element.convert(arr)), Element.convert(arrB)));
                        System.out.println(Arrays.deepToString(elms));
                        JTableUtils.writeArrayToJTable(task3_tableOutput, elms);
                    }
                    else if (curTask == CurrentTask.TASK4) {
                        int[][] arr = Objects.requireNonNull(JTableUtils.readIntMatrixFromJTable(kramer_tableInput));
                        kramer_SLAU.setText(textSLAU(arr));
                        Element[] elem = Main.kramer(arr);
                        if (elem == null) {
                            kramer_Variables.setText("Det A = 0");
                        }
                        else{
                            kramer_Variables.setText(textVariables(elem));
                        }

                        arr = Objects.requireNonNull(JTableUtils.readIntMatrixFromJTable(gauss_tableInput));

                        gauss_SLAU.setText(textSLAU(arr));
                        Element[][] elem1 = Main.gauss(Element.convert(arr)).getErr();
                        System.out.println(Arrays.deepToString(printElems(elem1)));

                        int k = 0;
                        int k1 = 0;

                        int m = Math.min(elem1[0].length, elem1.length);
                        m = elem1.length == elem1[0].length ? m - 1 : m;

                        for (int i = 0; i < elem1.length; i++) {
                            if (i < m && elem1[i][i].equal(1)){
                                k++;
                            }
                            else if (!elem1[i][elem1[i].length - 1].equal(0)){
                                k1++;
                            }
                        }

                        final String rang = "rank A= " + k + " rank A_= " + (k + k1) + " n= " + (elem1[0].length - 1);
                        System.out.println(rang);

                        if (elem1 == null) {
                            gauss_Variables.setText("Error!");
                        }
                        else if (k1 > 0){
                            gauss_Variables.setText(rang);
                        }
                        else{

                            Element[][] elemsArr = new Element[elem1[0].length - 1][elem1[0].length - k];
                            int[][] zerOne = new int[Math.max(1, elem1[0].length - k - 1)][elem1[0].length - k];
                            for (int i = 0; i < Math.max(1, elem1[0].length - k - 1); i++) {
                                for (int j = 0; j < elem1[0].length - k; j++) {
                                    if (j > 0 && i == j - 1) {
                                        zerOne[i][j] = 1;
                                    }
                                    else{
                                        zerOne[i][j] = 0;
                                    }
                                }
                            }
                            System.out.println(Arrays.deepToString(zerOne));
                            for (int i = 0; i < k; i++) {
                                int z;
                                for (int j = 0; j < elemsArr[0].length; j++) {
                                        elemsArr[i][j] = elem1[i][elem1[0].length - 1];
                                        boolean flag = false;
                                        z = 0;
                                        for (int n = 0; n < elem1[0].length - 1; n++) {
                                    if (flag && !elem1[0][n].equal(0)){
                                        elemsArr[i][j] = elemsArr[i][j].minus(elem1[i][n].multiply(zerOne[z][j]));
                                        z++;
                                    }
                                            if ((elem1[i][n].equal(1))) {
                                                flag = true;
                                            }
                                        }
                                        z = 0;
                                            for (int l = k; l < elemsArr.length; l++) {
                                                elemsArr[l][j] = new Element(zerOne[z][j], 1);
                                                z++;
                                        }
                                }
                            }
                            gauss_Variables.setText(textVariables(elemsArr, Main.gauss(Element.convert(arr)).getChanges()));
                        }
                    }
                    else if (curTask == CurrentTask.TASK5) {
                        int[][] arr = Objects.requireNonNull(JTableUtils.readIntMatrixFromJTable(task5_tableInput));
                        if (arr.length > 3 || arr.length < 2) {
                            JOptionPane.showMessageDialog(null,
                                    "The matrix should be 2x2 or 3x3 in size.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            Element[] roots = Main.roots(arr);
                            if (roots == null) {
                                task5_values.setText("Discriminant < 0\nThe roots are imaginary\nDiscriminant = " + Main.disc(arr).print());
                            } else {
                                task5_values.setText(columnPrint(printElems(roots)));
                                StringBuilder sb = new StringBuilder();
                                for (Element e : roots) {
                                    gauss_SLAU.setText(textSLAU(arr));
                                    Element[][] elem1 = Main.gauss(Main.toGauss(Main.IMatrix(Element.convert(arr), e))).getErr();
                                    System.out.println(Arrays.deepToString(printElems(elem1)));

                                    int k = 0;
                                    int k1 = 0;

                                    int m = Math.min(elem1[0].length, elem1.length);
                                    m = elem1.length == elem1[0].length ? m - 1 : m;

                                    for (int i = 0; i < elem1.length; i++) {
                                        if (i < m && elem1[i][i].equal(1)){
                                            k++;
                                        }
                                        else if (!elem1[i][elem1[i].length - 1].equal(0)){
                                            k1++;
                                        }
                                    }

                                    final String rang = "rank A= " + k + " rank A_= " + (k + k1) + " n= " + (elem1[0].length - 1);
                                    System.out.println(rang);

                                    if (elem1 == null) {
                                        gauss_Variables.setText("Error!");
                                    }
                                    else if (k1 > 0){
                                        gauss_Variables.setText(rang);
                                    }
                                    else{

                                        Element[][] elemsArr = new Element[elem1[0].length - 1][elem1[0].length - k];
                                        int[][] zerOne = new int[Math.max(1, elem1[0].length - k - 1)][elem1[0].length - k];
                                        for (int i = 0; i < Math.max(1, elem1[0].length - k - 1); i++) {
                                            for (int j = 0; j < elem1[0].length - k; j++) {
                                                if (j > 0 && i == j - 1) {
                                                    zerOne[i][j] = 1;
                                                }
                                                else{
                                                    zerOne[i][j] = 0;
                                                }
                                            }
                                        }
                                        System.out.println(Arrays.deepToString(zerOne));
                                        for (int i = 0; i < k; i++) {
                                            int z;
                                            for (int j = 0; j < elemsArr[0].length; j++) {
                                                elemsArr[i][j] = elem1[i][elem1[0].length - 1];
                                                boolean flag = false;
                                                z = 0;
                                                for (int n = 0; n < elem1[0].length - 1; n++) {
                                                    if (flag && !elem1[0][n].equal(0)){
                                                        elemsArr[i][j] = elemsArr[i][j].minus(elem1[i][n].multiply(zerOne[z][j]));
                                                        z++;
                                                    }
                                                    if ((elem1[i][n].equal(1))) {
                                                        flag = true;
                                                    }
                                                }
                                                z = 0;
                                                for (int l = k; l < elemsArr.length; l++) {
                                                    elemsArr[l][j] = new Element(zerOne[z][j], 1);
                                                    z++;
                                                }
                                            }
                                        }
                                        sb.append(textVariables(elemsArr, Main.gauss(Main.toGauss(Main.IMatrix(Element.convert(arr), e))).getChanges()));
                                        sb.append("_____________\n");
                                    }
                                    task5_gausses.setText(sb.toString());
                                }
                            }
                        }
                    }
                    else{
                        a = Integer.parseInt(task6_inputA.getText());
                        b = Integer.parseInt(task6_inputB.getText());
                        task6_paint.repaint();
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonLoadInputFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[] arr = ArrayUtils.readIntArrayFromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.writeArrayToJTable(task1_tableInput, arr);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[] intArray = JTableUtils.readIntArrayFromJTable(task1_tableOutput);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, intArray);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), 10, 10));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        task1_tableInput = new JTable();
        scrollPane1.setViewportView(task1_tableInput);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonLoadInputFromFile = new JButton();
        buttonLoadInputFromFile.setText("Загрузить из файла");
        panel1.add(buttonLoadInputFromFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonOutput = new JButton();
        buttonOutput.setText("Сохранить в файл");
        panel1.add(buttonOutput, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(100, -1), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane2, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        task1_tableOutput = new JTable();
        scrollPane2.setViewportView(task1_tableOutput);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSolve = new JButton();
        buttonSolve.setText("Сохранить в файл");
        panel3.add(buttonSolve, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
