package ru.job4j.search;

import java.util.LinkedList;

/**
 * Очередь с приоритетом
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определять по полю приоритет.
     * Для вставик использовать add(int index, E value)
     * @param task задача
     */
    public void put(Task task) {
        int pos = 0;
        for (Task t : this.tasks) {
            if (task.getPriority() >= t.getPriority()) {
                pos++;
            } else {
                break;
            }
        }
        this.tasks.add(pos, task);
    }

    public Task take() {
        return this.tasks.poll();
    }
}
