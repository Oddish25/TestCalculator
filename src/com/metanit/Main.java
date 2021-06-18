package com.metanit;

import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {


        String[] inputList = getData();
        int[] numbers = getInt(inputList);
        int result = calc(numbers[1],numbers[2],inputList[1].charAt(0));
        output(result, numbers[0]);
    }

    public static boolean isNumeric(String str) {

        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    public static void output(int result, int flag){

        if (flag == 0)
        {
            System.out.println("Результат операции:" +result);
        }
        else
        {
            if (result == 0)
            {
                System.out.println("Результат операции: " + result);
                return;
            }
            else if (result < 0)
            {
                System.out.println("Результат операции: -" + arabicToRoman(Math.abs(result)));
                return;
            }
            System.out.println("Результат операции: " + arabicToRoman(result));
        }
    }

    public static String[] getData(){

        System.out.println("Введите желаемое выражение");
        String[] result;
        if(scanner.hasNext()){
            String input = scanner.nextLine();
            String[] inputList = input.split(" ");

            if (inputList.length == 3)
            {
                int[] num;
                ValueRange range = ValueRange.of(1,9);
                num = getInt(inputList);
                if (range.isValidIntValue(num[1]) && range.isValidIntValue(num[2]))
                {
                    return new String[]{inputList[0],inputList[1],inputList[2]};
                }
                else
                {
                    System.out.println("Число выходит за диапазон, введите еще раз");
                    result = getData();
                }
            }
            else {
                System.out.println("Ошибка ввода, введите еще раз");
                result = getData();
            }
        }
        else
        {
            System.out.println("Ошибка ввода, введите еще раз");
            result = getData();
        }
        return result;
    }

    public static int[] getInt(String[] input){

        if (isNumeric(input[0]) && isNumeric(input[2]))
        {
            int num1 = Integer.parseInt(input[0]);
            int num2 = Integer.parseInt(input[2]);
            return new int[]{0,num1,num2};
        } else
        {
            int num1 = romanToArabic(input[0]);
            int num2 = romanToArabic(input[2]);

            return new int[]{1,num1,num2};
        }
    }

    public static char getOperation(){

        System.out.println("Введите операцию:");
        char operation;
        if(scanner.hasNext()){
            operation = scanner.next().charAt(0);
        } else {
            System.out.println("Вы допустили ошибку при вводе операции. Попробуйте еще раз.");
            scanner.next();
            operation = getOperation();
        }
        return operation;
    }
    public static int calc(int num1, int num2, char operation){

        int result;
        switch (operation){
            case '+':
                result = num1+num2;
                break;
            case '-':
                result = num1-num2;
                break;
            case '*':
                result = num1*num2;
                break;
            case '/':
                result = num1/num2;
                break;
            default:
                System.out.println("Операция не распознана, повторите ввод");
                result = calc(num1, num2, getOperation());
        }
        return result;
    }


    public static String arabicToRoman(int number) {

        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " число выходит за диапозон от 0 до 4000");
        }

        List romanNumerals = RoNum.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RoNum currentSymbol = (RoNum) romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }


    public static int romanToArabic(String input) {

        String romanNumeral = input.toUpperCase();
        int result = 0;

        List romanNumerals = RoNum.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RoNum symbol = (RoNum) romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " вы совершили ошибку ввода");
        }
        return result;
    }
}