package martinandersson.com.methodreferences;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ImplicitContextInMethodReference
{
    public static void main(String... ignored) {
        Test test1 = new Test("test1"), test2 = new Test("test2");
        
        Function<Test, Long> x = test1::acceptOneArgReturnOneArg;
        
        // Does not compile, getCreationDate() does not accept an argument.
//        x = test1::getCreationDate;
        
        // This do compile, argument passed in will be used as context (this):
        x = Test::getCreationDate;
        
        // Is equivalent to:
//        x = t -> t.getCreationDate();
        
        // Arg in must be of same type, does not work:
//        Function<Object, Long> x2 = Test::getCreationDate;
        
        Stream.of(test2, test1)
                // Real world example:
                .sorted(Comparator.comparing(Test::getCreationDate))
                .forEach(System.out::println);
    }
}

class Test {
    
    final String name;
    final long creationDate; 
    
    Test(String name) {
        this.name = name;
        this.creationDate = System.nanoTime();
    }
    
    long getCreationDate() {
        return creationDate;
    }
    
    long acceptOneArgReturnOneArg(Test test) {
        return test.creationDate;
    }

    @Override
    public String toString() {
        return name + " " + String.valueOf(creationDate);
    }
}