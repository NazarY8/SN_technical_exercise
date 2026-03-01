# Minimum Triangle Path

Computes the minimal path from top to bottom by processing the triangle bottom-up.
Each node stores the minimal sum to the bottom and the chosen child index.

## Input Format

- The first row contains 1 number
- Row `i` contains exactly `i` numbers
- Numbers must be separated by whitespace

## Prerequisites

- Java 11+ (tested with Java 22)
- sbt 1.x

Scala 2.13 is managed by sbt automatically.

## How to Run

The program reads input from stdin and writes the result to stdout.

```bash
git clone <repository-url>
cd SN_technical_exercise
```

### Custom triangle (example from the exercise)

```bash
cat << EOF | sbt run
7
6 3
3 8 5
11 2 10 9
EOF
```

Output: `Minimal path is: 7 + 6 + 3 + 2 = 18`

### Small dataset

```bash
cat src/main/resources/datasets/data_small.txt | sbt run
```

### Big dataset

```bash
cat src/main/resources/datasets/data_big.txt | sbt run
```

## How to Run Tests

```bash
sbt test
```

## Performance

Time-based assertions are intentionally not included in the test suite to avoid
flaky results across different environments.

Note that the `sbt run` summary (e.g. `Total time: 0 s`) is not a reliable
measurement of the algorithm runtime, as it includes sbt overhead and rounds
execution time.

To measure performance more precisely, a dedicated runner is provided.
It measures parsing time and algorithm time separately, excluding file I/O.

```bash
sbt "runMain PerformanceCheck"
```
