package org.learning;

/**
 *
 * An exception is an event, which occurs during the execution of a program, that disrupts the normal flow of the program's instructions.
 *
 *  1. Throwable
 *      1.1 Error
 *      1.2 Exception
 *          1.2.1 RuntimeException
 *
 * There are two types
 * 1. Checked :- Exception class and all its subclasses represents checked exception. All checked exception must be caught or thrown
 *                  i.e. need to follow "Catch or specify" requirement. Checked exception represent conditions that are recoverable i.e. client -
 *                  can try to recover from this exception by retrying or by specifying correct file name.
 *
 * 2. Unchecked :- Error, RuntimeException and all their subclasses are uncheck exception. Generally runtimeException represents programming error or bug
 *                  which should be solved rather than handled therefor the "Catch or specify" requirement is not there for runtime exceptions.
 *                  Errors are usually caused by serious problems that are outside the control of the program, such as running out of memory or a system crash.
 *
 */
class ExceptionTest {
}
