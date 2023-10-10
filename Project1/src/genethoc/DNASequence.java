/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genethoc;

import java.time.LocalDateTime;
import java.util.*;


/**
 *
 * @author nickdalton
 */
public class DNASequence 
{
    protected String dnaSequence = null  ; 
    protected String sampleID  = null ; 
    protected LocalDateTime enteredIntoDB = null ; 
    protected String criminalIDConfirmedAs = null ; 
    protected List<String> crimeScenes  = null ; // no duplicates allowed. 

    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    public DNASequence(String dnaSequence, String sampleID) 
    {
        assert isDNA( dnaSequence ) == true ; 
        
        this.dnaSequence = dnaSequence;
        this.sampleID = sampleID;
        this.enteredIntoDB = null ; 
        this.crimeScenes = new LinkedList<String>() ; 
    }
    
    
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    public void addCrimeScene(String whereID ) 
    {
        if( this.crimeScenes.contains( whereID) ) return ; // must be 
        this.crimeScenes.add( whereID );
    }
    /**
     *  By Dr Andrew Chemist Chief technology officer GenetHoc Ltd.
     * @param whereID
     * @return 
     */
    public boolean linkedToCrimeScene( String whereID)
    { 
       assert crimeScenes !=null ; 
        for( String it : crimeScenes ) 
        { 
            if( whereID.equals( it ))return true ; 
        }
        return false ; 
    }
    /**
     *  By Dr Andrew Chemist Chief technology officer GenetHoc Ltd.
     * @param whereID
     * @return 
     */
    public void addCrimeScenseFrom( DNASequence other  )
    { 
        for( String it : other.crimeScenes ) 
        { 
          this.addCrimeScene(it); 
        }
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd.
     *  return any crime scene in the list used for testing. 
     */
    public String getRandomCrimeScene(  Random dice )
    { 
        assert crimeScenes != null ; 
        return crimeScenes.get( dice.nextInt(crimeScenes.size()  )); 
    }
    
    public boolean sameDNASequence( String it ) 
    { 
        return this.dnaSequence.equals( it); 
    }
    public boolean  isDNA(  )
    { 
       return  isDNA(  this.dnaSequence ); 
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.dnaSequence);
        return hash;
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DNASequence other = (DNASequence) obj;
        if (!Objects.equals(this.dnaSequence, other.dnaSequence)) {
            return false;
        }
        return true;
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    
    @Override
    public String toString() {
        return "DNASequence{" + "sampleID=" + sampleID +
                ", enteredIntoDB=" + enteredIntoDB + 
                ", crime location=" + criminalIDConfirmedAs + 
                 '}';
    }
    
    boolean containsFragement( String dnaFragment )
    { 
        return dnaSequence.contains(dnaFragment);
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */

    public String getDnaSequence() {
        return dnaSequence;
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    public void setDnaSequence(String dnaSequence) {
        this.dnaSequence = dnaSequence;
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    public String getSampleID() {
        return sampleID;
    }
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }
/**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     * /
    public LocalDateTime getEnteredIntoDB() {
        return enteredIntoDB;
    }
/**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     * /
    public void setEnteredIntoDB(LocalDateTime enteredIntoDB) {
        this.enteredIntoDB = enteredIntoDB;
    }
/**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    public String getCriminalIDConfirmedAs() {
        return criminalIDConfirmedAs;
    }
/**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    public void setCriminalIDConfirmedAs(String criminalIDConfirmedAs) {
        this.criminalIDConfirmedAs = criminalIDConfirmedAs;
    }
     /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
  
 
    
     /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    static  public  String generateRandomDNA( Random rn)
    { 
        StringBuffer bx = new StringBuffer() ; 
        assert rn != null ; 
       
        char letter[] = { 'A', 'C', 'G' , 'T' } ; 
        
        for( int a = 0 ; a < 10_000 ; a++ )
        { 
           bx.append( letter[ rn.nextInt(letter.length)] ); 
        }
        return bx.toString(); 
    }
    /* 
            internal checking only not production code. 
    */
    public static String randomString( int howLong , Random rn) 
    { 
        assert howLong > 0 :"No negatives"; 
        String a = ""; 
        for( int b = 0 ; b < howLong ; b++  )
        { 
            a += (char)(rn.nextInt('Z'-'A'))+ 'A'; 
        }
        return a; 
    }
   
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    static DNASequence addRandomCrimeLocations( DNASequence it , Random rn )
    { 
        return addRandomCrimeLocations( it , rn, 600 ); 
    }
    static DNASequence addRandomCrimeLocations( DNASequence it , Random rn,
                                                                int maxLocatins )
    { 
        assert maxLocatins > 0 ; 
        int a  = rn.nextInt(maxLocatins)+1 ; 
        while( a-- > 0 )
        { 
            String where = randomString( 10, rn) ; 
            it.addCrimeScene("CRMLOC"+where);
        } 
        return it; 
    }
        
    /**
     *   By Dr Andrew Chemist Chief technology officer GenetHoc Ltd. 
     */
    static boolean isDNA( String it )
    { 
        for(int a = 0 ; a < it.length() ;a++ )
        { 
             if(!( ( it.charAt(a) =='A' )|| 
                   ( it.charAt(a) =='G' ) || 
                   ( it.charAt(a) =='C' )  ||  
                   ( it.charAt(a) =='T' )
                     )) return false ; 
        }
        return true ; 
    } 
}
