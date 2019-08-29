package uniandes.algorithms.readsanalyzer;
/**
 * Represents a suffix/prefix overlap between two sequences
 * @author Jorge Duitama
 *
 */
public class ReadOverlap {
	private String sourceSequence;
	private String destSequence;
	private int overlap;
	
	/**
	 * Creates a new overlap with the given information
	 * @param sourceSequence Sequence with the overlap as a suffix
	 * @param destSequence Sequence with the overlap as a prefix
	 * @param overlap Length of the overlap sequence
	 */
	public ReadOverlap(String sourceSequence, String destSequence, int overlap) {
		this.sourceSequence = sourceSequence;
		this.destSequence = destSequence;
		this.overlap = overlap;
	}
	/**
	 * @return String sequence with the overlap as a suffix
	 */
	public String getSourceSequence() {
		return sourceSequence;
	}
	/**
	 * @return String sequence with the overlap as a prefix
	 */
	public String getDestSequence() {
		return destSequence;
	}
	/**
	 * @return int length of the overlap
	 */
	public int getOverlap() {
		return overlap;
	}
	
}
