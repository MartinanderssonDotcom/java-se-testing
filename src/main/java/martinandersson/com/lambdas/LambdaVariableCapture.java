package martinandersson.com.lambdas;

import java.util.ArrayList;
import java.util.List;

/**
 * The book "Java SE 8 for the Really Impatient" has an interesting exercise
 * attached to chapter 1 (exercise number 8). It reads:
 * 
 * <pre>{@code
 *   What happens when a lambda expression captures values in an enhanced for
 *   loop such as this one?
 *   
 *       String[] names = {"Peter", "Paul", "Mary"};
 *       List<Runnable> runners = new ArrayList<>();
 *       for (String name : names)
 *           runners.add(() -> System.out.println(name));
 *   
 *   Is it legal? Does each Lambda expression capture a different value, or do
 *   they all get the last value? What happens if you use a traditional loop
 *   for (int i = 0; i < names.length; i++)?
 * }</pre>
 * 
 * This class is an investigation into the matter.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class LambdaVariableCapture
{
    public static void main(String... ignored)
    {
        /*
         * I believe that in the first question (yes there are two questions),
         * the Lambda capture the name variable and all names are printed as
         * "expected". The iterator behind the for construct returns a new
         * reference for each iteration. Otherwise, the name couldn't be
         * effectively final. This is no different from making the name variable
         * explicitly final or swapping the lambda for an anonymous class.
         */
        
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names)
            runners.add(() -> System.out.println(name));
        
        for (Runnable r : runners)
            r.run();
        
        // Was my answer right? YES
        
        /*
         * In the second question, the variable i cannot be effectively final
         * and therefore, this code block won't even compile.
         */
        
//        for (int i = 0; i < names.length; i++)
//            runners.add(() -> System.out.println(names[i]));
        
        // Was my answer right? YES. The code won't compile.
    }
}
