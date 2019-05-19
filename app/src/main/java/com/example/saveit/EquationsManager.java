package com.example.saveit;

public class EquationsManager {
    private String[] one;
    private String[] two;
    private String[] three;
    private String[] four;
    private String[] five;
    private String[] six;
    private String[] seven;
    private String[] eight;
    private String[] nine;
    private String[] ten;

    public EquationsManager(){
        one = new String[]{"2 x 3 - 5", "3 x 6 -17", "4 x 8 - 31", "5 x 12 - 59", "9 x 3 - 26",
                            "6 - 5 x 1", "7 - 2 x 3", "25 - 8 x 3", "81 - 8 x 10", "37 - 9 x 4",
                            "(55/5) - 10", "(12/6) - 1", "(15/3) - 4", "(60/20) - 2", "(42/2) - 20",
                            "3 - (24/12)", "13 - (36/3)", "9 - (40/5)", "11 - (99/9)", "21 - (100/5)",
                            "86 - 52 - 33", "62 - 8 - 53", "53 - 21 - 31", "75 - 31 -43", "34 - 16 - 17",
                            "21 - 52 + 32", "32 + 9 - 40", "51 + 23 - 73", "32 - 41 + 10", "61 - 83 + 23"};
        two = new String[]{"3 x 3 - 7", "4 x 6 - 22", "4 x 9 - 34", "8 x 12 - 94", "7 x 3 - 19",
                    "32 - 5 x 6", "26 - 8 x 3", "29 - 9 x 3", "22 - 4 x 5", "12 - 2 x 5",
                    "(20/5) - 2", "(48/12) - 2", "(18/3) - 4", "(46/2) - 21", "(72/2) - 70",
                    "10 - (40/5)", "4 - (36/9)", "7 - (35/7)", "8 - (72/12)", "18 - (80/5)",
                    "56 - 22 - 32", "12 - 4 - 6", "23 - 11 - 10", "45 - 21 - 22", "15 - 5 - 8",
                    "11 - 25 + 16", "3 + 4 - 5", "51 + 23 - 72", "32 - 41 + 11", "13 - 6 - 5"};

    }

    public String[] getOne() {
        return one;
    }

    public String[] getTwo() {
        return two;
    }

    public String[] getThree() {
        return three;
    }

    public String[] getFour() {
        return four;
    }

    public String[] getFive() {
        return five;
    }

    public String[] getSix() {
        return six;
    }

    public String[] getSeven() {
        return seven;
    }

    public String[] getEight() {
        return eight;
    }

    public String[] getNine() {
        return nine;
    }

    public String[] getTen() {
        return ten;
    }
}
