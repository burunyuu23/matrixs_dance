package ru.vsu.edu.shlyikov_d_g;

import org.w3c.dom.ls.LSOutput;
import ru.vsu.edu.shlyikov_d_g.util.ArrayUtils;
import ru.vsu.edu.shlyikov_d_g.util.SwingUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Main {

    public static int maxMatLen;
    public static Element saveTemp;
    public static Element result;

    // функция создания матрицы путём "вычеркивания" строки и столбца содержащих элемент матрицы
    public static Element[][] inDepth(Element[][] matrix, int x) {
        Element[][] result = new Element[matrix.length - 1][matrix[0].length - 1];
        int k = 0;

        for (int i = 0; i < matrix.length; i++) {
            int m = 0;
            if (i != x) {
                for (int j = 1; j < matrix[0].length; j++) {
                    result[k][m] = matrix[i][j];
                    m++;
                }
                k++;
            }
        }

        return result;
    }

    public static Element[][] inDepth(Element[][] matrix, int x, int y) {
        Element[][] result = new Element[matrix.length - 1][matrix[0].length - 1];
        int k = 0;

        for (int i = 0; i < matrix.length; i++) {
            int m = 0;
            if (i != x) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (j != y) {
                        result[k][m] = matrix[i][j];
                        m++;
                    }
                }
                k++;
            }
        }

        return result;
    }
    //////////////    \\\\\\\\\\\\\\\
    // функция подготовки матрицы для вычисления определителя
    public static Element inDepthCount(Element[][] matrix) {
        maxMatLen = matrix.length;
        saveTemp = new Element(0, 1);
        result = new Element(0, 1);
        return inDepthCount(matrix, new Element(1, 1), new Element(0, 1), 1);
    }

    // функция вычисления определителя
    public static Element inDepthCount(Element[][] matrix, Element temp, Element saveTemp, int zeroPoint) {
        if (matrix.length > 2) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix.length == maxMatLen) {
                    saveTemp = matrix[i][0].multiply((int) Math.pow(-1, i));
                    temp = saveTemp;
                    zeroPoint = 1;
                } else {
                    if (!matrix[i][0].equal(0)) {
                        temp = temp.multiply(matrix[i][0].multiply((int) Math.pow(-1, i)));
                        zeroPoint = 1;
                    }
                    else{
                        zeroPoint = 0;
                    }
                }
                inDepthCount(inDepth(matrix, i, 0), temp, saveTemp, zeroPoint);
                if (!matrix[i][0].equal(0)) {
                    temp = temp.divide(matrix[i][0].multiply((int) Math.pow(-1, i)));
                }
