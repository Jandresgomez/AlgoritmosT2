package uniandes.algorithms.readsanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import htsjdk.samtools.util.RuntimeEOFException;
import ngsep.math.Distribution;
import ngsep.sequences.RawRead;

/**
 * Represents an overlap graph for a set of reads taken from a sequence to assemble
 * @author Jorge Duitama
 *
 */
public class OverlapGraph implements RawReadProcessor {

	private int minOverlap;
	private Map<String,Integer> readCounts = new HashMap<>();
	private Map<String,ArrayList<ReadOverlap>> overlaps = new HashMap<>();
	
	/**
	 * Creates a new overlap graph with the given minimum overlap
	 * @param minOverlap Minimum overlap
	 */
	public OverlapGraph(int minOverlap) {
		this.minOverlap = minOverlap;
	}

	/**
	 * Adds a new read to the overlap graph
	 * @param read object with the new read
	 */
	public void processRead(RawRead read) {
		String seq = read.getSequenceString();
		//TODO: Paso 1. Agregar la secuencia al mapa de conteos si no existe.
		//Si ya existe, solo se le suma 1 a su conteo correspondiente y no se deben ejecutar los pasos 2 y 3 
		Integer count = readCounts.get(seq);
		if(count != null) {
			readCounts.put(seq, count + 1);
			return;
		}
		
		readCounts.put(seq, 1);
		
		//TODO: Paso 2. Actualizar el mapa de sobrelapes con los sobrelapes en los que la secuencia nueva sea predecesora de una secuencia existente
		//2.1 Crear un ArrayList para guardar las secuencias que tengan como prefijo un sufijo de la nueva secuencia
		//2.2 Recorrer las secuencias existentes para llenar este ArrayList creando los nuevos sobrelapes que se encuentren.
		//2.3 Después del recorrido para llenar la lista, agregar la nueva secuencia con su lista de sucesores al mapa de sobrelapes 
		ArrayList<String> keys = new ArrayList<>(readCounts.keySet());
		ArrayList<ReadOverlap> matches = new ArrayList<ReadOverlap>();
		
		for(String key : keys) {
			int ovlpLength = getOverlapLength(seq, key);
			if(ovlpLength >= minOverlap) {
				matches.add(new ReadOverlap(seq, key, ovlpLength));
			}
		}
		
		overlaps.put(seq, matches);
		
		//TODO: Paso 3. Actualizar el mapa de sobrelapes con los sobrelapes en los que la secuencia nueva sea sucesora de una secuencia existente
		// Recorrer el mapa de sobrelapes. Para cada secuencia existente que tenga como sufijo un prefijo de la nueva secuencia
		//se agrega un nuevo sobrelape a la lista de sobrelapes de la secuencia existente
		
		
	}
	/**
	 * Returns the length of the maximum overlap between a suffix of sequence 1 and a prefix of sequence 2
	 * @param sequence1 Sequence to evaluate suffixes
	 * @param sequence2 Sequence to evaluate prefixes
	 * @return int Maximum overlap between a prefix of sequence2 and a suffix of sequence 1
	 */
	private int getOverlapLength(String sequence1, String sequence2) {
		int lengthMaxOverlap = Math.min(sequence1.length() - 1, sequence2.length() - 1);
		int bestLength = 0;
		
		for(int i = 0; i <= lengthMaxOverlap; i++) {
			String prefix = sequence2.substring(0, i + 1);
			String suffix = sequence1.substring(sequence1.length() - 1 - i, sequence1.length());
			
			if(prefix.equals(suffix)) {
				bestLength = i + 1;
			}
		}
		
		return bestLength;
	}

	

	/**
	 * Returns a set of the sequences that have been added to this graph 
	 * @return Set<String> of the different sequences
	 */
	public Set<String> getDistinctSequences() {
		return readCounts.keySet();
	}

	/**
	 * Calculates the abundance of the given sequence
	 * @param sequence to search
	 * @return int Times that the given sequence has been added to this graph
	 */
	public int getSequenceAbundance(String sequence) {
		Integer count = readCounts.get(sequence);
		if(count == null) return 0;
		return count;
	}
	
	/**
	 * Calculates the distribution of abundances
	 * @return int [] array where the indexes are abundances and the values are the number of sequences
	 * observed as many times as the corresponding array index. Position zero should be equal to zero
	 */
	public int[] calculateAbundancesDistribution() {
		//TODO: Implementar metodo
		return null;
	}
	/**
	 * Calculates the distribution of number of successors
	 * @return int [] array where the indexes are number of successors and the values are the number of 
	 * sequences having as many successors as the corresponding array index.
	 */
	public int[] calculateOverlapDistribution() {
		// TODO: Implementar metodo
		return null;
	}
	/**
	 * Predicts the leftmost sequence of the final assembly for this overlap graph
	 * @return String Source sequence for the layout path that will be the left most subsequence in the assembly
	 */
	public String getSourceSequence () {
		// TODO Implementar metodo recorriendo las secuencias existentes y buscando una secuencia que no tenga predecesores
		return null;
	}
	
	/**
	 * Calculates a layout path for this overlap graph
	 * @return ArrayList<ReadOverlap> List of adjacent overlaps. The destination sequence of the overlap in 
	 * position i must be the source sequence of the overlap in position i+1. 
	 */
	public ArrayList<ReadOverlap> getLayoutPath() {
		ArrayList<ReadOverlap> layout = new ArrayList<>();
		HashSet<String> visitedSequences = new HashSet<>(); 
		// TODO Implementar metodo. Comenzar por la secuencia fuente que calcula el método anterior
		// Luego, hacer un ciclo en el que en cada paso se busca la secuencia no visitada que tenga mayor sobrelape con la secuencia actual.
		// Agregar el sobrelape a la lista de respuesta y la secuencia destino al conjunto de secuencias visitadas. Parar cuando no se encuentre una secuencia nueva
		
		return layout;
	}
	/**
	 * Predicts an assembly consistent with this overlap graph
	 * @return String assembly explaining the reads and the overlaps in this graph
	 */
	public String getAssembly () {
		ArrayList<ReadOverlap> layout = getLayoutPath();
		StringBuilder assembly = new StringBuilder();
		// TODO Recorrer el layout y ensamblar la secuencia agregando al objeto assembly las bases adicionales que aporta la región de cada secuencia destino que está a la derecha del sobrelape 
		
		return assembly.toString();
	}

	public static void main(String[] args) {
		String s1 = "mafafa";
		String s2 = "fafamilia";
		
		OverlapGraph og = new OverlapGraph(1);
		System.out.println(og.getOverlapLength(s1, s2));
	}
}
