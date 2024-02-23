/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */
package zad2;

public class StringTask implements Runnable {

    public enum TaskState {
        CREATED,
        RUNNING,
        ABORTED,
        READY
    }

    private final String input;
    private final int repetitions;
    private volatile String result;
    private volatile TaskState state;

    public StringTask(String input, int repetitions) {
        this.input = input;
        this.repetitions = repetitions;
        this.state = TaskState.CREATED;
    }

    @Override
    public void run() {
        state = TaskState.RUNNING;
        result = "";
        for (int i = 0; i < repetitions; i++) {
            result += input;
        }
        state = TaskState.READY;
    }

    public String getResult() {
        return result;
    }

    public TaskState getState() {
        return state;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void abort() {
        state = TaskState.ABORTED;
    }

    public boolean isDone() {
        return state == TaskState.READY || state == TaskState.ABORTED;
    }
}
