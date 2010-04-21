package util;

import indeece.Model.Result;

import java.util.Iterator;

public class HeapIterator<T> implements Iterator<Result> {
	private BinaryHeap bHeap;

	public HeapIterator(BinaryHeap binaryHeap) {
		bHeap = binaryHeap;
	}

	public boolean hasNext() {
		return !bHeap.isEmpty();
	}

	public Result next() {
		return bHeap.deleteMin();
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}
