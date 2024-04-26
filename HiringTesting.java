//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    TA Hiring
// Course:   CS 300 Fall 2023
//
// Author:   Ritesh Neela
// Email:    rneela@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Shourya Gupta
// Partner Email:   sgupta326@wisc.edu
// Partner Lecturer's Name: Mark Mansi
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X__ Write-up states that pair programming is allowed for this assignment.
//   _X__ We have both read and understand the course Pair Programming Policy.
//   _X__ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements methods that test the functionality of the static methods
 * in the Hiring Class
 */
public class HiringTesting {

  /**
   * Main method that calls all the testers
   * @param args
   */
  public static void main(String[] args) {

    // Calls all the Greedy Hiring tester Methods
    System.out.print("Greedy Hiring Base Test: ");
    System.out.println(greedyHiringBaseTest());
    System.out.print("Greedy Hiring Recursive Test: ");
    System.out.println(greedyHiringRecursiveTest());
    System.out.println();

    //Calls all the Optimal Hiring tester methods
    System.out.print("Optimal Hiring Base Test: ");
    System.out.println(optimalHiringBaseTest());
    System.out.print("Optimal Hiring Recursive Test: ");
    System.out.println(optimalHiringRecursiveTest());
    System.out.print("Optimal Hiring Fuzz Test: ");
    System.out.println(optimalHiringFuzzTest());
    System.out.println();

    // Calls all the Minimum Coverage Hiring tester methods
    System.out.print("Minimum Coverage Hiring Base Test: ");
    System.out.println(minCoverageHiringBaseTest());
    System.out.print("Minimum Coverage Hiring Recursive Test: ");
    System.out.println(minCoverageHiringRecursiveTest());
    System.out.print("Minimum Coverage Hiring Fuzz Test: ");
    System.out.println(minCoverageHiringFuzzTest());

  }

  /**
   * This method tests the base case for the greedyHiring() method
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean greedyHiringBaseTest() {

    // Base case tests 1 and 2
    boolean test1 = false;
    boolean test2 = false;

    /*
     * Base case test 1: Solution with no hires. Initially, hiresLeft = 0
     */

    // new CandidateList with varying hours of availability, total 8 hours possible
    boolean[] avalCan1 = new boolean[] {true, false, true, false, false, true, true, true};
    boolean[] avalCan2 = new boolean[] {false, false, true, true, false, true, false, true};
    boolean[] avalCan3 = new boolean[] {true, true, false, true, false, false, true, false};
    boolean[] avalCan4 = new boolean[] {false, true, false, false, true, true, false, true};

    CandidateList candidateArray = HiringTestingUtilities
        .makeCandidateList(new boolean[][] {avalCan1, avalCan2, avalCan3, avalCan4});

    // Candidates initially hired is 0
    CandidateList initialHires = new CandidateList();

    // expected hires are none as no Candidate gets hired
    CandidateList expectedHires = new CandidateList();

    // actualHires -> CandidateList of hired Candidates when hiresLeft is 0
    CandidateList actualHires = Hiring.greedyHiring(candidateArray, initialHires, 0);

    // checks if Candidates in expectedHires are equal to Candidates in actualHires (0)
    test1 = HiringTestingUtilities.compareCandidateLists(expectedHires, actualHires);

    /*
     * Base case test 2: No Candidate can increase total no. of aval. hours
     * by any amount, but greedyHiring() keeps hiring till hiresLeft = 0
     */

    // new CandidateList with varying hours of availability, total 8 hours possible
    avalCan1 = new boolean[] {true, false, true, false, false, true, true, true};
    avalCan2 = new boolean[] {false, false, true, true, false, true, false, true};
    avalCan3 = new boolean[] {true, true, false, true, false, false, true, false};
    avalCan4 = new boolean[] {false, true, false, false, true, true, false, true};

    candidateArray = HiringTestingUtilities
        .makeCandidateList(new boolean[][] {avalCan1, avalCan2, avalCan3, avalCan4});

    // already hired candidate covers all hoursNeeded, no further candidates should be hired
    initialHires.clear();
    initialHires.add(new Candidate(new boolean[] {true, true, true, true, true, true, true, true}));

