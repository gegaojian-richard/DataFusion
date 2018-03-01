package com.iip.datafusion.backend.textprocess.textrank;

/**
 * Created by ganjun on 2018/1/3.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TextRankGraph {
    private int k ;  // 窗口大小
    private HashMap<String , Integer> wordsHash ;
    private int wordId; // 表示当前对不同的单词已经编号到了第几个id
    private List<String> words;

    private List<HashSet<Integer>> edges;  // edges.get(i) 表示第i个点连接的所有点存在这个HashSet中

    public TextRankGraph(){
        k = 3;
        wordId = 0;
        wordsHash = new HashMap<>();
        this.words = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public TextRankGraph(int k){
        this.k = k;
        wordId = 0;
        wordsHash = new HashMap<>();
        this.words = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public TextRankGraph(List<String> wordsList , int k){
        this.k = k;
        this.wordId = 0;
        this.wordsHash = new HashMap<>();
        this.words = new ArrayList<>();
        int n = wordsList.size();
        this.edges = new ArrayList<>();
        for(int i=0 ; i<n ; i++){
            if(wordsHash.containsKey(wordsList.get(i)) == false){
                words.add(wordsList.get(i));
                HashSet<Integer> edge = new HashSet<>();
                edges.add(edge);
                wordsHash.put(wordsList.get(i) , wordId++);
            }
        }

        addEdges(wordsList);
    }

    public boolean addSentence(List<String> wordsList){
        int n = wordsList.size();
        for(int i=0 ; i<n ; i++){
            if(wordsHash.containsKey(wordsList.get(i)) == false){
                words.add(wordsList.get(i));
                HashSet<Integer> edge = new HashSet<>();
                edges.add(edge);
                wordsHash.put(wordsList.get(i) , wordId++);
            }
        }
        addEdges(wordsList);
        return true;
    }

    public void addEdges(List<String> wordsList){
        int n = wordsList.size();
        // 构建边
        for(int i=0 ; i<n ; i++){
            // left
            for(int j=1 ; j<k ; j++){
                int p = i-j;
                if(p<0) break;
                int u = wordsHash.get(wordsList.get(i));
                int v = wordsHash.get(wordsList.get(p));
                edges.get(u).add(v);
            }
            // right
            for(int j=1 ; j<k ; j++){
                int p = i+j;
                if(p>=n) break;
                int u = wordsHash.get(wordsList.get(i));
                int v = wordsHash.get(wordsList.get(p));
                edges.get(u).add(v);
            }
        }
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public HashMap<String, Integer> getWordsHash() {
        return wordsHash;
    }

    public void setWordsHash(HashMap<String, Integer> wordsHash) {
        this.wordsHash = wordsHash;
    }

    public int getWordId() {
        return wordId;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<HashSet<Integer>> getEdges() {
        return edges;
    }

    public void setEdges(List<HashSet<Integer>> edges) {
        this.edges = edges;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
}
