package org.learning.core;

/**
 * Nested classes, Switch expressions, Enum, Records
 */
class LanguageBasics {

    /**
     *      Nested classes
     *
     *      1: Inner class
     *      2: static inner class
     *      3: Local class
     *      4: Anonymous inner class
     */
    private class InnerClass {

        /*
            Can access enclosing class fields and methods
            Cannot have static members declared within it

            LanguageBasicsTest outerObj = new LanguageBasicsTest();
            outerObj.InnerClass innerObj = outerObj.new InnerClass();
         */
    }

    private static class StaticInnerClass{

        /*
            Cannot access outer class instance fields and methods
            But can access static members of outer class

            StaticInnerClass obj = new StaticInnerClass();
         */
    }

    private interface AnonymousClassInterface {
        void print(String s);
    }

    private void innerClasses(){

        class LocalClass{
            /*
                Can access enclosing class members and local variables declared within method
                if the variables are final or effective final.

                Cannot have static members
             */
        }

        var anonymousInnerClassObj = new AnonymousClassInterface(){
            /*
                Can access enclosing class members and local variables declared within method
                if the variables are final or effective final.

                Cannot have static members and constructor as well
             */
            @Override
            public void print(String s){
                System.out.println(s);
            }
        };

    }

    void testSwitchExpression(){
        var choice  = 10;

        var result = switch(choice){
            case 1, 2, 3 -> "Case one";
            case 4, 5 -> {
                var s = "two";
                yield "Case " + s;  // Use yield to return values from block
            }
            case 10 -> "Case three";
            default -> "Default";   // Default not required in case of Enum
        };

    }


    /**
     * Enums are classes where all instances are known to the compiler.
     *
     * They can have fields, constructor and methods
     * They can have abstract method which all instances need to provide implementation and they can implement interfaces
     */
    private enum PrimaryColors{

        RED(1), BLUE(2), GREEN(3);

        private int code;

        PrimaryColors(int code){
            this.code = code;
        }

        public int getCode(PrimaryColors primaryColors){
            return primaryColors.code;
        }
    }


    /**
     *  Record provides a constructor, equals(), hashcode() and accessor methods
     *
     *  We can use compact constructor if we need to perform some validation or make defensive copy of mutable collection
     */
    private record Interval(int start, int end) {

        /*
            Compact constructor
         */
        public Interval {
            if(start < 0 || end < 0){
                throw new IllegalStateException();
            }
        }

        /*

            We can either have compact constructor or a canonical constructor given both


            public Interval(int start, int end){
                if(start < 0 || end < 0){
                    throw new IllegalStateException();
                }
                this.start = start;
                this.end = end;
            }

         */

        public Interval(int start){
            this(start, start);
        }

    }

}
