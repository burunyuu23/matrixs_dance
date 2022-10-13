package ru.vsu.edu.shlyikov_d_g;

public class Element {
    private long numerator;
    private long denominator;
    private long sqrtNum;
    private long sqrtDen;
    private int sqrt = 1;

    public Element(long num, long den) {
        this.numerator = num;
        this.denominator = den;
        this.sqrtNum = 0;
        this.sqrtDen = 0;
    }
    public Element(long num, long den, long sqrtNum, long sqrtDen) {
        this.numerator = num;
        this.denominator = den;
        this.sqrtNum = sqrtNum;
        this.sqrtDen = sqrtDen;
    }

    public static Element refresh(Element e){
        Element el = new Element(e.numerator, e.denominator, e.sqrtNum, e.sqrtDen);
        if (el.denominator < 0){
            el.numerator *= -1;
            el.denominator *= -1;
        }

        boolean flag = false;

        if (el.denominator == 0){
            if (el.sqrtDen != 0) flag = true;
//            System.out.println("ERROR! DENOMINATOR CAN'T BE EQUAL ZERO");
            if (el.numerator != 0) {
                el.denominator = 1;
            }
        }
//        loop: while (true) {
//            int maxInt = el.denominator;
//            for (int i = maxInt; i >= 2; i--) {
//                if (el.numerator % i == 0 && el.denominator % i == 0 && el.numerator / i != 0) {
//                    el.numerator /= i;
//                    el.denominator /= i;
//                    continue loop;
//                }
//            }
//            break;
//        }

        long a = Math.abs(el.numerator);
        long b = Math.abs(el.denominator);

            a = NOD(a, b);

            if (a != 0 && el.sqrtDen == 0 && el.sqrtNum == 0) {
                el.numerator /= a;
                el.denominator /= a;
            }
            else{
                if (el.sqrtNum % (a * a) == 0){
                    el.sqrtNum /= a * a;
                    el.numerator /= a;
                    el.denominator /= a;
                }
        }

        if(flag){
            el.denominator = 0;
        }

        return el;
    }

    public boolean isPositive(){
        return numerator > 0;
    }

    public boolean isNegative(){
        return numerator < 0;
    }

