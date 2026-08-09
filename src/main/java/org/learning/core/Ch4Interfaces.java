package org.learning.core;

public class Ch4Interfaces {

    private interface parentInterface {
        // An interface can also be declared inside class

        float PI = 3.147f; // Constant by default public, static and final

        enum State{
            ACTIVE, FORMER
        }

        String modify(String element); // Abstract method
    }

    private interface childInterface extends parentInterface {

        default String modify(String element) { // Default method
            return element + " Modified";
        }

        static String modifySecond(String element){ // Static method
            return element + " Modified second";
        }

        private void printUtility(String element){  // Private method
            IO.println(element);
        }
    }

}
