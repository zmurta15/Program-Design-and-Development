/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genethoc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nickdalton
 */
public class StudentSimulationTest {
    
    public StudentSimulationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

     //-------------------------------------------------------------------------
    protected long testDNALookups( GenHocDNADatabase testSim )
    { 
        assert testSim != null ; 
        long timesRoundloop = 0 ; 
        
        LocalDateTime thenow = LocalDateTime.now(); 
        LocalDateTime oneSecond = thenow.plusSeconds(1); 
        
        do
        { 
            for( int a = 0 ; a < 5 ; a++)
            { 
                timesRoundloop += 1 ; 
                testSim.testLookingForDNASequence(1) ;
            } 
            thenow = LocalDateTime.now(); 
        }while ( thenow.isBefore(oneSecond  )  ); 
        
        return timesRoundloop ; 
    }
    //-------------------------------------------------------------------------
    protected long testCrimeScneCodes( GenHocDNADatabase testSim )
    { 
        assert testSim != null ; 
        long timesRoundloop = 0 ; 
        
        LocalDateTime thenow = LocalDateTime.now(); 
        LocalDateTime oneSecond = thenow.plusSeconds(1); 
        
        do
        { 
            for( int a = 0 ; a < 5 ; a++)
            { 
                timesRoundloop += 1 ; 
                testSim.testLookingForCrimeSceneCodes(1) ;
            } 
            thenow = LocalDateTime.now(); 
        }while ( thenow.isBefore(oneSecond  )  ); 
       
        return timesRoundloop ; 
    }
    //-------------------------------------------------------------------------
    protected long LookingForFragement( GenHocDNADatabase testSim )
    { 
        assert testSim != null ; 
        long timesRoundloop = 0 ; 
        
        LocalDateTime thenow = LocalDateTime.now(); 
        LocalDateTime oneSecond = thenow.plusSeconds(1); 
        
        do
        { 
            for( int a = 0 ; a < 5 ; a++)
            { 
                timesRoundloop += 1 ; 
                testSim.testLookingForFragement(1) ;
            } 
            thenow = LocalDateTime.now(); 
        }while ( thenow.isBefore(oneSecond  )  ); 
       
        return timesRoundloop ; 
    }
    //-------------------------------------------------------------------------
    protected long testForDuplicates( GenHocDNADatabase testSim )
    { 
        assert testSim != null ; 
        long itemsPrcessed = 0 ; 
        
        Deque<DNASequence> premade = new ArrayDeque<DNASequence>();
        //ArrayList<DNASequence> premade = new ArrayList<DNASequence>( ); 
        final int MAX_PREMADE = 190000;
        for( int a = 0 ; a < MAX_PREMADE; a++ )
        { 
            premade.push( testSim.makeRandomDNASequence(2) ); 
        }
        
        int number = 10 ; 
        int dimension = 200 ;
        
        LocalDateTime thenow = LocalDateTime.now(); 
        LocalDateTime oneSecond = thenow.plusSeconds(7); 
        
        testSim.resetDatabase();
        do  // this is the slow part.
        { 
            for( int a = 0 ; a < dimension ; a++ )
            { 
                assert premade.peek() != null :"**Make constant MAX_PREMADE bigger**" ; 
                testSim.addDNASequence( premade.pop() ); 
            }
  
            testSim.testRemoveDuplicateDNASequnces(number) ;
            number += 20; 
            dimension += 200 ; 
            itemsPrcessed += dimension  ;
            thenow = LocalDateTime.now();
        }while ( thenow.isBefore(oneSecond  )  ); 
        
        return itemsPrcessed  * 1000 ; 
    }
    //------------------------------------------------------------------------
    @Test
    public void testDNASequence() 
    {
        System.out.println("testDNASequence");
        
        StudentSimulation testSim = new StudentSimulation( 0xFACE) ;
        
        String dna = "TTTTAAAAGGGGCCCC";
        DNASequence ds = testSim.makeDNASequence(dna); 
        assert ds.getDnaSequence()!=null ; 
        assertEquals("failure - strings are not equal",dna, ds.getDnaSequence());  
    }
    //------------------------------------------------------------------------
    @Test
    public void makeRandomDNASequence() 
    {
        System.out.println("makeRandomDNASequence");
        
        StudentSimulation testSim = new StudentSimulation( 0xFACE) ;
        DNASequence  it = testSim.makeRandomDNASequence(10 ); 
        assertNotNull( it);   
    }
     //------------------------------------------------------------------------
    @Test
    public void addCrimeScene() 
    {
        System.out.println("addCrimeScene");
        
        StudentSimulation testSim = new StudentSimulation( 0xFACE) ;
        DNASequence  it = testSim.makeRandomDNASequence(10 ); 
        assertNotNull( it);  
        String cs = "CRIMESEENTEST"; 
        assertFalse( it.linkedToCrimeScene(cs)); 
        it.addCrimeScene(cs); 
        assertTrue( it.linkedToCrimeScene(cs)); 
        assertFalse( it.linkedToCrimeScene("NOT_IN_CRIME_SCNE")); 
    }
    //-------------------------------------------------------------------------
      @Test
    public void testContainsFragment() 
    {
        System.out.println("testContainsFragment");
        
        StudentSimulation testSim = new StudentSimulation( 0xFACE) ;
        
        String dna = "TTTTAAAAGGGGCCCC";
        String fragement = "GGGCCCC" ; 
        
        DNASequence ds = testSim.makeDNASequence(dna); 
        assert ds.getDnaSequence()!=null ; 
        assertEquals("failure - strings are not equal",dna, ds.getDnaSequence());
        assertTrue( ds.containsFragement(fragement ) ); 
        assertFalse( ds.containsFragement("NOT_APPEARING" ) ); 
    }
     //-------------------------------------------------------------------------
    /**
     * Test of main method, of class StudentSimulation.
     */
    @Test
    public void testMain() 
    {
        System.out.println("Marking");
     
        System.out.println("******   BASELINE  *********");
        GenHocDNADatabase testSim = new GenHocDNADatabase( 0xFACE) ; 
        testSim.readyForSelfTest();
        long testLooking = testDNALookups( testSim); 
        System.out.println("LOOP TIMES = " + testLooking);
        long testCrimeScenes = testCrimeScneCodes( testSim ); 
        System.out.println("Crime scene search = " + testCrimeScenes );
        long fragmentSearch = LookingForFragement( testSim); 
        System.out.println("fragmentSearch search = " + fragmentSearch );
        
        // Make the size of the system smaller then expand faster. 
        long duplicates = testForDuplicates( testSim); 
        System.out.println("testForDuplicates  = " + duplicates );
        
        System.out.println("------   STUDENT  ------");
                
        testSim = new StudentSimulation( 0xFACE) ; //   
       // testSim = new MasterSolution( 0xFACE) ; //  MasterSolution( 0xFACE) ; 
        
        testSim.readyForSelfTest();
        long testLookingSTUDENT = testDNALookups( testSim); 
        System.out.println("LOOP TIMES STUDENT = " + testLookingSTUDENT);
        long testCrimeScenesSTUDENT = testCrimeScneCodes( testSim ); 
        System.out.println("Crime scene search STUDENT = " + testCrimeScenesSTUDENT );
        long fragmentSearchSTUDENT = LookingForFragement( testSim); 
        System.out.println("fragmentSearch search STUDENT = " + fragmentSearchSTUDENT );
        long duplicatesSTUDENT = testForDuplicates( testSim); 
        System.out.println("testForDuplicates STUDENT = " + duplicatesSTUDENT );
        
        double duplicateWarp = (duplicatesSTUDENT/(double)duplicates);
        if( duplicateWarp < 1.0 )duplicateWarp = 1; 
        
        System.out.printf("DNALOOKUPS WARP  = %.0f %n",  (testLookingSTUDENT/(double)testLooking));
        System.out.printf("Crime scenes WARP = %.0f %n" , (testCrimeScenesSTUDENT/(double)testCrimeScenes));
        System.out.printf("fragment Search WARP =  %.0f %n" , (fragmentSearchSTUDENT/(double)fragmentSearch));
        System.out.printf("Duplicates  WARP =  %.1f %n" , duplicateWarp);
        
        System.out.println("FOR CONVERSION TO MARK USE http://unn-mjtd9.newnumyspace.co.uk/5008/ ");
        if( duplicateWarp > 2 )
        { 
            System.out.println("Well done"); 
        }
        
       // StudentSimulation.main(args);
        // TODO review the generated test code and remove the default call to fail.
    }
   
    
}
