ioS Calculator Appium Tests
===========================

This example will construct tests using Appium and run them on TestObject / Sauce Labs.


### Set Environment variable
Specify the TESTOBJECT_API_KEY by setting an environment variable.
The TESTOBJECT_API_KEY is unique per user, per app.

    export TESTOBJECT_API_KEY=<YOUR TESTOBJECT API KEY>

###Upload the Calculator app


The included Calculator_1.0.ipa must first be uploaded to TestObject for these tests to work.


###Testing in Parallel


The Maven Surefire plugin enables parallel testing. 

To run tests in parallel across devices in `ParallelIOSCalculatorTest` set parallel=classes in pom.xml

     <parallel>classes</parallel>

To run all tests in parallel in the same class in `SimpleIOSCalculatorTest`, set parallel=methods in pom.xml

    <parallel>methods</parallel>
