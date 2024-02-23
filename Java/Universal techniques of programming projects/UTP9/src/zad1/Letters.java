package zad1;

class Letters {
    private final String letters;
    private final Thread[] threads;

    public Letters(String letters) {
        this.letters = letters;
        this.threads = new Thread[letters.length()];
        initializeThreads();
    }

    private void initializeThreads() {
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            threads[i] = new Thread(() -> {
                while (!Thread.interrupted()) {
                    System.out.print(letter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }, "Thread " + letter);
        }
    }

    public Thread[] getThreads() {
        return threads;
    }

    public void stopThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}