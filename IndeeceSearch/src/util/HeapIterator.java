package util;

import indeece.Indeece;
import indeece.Model.Result;

import java.util.Iterator;

public class HeapIterator<T> implements Iterator<Result> {
	private BinaryHeap bHeap;
	private int K = Indeece.getK();
	
	public HeapIterator(BinaryHeap binaryHeap) {
		bHeap = binaryHeap;
	}

	public boolean hasNext() {
		return ((!bHeap.isEmpty())&&(K>0));
	}

	public Result next() {
		K--;
		return bHeap.deleteMin();
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}
