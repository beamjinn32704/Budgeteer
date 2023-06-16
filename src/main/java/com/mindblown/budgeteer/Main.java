/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mindblown.budgeteer;

import com.mindblown.parser.ParseRes;
import com.mindblown.parser.Parser;
import com.mindblown.parser.util.CHR;
import com.mindblown.parser.util.ONE_OR_MORE;
import com.mindblown.parser.util.PUtil;
import com.mindblown.parser.util.SAT;
import com.mindblown.parser.util.SYMBOL;
import com.mindblown.util.CalendarHelper;
import java.lang.reflect.Array;

/**
 *
 * @author beamj
 */
public class Main {

    public static void main(String[] args) {
        go(args);
    }

    private static String go(String[] args) {
        System.out.println(Float.valueOf("adslkjf"));
        if (args.length == 0) {
            return "";
        }
        Budget b = new Budget();
        String command = String.join(" ", args);
        switch (args[0]) {
            case "new" -> {
                if (args.length == 1) {
                    return "new command expecting more arguments\n";
                }
                switch (args[1]) {
                    case "storage" -> {
                        if (args.length == 2) {
                            return "error, no storage name given";
                        } else if (args.length == 3) {
                            return b.addStorage(args[2], 0);
                        } else if (args.length == 4) {
                            ParseRes<Float> fpr = PFLOAT.parse(args[3]);
                            if (fpr.failed()) {
                                return "error, '" + args[3] + "isn't a number";
                            }
                            return b.addStorage(args[2], fpr.getParseVal());
                        } else {
                            return "error, new storage command expects fewer arguments\n";
                        }
                    }
                    case "deposit" -> {
                        
                    }
                    case "cost" -> {
                    }
                    case "transfer" -> {
                    }
                    case "category" -> {
                    }
                    default -> {
                    }
                }
            }
            case "set" -> {
                switch (args[1]) {
                    case "storage" -> {

                    }
                    case "category" -> {

                    }
                    case "transaction" -> {

                    }
                }
            }
            case "view" -> {
                switch (args[1]) {
                    case "transactions" -> {

                    }
                    case "transaction" -> {

                    }
                }
            }
            default -> {
            }
        }
        return "Error! Command not found";
    }

    private static Parser<Float> PFLOAT = (str) -> {
        ParseRes<String> pr = new ONE_OR_MORE(new SAT((str1) -> {
            return str1.length() > 0 && str1.charAt(0) != ' ';
        })).parse(str);
        if (pr.failed()) {
            return new ParseRes<Float>();
        }
        try {
            Float f = Float.valueOf(pr.getParseVal());
            return new ParseRes<Float>(pr.getStrRem(), f);
        } catch (Exception e) {
            return new ParseRes<Float>();
        }
    };
    
    private static Parser<Integer> PINT = (str) -> {
        ParseRes<String> pr = new ONE_OR_MORE(new SAT((str1) -> {
            return str1.length() > 0 && str1.charAt(0) != ' ';
        })).parse(str);
        if (pr.failed()) {
            return new ParseRes<Integer>();
        }
        try {
            Integer i = Integer.valueOf(pr.getParseVal());
            return new ParseRes<Integer>(pr.getStrRem(), i);
        } catch (Exception e) {
            return new ParseRes<Integer>();
        }
    };
    
    private static Parser<String> SPINT = (str) -> {
        ParseRes<Integer> pr = PINT.parse(str);
        if(pr.failed()){
            return new ParseRes<String>();
        }
        return new ParseRes(pr.getStrRem(), pr.getParseVal().toString());
    };
    
    private Parser<Date> PDATE = (str) -> {
        ParseRes<String[]> pr = PUtil.parserSeqNoB(str, SPINT, new CHR('/'), SPINT);
        if(pr.failed()){
            return new ParseRes<Date>();
        }
        String[] prRes = pr.getParseVal();
        Integer month = Integer.valueOf(prRes[0]);
        Integer day = Integer.valueOf(prRes[2]);
        
        ParseRes<String[]> pr1 = PUtil.parserSeqNoB(pr.getStrRem(), new CHR('/'), SPINT);
        if(!pr1.failed()){
            Integer year = Integer.valueOf(pr1.getParseVal()[1]);
            return new ParseRes<>(pr1.getStrRem(), new Date(month, day, year));
        }
        Integer year = Integer.valueOf(CalendarHelper.year(CalendarHelper.now()));
        return new ParseRes<>(pr.getStrRem(), new Date(month, day, year));  
    };
}
