package org.fnet.countdown;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class WatchableList<E> extends ArrayList<E> {

	public static enum ActionType {
		ADD, REMOVE, SET;
	}

	@FunctionalInterface
	public static interface ElementListener {
		/**
		 * Called after action
		 */
		void onAction(ActionType type, Object e);
	}

	private List<ElementListener> listeners = new ArrayList<>();

	public void addElementListener(ElementListener listener) {
		if (listener == null)
			throw new NullPointerException("listener");
		listeners.add(listener);
	}

	@Override
	public boolean add(E e) {
		boolean success = super.add(e);
		if (success)
			listeners.forEach(el -> el.onAction(ActionType.ADD, e));
		return success;
	}

	@Override
	public void add(int index, E element) {
		super.add(index, element);
		listeners.forEach(el -> el.onAction(ActionType.ADD, element));
	}

	@Override
	public E remove(int index) {
		E removedElement = super.remove(index);
		listeners.forEach(el -> el.onAction(ActionType.REMOVE, removedElement));
		return removedElement;
	}

	@Override
	public boolean remove(Object o) {
		boolean removed = super.remove(o);
		if (removed)
			listeners.forEach(el -> el.onAction(ActionType.ADD, o));
		return removed;
	}

	@Override
	public E set(int index, E element) {
		E before = super.set(index, element);
		listeners.forEach(el -> el.onAction(ActionType.SET, element));
		return before;
	}

}
