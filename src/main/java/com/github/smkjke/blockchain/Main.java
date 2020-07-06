package com.github.smkjke.blockchain;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {

        int complexity = 2;

        final Blockchain blockchainHolder = new Blockchain(complexity);
        final ExecutorService executor = Executors.newFixedThreadPool(4);

        List<List<Message>> chatter = generateMessages();

        for (int i = 0; i < 8 / 2; i++) {
            executor.submit(new Miner(blockchainHolder, i));
        }

        for (int i = 0; i < chatter.size(); i++) {
            blockchainHolder.addPendingMessages(chatter.get(i));
            while (!blockchainHolder.getPendingMessages().isEmpty()) {
                Thread.sleep(3000);
            }
        }

        while (blockchainHolder.getBlockCount() < 5) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < blockchainHolder.getBlockchain().size(); i++) {
            blockchainHolder.getBlockchain().get(i).printInfo(i);
        }
    }

    private static List<List<Message>> generateMessages() {
        User tom = new User("Tomas");
        User misha = new User("Misha");

        return List.of(
                List.of(new Message(tom, "Hey, I'm first!", Utils.INT_SUPPLIER.getAsInt())),
                List.of(new Message(misha, "It's not fair!", Utils.INT_SUPPLIER.getAsInt()),
                        new Message(tom, "You always will be first because it is your blockchain!", Utils.INT_SUPPLIER.getAsInt()),
                        new Message(misha, "Anyway, thank you for this amazing chat.", Utils.INT_SUPPLIER.getAsInt())),
                List.of(new Message(tom, "You're welcome :)", Utils.INT_SUPPLIER.getAsInt()),
                        new Message(tom, "Hey Tom, nice chat", Utils.INT_SUPPLIER.getAsInt())),
                List.of(new Message(misha, "olala", Utils.INT_SUPPLIER.getAsInt())),
                List.of(new Message(tom, "make great", Utils.INT_SUPPLIER.getAsInt())));
    }
}
