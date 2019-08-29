package uniandes.algorithms.readsanalyzer;

import ngsep.sequences.RawRead;
/**
 * Interface for objects that are able to process raw reads
 * @author Jorge Duitama
 *
 */
public interface RawReadProcessor {
	/**
	 * Process the given read according to the implementation
	 * @param read object that holds the id, the sequence and the base quality of one raw read
	 */
	public void processRead(RawRead read);
}
