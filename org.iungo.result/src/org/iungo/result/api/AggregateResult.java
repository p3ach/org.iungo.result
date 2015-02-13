package org.iungo.result.api;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.iungo.common.api.ConcurrentLinkedList;

/**
 * 
 * Provides a way to aggregate multiple Result objects.
 * 
 * @author dick
 *
 */
public class AggregateResult extends Result {

	private static final long serialVersionUID = 1L;

	protected volatile Boolean aggregateState = null;
	
	protected volatile String aggregateText = null;
	
	protected final List<Result> aggregateValue = new ConcurrentLinkedList<>(); 
	
	public AggregateResult() {
		super(null, null, null);
	}

	public synchronized void add(final Result result) {
		aggregateState = (aggregateState == null ? result.getState() : (result.getState() == null ? null : aggregateState & result.getState()));
		aggregateText = null;
		aggregateValue.add(result);
	}
	
	@Override
	public Boolean getState() {
		return aggregateState;
	}
	
	@Override
	public synchronized String getText() {
		final StringBuilder text = new StringBuilder(aggregateValue.size() * 2<<8);
		final Iterator<Result> iterator = aggregateValue.iterator();
		if (iterator.hasNext()) {
			text.append(String.format("[%s]", iterator.next().getText()));
			while (iterator.hasNext()) {
				text.append(String.format(",[%s]", iterator.next().getText()));
			}
		}
		return text.toString();
	}

	/**
	 * Returns an unmodifiable List<Result>.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue() {
		return (T) Collections.unmodifiableList(aggregateValue);
	}

	/**
	 * Value will never be null...
	 */
	@Override
	public <T> T getValue(final T ifNull) {
		return getValue();
	}
}
