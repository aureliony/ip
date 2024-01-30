package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import exceptions.ConvoBotException;
import tasks.Task;

class TaskListTest {

    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Mockito.mock(Storage.class);
        taskList = new TaskList(storage);
    }

    @Test
    void addTask_sampleTask_singleTaskAdded() {
        Task task = new Task("Sample Task");
        taskList.add(task);
        assertEquals(1, taskList.size());
    }

    @Test
    void markTaskAsDone_sampleTask_taskMarkedAsDone() throws ConvoBotException {
        Task task = new Task("Sample Task");
        taskList.add(task);
        taskList.mark(0, true);
        assertTrue(task.getIsDone());
    }

    @Test
    void getTaskString_validTaskIndex_taskStringReturned() throws ConvoBotException {
        Task task = new Task("Sample Task");
        taskList.add(task);
        String taskString = taskList.getTaskString(0);
        assertEquals("[ ] Sample Task", taskString);
    }

    @Test
    void deleteTask_validTaskIndex_taskDeleted() throws ConvoBotException {
        Task task = new Task("Sample Task");
        taskList.add(task);
        taskList.delete(0);
        assertEquals(0, taskList.size());
    }

    @Test
    void markTaskAsDone_invalidIndex_exceptionThrown() {
        assertThrows(ConvoBotException.class, () -> taskList.mark(-1, true));
        assertThrows(ConvoBotException.class, () -> taskList.mark(10, true));
    }

    @Test
    void getTaskString_invalidIndex_exceptionThrown() {
        assertThrows(ConvoBotException.class, () -> taskList.getTaskString(-1));
        assertThrows(ConvoBotException.class, () -> taskList.getTaskString(10));
    }

    @Test
    void deleteTask_invalidIndex_exceptionThrown() {
        assertThrows(ConvoBotException.class, () -> taskList.delete(-1));
        assertThrows(ConvoBotException.class, () -> taskList.delete(10));
    }
}
