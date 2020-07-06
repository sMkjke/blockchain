package com.github.smkjke.blockchain;


import java.util.List;

public class Miner implements Runnable {

    private final Blockchain blockchain;
    private final int id;

    public Miner(Blockchain blockchain, int id) {
        this.blockchain = blockchain;
        this.id = id;
    }

    @Override
    public void run() {
        while (blockchain.getBlockCount() <= 5) {
            if (!blockchain.getPendingMessages().isEmpty()) {
                Block block = mineBlock(blockchain.getPendingMessages());
                block.setTimeStampFinish(System.currentTimeMillis());
                blockchain.addBlock(block);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Block mineBlock(List<Message> messages) {
        Block previousBlock = blockchain.getPreviousBlock();
        if (previousBlock == null) {
            return new Block("0", System.currentTimeMillis(), id, messages);
        }
        Block block = new Block(previousBlock.calculateBlockHash(), System.currentTimeMillis(), id, messages);
        String prefixString = new String(new char[blockchain.getComplexity()]).replace('\0', '0');

        while (!block.calculateBlockHash().startsWith(prefixString)) {
            block.nonce++;
        }
        return block;
    }
}
