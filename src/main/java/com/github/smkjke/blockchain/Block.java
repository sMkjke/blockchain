package com.github.smkjke.blockchain;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

public class Block implements Serializable {

    public int nonce;
    public String blockComplexityWhenMine;
    private long miningStartTime;
    private long timeStampFinish;
    private int minerID;
    private volatile String previousHash;
    private String blockMessage;
    private List<Message> blockMessages;
    private long maxMsgID = 0;

    public Block(String previousHash, long miningStartTime, int minerID, List<Message> blockMessages) {
        this.previousHash = previousHash;
        this.miningStartTime = miningStartTime;
        this.timeStampFinish = getTimeStampFinish();
        this.minerID = minerID;
        this.blockMessages = blockMessages;

        StringJoiner builder = new StringJoiner("\n");
        for (Message i : blockMessages) {
            builder.add(i.getAuthor().getName() + ": " + i.getText());
        }
        blockMessage = builder.toString();
    }

    public String getBlockComplexityWhenMine() {
        return blockComplexityWhenMine;
    }

    public void setBlockComplexityWhenMine(String blockComplexityWhenMine) {
        this.blockComplexityWhenMine = blockComplexityWhenMine;
    }

    public long getTimeStampFinish() {
        return timeStampFinish;
    }

    public void setTimeStampFinish(long timeStampFinish) {
        this.timeStampFinish = timeStampFinish;
    }

    public long getMiningStartTime() {
        return miningStartTime;
    }

    public String calculateBlockHash() {
        return CrypthoUtils.sha256(previousHash
                + miningStartTime
                + nonce
                + blockMessage);
    }

    public synchronized String getHash() {
        return calculateBlockHash();
    }

    public synchronized String getPreviousHash() {
        return this.previousHash;
    }

    public void printInfo(int num) {
        System.out.println();
        System.out.println("Block: ");
        System.out.println("Created by miner # " + minerID);
        System.out.println("Id: " + num);
        System.out.println("Timestamp: " + miningStartTime);
        System.out.println("Magic number: " + nonce);
        System.out.println("Hash of the previous block: ");
        System.out.println(getPreviousHash());
        System.out.println("Hash of the block: ");
        System.out.println(getHash());
        System.out.println("Block data:");
        System.out.println(blockMessage);
        System.out.println("Block was generating for " +
                ((getTimeStampFinish() - getMiningStartTime()) / 1000) + " seconds");
        System.out.println(getBlockComplexityWhenMine());
    }

    public List<Message> getBlockMessages() {
        return this.blockMessages;
    }

    public void setMaxMsgID(long maxMsgID) {
        this.maxMsgID = maxMsgID;
    }

    public synchronized long getMaxMsgID() {
        return this.maxMsgID;
    }

    private synchronized String addAllMessages() {
        StringJoiner builder = new StringJoiner("\n");
        for (Message i : getBlockMessages()) {
            builder.add(i.getAuthor().getName() + ": " + i.getText());
        }
        return builder.toString();
    }
}