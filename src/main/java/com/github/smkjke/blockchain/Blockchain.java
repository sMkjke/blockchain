package com.github.smkjke.blockchain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Blockchain {

    public volatile List<Message> pendingMessages = new LinkedList<>();
    private int complexity;
    private volatile ArrayList<Block> blockchain = new ArrayList<>();

    public Blockchain(int complexity) {
        this.complexity = complexity;
    }

    public synchronized int getComplexity() {
        return complexity;
    }

    public synchronized Block getPreviousBlock() {
        if (blockchain.size() > 0) {
            return blockchain.get(blockchain.size() - 1);
        }
        return null;
    }

    public synchronized int getBlockCount() {
        return blockchain.size();
    }

    public synchronized void addBlock(Block block) {
        if (getPreviousBlock() != null && !getPreviousBlock().getHash().equals(block.getPreviousHash()) ||
                getBlockCount() == 0 && !block.getPreviousHash().equals("0")) {
            return;
        }

        String lastBlockHash = block.getPreviousHash();
        if (lastBlockHash.equals(block.getPreviousHash()) && areBlockMessagesValid(block) && isValid(block)) {
            blockchain.add(block);
            clearMessageList();

            if (((block.getTimeStampFinish() - block.getMiningStartTime()) / 1000) > 5) {
                if (getComplexity() > 0) {
                    complexity--;
                }
                block.setBlockComplexityWhenMine("N was decreased to " + this.complexity);
            } else if (((block.getTimeStampFinish() - block.getMiningStartTime()) / 1000) < 5) {
                complexity++;
                block.setBlockComplexityWhenMine("N was increased to " + this.complexity);
            } else {
                block.setBlockComplexityWhenMine("N stays the same");
            }
        }
    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public synchronized List<Message> getPendingMessages() {
        return this.pendingMessages;
    }

    public synchronized void addPendingMessages(List<Message> messages) {
        this.pendingMessages.addAll(messages);
    }

    private boolean isValid(Block block) {
        return (block.getHash().startsWith(new String(new char[getComplexity()])
                .replace('\0', '0')));
    }

    private synchronized boolean areBlockMessagesValid(Block block) {
        //for testing
        if (block.getBlockMessages().isEmpty()) {
            return false;
        }
        if (!getBlockchain().isEmpty()) {
            for (Message message : block.getBlockMessages()) {
                if (getPreviousBlock().getMaxMsgID() < message.getId()) {
                    block.setMaxMsgID(message.getId());
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    private synchronized void clearMessageList() {
        pendingMessages.clear();
    }
}