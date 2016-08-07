package martinandersson.com.reflect.creation;

import java.lang.reflect.Array;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class CreateArray
{
    public static void main(String... ignored) {
        class Animal {}
        class Cat extends Animal {}
        class Dog extends Animal {}
        
        // Usually, even though arrays are covariant, their elements are invariant:
        Object[] objects = new Cat[1];
        
        objects[0] = new Cat();
        System.out.println("No problems putting a cat amongst cats!");
        
        try {
            objects[0] = new Dog();
        }
        catch (ArrayStoreException e) {
            System.out.println("Oops.. cannot mix dogs and cats!");
        }
        
        try {
            objects[0] = new Object();
        }
        catch (ArrayStoreException e) {
            System.out.println("Nor can we put an object in there!");
        }
        
        // Arrays 
        try {
            Cat[] cats = createArrayWithCast();
        }
        catch (ClassCastException e) {
            System.out.println("Arrays are covariant not contravariant!");
        }
        
        // BEST is to use reflection
        Cat[] cats = createArrayUsingReflection(Cat[].class, 1);
        
        cats[0] = new Cat();
        System.out.println("We can put a cat in the array created using reflection.");
        
        try {
            ((Object[]) cats)[0] = new Dog();
        }
        catch (ArrayStoreException e) {
            System.out.println("And we still cannot mix dogs and cats!");
        }
    }
    
    static <T> T[] createArrayWithCast() {
        return (T[]) new Object[2];
    }
    
    static <T> T[] createArrayUsingReflection(Class<? extends T[]> type, int length) {
        return ((Object) type == (Object) Object[].class) ?
                (T[]) new Object[length] :
                (T[]) Array.newInstance(type.getComponentType(), length);
    }
}