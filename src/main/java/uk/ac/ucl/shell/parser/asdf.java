
public class TypeA extends Thread {
    private Caller caller;
    private Object lock1;
    private Object lock2;
    public TypeA(Object o1, Object o2, Caller o3) {
        lock1 = o1;
        lock2 = o2;
        caller = o3;
    }
    public void run() {
        int it = 0;
        while(it < 3){
            try {
                synchronized(lock1) {
                    lock1.wait();
                }
            }
            catch(InterruptedException e){
                break;
            }
            it++;
            if(caller.credit > 2){
                caller.credit -= 2;
                synchronized(lock2) {
                    lock2.notifyAll();
                }
            }
            else{
                it--;
            }
        }
    }
}
public class TypeB extends Thread {
    private Caller caller;
    private Object lock1;
    private Object lock2;
    public TypeB(Object o1, Object o2, Caller o3) {
        lock1 = o1;
        lock2 = o2;
        caller = o3;
    }
    public void run(){
        int it = 0;
        while(it < 10){
            if(caller.credit < 6){
                caller.credit += 1;
                synchronized(lock1) {
                    lock1.notifyAll();
                }
                it++;
            }
            else{
                synchronized(lock1) {
                    lock1.notifyAll();
                }
                try {
                    synchronized(lock2) {
                        lock2.wait();
                    }
                }
                catch(InterruptedException e){
                    break;
                }
            }
        }
    }
}
public class TypeC extends Thread {
    private Caller caller;
    private Object lock1;
    private Object lock2;
    public TypeC(Object o1, Object o2, Caller o3) {
        lock1 = o1;
        lock2 = o2;
        caller = o3;
    }
    public void run(){
        while(true){
            try {
                synchronized(lock1) {
                    lock1.wait();
                }
                synchronized(lock2) {
                    lock2.wait();
                }
            }
            catch(InterruptedException e){
                break;
            }
            if(caller.credit > 1)
                break;
        }
    }
}
public class Caller {
    public volatile int credit = 0;
}
public class Main {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();
    public static void main(String[] args)
            throws InterruptedException {
        Caller b = new Caller();
        TypeA t1 = new TypeA(lock1,lock2,b);
        TypeB t2 = new TypeB(lock1,lock2,b);
        TypeC t3 = new TypeC(lock1,lock2,b);
        t1.start();
        t2.start();
        t3.start();
    }
}{
}
