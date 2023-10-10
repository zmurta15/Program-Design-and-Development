/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genethoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 *
 * @author nickdalton
 */
public class StudentSimulation extends GenHocDNADatabase {

    /**
     * Over ride methods of GenHocDNADatabase to speed them up.
     */
    static class StudentDNASqeuence extends DNASequence {
        
        //Jose Murta
        //w21032320
        private Map<String, String> mapScenes = null;
        
        //Jose Murta
        //w21032320
        public StudentDNASqeuence(String dnaSequence, String sampleID) {
            super(dnaSequence, sampleID);
            mapScenes = new HashMap<>();
            this.crimeScenes = new ArrayList<String>();
        }
        
        //Jose Murta
        //w21032320
        @Override
        public void addCrimeScene(String whereID) {
            if (mapScenes.get(whereID) != null) {
                return;
            }
            mapScenes.put(whereID, whereID);
            crimeScenes.add(whereID);
        }
        
        //Jose Murta
        //w21032320
        @Override
        public boolean linkedToCrimeScene(String whereID) {
            assert mapScenes != null;
            boolean result = false;
            if (mapScenes.get(whereID) != null) {
                result = true;
            }
            return result;
        }
        
    }

    //=================== END OF CLASS ===========================
    
    //Jose Murta
    //w21032320
    Map<String, DNASequence> db = new HashMap<>();
    
    //Jose Murta
    //w21032320
    @Override
    public DNASequence makeRandomDNASequence(int maxCrimeScens) {

        DNASequence it = new StudentDNASqeuence(
                DNASequence.generateRandomDNA(this.dice), ""
                + (++GenHocDNADatabase.idCode));
        DNASequence.addRandomCrimeLocations(it, this.dice, maxCrimeScens);
        return it;

    }
    
    //Jose Murta
    //w21032320
    @Override
    public void addDNASequence(DNASequence it) {
        assert it != null;
        database.add(it);
        db.put(it.getDnaSequence(), it);
    }
    
    //Jose Murta
    //w21032320
    @Override
    public boolean searchForDNA(String dna) {
        boolean result = false;
        if (db.get(dna) != null) {
            result = true;
        }
        return result;
        //return db.containsKey(dna);
    }
    
    //Jose Murta
    //w21032320
    @Override
    public boolean lookForPositiveDNASeq(int howMany) {
        assert howMany > 0;
        boolean result = true;
        // do not change 50000  Dr Andrew Chemist Chief technology officer GenetHoc Ltd.
        for (int a = 0; a < howMany; a++) {
            DNASequence existing = pickRandomSeqence(dice);
            String existString = existing.getDnaSequence();

            if (searchForDNA(existString) == false) {
                System.out.println("You made a mistake - could not find DNA which existed  ");
                result = false;
                return result;
            }
        }
        return result;
    }
    
    //Jose Murta
    //w21032320
    @Override
    public boolean lookForNegativeDNASeq(int howMany) {
        assert howMany > 0;
        boolean result = true;
        // do not change 50000  Dr Andrew Chemist Chief technology officer GenetHoc Ltd.
        for (int a = 0; a < howMany; a++) {
            if (searchForDNA("Dr Andrew Chemist Chief technology officer GenetHoc") == true) {
                System.out.println("You made a mistake - could not find DNA which existed  ");
                result = false;
                return result;
            }
        }
        return result;
    }
    
    //Jose Murta
    //w21032320
    @Override
    public int howManySequencesForCrimeScene(String crimeSceneCode) {
        assert crimeSceneCode != null;
        int howMany = 0;
        for (DNASequence seq : database) {
            if (seq.linkedToCrimeScene(crimeSceneCode)) {
                howMany++;
            }
            if (howMany >= 1) {
                return howMany;
            }
        }

        return howMany;
    }
    
    //Jose Murta
    //w21032320
    @Override
    public void readyForSelfTest() {
        loadSimulatedDNAFromDatabase();
    }
    
    //Jose Murta
    //w21032320
    @Override
    public void resetDatabase()
    { 
          this.database = new  ArrayList<DNASequence>() ;
          this.db = new HashMap<>();
    }
    
    
    //Jose Murta
    //w21032320
    @Override
    public boolean testLookingForFragementPositive(int kFragementPositiveTests) {
        Random rn = this.dice;
        boolean result = true;

        for (int a = 0; a < kFragementPositiveTests; a++) {
            DNASequence existing = database.get(rn.nextInt(database.size()));

            String existString = existing.getDnaSequence();
            existString = existString.substring(rn.nextInt(existString.length() - 5),
                    existString.length());

            boolean found = false;
            for (DNASequence it : database) {
                if(it.getDnaSequence().contains(existString)) {
                    return true;
                }
            }
            if (!found) {
                System.out.println("You made a mistake - could not find DNA *FRAGMENT* which existed  ");
                result = false;
            }
        }
        return result;
    }
    
    
    //Jose Murta
    //w21032320
    @Override
    public int removeDuplicateDNASequnces() {
        List<DNASequence> duplicates = new LinkedList<DNASequence>();
        Set<DNASequence> setDups = new HashSet<>();
        for(int i = 0; i<database.size(); i++) {
            DNASequence dna = database.get(i);
            if(!setDups.add(dna)) {
                duplicates.add(dna);
            }
        }
        
        for (DNASequence dup : duplicates ) {
            database.remove(dup);
        }
        
        return duplicates.size();
    }
    
    //Jose Murta
    //w21032320
    @Override
    public void testRemoveDuplicateDNASequnces(final int DUPLICATES) {

        int before = database.size();
        assert DUPLICATES < database.size();
        Map<String, DNASequence> duplicates = new HashMap<>();

        for (int a = 0; a < DUPLICATES; a++) {
            DNASequence dup;
            do {
                DNASequence existing = pickRandomSeqence(dice);
                dup = makeDNASequence(existing.getDnaSequence());
            } while (duplicates.containsKey(dup.getDnaSequence()));
            duplicates.put(dup.getDnaSequence(), dup);
            addDNASequence(dup);
        }

        assert database.size() == before + DUPLICATES : "Error making duplicates";

        int removed = removeDuplicateDNASequnces();

        assert removed == DUPLICATES : "Number of duplicates found wrong Removed="
                + removed + " before " + before + " " + DUPLICATES + " "
                + database.size();
        assert database.size() == before;

    }

    /**
     * for assessment purposes don't change
     *
     * @param seed
     */
    public StudentSimulation(long seed) {
        super(seed);
    }

    public static void main(String[] args) {
        StudentSimulation db = new StudentSimulation(System.currentTimeMillis());
        db.everythingWhichShouldBeImproved();
    }

}
