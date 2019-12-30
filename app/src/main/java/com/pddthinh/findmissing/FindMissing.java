package com.pddthinh.findmissing;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindMissing {
    public static void main(String[] params) {
        Generator generator = new Generator();

        for (int i = 0; i < 5; i++) {
            List<Generator.OpInfo> data = generator.genHiddenOps(Generator.Difficulty.HARD);
            System.out.println("f(x) = " + generator.print(data));
        }
    }
}

class Generator {
    private static final int SIZE = 1;
    private static final int SIZE_HARD = 2;

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD;

        public boolean isValid(int value) {
            switch (this) {
                case EASY:
                    return (0 < value && value <= 2);

                case MEDIUM:
                    return (3 <= value && value <= 6);

                case HARD:
                    return (7 <= value && value <= 9);

                default:
                    return false;
            }
        }

        public boolean isValid(int param, Operator op) {
            switch (op) {
                case PLUS:
                case MINUS:
                    return isValid(param);

                case MULTIPLY:
                    return param > 0 && isValid(param);

                case DIVISION:
                    return param != 0 && isValid(param);

                default:
                    return false;
            }
        }

        public static Difficulty fromValue(int random) {
            int tmp = random % Difficulty.values().length;
            return Difficulty.values()[tmp];
        }
    }

    enum Operator {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVISION

        ;

        public static Operator fromValue(int random) {
            int tmp = random % (Operator.values().length - 1);
            return Operator.values()[tmp];
        }
    }

    class OpInfo {
        int param;
        Operator operator;
    }

    class Question {
        List<String> question;
        List<String> suggestion;
    }

    public List<OpInfo> genHiddenOps(Difficulty sizeMode) {
        ArrayList<OpInfo> hiddenOps = new ArrayList<>();
        int size = SIZE;
        if (sizeMode == Difficulty.HARD)
            size = SIZE_HARD;

        Set<Integer> existed = new HashSet<>();

        SecureRandom random = new SecureRandom();
        OpInfo inf;
        Difficulty infMode;

        while (hiddenOps.size() != size) {
            inf = new OpInfo();

            // Generate Op
            do {
                inf.operator = Operator.fromValue(random.nextInt(9));
            } while (hiddenOps.isEmpty() && inf.operator == Operator.MINUS);

            // Generate param
            do {
                infMode = Difficulty.fromValue(random.nextInt(9));
                inf.param = random.nextInt(9);

            } while (inf.param == 1
                    || existed.contains(inf.param)
                    || !infMode.isValid(inf.param, inf.operator));

            hiddenOps.add(inf);
            existed.add(inf.param);
        }

        return hiddenOps;
    }

    public String print(List<OpInfo> ops) {
        StringBuilder builder = new StringBuilder();

        do {
            if (ops == null || ops.isEmpty()) break;

            for (OpInfo inf : ops) {
                switch (inf.operator) {
                    case MINUS: {
                        builder.append(" -")
                                .append(inf.param)
                                .append('x')
                        ;
                        break;
                    }

                    case PLUS: {
                        if (builder.length() > 0)
                            builder.append(" + ");

                        builder.append(inf.param)
                                .append('x')
                        ;
                        break;
                    }

                    case DIVISION: {
                        if (builder.length() > 0)
                            builder.append(" + ");

                        builder.append("x").append('/')
                                .append(inf.param);
                        break;
                    }

                    case MULTIPLY: {
                        if (builder.length() > 0)
                            builder.append(" + ");

                        builder.append(inf.param)
                                .append("x");
                        break;
                    }

                    default:
                        break;
                }
            }

            builder.setLength(builder.length() - 1);
        } while (false);

        return builder.toString();
    }

    List<Question> genQuestions(int size) {
        List<Question> questions = new ArrayList<>();



        return questions;
    }
}