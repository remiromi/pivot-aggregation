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
### pivot.PivotTreemvn
### pivot.PivotTreeBuilder


## Install and Import 
To install the library, first clone this repo with:
```bash
git clone https://github.com/remiromi/pivot-aggregation.git
``` 
Then, in the repo directory run the command to build it in local:
```bash
# SET JAVA_HOME to JAVA 11
set JAVA_HOME="<JAVA11 path>"
# Install
mvn clean install
```
After the build is successful, in the `pom.xml` we can add the dependency as follows:
```xml
<dependency>
  <groupId>com.romano</groupId>
  <artifactId>pivot-aggregation</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage Example
Once the library is imported in your project, it's possible to build the pivot tree and query it.

**Input** is a `List<PivotRow<LabelDomain,ValueDomain>` object. If we have String labels and int number we have values like:
```java
List<PivotRow<String,Integer> pivotRows = Arrays.asList(
new PivotRow<>(Arrays.asList("Node D","Node C","Node J","Node I"), 1),
new PivotRow<>(Arrays.asList("Node E","Node C","Node J","Node I"), 2),
new PivotRow<>(Arrays.asList("Node H","Node G","Node B","Node A"), 3),
new PivotRow<>(Arrays.asList("Node Y","Node X","Node B","Node A"), 4),
new PivotRow<>(Arrays.asList("Node H","Node X","Node B","Node A"), 5));
```
The second input argument is an **aggregation function**, meaning any function that takes a collection of numerical 
values as an input and returns a single value:
```java
Function<List<Integer>, Integer> sum = (numbers) -> numbers.stream().reduce(0, Integer::sum);
```
The third input (optional), consists in an aggregation order, which represents the description of 
the aggregation hierarchy:
```java
List<Integer> aggregationOrder = List.of(3, 2, 1, 0);
```

Once all inputs are instantiated, we can build the Pivot tree by using the PivotTreeBuilder as follows:
```java
// Case no specific aggregation order -> rows ordered already
QueryableTree<String, Integer> tree = new PivotTreeBuilder().build(pivotRows, sum);

// Case different aggregation order -> rows to be ordered
QueryableTree<String, Integer> tree = new PivotTreeBuilder().build(pivotRows, sum, aggregationOrder);
```
Now the tree is returned with all nodes set to the appropriate value. We can query it by running:
```java
// Method that returns tree root value (Global Aggregation)
int totalValue = tree.getTotal();

// Labels declaration -> Middle level
List<String> queryLabels = List.of("Node D", "Node C");
int middleValue = tree.findValue(queryLabels);

// Labels declaration -> Last level
List<String> queryLabels = List.of("Node D", "Node C","Node J","Node I");
int lastValue = tree.findValue(queryLabels);
```

## Possible improvements
- Add Execution log file
