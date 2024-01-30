package utils;

import java.util.ArrayList;

import exceptions.ConvoBotException;
import tasks.Task;

/**
 * The {@code TaskList} class represents a collection of tasks and provides methods for managing the task list.
 * It includes operations such as adding tasks, marking tasks as done or not done, retrieving task details,
 * deleting tasks, and obtaining the size of the task list.
 */
public class TaskList {

    private ArrayList<Task> taskList;
    private Storage db;

    /**
     * Constructs a new {@code TaskList} instance with an empty task list.
     */
    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Constructs a new {@code TaskList} instance and initializes it with tasks read from the specified storage.
     *
     * @param db the storage instance used to read tasks
     */
    public TaskList(Storage db) {
        this.db = db;
        taskList = db.read();
    }

    /**
     * Adds a task to the task list and updates the storage.
     *
     * @param task the task to be added to the task list
     */
    public void add(Task task) {
        taskList.add(task);
        db.write(taskList);
    }

    /**
     * Marks a task in the task list as done or not done based on the specified index.
     *
     * @param i      the index of the task in the task list
     * @param isDone {@code true} if the task should be marked as done, {@code false} if marked as not done
     * @throws ConvoBotException if the index is out of bounds
     */
    public void mark(int i, boolean isDone) throws ConvoBotException {
        if (i < 0 || i >= taskList.size()) {
            throw new ConvoBotException("Invalid input. You must specify a valid index.");
        }
        Task task = taskList.get(i);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    /**
     * Retrieves the string representation of a task in the task list based on the specified index.
     *
     * @param i the index of the task in the task list
     * @return the string representation of the specified task
     * @throws ConvoBotException if the index is out of bounds
     */
    public String getTaskString(int i) throws ConvoBotException {
        if (i < 0 || i >= taskList.size()) {
            throw new ConvoBotException("Invalid input. You must specify a valid index.");
        }
        return taskList.get(i).toString();
    }

    /**
     * Deletes a task from the task list based on the specified index and updates the storage.
     *
     * @param i the index of the task in the task list
     * @throws ConvoBotException if the index is out of bounds
     */
    public void delete(int i) throws ConvoBotException {
        if (i < 0 || i >= taskList.size()) {
            throw new ConvoBotException("Invalid input. You must specify a valid index.");
        }
        taskList.remove(i);
        db.write(taskList);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return the size of the task list
     */
    public int size() {
        return taskList.size();
    }
}
