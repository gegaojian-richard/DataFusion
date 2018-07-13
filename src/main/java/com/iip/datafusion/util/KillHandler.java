package com.iip.datafusion.util;

import com.iip.datafusion.backend.JoinManager;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class KillHandler implements SignalHandler{

    public void registerSignal(String signalName) {
        Signal signal = new Signal(signalName);
        Signal.handle(signal, this);
    }


    @Override
    public void handle(Signal signal) {
        if (signal.getName().equals("TERM")) {
            //
            System.out.println("TERM");
        } else if (signal.getName().equals("INT") || signal.getName().equals("HUP")) {
            //
            System.out.println("INT or HUP");
        } else {
            //
            System.out.println("OTHER");
        }
        JoinManager.getInstance().shutdown();
    }
}
