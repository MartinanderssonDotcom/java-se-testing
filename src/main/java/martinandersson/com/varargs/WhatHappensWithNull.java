package martinandersson.com.varargs;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class WhatHappensWithNull
{
    public static void main(String... ignored) {
        String[] returned = repeat("arg1", null, "arg2", null);
        
        for (String s : returned)
            System.out.println("s: " + s); // <-- Prints arg1, null, arg2, null: so null is kept
    }
    
    static String[] repeat(String... args) {
        return args;
    }
}