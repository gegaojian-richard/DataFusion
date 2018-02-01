package com.iip.datafusion.backend.channel;

import com.iip.datafusion.backend.job.accuracy.AccuracyJob;
import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.backend.job.test.TestJob;
public class ChannelManager {

    private WorkStealingEnabledChannel<JoinJob> joinChannel;

    private WorkStealingEnabledChannel<AccuracyJob> accuracyChannel;

    private WorkStealingEnabledChannel<ConsistencyJob> consistencyChannel;

    private WorkStealingEnabledChannel<IntegrityJob> integrityChannel;


    private final static ChannelManager singleInstance = new ChannelManager();

    // ganjun test
    private WorkStealingEnabledChannel<TestJob> testChannel;
    // ganjun textRank
    private WorkStealingEnabledChannel<TextRankJob> textRankChannel;
    // ganjun nameRecognition
    private WorkStealingEnabledChannel<NameRecognitionJob> nameRecognitionChannel;

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

    // ganjun add TestJob
    public WorkStealingEnabledChannel<TestJob> getTestChannel() {
        return testChannel;
    }

    public void setTestChannel(WorkStealingEnabledChannel<TestJob> testChannel){
        this.testChannel = testChannel;
    }

    // ganjun add TextRankJob

    public WorkStealingEnabledChannel<TextRankJob> getTextRankChannel() {
        return textRankChannel;
    }

    public void setTextRankChannel(WorkStealingEnabledChannel<TextRankJob> textRankChannel) {
        this.textRankChannel = textRankChannel;
    }

    // ganjun add name recognition job

    public WorkStealingEnabledChannel<NameRecognitionJob> getNameRecognitionChannel() {
        return nameRecognitionChannel;
    }

    public void setNameRecognitionChannel(WorkStealingEnabledChannel<NameRecognitionJob> nameRecognitionChannel) {
        this.nameRecognitionChannel = nameRecognitionChannel;
    }
}
