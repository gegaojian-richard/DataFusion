package com.iip.datafusion.backend.channel;

import com.iip.datafusion.backend.job.accuracy.AccuracyJob;
import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.backend.job.consistency.UpdateConsistencyJob;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.dgs.model.UpdateIntegrityJob;

public class ChannelManager {

    private WorkStealingEnabledChannel<JoinJob> joinChannel;

    private WorkStealingEnabledChannel<AccuracyJob> accuracyChannel;

    private WorkStealingEnabledChannel<ConsistencyJob> consistencyChannel;

    private WorkStealingEnabledChannel<UpdateConsistencyJob> updateconsistencyChannel;

    private WorkStealingEnabledChannel<IntegrityJob> integrityChannel;

    private WorkStealingEnabledChannel<UpdateIntegrityJob> updateIntegrityChannel;


    private final static ChannelManager singleInstance = new ChannelManager();

    // ganjun test
    private WorkStealingEnabledChannel<TestJob> testChannel;
    // ganjun textRank
    private WorkStealingEnabledChannel<TextRankJob> textRankChannel;
    // ganjun nameRecognition
    private WorkStealingEnabledChannel<NameRecognitionJob> nameRecognitionChannel;
    // ganjun tfidf
    private WorkStealingEnabledChannel<TFIDFJob> tfidfChannel;
    // ganjun topic model
    private WorkStealingEnabledChannel<TopicModelJob> topicModeChannel;

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

    public void setUpdateConsistencyChannel(WorkStealingEnabledChannel<UpdateConsistencyJob> updateconsistencyChannel) {
        this.updateconsistencyChannel = updateconsistencyChannel;
    }

    public void setIntegrityChannel(WorkStealingEnabledChannel<IntegrityJob> integrityChannel) {
        this.integrityChannel = integrityChannel;
    }

    public void setUpdateIntegrityChannel(WorkStealingEnabledChannel<UpdateIntegrityJob> updateIntegrityChannel) {
        this.updateIntegrityChannel = updateIntegrityChannel;
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

    public WorkStealingEnabledChannel<UpdateConsistencyJob> getUpdateConsistencyChannel() {
        return updateconsistencyChannel;
    }

    public WorkStealingEnabledChannel<IntegrityJob> getIntegrityChannel() {
        return integrityChannel;
    }

    public WorkStealingEnabledChannel<UpdateIntegrityJob> getUpdateIntegrityChannel() {
        return updateIntegrityChannel;
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

    // ganjun add tf idf job

    public WorkStealingEnabledChannel<TFIDFJob> getTfidfChannel() {
        return tfidfChannel;
    }

    public void setTfidfChannel(WorkStealingEnabledChannel<TFIDFJob> tfidfChannel) {
        this.tfidfChannel = tfidfChannel;
    }
    // ganjun add topic model

    public WorkStealingEnabledChannel<TopicModelJob> getTopicModeChannel() {
        return topicModeChannel;
    }

    public void setTopicModeChannel(WorkStealingEnabledChannel<TopicModelJob> topicModeChannel) {
        this.topicModeChannel = topicModeChannel;
    }
}
