import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число: ");
        //Получаем из консоли первое число
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число: ");
        //Получаем из консоли второе число
        int secondNumber = new Scanner(System.in).nextInt();
        //Вычисляем и выводим в косоль сумму этих чисел
        int sum = firstNumber + secondNumber;
        System.out.println("Сумма этих чисел: " + sum);
        //Вычисляем и выводим в косоль разность этих чисел
        int sub = firstNumber - secondNumber;
        System.out.println("Разность этих чисел: " + sub);
        //Вычисляем и выводим в косоль произведение этих чисел
        int mul = firstNumber * secondNumber;
        System.out.println("Произведение этих чисел: " + mul);
        //Вычисляем и выводим в косоль частное этих чисел
        double quotient = (double) firstNumber / secondNumber;
        System.out.println("Частное этих чисел: " + quotient);
    }
}
