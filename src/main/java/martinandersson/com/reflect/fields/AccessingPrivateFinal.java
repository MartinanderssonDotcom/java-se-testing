package martinandersson.com.reflect.fields;

import static java.lang.System.out;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class AccessingPrivateFinal
{
    public static void main(String... ignored) {
        out.print("Provide a Person name (not Oskar): ");
        
        /*
         * If name is a compile-constant, not grabbed during runtime, then
         * optimization might make the change to the field not observable.
         */
        String name = new Scanner(System.in).nextLine();
        Person p = new Person(name);
        
        out.println("p.getName() before: " + p.getName());
        
        setField("name", "Oskar", p);
        
        out.println("p.getName() after: " + p.getName());
    }
    
    private static void setField(String field, Object value, Object instance) {
        try {
            Field f = instance.getClass().getDeclaredField(field);
            
            f.setAccessible(true);
            makeNonFinal(f);
            
            f.set(instance, value);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void makeNonFinal(Field field) {
        try {
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

class Person {
    private final String name;
    
    Person(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}