//                System.out.println("i= " + i);
//                System.out.println("mtr= " + matrix[i][0]);
            }
        } else if (matrix.length == 2) {
            result = result.plus(temp.multiply(matrix[0][0].multiply(matrix[1][1]).minus(matrix[0][1].multiply(matrix[1][0]))).multiply(zeroPoint));
//            System.out.println("temp= " + temp);
//            System.out.println("result= " + result);
//            System.out.println("saveTemp= " + saveTemp);
//            System.out.println("|" + matrix[0][0] + " " + matrix[0][1] + "|\n" +
//                                "|" + matrix[1][0] + " " + matrix[1][1] + "|\n");
        }
        else if (matrix.length == 1){
            result = matrix[0][0];
        }
        return result;
    }

    // функция подготовки матрицы для вычисления алгебраических дополнений
    public static Element[][] inDepthCountAlg(Element[][] matrix) {
        Element detA = inDepthCount(matrix);

        if (detA.equal(0)){
            System.out.println("ОПРЕДЕЛИТЕЛЬ РАВЕН 0");
            return new Element[][]{{new Element(0, 1)}};
        }

        maxMatLen = matrix.length;
        saveTemp = new Element(0, 1);
        result = new Element(0, 1);

        Element[][] arr = new Element[maxMatLen][maxMatLen];

            for (int i = 0; i < maxMatLen; i++) {
                for (int j = 0; j < maxMatLen; j++) {
                    saveTemp = new Element(0, 1);
                    result = new Element(0, 1);
                    arr[i][j] = inDepthCountAlg(matrix, new Element(1,1), i, j, 1);
                }
            }

            arr = changeRowsForResult(arr);

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] = arr[i][j].divide(detA);
            }
        }

        return arr;
    }

    public static String[][] printElems(Element[][] arr){
        String[][] elms = new String[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                elms[i][j] = arr[i][j].print();
            }
        }
        return elms;
    }

    public static String[] printElems(Element[] arr){
        String[] elms = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
                elms[i] = arr[i].print();
        }
        return elms;
    }

    public static Element[][] changeRowsForResult(Element[][] matrix) {
        Element[][] result = new Element[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    // функция вычисления алгебраических дополнений
    public static Element inDepthCountAlg(Element[][] matrix, Element temp, int i, int j, int zeroPoint) {
        if (maxMatLen == 2){
            result = inDepth(matrix, i, j)[0][0].multiply((int) Math.pow(-1, i + j));
        }
        else if (matrix.length > 2) {
            if (matrix.length == maxMatLen) {
                saveTemp = new Element((int) Math.pow(-1, i + j), 1);
                temp = saveTemp;
            } else {
                if (!matrix[i][j].equal(0)) {
                    temp = temp.multiply(matrix[i][j].multiply((int) Math.pow(-1, i + j)));
                    zeroPoint = 1;
                }
                else{
                    zeroPoint = 0;
                }
            }
            inDepthCount(inDepth(matrix, i, j), temp, new Element(0, 1), zeroPoint);
            if (!matrix[i][j].equal(0)) {
                temp = temp.divide(matrix[i][j].multiply((int) Math.pow(-1, i + j)));
            }
        } else if (matrix.length == 2) {
            result = result.plus(temp.multiply(matrix[i][j].multiply(matrix[i+1][j+1]).minus(matrix[i][j+1].multiply(matrix[i+1][j]))).multiply(zeroPoint));
        }
        else if (matrix.length == 1){
            result = matrix[0][0];
        }
        return result;
    }

    // функция перемножения двух матриц
    public static Element[][] matrixMultiply(Element[][] a, Element[][] b) {
        Element[][] I = new Element[Math.min(a.length, b.length)][Math.min(a[0].length, b[0].length)];

        for (int j = 0; j < I[0].length; j++) {
            for (int k = 0; k < I.length; k++) {
                I[k][j] = new Element(0, 1);
                for (int l = 0; l < I.length; l++) {
                    I[k][j] = I[k][j].plus(a[k][l].multiply(b[l][j]));
                    System.out.println("l= " + l + " j= " + j + " k= " + k);
                    System.out.println("MULTIPLY: " + a[k][l].print() + " * " + b[l][j].print() + " = " + a[k][l].multiply(b[l][j]).print());
                    System.out.println(I[k][j].print());
                }
            }
        }

        return I;
    }

    public static double[][] matrixMultiply(int[][] a, double[][] b) {
        double[][] I = new double[maxMatLen][maxMatLen];

        for (int j = 0; j < maxMatLen; j++) {
            for (int k = 0; k < maxMatLen; k++) {
                double test = 0;
                for (int l = 0; l < maxMatLen; l++) {
                    test += a[k][l] * b[l][j];
                }
                I[k][j] = Math.round(test * 10e4) / 10e4;
            }
        }

        return I;
    }

    public static double[][] matrixMultiply(double[][] a, int[][] b) {
        double[][] I = new double[maxMatLen][maxMatLen];

        for (int j = 0; j < maxMatLen; j++) {
            for (int k = 0; k < maxMatLen; k++) {
                double test = 0;
                for (int l = 0; l < maxMatLen; l++) {
                    test += a[k][l] * b[l][j];
                }
                I[k][j] = Math.round(test * 10e4) / 10e4;
            }
        }

        return I;
    }

    public static Element[] kramer(int[][] arr){
        Element[] elems = new Element[arr.length];
        
        Element det = new Element(1,1);

        for (int k = -1; k < arr.length; k++) {
            Element[][] ints = new Element[arr.length][arr[0].length - 1];
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length - 1; j++) {
                    ints[i][j] = new Element(arr[i][j], 1);
                    if (k == j){
                        ints[i][j] = new Element(arr[i][arr[i].length - 1], 1);
                    }
                }
            }
            if (k == -1){
                det = Main.inDepthCount(ints);
                if (det.equal(new Element(0, 1))){
                    return null;
                }
            }
            else{
                elems[k] = Main.inDepthCount(ints).divide(det);
            }
        }
        return elems;
    }

    public static Ans gauss(Element[][] arr){
        Element[][] arrTemp = arr.clone();

        Element[] temp;
        Element max;
        Element max2;
        int z = 0;

        boolean flag = false;

        int m = Math.min(arrTemp[0].length, arrTemp.length);
        m = arrTemp.length == arrTemp[0].length ? m - 1 : m;

        for (int q = 0; q < 3; q++) {
            for (int i = 0; i < m; i++) {
                temp = arrTemp[i];
                for (int j = 0; j < arr.length; j++) {
                    if (i != j) {
                        max = temp[i];
                        max2 = arrTemp[j][i];
                        if (max.equal(0)) {
                            continue;
                        }
                        for (int k = 0; k < arr[0].length; k++) {
                            System.out.println(Arrays.deepToString(printElems(arrTemp)));
                            arrTemp[j][k] = arrTemp[j][k].minus(temp[k].multiply(max2).divide(max));
                            if (j == k && arrTemp[j][k].equal(0)) {
                                flag = true;
                            }
                            if (flag && k < arr.length - 1 && !arrTemp[j][k].equal(0)) {
                                flag = false;
                                Element[] tempo = arrTemp[j];
                                arrTemp[j] = arrTemp[k];
                                arrTemp[k] = tempo;
                            }
                        }
                        if (flag) {
                            flag = false;
                            z++;
                            if (j < arrTemp.length - z) {
                                Element[] tempo = arrTemp[j];
                                arrTemp[j] = arrTemp[arrTemp.length - z];
                                arrTemp[arrTemp.length - z] = tempo;
                                i--;
                                break;
                            }
                        }
                    }
                }
            }
        }

        z = 0;
        for (int i = 0; i < m; i++) {
            boolean flag2 = true;
            for (int j = 0; j < arrTemp[0].length; j++) {
                if (!arrTemp[i][j].equal(0)){
                    flag2 = false;
                    break;
                }
            }
            if (flag2 && i < arrTemp.length - z){
                z++;
                Element[] tempo = arrTemp[i];
                arrTemp[i] = arrTemp[arrTemp.length - z];
                arrTemp[arrTemp.length - z] = tempo;
                i--;
            }

        }

        int[] changes = new int[arrTemp[0].length - 1];
        for (int i = 0; i < changes.length; i++) {
            changes[i] = i + 1;
        }
        for (int i = 0; i < m; i++) {
            if (arrTemp[i][i].equal(0)){
                System.out.println("FLA1");
                for (int j = i; j < arrTemp[0].length - 1; j++) {
                    if (!arrTemp[i][j].equal(0)) {
                        System.out.println("FLA2");
                        changes[i] = j + 1;
                        changes[j] = i + 1;
                        for (int l = 0; l < arrTemp.length; l++) {
                                Element tempo = arrTemp[l][i];
                                arrTemp[l][i] = arrTemp[l][j];
                                arrTemp[l][j] = tempo;
                        }
                        break;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(changes));
        System.out.println(Arrays.deepToString(printElems(arrTemp)));

        System.out.println("         - ----------------------- ");
        System.out.println(Arrays.deepToString(printElems(arrTemp)));

        for (int i = 0; i < m; i++) {
            Element tempo = arrTemp[i][i];
            if (!arrTemp[i][i].equal(0)) {
                for (int j = 0; j < arrTemp[0].length; j++) {
                    System.out.println(arrTemp[i][j].print() + " ///  " + tempo.print() );
                    arrTemp[i][j] = arrTemp[i][j].divide(tempo);
                }
            }
        }

        System.out.println(Arrays.deepToString(printElems(arrTemp)));
        System.out.println("CHANGES= " + Arrays.toString(changes));
        return new Ans(changes, arrTemp);
    }

    public static Element[] roots(int[][] matrix){
        if (matrix.length == 2) {
            int a = matrix[0][0];
            int b = matrix[0][1];
            int c = matrix[1][0];
            int d = matrix[1][1];

            if ((int) Math.pow((a + d), 2) - 4 * ((a * d) - (b * c)) < 0){
                return null;
            }

            return quadratic(1, -(a + d), (a * d) - (b * c));
        }
        else{
            Element[] res = new Element[3];

            int a = matrix[0][0];
            int b = matrix[0][1];
            int c = matrix[0][2];
            int d = matrix[1][0];
            int e = matrix[1][1];
            int f = matrix[1][2];
            int g = matrix[2][0];
            int h = matrix[2][1];
            int j = matrix[2][2];

            int z = - (a + e + j);
            int y = - (g * c + d * b + f * h - e * j - a * e - a * j);
            int w = - (a * e * j - a * f * h - d * b * j + d * c * h + g * b * f - g * c * e);

            Element p = new Element(3 * y - z * z, 3);
            Element q = new Element(2 * z * z * z - 9 * y * z + 27 * w, 27);

            Element Q = q.multiply(q).divide(4).plus(p.multiply(p).multiply(p).divide(27));

            if (Q.isPositive()) return null;
//
//            Element alfa = q.divide(-2).plus(Q.sqrt());
//            Element beta = q.divide(-2).minus(Q.sqrt());
//
//            res[0] = alfa.plus(beta);
//
//            res[1] = (alfa.plus(beta)).divide(-2).plus((alfa.minus(beta).divide(2).multiply(new Element(3,1).sqrt()))); // i
//            res[2] = (alfa.plus(beta)).divide(-2).minus(alfa.minus(beta).divide(2).multiply(new Element(3,1).sqrt())); // i
//
//            System.out.println((alfa.plus(beta)).divide(-2).print());
//            System.out.println(alfa.minus(beta).divide(2).multiply(new Element(3,1).sqrt()).print());
//            System.out.println(alfa.minus(beta).divide(2).multiply(new Element(3,1).sqrt()).toComplex().print());

            int x1 = 0;

            System.out.println("D= " + Q.print());

            for (int i = 1; i <= Math.abs(w); i++) {
                if (w % i == 0) {
                    if (triad(1, z, y, w, i)) {
                        x1 = i;
                        break;
                    }
                    if (triad(1, z, y, w, -i)) {
                        break;
                    }
                }
            }

            if (x1 == 0){
                System.out.println("ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERROR");
            }

            res[0] = new Element(x1, 1);

            Element[] resTemp = quadratic(1, z + x1,y + (z + x1) * x1);

            res[1] = resTemp[0];
            res[2] = resTemp[1];

            return res;
        }
    }

    private static boolean triad(int a, int b, int c, int d, int x){
        System.out.println("ans= " + (a * (int) Math.pow(x, 3) + b * (int) Math.pow(x, 2) + c * x + d));
        System.out.println("x = " + x);
        return a * (int) Math.pow(x, 3) + b * (int) Math.pow(x, 2) + c * x + d == 0;
    }

    private static Element[] quadratic(int a, int b, int c){
        Element[] res = new Element[2];

        Element D = new Element((int) Math.pow(b, 2) -
                4 * a * c, 1);

        System.out.println(D.print());

        res[0] = new Element(-b, 1).plus(D.sqrt()).divide(2 * a);

        res[1] = new Element(-b, 1).minus(D.sqrt()).divide(2 * a);

        return res;
    }

    public static Element disc(int[][] matrix){
        if (matrix.length == 2){
            int a = matrix[0][0];
            int b = matrix[0][1];
            int c = matrix[1][0];
            int d = matrix[1][1];

            return new Element((int) Math.pow((a + d), 2) - 4 * ((a * d) - (b * c)), 1);
        }
        else {
            int a = matrix[0][0];
            int b = matrix[0][1];
            int c = matrix[0][2];
            int d = matrix[1][0];
            int e = matrix[1][1];
            int f = matrix[1][2];
            int g = matrix[2][0];
            int h = matrix[2][1];
            int j = matrix[2][2];

            int z = -(a + e + j);
            int y = -(g * c + d * b + f * h - e * j - a * e - a * j);
            int w = -(a * e * j - a * f * h - d * b * j + d * c * h + g * b * f - g * c * e);

            Element p = new Element(3 * y - z * z, 3);
            Element q = new Element(2 * z * z * z - 9 * y * z + 27 * w, 27);

            Element Q = q.multiply(q).divide(4).plus(p.multiply(p).multiply(p).divide(27));

            return Q.multiply(-108);
        }
    }

    public static Element[][] IMatrix(Element[][] arr, Element a){
        Element[][] arrTemp = arr.clone();
        for (int i = 0; i < arr.length; i++) {
            arrTemp[i][i] = arr[i][i].minus(a);
        }
        return arrTemp;
    }

    public static Element[][] toGauss(Element[][] arr){
        Element[][] newArr = new Element[arr.length][arr.length + 1];
        for (int i = 0; i < newArr.length; i++) {
            for (int j = 0; j < newArr.length; j++) {
                newArr[i][j] = arr[i][j];
                System.out.println(newArr[i][j].print());
            }
            newArr[i][newArr.length] = new Element(0, 1);
        }
        return newArr;
    }

    public static class Ans{
        private int[] changes;
        private Element[][] err;

        public Ans(int[] changes, Element[][] err){
            this.changes = changes;
            this.err = err;
        }

        public int[] getChanges(){
            return  changes;
        }

        public Element[][] getErr(){
            return err;
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);

        int[][] matrix = {
                {1, 2, 3, 4},
                {3, 2, 5, 5},
                {6, 5, 2, 6},
                {7, 9, 9, 9}};

        int[][] matrixTEST = {
                {2, 1, 0, 0},
                {3, 2, 0, 0},
                {1, 1, 3, 4},
                {2, -1, 2, 3}
        };
//
        int[][] matrix2 = {{3, 1, -1},
                {1, -3, 7},
                {2, 2, -4}};

        int[][] test = {{2, 1, 0, 0, 2},
                        {3, 2, 0, 0, 3},
        {1,1,3,4,4},{2,-1,2,3,5},{1,5,3,6,3}};

        Element a = new Element(12, 1);
        Element b = new Element(12, 0);
        Element c = new Element(12, 5);
        c = c.sqrt();
        Element d = new Element(144, 25);

        System.out.println(a.print());
        System.out.println(b.print());
        System.out.println(c.print());
        System.out.println(d.print());
        System.out.println("sqrt " + a.print() + " = " + a.sqrt().print());
        System.out.println("sqrt " + c.print() + " = " + c.sqrt().print());
        System.out.println("sqrt " + d.print() + " = " + d.sqrt().print());

        System.out.println(new Element(2, 3).plus(new Element(0, 1)).print());
        System.out.println(c.sqrt().print() + " + 2 = " + c.sqrt().plus(new Element(2, 1)).print());
        System.out.println(c.sqrt().print() + " + 2 = " + new Element(2, 1).plus(c.sqrt()).print());
        System.out.println(c.sqrt().print() + " - 1/2 = " + c.minus(c.sqrt()).print());
        System.out.println(c.sqrt().print() + " - 1/2 = " + new Element(0, 0).minus(c.sqrt()).print());

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(c.print());
        System.out.println(c.multiply(3).print());
        System.out.println(c.plus(new Element(12, 1)).multiply(3).print());
        System.out.println(c.multiply(new Element(12, 2)).print());
        System.out.println(c.plus(new Element(12, 1)).multiply(c).print());
        System.out.println(c.multiply(c).print());
//
//        int[][] matrix3 = {{0, 1, 1},
//                {1, 0, 1},
//                {1, 1, 0}};
//
//        int[][] matrix5 = {{1, 0, 1},
//                {1, 1, 0},
//                {0, 1, 1}};
//
//        int[][] matrix6 = {{1, 1, 0},
//                {0, 1, 1},
//                {1, 0, 1}};
//
//        int[][] matrix7 = {{1, 1, 0},
//                {1, 0, 1},
//                {0, 1, 1}};
//
//        int[][] matrix22 = {{123, 12},
//                {33, 42}};

//        // тест моей матрицы
//        System.out.println("first matrix= " + deepToString(matrix));
//        System.out.println("det= " + inDepthCount(matrix) + "\n\n");
//        System.out.println("first inverse matrix=" + deepToString((inDepthCountAlg(matrix))));
//        System.out.println("first I= " + deepToString(matrixMultiply(matrix, inDepthCountAlg(matrix))));
//        System.out.println();
//
//        // тест тестовой матрицы
//        System.out.println("second matrix=" + deepToString(matrixTEST));
//        System.out.println("det= " + inDepthCount(matrixTEST) + "\n\n");
//        System.out.println("second inverse matrix=" + deepToString((inDepthCountAlg(matrixTEST))));
//        System.out.println("second I= " + deepToString(matrixMultiply(matrixTEST, inDepthCountAlg(matrixTEST))));
//
//        // тест тестовой матрицы
//        System.out.println("second matrix=" + deepToString(matrix2));
//        System.out.println("det= " + inDepthCount(matrix2) + "\n\n");
//        System.out.println("second inverse matrix=" + deepToString((inDepthCountAlg(matrix2))));
//        System.out.println("second I= " + deepToString(matrixMultiply(matrix2, inDepthCountAlg(matrix2))));
//
//
//        // тест тестовой матрицы
//        System.out.println("second matrix=" + deepToString(test));
//        System.out.println("det= " + inDepthCount(test) + "\n\n");
//        System.out.println("second inverse matrix=" + deepToString((inDepthCountAlg(test))));
//        System.out.println("second I= " + deepToString(matrixMultiply(test, inDepthCountAlg(test))));
//
//        // A * X = B
//        // ввод A и B
//
//        System.out.println("" + deepToString(matrixMultiply(inDepthCountAlg(matrix), matrixTEST)));
//        System.out.println(deepToString(matrixMultiply(matrix, matrixMultiply(inDepthCountAlg(matrix), matrixTEST))));
//        System.out.println("first matrix= " + deepToString(matrixTEST));

        System.out.println(Arrays.deepToString(printElems(inDepthCountAlg(Element.convert(matrix)))));

        System.out.println(new Element(14,140).print());
        Element temp = new Element(7,33).multiply(new Element(2,30));
        System.out.println(temp.print());

        temp = new Element(7,33).plus(new Element(1,140));
        System.out.println(temp.print());

        temp = new Element(7,33).divide(new Element(2,30));
        System.out.println(temp.print());

        temp = new Element(7,33).multiply(0);
        System.out.println(temp.print());
        temp = temp.plus(new Element(-7,133));
        System.out.println(temp.print());
        temp = temp.plus(new Element(7,11).multiply(1)).plus(new Element(7,33).multiply(0));
        System.out.println(temp.print());

        winMain();

        // ввод вывод файла
//        inputOutputFile();

//        deepToString(matrix2);
//        System.out.println(inDepthCount(matrix2) + "\n");
//
//        deepToString(matrix3);
//        System.out.println(inDepthCount(matrix3) + "\n");
//
//        deepToString(matrix5);
//        System.out.println(inDepthCount(matrix5) + "\n");
//
//        deepToString(matrix6);
//        System.out.println(inDepthCount(matrix6) + "\n");
//
//        deepToString(matrix7);
//        System.out.println(inDepthCount(matrix7) + "\n");
//
//        deepToString(matrix22);
//        System.out.println(inDepthCount(matrix22) + "\n");
    }

    public static void winMain(){
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Castellar", 1,18);
        //SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() { new FrameMain().setVisible(true); }
        });
        // 0 - Plain
        // 1 - Bold
        // 2 - Italic
        // 3 - Bold+Italic
    }

    //функция красивого вывода матрицы целых чисел
    public static String deepToString(int[][] m) {
        double[][] result = new double[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                result[i][j] = m[i][j];
            }
        }
        return deepToString(result);
    }

    //функция красивого вывода матрицы чисел с точкой
    public static String deepToString(double[][] m) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m.length; i++) {
            sb.append(Arrays.toString(m[i])).append("\n");
        }
        return sb.toString();
    }

    public static String deepToString(String[][] m) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m.length; i++) {
            sb.append(Arrays.toString(m[i])).append("\n");
        }
        return sb.toString();
    }

    //функция создания матрицы из случайных целых чисел размера m x m
    public static int[][] rnd(int m, int max) {
        int[][] a = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = (int) (Math.random() * (max + 1)) + 1;
            }
        }

        return a;
    }

    public static int[][] rnd(int n, int m, int max) {
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = (int) (Math.random() * (max + 1)) + 1;
            }
        }

        return a;
    }

    //функция ввода вывода матриц из/в файл(а)
