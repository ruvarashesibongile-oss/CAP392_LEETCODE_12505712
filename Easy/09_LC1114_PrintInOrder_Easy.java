class Foo {

    CountDownLatch firstDone=new CountDownLatch(1);
     CountDownLatch secondDone=new CountDownLatch(1);

    public Foo() {
        
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        firstDone.countDown();
        
        // printFirst.run() outputs "first". Do not change or remove this line.
        
    }

    public void second(Runnable printSecond) throws InterruptedException {

        firstDone.await();

        printSecond.run();
        secondDone.countDown();
        
        // printSecond.run() outputs "second". Do not change or remove this line.
        
    }

    public void third(Runnable printThird) throws InterruptedException {
       secondDone.await();
        printThird.run();
        
        // printThird.run() outputs "third". Do not change or remove this line.
        
    }
}