    // The last two Candidates i.e. 3 and 4 will get added
    CandidateList expectedHire = new CandidateList();
    expectedHire.add(initialHires.get(0));
    expectedHire.add(candidateArray.get(2));
    expectedHire.add(candidateArray.get(3));
    
    // actualHires -> CandidateList for all Candidates hired when no Candidate increases numHours
    actualHires = Hiring.greedyHiring(candidateArray, initialHires, 2);
    
    // checks if Candidates in expectedHires are equal to Candidates in actualHires
    test2 = HiringTestingUtilities.compareCandidateLists(expectedHire, actualHires);

    return test1 && test2;



  }

  /**
   * This method tests two recursive cases for the greedyHiring() method
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean greedyHiringRecursiveTest() {

    // Recursive tests 1 and 2
    boolean test1 = false;
    boolean test2 = false;

    /*
     * Recursive test 1: Initial hired 0, Candidates at 1 and 3 get hired
     */

    // new CandidateList with varying hours of availability, total 8 hours possible
    boolean[] avalCan1 = new boolean[] {true, false, true, false, false, true, true, true};
    boolean[] avalCan2 = new boolean[] {false, false, true, true, false, true, false, true};
    boolean[] avalCan3 = new boolean[] {true, true, false, true, false, false, true, false};
    boolean[] avalCan4 = new boolean[] {false, true, false, false, true, true, false, true};

    CandidateList candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] 
        {avalCan1, avalCan2, avalCan3, avalCan4});


    // solution with Candidates 1 and 3
    CandidateList soln1 = new CandidateList();
    soln1.add(candidateArray.get(0));
    soln1.add(candidateArray.get(2));

    // solution with Candidates 1 and 4
    CandidateList soln2 = new CandidateList();
    soln2.add(candidateArray.get(0));
    soln2.add(candidateArray.get(3));

    // array list of different possible greedy hire solutions, indifferent solutions
    ArrayList<CandidateList> expectedHires = new ArrayList<CandidateList>();
    expectedHires.add(soln1);
    expectedHires.add(soln2);

    // containts 0 Candidates, none hired for Recursive test 1
    CandidateList initialHires = new CandidateList();

    CandidateList actualHired = Hiring.greedyHiring(candidateArray, initialHires, 2);

    test1 = HiringTestingUtilities.compareCandidateLists(expectedHires, actualHired);


    /*
     * Recursive test 2: Initial hire not 0, Candidates 2 and 3 get hired
     */

    // adds new candidates with varying hours of availability, total 8 hours of possible
    // availability
    avalCan1 = new boolean[] {true, true, false, false, true, true, true, false, true, true};
    avalCan2 = new boolean[] {true, false, false, true, true, true, true, true, false, true};
    avalCan3 = new boolean[] {true, true, true, true, false, false, true, true, true, false};

    candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] {avalCan1,
        avalCan2, avalCan3});



    // containts 0 Candidates, none hired for Recursive test 1
    initialHires = HiringTestingUtilities.makeCandidateList(
        new boolean[][] {{false, true, false, true, false, false, false, false, true, true},
            {true, true, false, true, false, false, true, false, true, true}});

    // solution with Candidates 2 and 3
    soln1 = new CandidateList();
    soln1.addAll(initialHires);
    soln1.add(candidateArray.get(1));
    soln1.add(candidateArray.get(2));

    // solution with Candidates 1 and 2
    soln2 = new CandidateList();
    soln2.addAll(initialHires);
    soln2.add(candidateArray.get(0));
    soln2.add(candidateArray.get(1));
    
    expectedHires.clear();
    expectedHires.add(soln1);
    expectedHires.add(soln2);

    actualHired = Hiring.greedyHiring(candidateArray, initialHires, 2);

    test2 = HiringTestingUtilities.compareCandidateLists(expectedHires, actualHired);

    return test1 && test2;

  }

  /**
   * This method tests the base case for the optimalHiring() method
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean optimalHiringBaseTest() {

    boolean test1 = false;

    /*
     * Base case test : Hires left is 0
     */

    // new CandidateList with varying hours of availability, total 8 hours possible
    boolean[] avalCan1 = new boolean[] {true, false, true, false, false, true, true, true};
    boolean[] avalCan2 = new boolean[] {false, false, true, true, false, true, false, true};
    boolean[] avalCan3 = new boolean[] {true, true, false, true, false, false, true, false};
    boolean[] avalCan4 = new boolean[] {false, true, false, false, true, true, false, true};

    CandidateList candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] 
        {avalCan1, avalCan2, avalCan3, avalCan4});

    // initialHires
    CandidateList initialHires = new CandidateList();

    // expectedHire has no elements as no Candidate gets hired
    CandidateList expectedHire = new CandidateList();

    // actualHires -> CandidateList of hired Candidates when hiresLeft is 0
    CandidateList actualHires = Hiring.optimalHiring(candidateArray, initialHires, 0);

    // checks if Candidates in expectedHire are equal to Candidates in actualHired (0)
    test1 = HiringTestingUtilities.compareCandidateLists(expectedHire, actualHires);

    return test1;
  }

  /**
   * This method tests two recursive cases for the optimalHiring() method
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean optimalHiringRecursiveTest() {

    // Recursive tests 1 and 2

    boolean test1 = false;
    boolean test2 = false;

    /*
     * Recursive test 1: Initial hired 0, Candidates at 1 and 3 or 2 and 3 get hired
     */

    // new CandidateList with varying hours of availability, total 6 hours possible
    boolean[] avalCan1 = new boolean[] {true, false, true, false, false, true};
    boolean[] avalCan2 = new boolean[] {false, false, true, true, false, true};
    boolean[] avalCan3 = new boolean[] {false, true, true, false, true, false};

    CandidateList candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] 
        {avalCan1, avalCan2, avalCan3});


    // solution with Candidates 1 and 3
    CandidateList hireCombination1 = new CandidateList();
    hireCombination1.add(candidateArray.get(0));
    hireCombination1.add(candidateArray.get(2));

    // solution with Candidates 2 and 3
    CandidateList hireCombination2 = new CandidateList();
    hireCombination2.add(candidateArray.get(1));
    hireCombination2.add(candidateArray.get(2));

    ArrayList<CandidateList> expectedCombinations = new ArrayList<CandidateList>();
    expectedCombinations.add(hireCombination1);
    expectedCombinations.add(hireCombination2);

    // containts 0 Candidates, none hired for Recursive test 1
    CandidateList initialHires = new CandidateList();

    CandidateList actualHired = Hiring.optimalHiring(candidateArray, initialHires, 2);

    test1 = HiringTestingUtilities.compareCandidateLists(expectedCombinations, actualHired);

    /*
     * Recursive test 2: Initial hire not 0, Candidates 1 and 2 or 2 and 3 get hired
     */

    // new CandidateList with varying hours of availability, total 10 hours possible
    avalCan1 = new boolean[] {false, true, true, false, true, true, false, true, false, true};
    avalCan2 = new boolean[] {true, false, false, true, false, false, true, false, false, false,};
    avalCan3 = new boolean[] {false, true, false, false, true, true, false, true, false, true};

    candidateArray =
        HiringTestingUtilities.makeCandidateList(new boolean[][] {avalCan1, avalCan2, avalCan3});


    // containts 0 Candidates, none hired for Recursive test 1
    initialHires = new CandidateList();
    initialHires.add(new Candidate(new boolean[] {true, false, true, false, true, false, false, 
        true, false, false}));

    // solution with Candidates 1 and 2
    hireCombination1 = new CandidateList();
    hireCombination1.add(initialHires.get(0));
    hireCombination1.add(candidateArray.get(0));
    hireCombination1.add(candidateArray.get(1));

    // solution with Candidates 2 and 3
    hireCombination2 = new CandidateList();
    hireCombination2.add(initialHires.get(0));
    hireCombination2.add(candidateArray.get(1));
    hireCombination2.add(candidateArray.get(2));

    expectedCombinations = new ArrayList<CandidateList>();
    expectedCombinations.add(hireCombination1);
    expectedCombinations.add(hireCombination2);


    actualHired = Hiring.optimalHiring(candidateArray, initialHires, 2);

    test2 = HiringTestingUtilities.compareCandidateLists(expectedCombinations, actualHired);


    return test1 && test2;


  }

  /**
   * This method tests the optimalHiring() method with a large number of random Candidates
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean optimalHiringFuzzTest() {

    Random randGen = new Random(123);
    int i;

    for (i = 0; i < 150; ++i) {

      // generates total possible number of hours b/w [1,5], inclusive for which any Candidate
      // could be available
      int numHours = randGen.nextInt(5) + 1;
      // generates total possible number of candidates b/w [1,10], inclusive
      int numCan = randGen.nextInt(10) + 1;
      // generates desired number of people to hire b/w [1,numCan], inclusive
      int desiredHires = randGen.nextInt(numCan) + 1;
      
      // creates a new CandidateList filled with Candidates of different, random availabilities
      CandidateList candidateArray = HiringTestingUtilities.generateRandomInput(numHours, numCan);

      CandidateList initialHires = new CandidateList();
      
      // uses the reference implementation in HiringTestingUtilities.java to get all optimal 
      // solutions
      ArrayList<CandidateList> expectedCombinations = HiringTestingUtilities.allOptimalSolutions(
          candidateArray, desiredHires);

      // uses the designed recursive impelmentation in Hiring.java to get actual optimal solution
      CandidateList actualHires = Hiring.optimalHiring(candidateArray, initialHires, desiredHires);


      // checks if actual optimal solution is a subset of the expected optimal solutions
      if (HiringTestingUtilities.compareCandidateLists(expectedCombinations, actualHires))
        continue;
      else {
        return false;
      }


    }

    return true;


  }

  /**
   * This method tests the base cases for the minCoverageHiring() method
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean minCoverageHiringBaseTest() {

    // Base case tests 1 and 2

    boolean test1 = false;
    boolean test2 = false;

    /*
     * Base case test 1: Minimum number of hours is less than or equal to 0
     */

    // new CandidateList with varying hours of availability, total 8 hours possible
    boolean[] avalCan1 = new boolean[] {true, false, true, false, false, true, true, true};
    boolean[] avalCan2 = new boolean[] {false, false, true, true, false, true, false, true};
    boolean[] avalCan3 = new boolean[] {true, true, false, true, false, false, true, false};
    boolean[] avalCan4 = new boolean[] {false, true, false, false, true, true, false, true};

    CandidateList candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] 
        {avalCan1, avalCan2, avalCan3, avalCan4});

    // initialHires
    CandidateList initialHires = new CandidateList();

    // expectedHire remains empty as no Candidate gets hired
    CandidateList expectedHire = new CandidateList();

    // actualHires -> CandidateList of hired Candidates when minimum number of hours is 0
    CandidateList actualHires = Hiring.minCoverageHiring(candidateArray, initialHires, -6);

    // checks if Candidates in initialHires are equal to Candidates in expectedHire (0 added)
    test1 = HiringTestingUtilities.compareCandidateLists(expectedHire, actualHires);


    /*
     * Base case test 2: No solution, minimum number of hours can't be met
     */

    // new CandidateList with varying hours of availability, total 4 hours possible
    avalCan1 = new boolean[] {true, false, false, false};
    avalCan2 = new boolean[] {true, false, false, false};
    avalCan3 = new boolean[] {true, false, false, false};

    candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] {avalCan1, avalCan2,
        avalCan3, avalCan4});

    // already hired candidate covers all hoursNeeded, no further candidates should be hired
    initialHires.add(new Candidate(new boolean[] {true, false, false, false}));

    // expected return value -> null as no solution is possible
    CandidateList expectedHires = null;

    // actualHires -> CandidateList for all Candidates hired if initialHires is provided
    actualHires = Hiring.minCoverageHiring(candidateArray, initialHires, 2);

    // checks if the minCoverageHiring method returns null when no solution possible
    test2 = HiringTestingUtilities.compareCandidateLists(expectedHires, actualHires);

    return test1 && test2;
  }

  /**
   * This method tests recursive case for the minCoverageHiring() method
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean minCoverageHiringRecursiveTest() {
    
    // recursive tests 1 and 2
    boolean t1 = false;
    boolean t2 = false;

    /*
     * Recursive case test 1: 4 Candidates, candidates A and B get hired
     */

    // new CandidateList with varying hours of availability, total 10 hours possible
    boolean[] A = new boolean[] {true, false, true, false, true, false, false, true, false, false};
    boolean[] B = new boolean[] {false, true, true, false, true, true, false, true, false, true};
    boolean[] C = new boolean[] {true, false, false, true, false, false, true, false, false, false};
    boolean[] D = new boolean[] {false, true, false, false, true, true, false, true, false, true};

    // int Array of payrates of Candidate objects
    int[] payrates = new int[] {1, 2, 3, 4};
    int minhours = 7;

    CandidateList candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] {A, B,
        C, D}, payrates);

    // minCoverage solution with Candidates A and B
    CandidateList expectedHires = new CandidateList();
    expectedHires.add(candidateArray.get(0));
    expectedHires.add(candidateArray.get(1));

    CandidateList initialHired = new CandidateList();

    // gets actualHired candidates using recursive implementation, minHours = 7;
    CandidateList actualHired = Hiring.minCoverageHiring(candidateArray, initialHired, minhours);

    // checks if actualHired is the same expectedHires
    t1 = HiringTestingUtilities.compareCandidateLists(expectedHires, actualHired);

    /*
     * Recursive case test 2: 5 Candidates, candidates B and C get hired
     */

    A = new boolean[] {false, false, false, false, false, false, true, false, false, true};
    B = new boolean[] {false, true, true, true, true, true, false, false, true, true};
    C = new boolean[] {true, true, false, false, true, true, true, true, true, false};
    D = new boolean[] {true, false, false, false, true, false, true, false,false, true};
    boolean[] E = new boolean[] {true, true, true, true, false, true, false, true, false, true};


    candidateArray = HiringTestingUtilities.makeCandidateList(new boolean[][] {A, B, C, D, E}, new int[] {6, 2, 6, 5, 3});

    minhours = 10;

    // minCoverage solution yields Candidates B and C
    expectedHires.clear();
    expectedHires.add(candidateArray.get(1));
    expectedHires.add(candidateArray.get(2));

    // initialHired
    initialHired.clear();

    //gets actual hired candidates using recursive imlementation, minHours is 10
    actualHired = Hiring.minCoverageHiring(candidateArray, initialHired, minhours);

    // checks if actualHired is the same expectedHires
    t2 = HiringTestingUtilities.compareCandidateLists(expectedHires, actualHired);

    return t1 && t2;


  }

  /**
   * This method tests the minCoverageHiring() method with a large amount of random Candidates 
   * 
   * @return true if method returns correctly, false if otherwise
   */
  public static boolean minCoverageHiringFuzzTest() {
    Random randGen = new Random(123);
    int i;

    for (i = 0; i < 150; ++i) {


      // generates total possible number of hrs b/w [1,5] inclusive for which any Candidate could
      // be available
      int numHours = randGen.nextInt(5) + 1;
      // generates total possible number of candidates b/w [1,10], inclusive
      int numCan = randGen.nextInt(10) + 1;
      // generates desired minimum no. of hours to hire candidates for b/w [1,numHours], inclusive
      int minHours = randGen.nextInt(numHours) + 1;
      // generates maximum payrate of random list of Candidates, approx. < numCan /2
      int maxPayRate = randGen.nextInt(numCan + 1 / 2) + 1;

      // creates a new CandidateList filled with Candidates of different, random availabilities
      CandidateList candidateArray = HiringTestingUtilities.generateRandomInput(numHours, numCan,
          maxPayRate);

      CandidateList initialHires = new CandidateList();

      // uses reference implementation in HiringTestingUtilities class to get all min coverage solns
      ArrayList<CandidateList> expectedCombinations = HiringTestingUtilities.allMinCoverageSolutions
          (candidateArray, minHours);
      CandidateList actualHires = Hiring.minCoverageHiring(candidateArray, initialHires, minHours);

      if (HiringTestingUtilities.compareCandidateLists(expectedCombinations, actualHires)) {
        continue;
      } else {

        return false;
      }

    }

    return true;

  }
}
