# Assumptions

## Task Interpretation

The problem is a classical minimum path sum in a triangle:

- Each step moves from row `r`, index `i` to row `r + 1`, index `i` or `i + 1`
- The path must contain exactly one element from each row

I assume:
- The triangle contains at least one row
- All values fit into a 64-bit signed integer (`Long`)
- There exists exactly one minimal path (as stated in the task)

## After Analyzing the Document, I Came to the Following Conclusions

### What Is the Actual Problem of This Task?

Using the example triangle from the task:

```
       7
      6   3
    3   8   5
  11   2  10   9
```

I start at the top (7), and at each step I can go down-left (same index) or down-right
(index + 1). I need to find the path from top to bottom with the smallest sum.

### The Problem

The number of paths grows very fast.

For 4 rows → 8 paths, and already for 50 rows → the number becomes astronomically large.
That is, if I try to "enumerate all variants", the program will never finish
(The program would either overflow the stack or take an impractically long time due to exponential growth in possible paths).

This is why this naturally becomes a DP problem.

### Why Dynamic Programming (DP)?

DP is used when:
- there are many variants
- but those variants **reuse the same sub-paths**

If we look at node `2` (in the third row):

```
   3   8   5
 11   2  10   9
```

If I arrived at `8`, I look at `2` and `10`.
If I arrived at `3` (to the left), I also look at `2`.

The minimal path from `2` to the bottom is always the same.

There is no point computing it many times.

This is the essence of DP:
**Compute each "subproblem" exactly once.**

### How Does DP Work on This Example?

```
       7
      6   3
    3   8   5
  11   2  10   9
```

In this example I want to find the minimal path from top to bottom, but to decide
where to go from 7 (to 6 or to 3?), I need to know:
- if I go to 6, what is the minimal path from there to the bottom?
- if I go to 3, what is the minimal path from there to the bottom?

That is, first I need to know the "cost to finish" from each node. This is exactly
why I must move **bottom-up**.

**1)** Last row: `11  2  10  9`
This is the base case.
The minimal path from each of them to the bottom is just themselves: `11  2  10  9`

**2)** Move up one row: `3  8  5` - and here I ask the question:
if I'm standing at `3`, what is the minimal path to the bottom?
(`3` can go to `11` or `2`) - the choice is obvious, take `2`.

**3)** For `8`, we can go to `2` or `10` (take `2`), and for `5` we can go to `10`
or `9` (take `9`) - as a result we get a new *collapsed* row: `5  10  14`

**4)** Based on the new collapsed row `5  10  14`, take the second row `6` and `3`:
`6` can go to `5` or `10` (take `5`), and `3` can go to `10` or `14` (take `10`)
- and again we get a new row: `11  13`

**5)** Take the top `7` and the resulting row `11  13`, choose `11` - as a result
the smallest sum on the analyzed example => `7 + 11 = 18` (full minimal path in this example is: 7 → 6 → 3 → 2.)

**The key idea**: we search for a path top-to-bottom, but we compute bottom-to-up,
because it is easier to compute the minimum when asking the right question:
**"If I'm standing here (node) - what is the minimal path to the finish?"**

## Complexity (How Much Time and Memory Does This Take)

Let `n` be the number of rows.

The triangle contains approximately `n² / 2` numbers.

I traverse each number exactly once, computing the minimal sum bottom-up.

Therefore:

- **Time**: O(n²) - grows proportionally to the number of elements
- **Space**: O(n) - I do not store the entire table, only one current row

For 500 rows that is ~125,000 numbers - this is very little for a computer, so the
program should run fast.

## Why I Chose This Approach and These Technologies

I plan to use Scala and the standard library.

The reasons are simple:

- The task is straightforward - read numbers, compute, output the result.
- There is no networking, file streaming, concurrency, or asynchronous work.
- Only math and computation are needed.

I do not plan to use additional libraries (cats, cats-effect, etc.) because they would
provide no benefit here and would only complicate the solution.

The code should be written without mutable variables - everything expressed through
functions (`fold`, `map`, etc.).

## Summary

The problem is that if you try to check all possible paths from top to bottom,
there become too many of them - the program would take an impractical amount of time.

So I compute differently:

I go bottom-up and for each node I ask a simple question:

**"If I'm standing here - what is the minimal path to the bottom?"**

This way I compute each part only once.

As a result, I should get the correct minimal path, and the program should run
without any issues even for 500 rows.
