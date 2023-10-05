public class Numbers {

    public int getRandomNum (int num) {
        return (int) (Math.random() * num);
    }

    public String numToString (int num) {
        return String.valueOf(num);
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
