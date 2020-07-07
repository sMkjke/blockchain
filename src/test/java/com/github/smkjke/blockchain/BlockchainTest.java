package com.github.smkjke.blockchain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class BlockchainTest {


    @Test
    public void duplicateValidAddBlockTest() {
        Blockchain blockchain = new Blockchain(0);
        // todo hardcode here bloch with params which generates valid block for 0 or whatsoever complexity
        Block firstBlock = new Block("0", 0, 0, List.of(new Message(new User("usr1"), "text", 0l)));
        firstBlock.setTimeStampFinish(Long.MAX_VALUE);

        blockchain.addBlock(firstBlock);

        Block secondBlock = new Block(firstBlock.getHash(), 0, 0, List.of(new Message(new User("usr1"), "text", 0l)));
        secondBlock.setTimeStampFinish(Long.MAX_VALUE);
        blockchain.addBlock(secondBlock);

        Assert.assertEquals(1, blockchain.getBlockCount());
        Assert.assertEquals(0, blockchain.getComplexity());
    }

    @Test
    public void InvalidAddBlockTest() {
        Blockchain blockchain = new Blockchain(0);
        Block block = new Block("0", 0, 0, List.of(new Message(new User("usr1"), "text", 0l)));
        blockchain.addBlock(block);
        Block secondBlock = new Block("0", 0, 0, Collections.emptyList());
        blockchain.addBlock(secondBlock);

        Assert.assertEquals(1, blockchain.getBlockCount());
    }

    @Test
    public void invalidPrevHashAddBlockTest() {
        Blockchain blockchain = new Blockchain(0);
        Block block = new Block("12131212121212", 0, 0, List.of(new Message(new User("usr1"), "text", 0l)));
        block.setTimeStampFinish(System.currentTimeMillis());
        blockchain.addBlock(block);

        Assert.assertEquals(0, blockchain.getBlockCount());
    }

    @Test
    public void emptyAddBlockTest() {
        Blockchain blockchain = new Blockchain(0);
        Miner miner = new Miner(blockchain, 0);
        Block block = miner.mineBlock(Collections.emptyList());
        blockchain.addBlock(block);

        Assert.assertEquals(0, blockchain.getBlockCount());
    }

    @Test
    public void AddBlockTest() {
        Blockchain blockchain = new Blockchain(0);
        Block block = new Block("0", 0, 0, List.of(new Message(new User("usr1"), "text", 0l)));
        block.setTimeStampFinish(Long.MAX_VALUE);
        blockchain.addBlock(block);
        Assert.assertEquals(1, blockchain.getBlockCount());
    }
}
