# Pivot Library

## Introduction

## Exposed Function

### Function Signature Explanation
### Assumptions on the input
- The row is a generic tuple
- Elements search is assumed to be queried only in order: e.g. Nation -> Hair Color -> Eyes Color
 -> implica che io possa cercare solo in quest'ordine (non Capelli senza Nazione),
  perché se voglio un altro ordine ricostruisco l'albero
  
### Output Description

## Technical Documentation

### pivot.PivotNode
### pivot.PivotTree
### pivot.PivotTree
### pivot.PivotTreeBuilder


#### TODOs

    // TODO : Generalize int to Number
    // TODO : Generalize sum to generic function
    // -> https://www.programiz.com/java-programming/examples/passing-method-as-argument
    // TODO : Generalize and introduce aggregation order -> refactor Row with order and then use addBranch
    // TODO : Create test cases with lines above
    // TODO : find row (e.g. A, B)