package uniandes.algorithms.readsanalyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ngsep.sequences.RawRead;
/**
 * Stores abundances information on a list of subsequences of a fixed length k (k-mers)
 * @author Jorge Duitama
 */
public class KmersTable implements RawReadProcessor {
	Map<String, Integer> kmerTable = new HashMap<String, Integer>();
	private int kmerSize;

	/**
	 * Creates a new table with the given k-mer size
	 * @param kmerSize length of k-mers stored in this table
	 */
	public KmersTable(int kmerSize) {
		this.kmerSize = kmerSize;
	}

	/**
	 * Identifies k-mers in the given read
	 * @param read object to extract new k-mers
	 */
	public void processRead(RawRead read) {
		String sequence = read.getSequenceString();
		// TODO Implementar metodo. Calcular todos los k-mers del tamanho dado en la constructora y actualizar la abundancia de cada k-mer
		
		for(int i = kmerSize; i <= sequence.length() - kmerSize; i++) {
			String kmer = sequence.substring(i, i + kmerSize);
			Integer count = kmerTable.get(kmer);
			if(count != null) {
				kmerTable.put(kmer, count + 1);
			} else {
				kmerTable.put(kmer, 1);
			}
		}
	}
	
	//   n-4 n-3 n-2 n-1 n 
	
	/**
	 * List with the different k-mers found up to this point
	 * @return Set<String> set of k-mers
	 */
	public Set<String> getDistinctKmers() {
		return kmerTable.keySet();
	}
	
	/**
	 * Calculates the current abundance of the given k-mer 
	 * @param kmer sequence of length k
	 * @return int times that the given k-mer have been extracted from given reads
	 */
	public int getAbundance(String kmer) {
		Integer count = kmerTable.get(kmer);
		if(count == null) return 0;
		return count;
	}
	
	/**
	 * Calculates the distribution of abundances
	 * @return int [] array where the indexes are abundances and the values are the number of k-mers
	 * observed as many times as the corresponding array index. Position zero should be equal to zero
	 */
	public int[] calculateAbundancesDistribution() {
		int biggest = 0;
		for(String key : kmerTable.keySet()) {
			int count = kmerTable.get(key);
			if(biggest < count) biggest = count;
		}
		int[] data = new int[biggest + 1];
		
		for(String key : kmerTable.keySet()) data[kmerTable.get(key)]++;
		
		return data;
	}
}
