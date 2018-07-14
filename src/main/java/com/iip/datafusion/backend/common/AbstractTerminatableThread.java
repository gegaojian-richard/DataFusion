package com.iip.datafusion.backend.common;

/**
 * 可终止线程抽象类
 * @Author Ge GaoJian
 */
public abstract class AbstractTerminatableThread extends Thread implements Terminatable{
    public final TerminationToken terminationToken;

    public AbstractTerminatableThread(){
        this(new TerminationToken());
    }

    /**
     * 多线程共享TerminationToken实例时使用该构造函数
     * @param terminationToken 线程间共享的线程终止标志
     */
    public AbstractTerminatableThread(TerminationToken terminationToken){
        super();
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    /**
     * 由子类实现其线程的处理逻辑
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;

    /**
     * 线程真正停止前的清理动作
     * 线程准备停止后执行的操作，即准备阶段后在执行阶段做的事
     * @param cause
     */
    protected void doCleanup(Exception cause){
    }

    /**
     * 线程停止所需的操作
     */
    protected void doTerminiate(){

    }

    /**
     * 线程在run过程中发现终止信号后自动进行终止操作的阶段二，执行阶段
     */
    @Override
    public void run() {
        // 用于判断线程catch的异常是否为interrupt异常
        Exception exception = null;
        try {
            while (true){
                if (terminationToken.isToShutdown() && terminationToken.reservations.get() <= 0){
                    //如果当前线程正在执行，发现其关联的terminationToken实例的shutdown被设置为true并且没有需要处理的业务，则退出循环准备终止
                    break;
                }
                // 没有发现终止命令，则执行该线程该处理的逻辑
                doRun();
            }
        }catch (Exception e){
            //
            exception = e;
        }finally {
            // 无论是线程运行时正常结束还是在等待状态接受到interrupt调用而终止都要执行doCleanup，并且通知关联了同一个terminationToken实例的其他线程都进行终止
            try {
                doCleanup(exception);
            }finally {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate(); // 先通过doTerminiate方法完成停止准备工作，doTerminiate由子线程实现，准备工作完成后再调用Thread.interrupt()
    }

    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminiate();
        }finally {
            // 再次检查如果没有待处理的任务后直接强制终止线程，因为doTerminiate进行终止准备工作时可能还有任务需要处理
            if (terminationToken.reservations.get() <= 0) {
                super.interrupt();
            }
        }
    }

    // 线程拥有者会等待其欲终止的线程终止后再进行之后的操作
    public void terminate(boolean waitUtilThreadTerminated){
        terminate();
        if (waitUtilThreadTerminated){
            try {
                this.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}