    private static long NOD(long a, long b){
        while (b !=0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        if (a == 0){
            a = 1;
        }
        return a;
    }

    public boolean equal(Element e){
        Element elem = new Element(numerator, denominator);
        elem = refresh(elem);
        e = refresh(e);

        return (elem.numerator == e.numerator) && (elem.denominator == e.denominator)
                && (elem.sqrtNum == e.sqrtNum) && (elem.sqrtDen == e.sqrtDen);
    }

    public boolean equal(int e){
        Element elem = new Element(numerator, denominator);
        elem = refresh(elem);

        if (e == 0){
            return elem.numerator == e;
        }

        return (elem.numerator == e) && (elem.denominator == 1) && (elem.sqrtNum == 0);
    }

    private Element sqrtX(int x){
        Element elem = new Element(numerator, denominator);
        int temp = (int) Math.pow(elem.numerator, 1.0/x);
        if ((int) Math.pow(temp, x) == numerator){
            elem.numerator = temp;
        }
        else{
            elem.sqrtNum = elem.numerator;
            elem.numerator = 0;
        }
        temp = (int) Math.pow(elem.denominator, 1.0/x);
        if ((int) Math.pow(temp, x) == denominator){
            elem.denominator = temp;
        }
        else{
            elem.sqrtDen = elem.denominator;
            elem.denominator = 0;
        }
        elem.sqrt = x;
        return elem;
    }

    public Element sqrt(){
        return sqrtX(2);
    }

    public Element sqrt(int x){
        return sqrtX(x);
    }

    public Element multiply(Element el){
        Element elem = new Element(numerator, denominator, sqrtNum, sqrtDen);
        Element elem2 = new Element(el.numerator, el.denominator, el.sqrtNum, el.sqrtDen);
        elem = refresh(elem);
        elem2 = refresh(elem2);

        elem.numerator *= elem2.numerator;
        elem.denominator *= elem2.denominator;
        elem.sqrtNum *= elem2.numerator * elem2.numerator;
        elem.sqrtDen *= elem2.denominator * elem2.denominator;
        elem = refresh(elem);
        return elem;
    }

    public Element multiply(int i){
        Element elem = new Element(numerator, denominator, sqrtNum, sqrtDen);

        if (i == 0){
            return new Element(0, 1);
        }
        if (i == 1){
            return elem;
        }

        elem.numerator *= i;
        elem.sqrtNum *= i * i;
        elem = refresh(elem);

        return elem;
    }

    public Element divide(Element el){
        Element elem = new Element(numerator, denominator, sqrtNum, sqrtDen);

        elem.denominator *= el.numerator;
        if (sqrtNum % (el.numerator * el.numerator) == 0){
                elem.numerator *= el.denominator;
                sqrtNum /= el.numerator * el.numerator;
            }
        elem = refresh(elem);

        return elem;
    }


    public Element divide(int el){
        return divide(new Element(el, 1));
    }

    public Element plus(Element el){
        return add(el, 1);
    }

    public Element minus(Element el){
        return add(el, -1);
    }

    private Element add(Element el, int plus){
        Element elem = new Element(numerator, denominator, sqrtNum, sqrtDen);
        Element elem2 = new Element(el.numerator, el.denominator, el.sqrtNum, el.sqrtDen);

        boolean flag = false;
        boolean flag2 = false;

        elem = refresh(elem);
        elem2 = refresh(elem2);

        if (elem.denominator == 0 && elem.sqrtDen != 0){
            elem.denominator = 1;
            flag = true;
        }
        if (elem2.denominator == 0 && elem2.sqrtDen != 0){
            elem2.denominator = 1;
            flag2 = true;
        }

        long dem = elem.denominator * elem2.denominator / NOD(elem.denominator, elem2.denominator);

        elem.sqrtNum += el.sqrtNum;
        elem.sqrtDen += el.sqrtDen;

        if (elem.denominator != elem2.denominator) {
            elem.numerator *= (double) dem / elem.denominator;
            elem.denominator *= (double) dem / elem.denominator;

            elem2.numerator *= (double) dem / elem2.denominator;
            elem2.denominator *= (double) dem / elem2.denominator;
        }

        elem.numerator += plus * elem2.numerator;

        if (flag && elem.denominator == 1){
            elem.denominator = 0;
        }
        if (flag2 && elem2.denominator == 1){
            elem2.denominator = 0;
        }

        elem.sqrtNum *= plus;

        elem = refresh(elem);

        return elem;
    }

    public String print(){
        Element el = new Element(this.numerator, this.denominator,
                this.sqrtNum, this.sqrtDen);

        el = refresh(el);

        boolean minus = false;

        if (el.sqrtNum < 0) {
            minus = true;
            el.sqrtNum = -el.sqrtNum;
        }

        StringBuilder ans = new StringBuilder();

        if (el.numerator != 0){
            if (el.sqrtNum != 0){
                ans.append("(");
            }
            ans.append(el.numerator);
        }
        if (el.sqrtNum != 0){
            if (el.numerator != 0) {
                if (minus) {
                    ans.append(" - ");
                } else {
                    ans.append(" + ");
                }
            }
            else{
                if (minus) {
                    ans.append("-");
                }
            }
            ans.append("√").append(el.sqrtNum);
            if (el.numerator != 0){
                ans.append(")");
            }
        }
            if (el.sqrtDen != 0) {
                if(el.denominator == 0){
                    ans.append("/√");
                    ans.append(el.sqrtDen);
                }
                else{
                    ans.append("/(");
                    ans.append(el.denominator);
                    ans.append(" + √");
                    ans.append(el.sqrtDen);
                    ans.append(")");
                }
            }else{
                if(el.denominator != 1 && el.numerator != 0) {
                    ans.append("/");
                    ans.append(el.denominator);
                }
        }
        if(el.numerator == 0 && el.sqrtDen == 0 && el.sqrtNum == 0) {
            ans.append(el.numerator);
        }

        return ans.toString();
    }

    public String printOld(){
        Element el = new Element(this.numerator, this.denominator);

        el = refresh(el);

        StringBuilder sb = new StringBuilder();
        String num = String.valueOf(el.numerator);

        if(el.denominator == 1 || el.numerator == 0) {
            return num;
        }

        String den = String.valueOf(el.denominator);
        int maxSize = Math.max(num.length(), den.length());
        sb.append(" ".repeat((maxSize - num.length()) / 2));
        sb.append(num);
        sb.append(" ".repeat((maxSize - num.length()) / 2));
        sb.append("\n");
        sb.append("-".repeat(maxSize));
        sb.append("\n");
        sb.append(" ".repeat((maxSize - den.length()) / 2));
        sb.append(den);
        sb.append(" ".repeat((maxSize - den.length()) / 2));
        return sb.toString();
    }

    public static String[][] convertString(int[][] arr){
        String[][] elms = new String[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                elms[i][j] = new Element(arr[i][j], 1).print();
            }
        }
        return elms;
    }

    public static Element[][] convert(int[][] arr){
        Element[][] elms = new Element[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                elms[i][j] = new Element(arr[i][j], 1);
            }
        }
        return elms;
    }

    public static void print(Element[][] els){
        for (int i = 0; i < els.length; i++) {
            for (int j = 0; j < els[0].length; j++) {
                System.out.print(els[i][j].print() + " ");
            }
            System.out.println();
        }
    }

}
