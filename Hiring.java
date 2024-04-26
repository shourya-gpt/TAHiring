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

/**
   * A simple program for solving various hiring problems.
   */
public class Hiring {

  /**
   * Given a set of `candidates` that we can hire, a list of candidates we've already hired, and a
   *  maximum number of tas to hire, return the set of hires made using a greedy strategy that 
   *  always chooses the candidate that increases hours covered the most. In this function, 
   *  we will ignore pay rates.
   * 
   * @param candidates - the set of available candidates to hire from 
   *  (excluding those already hired)
   * @param hired - the list of those currently hired
   * @param hiresLeft - the maximum number of candidates to hire
   * 
   * @return a list of hired candidates
   */
  static CandidateList greedyHiring(CandidateList candidates, CandidateList hired, int hiresleft) {

    // Base case: Soultion with no hires, returns hired
    if (hiresleft == 0 || candidates.size() == 0) {
      return hired;
    }
    // base case 2, no solution, returns null
    if (hiresleft < 0) {
      return null;
    }

    // decrementing counter
    hiresleft--;

    // finding which candidate adds the most number of hours
    Candidate toHire = null;
    int currHours = hired.numCoveredHours();
    int maxIncrease = 0;
    for (int i = 0; i < candidates.size(); i++) {
      int currIncrease = hired.withCandidate(candidates.get(i)).numCoveredHours() - currHours;
      if (currIncrease >= maxIncrease) {
        maxIncrease = currIncrease;
        toHire = candidates.get(i);
      }
    }
    if (toHire != null) {
      // hires selected candidate and calls method recursively on remaining candidates
      hired.add(toHire);
      candidates = candidates.withoutCandidate(toHire);
      hired = greedyHiring(candidates, hired, hiresleft);
    }
    return hired;
  }

  /**
   * Given a set of `candidates` that we can hire, a list of candidates we've already hired, and a 
   *  maximum number of tas to hire, return the set of hires that maximizes number of scheduled 
   *  hours. In this function, we will ignore pay rates.
   * 
   * @param candidates - the set of available candidates to hire from (excluding those already 
   *  hired)
   * @param hired - the list of those currently hired
   * @param hiresLeft - the maximum number of candidates to hire
   * 
   * @return a list of hired candidates
   */
  public static CandidateList optimalHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {

    // base cases
    if (hiresLeft == 0) {
      return hired;
    }
    if (hiresLeft < 0) {
      return null;
    }

    // decrementing counter
    hiresLeft--;

    // finding which candidate is most optimal by exploring all possibilites
    Candidate toHire = null; // selected candidate
    int maxHoursCovered = 0;
    for (int i = 0; i < candidates.size(); i++) {
      // select one to (potentially) hire and go down that recursive trail
      Candidate currHHire = candidates.get(i);
      CandidateList newHHired = hired.withCandidate(currHHire);
      CandidateList newHCandidates = candidates.withoutCandidate(currHHire);
      int hoursCovered = optimalHiring(newHCandidates, newHHired, hiresLeft).numCoveredHours();

      // if chosing this candidate gets us more hours (considering future steps as well) 
      // then we select this candidate
      if (hoursCovered >= maxHoursCovered) {
        maxHoursCovered = hoursCovered;
        toHire = currHHire;
      }
    }

    if (toHire != null) {
      // hires selected candidate and calls method on remaining candidates
      hired.add(toHire);
      CandidateList newCandidates = candidates.withoutCandidate(toHire);
      optimalHiring(newCandidates, hired, hiresLeft);
    }
    return hired;
  }

  /**
   * Knapsack dual problem: find the minimum-budget set of hires to achieve a threshold number of 
   *  hours. That is, given a set of candidates, a set of already-hired candidates, and a minimum 
   *  number of hours we want covered, what is the cheapest set of candidates we can hire that 
   *  scover at least that minimum number of hours specified.
   * 
   * @param candidates - the set of available candidates to hire from (excluding those already hired)
   * @param hired - the set of candidates already hired
   * @param minHours - the minimum number of hours we want to cover total
   * 
   * @return a list of hired candidates or null if no set of candidates achieves the requested 
   * number of hours
   */
  public static CandidateList minCoverageHiring(CandidateList candidates, CandidateList hired,
      int minHours) {

    // base cases
    if (minHours <= 0) {
      return hired;
    }
    if (hired == null) {
      return hired;
    }

    // finding optimal candidate to hire by finding cheapest among all recursive paths
    Candidate toHire = null;
    int minPathCost = Integer.MAX_VALUE;
    for (int i = 0; i < candidates.size(); i++) {
      Candidate currHHire = candidates.get(i);
      CandidateList newHHired = hired.withCandidate(currHHire);
      CandidateList newHCandidates = candidates.withoutCandidate(currHHire);
      int hoursAdded = newHHired.numCoveredHours() - hired.numCoveredHours();
      int newHMinHours = minHours - hoursAdded;
      int currPathCost = Integer.MAX_VALUE;

      CandidateList Hhire = minCoverageHiring(newHCandidates, newHHired, newHMinHours);

      // Makes sure that there is at least one possibility
      if (Hhire != null) {
        currPathCost = Hhire.totalCost();

        // Checks to see if selecting this candidate allows minimum hours to be met
        if (Hhire.numCoveredHours() < minHours) {
          continue;
        }
      } 
      else {
        continue;
      }

      // Selects candidate if path cost is less than the current minimum
      if (minPathCost > currPathCost) {
        minPathCost = currPathCost;
        toHire = currHHire;
      }
    }

    // hires the selected candidate and calls method recursively in the next layer
    if (toHire != null) {
      int currHours = hired.numCoveredHours();
      hired.add(toHire);
      int hoursAdded = hired.numCoveredHours() - currHours;
      CandidateList newCandidates = candidates.withoutCandidate(toHire);
      int newMinHours = minHours - hoursAdded;
      minCoverageHiring(newCandidates, hired, newMinHours);
    } 
    else { // Sets hired ArrayList to null if there is no candidate that can meet min hours
      hired = null;
    }
    return hired;
  }
}