//    public static void inputOutputFile() {
//        Scanner scan = new Scanner(System.in);
//        String greetings;
//        File myPath;
//        File parentPath;
//        loop: for(;;) {
//            System.out.println("What's your task: ");
//            int task = scan.nextInt();
//            // 1 task - det A
//            // 2 task - inverse matrix
//            // 3 task - A * X = B
//            // 4 task - Cramer
//            // 5 task - Gauss
//            System.out.println("Input your file path: ");
//
//            //    C:\Users\zEzzLike\Desktop\Супер ВУЗник\2 сем\МЛиТА\test.txt
////        File myPath = new File("C:\\Users\\zEzzLike\\Desktop");
//            myPath = new File(scan.nextLine());
//            parentPath = myPath.getParentFile();
//            int[][] input = ArrayUtils.readIntArray2FromFile(myPath.toString());
//
//            while (input == null) {
//                System.out.println("Input err.\nPlease re::input your file path: ");
//                myPath = new File(scan.nextLine());
//                parentPath = myPath.getParentFile();
//                input = ArrayUtils.readIntArray2FromFile(myPath.toString());
//            }
//
////        int[][] input = ArrayUtils.readIntArray2FromFile(myPath + "\\input1.txt");
//            System.out.println("matrix=" + deepToString(input));
//            System.out.println("det= " + inDepthCount(input) + "\n\n");
//            System.out.println("inverse matrix=" + deepToString((inDepthCountAlg(input))));
//            System.out.println("I= " + deepToString(matrixMultiply(input, inDepthCountAlg(input))));
//            switch (task) {
//                case 1:
//                    greetings = deepToString(input) + inDepthCount(input) + "\n";
//                    break loop;
//                case 2:
//                    greetings = deepToString(input) + inDepthCount(input) +
//                            "\ninverse matrix=" + deepToString((inDepthCountAlg(input))) +
//                            "\nI= " + deepToString(matrixMultiply(input, inDepthCountAlg(input))) + "\n";
//                    break loop;
//                default:
//                    System.out.println("Wrong task!");
//            }
//        }
//
//            try {
//                FileOutputStream fileOutputStream;
//
//                fileOutputStream = new FileOutputStream(parentPath + "\\output.txt");
//
//                fileOutputStream.write(greetings.getBytes(StandardCharsets.UTF_8));
////
//                fileOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }
}