import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            try {
                System.out.println(calc(scanner.nextLine()));
            } catch (CalcExeption e) {
                throw new RuntimeException(e);
            }
    }

    public static String calc (String input) throws CalcExeption {
        input = input.replaceAll("\\s+","");

        CheckInput(input);

        char operator = input.replaceAll("[0-9IVX]","").charAt(0);
        boolean isArabic = false;

        int[] Nums = new int[2];

        byte i = 0;
        if(input.matches("(.*)[0-9](.*)")){

            for (String s : input.split("[*/+-]")){
                Nums[i]= Integer.parseInt(s);
                i++;
            }
        }
        else {
            isArabic = true;
            for (String s : input.split("[*/+-]")) {
                Nums[i] = toArabic(s);
                i++;
            }
        }
        if(Nums[0] < 1 || Nums[0] > 10 || Nums[1] < 1 || Nums[1] > 10) throw new CalcExeption();

        int answer = calculating(Nums[0],Nums[1],operator);

        if (!isArabic) return String.valueOf(answer);

        if(answer < 1) throw new CalcExeption();

        return toRomane(answer);
    }

    private static boolean isInvalidNumOperations(String text){

        long plusCount = text.chars().filter(c -> c == '+').count();
        long minusCount = text.chars().filter(c -> c == '-').count();
        long divideCount = text.chars().filter(c -> c == '/').count();
        long multiplyCount = text.chars().filter(c -> c == '*').count();

        return (plusCount > 1 || minusCount > 1 || divideCount > 1 || multiplyCount > 1) ||
                (plusCount == 0 && minusCount == 0 && divideCount == 0 && multiplyCount == 0);
    }
    private static boolean isInvalidNumNums(String text) {
        int lastIndex = text.length() - 1;
        return (text.indexOf("+") == 0 || text.indexOf("+") == lastIndex ||
                text.indexOf("-") == 0 || text.indexOf("-") == lastIndex ||
                text.indexOf("/") == 0 || text.indexOf("/") == lastIndex ||
                text.indexOf("*") == 0 || text.indexOf("*") == lastIndex);
    }
    private static void CheckInput(String input) throws CalcExeption {
        if(input.matches("(.*)[^0-9IVX*/+-](.*)")) {
            throw new CalcExeption();
        }
        if (isInvalidNumOperations(input)) {
            throw new CalcExeption();
        }
        if (isInvalidNumNums(input)){
            throw new CalcExeption();
        }
        if (input.matches("(.*)[0-9](.*)") && input.matches("(.*)[IVX](.*)")){
            throw new CalcExeption();
        }
    }

    private static int toArabic(String roman){

        final String[] numerals = {"X", "IX", "V", "IV", "I"};
        final int[] values = {10, 9, 5, 4, 1};

            int result = 0;
            for (int i = 0; i < numerals.length; i++) {
                while (roman.startsWith(numerals[i])) {
                    result += values[i];
                    roman = roman.substring(numerals[i].length());
                }
            }
            return result;

    }
    private static String toRomane(int num){
        String[] keys = new String[] {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] vals = new int[] {100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while(ind < keys.length)
        {
            while(num >= vals[ind])
            {
                var d = num / vals[ind];
                num = num % vals[ind];
                ret.append(keys[ind].repeat(d));
            }
            ind++;
        }

        return ret.toString();
    }
    private static int calculating(int a, int b, char operator){
        if (operator == '+') return a + b; else if (operator == '-') return a - b;
        else if (operator == '*') return a * b; else return a / b;
    }
}
