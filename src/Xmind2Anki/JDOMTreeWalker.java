package Xmind2Anki;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * This class provides an Iterator implementation that performs a 
 * pre-order, depth-first traversal of a JDOM Document or a JDOM subtree.
 * <p>
 * An optional {@link Filter} may be supplied with the constructor. 
 * Every node is visited, but only those nodes accepted by the filter 
 * will be returned.
 * <p>
 * Removing nodes is <strong>not</strong> supported.
 *
 * @author pernorrman@telia.com
 */

public class JDOMTreeWalker implements Iterator {
    
	private Stack _stack = new Stack();

	private Object _next;

	private Filter _filter;



	/**
	 * Create an ElementWalker for a JDOM Element with the
	 * specified filter.
	 */
	public JDOMTreeWalker(Element element, Filter filter) {
		this._filter = filter;
		_stack.push(element.getContent());
		_next = _stack.getNext();
	}

	/**
	 * Create an ElementWalker for a JDOM Element with an empty filter, i.e.
	 * every node will be returned.
	 */
	public JDOMTreeWalker(Element element) {
		this(element, null);
	}

	/**
	 * Create an ElementWalker for a JDOM Document with the
	 * specified filter.
	 */
	public JDOMTreeWalker(Document document, Filter filter) {
		this._filter = filter;
		_stack.push(document.getContent());
		_next = _stack.getNext();
	}

	/**
	 * Create an ElementWalker for a JDOM Document with an empty filter, i.e.
	 * all elements will be returned during iteration.
	 */
	public JDOMTreeWalker(Document document) {
		this(document, null);
	}

	//
	// Iterator implementation
	//

	public boolean hasNext() {
		return _next != null;
	}

	public Object next() {
		if (_next == null) {
			throw new NoSuchElementException();
		}

		Object object = _next;
		_next = _stack.getNext();

		return object;
	}

	public void remove() {
        throw new UnsupportedOperationException("Removal is not supported!");
	}

    /**
     * In order to keep track of the position in a list of child elements,
     * we use a stack of iterators. Each time we descend to the next level,
     * the list of child elements is pushed on to the stack and we iterate
     * on that list. When the list is exhausted, we pop up and resume iteration
     * from where we left.
     */
	private class Stack extends LinkedList {
		Iterator iter;

		public void push(List list) {
			add(0, iter = list.iterator());
		}

		private void pop2() {
			if (size() > 0) {
				this.remove(0);
			}
			iter = size() > 0 ? (Iterator) get(0) : null;
		}

		public Object getNext() {
			if (iter == null) {
				return null;
			}

			while (iter.hasNext()) {
				Object node = iter.next();
				if (node instanceof Element) {
					List list = ((Element) node).getContent();
					if (list.size() > 0) {
						push(((Element)node).getContent());
					}
				}
				if (_filter == null || _filter.matches(node)) {
					return node;
				}
			}
			pop2();
			return getNext();
		}
        

	} /* class Stack */

}