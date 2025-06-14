package org.learning.core;

/**
 * Four pillars of oops
 *
 * 1. Abstraction : I don't need to know all the details, I abstract it out and work at the abstraction level.
 *
 * 2. Encapsulation : Hiding implementation detail. we separate what you do from how to do.
 *                     Encapsulation exist at different level, a local variable declared inside a method is only visible inside that method.
 *
 * 3. Inheritance : It allows us to extend one abstraction from another abstraction. It is the weakest link of oops.
 *                      It creates tight coupling.
 *
 * 4. Polymorphism : The method we call is not based on the reference but on the type of the object at runtime.
 *                      *** It is the most important pillar of oops, as it allows extensibility.
 *                      Take away polymorphism and there is nothing much left in object-oriented programming.
 */
public class Ch2Oops {

    private class Before {

        class CreditCardPayment {
            void pay(int amount){}
        }

        class OrderProcessor{

            /*
                It is tightly coupled to CreditCardPayment class, Tight coupling makes it harder to test and extend code
                What if we want to accept payment via some other method, for example through Paypal
                Inorder to do that we need to change OrderProcessor clas This violates Open Closed principle
                To solve this we may use Dependency Inversion principle :
                Rather than depending on a concrete class i.e. CreditCardPayment class We depend on abstraction
            */
            public void processOrder(CreditCardPayment creditCardPayment){
                // Before payment
                creditCardPayment.pay(10_000);
                // After payment
            }
        }
    }


    private class After{

        /*
            We introduce abstraction by creating an interface paymentMethod
         */
        interface PaymentMethod {
            void pay(int amount);
        }

        class CreditCardPayment implements PaymentMethod {
            @Override
            public void pay(int amount){}
        }

        class PaypalPayment implements PaymentMethod {
            @Override
            public void pay(int amount){}
        }

        class OrderProcessor{

            /*
                Now we can make payment not only using credit card but also PaypalPayment
                as long as the PaypalPayment object follow the contract of PaymentMethod interface.

                Now our class is extensible, which is the essence of oops by using polymorphism.

                Polymorphism comes in play here by means that the method referred by processOrder method at compile time,
                is not the method invoked at runtime rather the method invoked depends on the instance passed.
             */
            public void processOrder(PaymentMethod paymentMethod){
                // Before payment
                paymentMethod.pay(10_000);
                // After payment
            }

        }
    }

}
