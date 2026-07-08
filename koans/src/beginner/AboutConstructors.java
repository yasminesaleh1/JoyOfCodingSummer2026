package beginner;


import com.sandwich.koan.Koan;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;

public class AboutConstructors {

    class A {
        String someString = "a";

        public A() {
            someString += "x";
        }

    }

    class B extends A {
        public B() {
            someString += "g";
        }

    }

    @Koan
    public void simpleConstructorOrder() {
        assertEquals(new B().someString, "axg");
    }

    class Aa {
        String someString = "a";

        public Aa() {
            someString += "x";
        }

        public Aa(String s) {
            someString += s;
        }
    }

    class Bb extends Aa {
        public Bb() {
            super("Boo");
            someString += "g";
        }

    }

    @Koan
    public void complexConstructorOrder() {
        // if super's arg-constructor is called then it takes place
        // of the default constructor
        assertEquals(new Bb().someString, "aBoog");
    }

}
