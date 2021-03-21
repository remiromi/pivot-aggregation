# Pivot Library

## Abstract
This is a Java Library developed as exercise. The following document contains a description of the exposed functionality,
the installation procedure and a technical introduction with an example. 

## Introduction
The library builds a PivotTree, built on the three input parameters: data rows, an aggregation function, an 
aggregation order. The output is a tree that can be queried to find out the aggregation at any level (any tree node).

## Exposed Functions
#### PivotTreeBuilder.build()
There are two similar build functions: they both initialize, build and fill an aggregation tree according to the 
provided input. The only difference concerns the aggregation order parameter: in the
`build(List<PivotRow<LabelType, ValueType>> rows, Function<List<ValueType>, ValueType> aggregationFunction)`
method the rows input are assumed to be ordered. In the other _**build()**_ method, there is also the reordering part that 
is applied.

#### QueryableTree.findValue(_List<LabelType> labels_)
Takes as input the labels to find and returns the requested aggregation value.
If the queried label does not exist, it throws a LabelNotFoundException, when there is no label match.

#### QueryableTree.getTotal()
Returns the total aggregation value. The same logic of the findValue method, but with an empty Labels value.
This logic was implemented to return the Tree root value as base command.

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

### Assumptions on the input
- All rows in the DATA input are **tuples** with the same size
- The row is a generic tuple where all elements are of the **same type**
- Elements search is assumed to be queried **only in order**: _e.g. Nation -> Hair Color -> Eyes Color_ , meaning that
  different aggregation orders belong to different trees
  
## Usage Example
Once the library is imported in your project, it's possible to build the pivot tree and query it.

**DATA** is a `List<PivotRow<LabelDomain,ValueDomain>` object. If we have String labels and Integers we have values like:
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

### Possible improvements
- Add Execution log file to monitor operations
- Private methods Javadoc Refinement
