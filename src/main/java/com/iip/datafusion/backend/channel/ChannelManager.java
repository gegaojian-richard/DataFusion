package com.iip.datafusion.backend.channel;

import com.iip.datafusion.backend.job.accuracy.AccuracyJob;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.join.JoinJob;

public class ChannelManager {

    private WorkStealingEnabledChannel<JoinJob> joinChannel;

    private WorkStealingEnabledChannel<AccuracyJob> accuracyChannel;

    private WorkStealingEnabledChannel<ConsistencyJob> consistencyChannel;

    private WorkStealingEnabledChannel<IntegrityJob> integrityChannel;


    private final static ChannelManager singleInstance = new ChannelManager();

    private ChannelManager(){
    }

    public static ChannelManager getInstance(){
        return singleInstance;
    }

    public void setJoinChannel(WorkStealingEnabledChannel<JoinJob> joinChannel){
        this.joinChannel = joinChannel;
    }

    public void setAccuracyChannel(WorkStealingEnabledChannel<AccuracyJob> accuracyChannel) {
        this.accuracyChannel = accuracyChannel;
    }

    public void setConsistencyChannel(WorkStealingEnabledChannel<ConsistencyJob> consistencyChannel) {
        this.consistencyChannel = consistencyChannel;
    }

    public void setIntegrityChannel(WorkStealingEnabledChannel<IntegrityJob> integrityChannel) {
        this.integrityChannel = integrityChannel;
    }

    public WorkStealingEnabledChannel<JoinJob> getJoinChannel() {
        return joinChannel;
    }

    public WorkStealingEnabledChannel<AccuracyJob> getAccuracyChannel() {
        return accuracyChannel;
    }

    public WorkStealingEnabledChannel<ConsistencyJob> getConsistencyChannel() {
        return consistencyChannel;
    }

    public WorkStealingEnabledChannel<IntegrityJob> getIntegrityChannel() {
        return integrityChannel;
    }
}
