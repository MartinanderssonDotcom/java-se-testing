package martinandersson.com.generics;

import java.util.function.Function;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class CovarianceContravariance
{
    public static void main(String... ignored) {
        execute1((/* Accept anything that is the supertype of Car: */ Car sports) -> new SportsCar());
        
        Function<Car, SportsCar> myCarFunc = (Car car) -> new SportsCar();
//        execute2(myCarFunc); // <-- Does not compile.
        
        /*
         * The argument or parameter types are said to be contravariant (Car can
         * be more specialized; here SportsCar). The return type is said to be
         * covariant, more generic (here SportsCar)
         */
        Object o = new RichCarOwner().example(new SportsCar());
    }
    
    static Car execute1(Function<? super Car, ? extends Car> func) {
        return func.apply(new SportsCar());
    }
    
    static Car execute2(Function<Car, Car> func) {
        return func.apply(new SportsCar());
    }
}

class Vehicle {}

class Car extends Vehicle {}

class SportsCar extends Car {}

class CarOwner
{
    public Car getCar() {
        return new Car();
    }
    
    public void acceptString(String str) {
        System.out.println("CarOwner.acceptString(String)");
    }
}

class RichCarOwner extends CarOwner
{
    // Does not compile:
//    @Override
//    public Vechicle getCar() {
//        return new Vehicle();
//    }
    
    // Compiles (the RETURN TYPE is covariant):
    @Override
    public SportsCar getCar() {
        return new SportsCar() {}; // <-- Return type is covariant
    }
    
    public Car example(Car car)
    {
        return new SportsCar();
    }
    
//    @Override This is not an override!
    public void acceptString(Object str) {
        System.out.println("RichCarOwner.acceptString(Object)");
    }
}