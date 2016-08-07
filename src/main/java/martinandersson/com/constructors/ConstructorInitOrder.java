package martinandersson.com.constructors;

import static java.lang.System.out;

/**
 * Lesson learned: All instance fields are initialized first. Then afterwards, a
 * constructor may change those (or use the values itself).
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ConstructorInitOrder
{
    public static void main(String... ignored) {
        // Constructor says "Kalle Andersson"
        Person p = new Person();
        
        // After constructor..
        out.println(p.name + " " + p.lastName); // Oskar Eriksson
    }
}

class Person
{
    String name = "Kalle"; // <-- BEFORE
    
    Person() {
        out.println("name: " + name); // Kalle
        name = "Oskar";
        
        out.println("lastName: " + lastName); // Andersson
        lastName = "Eriksson";
    }
    
    String lastName = "Andersson"; // <-- AFTER
}