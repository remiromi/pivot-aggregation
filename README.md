# Pivot Library

## Abstract
This is a Java Library developed as exercise. The following document contains a description of the exposed functionality,
the installation procedure and a technical introduction. 

## Introduction
The library builds a PivotTree, built on the three input parameters: data rows, an aggregation function, an 
aggregation order. The 
## Exposed Function

### Function Signature Explanation
### Assumptions on the input
- The row is a generic tuple
- Elements search is assumed to be queried only in order: e.g. Nation -> Hair Color -> Eyes Color
 -> implica che io possa cercare solo in quest'ordine (non Capelli senza Nazione),
  perché se voglio un altro ordine ricostruisco l'albero -> TODO Controlla aggiunta foglia duplicata
  
### Output Description

## Technical Documentation

### pivot.PivotNode
### pivot.PivotTree
### pivot.PivotTree
### pivot.PivotTreeBuilder


## Install Procedure
maven clean install -> pivot Snapshot


#### TODOs

    // TODO : Generalize and introduce aggregation order -> refactor Row with order and then use addBranch
// TODO : addRow nel builder e non nel tree

## Possible improvements
- Add Execution log file
