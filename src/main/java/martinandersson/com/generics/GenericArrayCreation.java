package martinandersson.com.generics;

import java.lang.reflect.Array;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class GenericArrayCreation
{
    public static void main(String... ignored) {
        try {
            String[] bad = badFactory("Will ", "crash.");
        }
        catch (ClassCastException e) {
            System.out.println("Client got: " + e);
        }
        
        String[] good = goodFactory("Hello ", "World");
        
        System.out.println(good[0] + good[1]);
        System.out.println("Array type: " + good.getClass()); // class [Ljava.lang.String;
    }
    
    private static <E> E[] badFactory(E first, E second) {
        E[] elems = (E[]) new Object[2];
        
        elems[0] = first;
        elems[1] = second;
        
        return elems;
    }
    
    private static <E> E[] goodFactory(E first, E second) {
        E[] elems = (E[]) Array.newInstance(first.getClass(), 2);
        
        elems[0] = first;
        elems[1] = second;
        
        return elems;
    }
}