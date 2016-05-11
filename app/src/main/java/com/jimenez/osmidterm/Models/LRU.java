package com.jimenez.osmidterm.Models;

/**
 * Created by Shanyl Jimenez on 5/6/2016.
 */
public class LRU {

    private String contains;
    private int FIFO;
    private int used;

    public LRU() {
    }

    public LRU(String contains, int FIFO, int used) {
        this.contains = contains;
        this.FIFO = FIFO;
        this.used = used;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public int getFIFO() {
        return FIFO;
    }

    public void setFIFO(int FIFO) {
        this.FIFO = FIFO;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
