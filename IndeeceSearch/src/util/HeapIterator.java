package util;

import indeece.Model.Result;

import java.util.Iterator;

public class HeapIterator<T> implements Iterator<Result> {
	private BinaryHeap bHeap;

	public HeapIterator(BinaryHeap binaryHeap) {
		bHeap = binaryHeap;
	}

	public boolean hasNext() {
		if(bHeap.isEmpty())
			System.out.println("Empty");
		System.out.println("Size of heap: "+ bHeap.getSize());
		return !bHeap.isEmpty();
	}

	public Result next() {
		return bHeap.deleteMin();
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}
