import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner myObj = new Scanner(System.in);
        String expression = myObj.nextLine();

        String result = calc(expression.toUpperCase());
        System.out.println(result);
    }

    public static String calc(String expression) throws Exception {
        Map<String, String> exp = new HashMap<String, String>();
        String[] temp = expression.split(" ");
        Pattern pattern = Pattern.compile("[A-Z]+");
        boolean operatorError = true;

        if (temp.length != 3) throw new Exception("not enough argument");
        exp.put("fOperand", temp[0]);
        exp.put("operator", temp[1]);
        exp.put("sOperand", temp[2]);
        exp.put("fType", String.valueOf(pattern.matcher(temp[0]).find()));
        exp.put("sType", String.valueOf(pattern.matcher(temp[2]).find()));

        if (!exp.get("fType").equals(exp.get("sType"))) throw new Exception("various data types");
        for (String i : operators)
            if (exp.get("operator").equals(i)) operatorError = false;

        if (operatorError) throw new Exception("invalid operator");


        if ((exp.get("fType").equals("true") && (romanToInt(exp.get("fOperand")) > 10 || romanToInt(exp.get("sOperand")) > 10 ))
        || (exp.get("fType").equals("false") && (Integer.parseInt(exp.get("fOperand")) > 10 ||  Integer.parseInt(exp.get("sOperand")) > 10)))throw new Exception("value error");

        int calculateData = exp.get("fType").equals("true") ?
                calculate(romanToInt(exp.get("fOperand")), romanToInt(exp.get("sOperand")), exp.get("operator")) :
                calculate(Integer.parseInt(exp.get("fOperand")), Integer.parseInt(exp.get("sOperand")), exp.get("operator"));

        if(exp.get("fType").equals("true") && calculateData < 1) throw new Exception("invalid value");

        return exp.get("fType").equals("true") ? intToRoman(calculateData) : String.valueOf(calculateData) ;
    }


    public static Integer calculate(int firstNumber, int secondNumber, String operator) {
        return switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "/" -> (firstNumber / secondNumber);
            default -> 0;
        };
    }

    public static String intToRoman(int number) {
        if (number >= 4000 || number <= 0)
            return null;
        StringBuilder result = new StringBuilder();
        for (int i = arabic.length - 1 ; i >= 0; i--) {
            while (number >= arabic[i]) {
                number -= arabic[i];
                result.append(roman[i]);
            }
        }
        return result.toString();
    }

    public static Integer romanToInt(String text1) {
        String text = text1.toUpperCase();
        Integer result = 0;
        M1:
        for (int i : arabic) {
            int posit = 0;
            int n = arabic.length - 1;
            while (n >= 0 && posit < text.length()) {
                try {
                    if (text.substring(posit, roman[n].length()).equals(roman[n])) {
                        result += arabic[n];
                        text = text.substring(roman[n].length());

                        if (text.length() == 0) {
                            break M1;
                        }
                        posit++;
                    } else n--;
                } catch (Exception e) {
                    n--;
                }

            }
        }
        return result;
    }

    static final String[] operators = {"+", "-", "*", "/"};
    static final Integer[] arabic = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    static final String[] roman = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
}