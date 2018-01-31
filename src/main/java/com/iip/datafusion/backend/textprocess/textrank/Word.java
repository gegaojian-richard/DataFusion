package com.iip.datafusion.backend.textprocess.textrank;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午7:03
 */
public class Word implements Comparable{
    private double weight;
    private int id;     // 根据这个 id 可以在TextGraph中找到对应的单词
    private String word;
    Word(double weight , int id , String word){
        this.weight = weight;
        this.id = id;
        this.word = word;
    }

    @Override
    public int compareTo(Object o){
        double del = ((Word)o).weight - weight;
        if(del < 0) return  -1;
        else if(del == 0.0) return 0;
        else return 1;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
