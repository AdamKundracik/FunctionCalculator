package JPAZ.FRP_Projekt;


import java.util.Scanner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;
 
public class Calculator {
    static Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
        public double apply(double... args) {
            int arg = (int) args[0];
            if ((double) arg != args[0]) {
                arg = Math.round(arg);
            }
            if (arg < 0) {
                throw new IllegalArgumentException("Číslo pred faktoriálom nemôže byť menšie ako 0");
            }
            double result = 1;
            for (int i = 1; i <= arg; i++) {
                result *= i;
            }
            return result;
        }};
 
    private static double Function(double x) {
        double result = x;
        try {
            Expression e = new ExpressionBuilder(function).variables("x").operator(factorial).build().setVariable("x",
                    x);
            result = e.evaluate();
        } catch (ArithmeticException a) {
            throw new ArithmeticException();
        }
        return result;
    }
 
    private static String function;
    static double middle;
 
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
        double intervalStart = 0;
        double intervalEnd = 0;
 
        int repeats = 100;
        double precision = 0.00001;
        int cycles = 0;
 
        System.out.println("Zadajte funkciu: f(x) = ");
        function = sc.nextLine();
 
        System.out.print("Dolná hranica: ");
        if (sc.hasNextDouble()) {
            intervalStart = sc.nextDouble();
        } else {
            System.out.println("Nesprávny vstup");
        }
 
        System.out.print("Horná hranica: ");
        if (sc.hasNextDouble()) {
            intervalEnd = sc.nextDouble();
        } else {
            System.out.println("Nesprávny vstup");
        }
 
        System.out.println("Počet opakovaní alebo PRESS ANY BUTTON: ");
        if (sc.hasNextInt()) {
            repeats = sc.nextInt();
        }
 
        sc.close();
 
        System.out.println("Skúšam čo to dá...");
        boolean found = false;
        while (Math.abs(intervalStart - intervalEnd) > precision || cycles < repeats) {
            middle = (intervalStart + intervalEnd) / 2.0;
            cycles++;
            try {
                double evaluated = Function(middle);
                if (evaluated == 0) {
                    System.out.println("Koreň je: " + middle);
                    System.out.println("Počet delení: " + cycles);
                    found = true;
                    break;
                }
                if (evaluated <= precision && evaluated >= -precision) {
                    System.out.println("Približný koreň je : " + middle);
                    System.out.println("Počet delení: " + cycles);
                    found = true;
                    break;
                } else {
                    if (Function(intervalStart) * evaluated < 0.0) {
                        intervalEnd = middle;
                    } else {
                        intervalStart = middle;
                    }
                }
            } catch (ArithmeticException a) {
                intervalStart = intervalStart + 0.00001;
                continue;
            }
 
        }
        if (found == false) {
            System.out.println("Požadovaný koreň sa nenašiel, skúste iný interval");
        }
 
    }
 
}
 