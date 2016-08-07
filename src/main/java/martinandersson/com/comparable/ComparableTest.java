package martinandersson.com.comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableTest
{
    public static void main(String... ignored) {
        List<Int> list = new ArrayList<>();
        
        for (int i = 0; i < 10; ++i)
            list.add(new Int(i));
        
        Collections.shuffle(list);
        Collections.sort(list);
        System.out.println(list);
        
        // Overflow problem using a - b;
        // -----------------------------
        List<Integer> realints = new ArrayList<>();
        
        for (int i = 0; i < 10; ++i)
            realints.add(i);
        
        Collections.shuffle(realints);
        realints.add(5, Integer.MIN_VALUE);
        
        // Knows to ignore DIFF:
//        Collections.sort(realints);
        
        // Dumb impl. do DIFF:
        Collections.sort(realints, (a, b) -> a - b); // diff overflows and MIN_VALUE is put in the back!
        
        System.out.println(realints);
    }
}

class Int implements Comparable<Int>
{
    public Int(int x) {
        this.x = x;
    }
    
    enum Strategy { ASC, DESC }
    
    static Strategy STRATEGY = Strategy.ASC;
    
    final int x;

    @Override
    public int compareTo(Int o) {
        if (STRATEGY == Strategy.ASC) {
            // ASC impl
            return this.x - o.x;
        }
        else {
            // DESC impl
            return o.x - this.x;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(x);
    }
}