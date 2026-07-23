# Max Profit Problem
Mr. X has land on Mars (infinite, apparently) and wants to build stuff to make money.
He can build:

- **Theatre** — takes 5 time units, earns $1500 per operational unit
- **Pub** — takes 4 time units, earns $1000 per operational unit  
- **Commercial Park** — takes 10 time units, earns $2000 per operational unit

Only one building can be under construction at a time. After `n` time units, how much can he earn and what's the best mix of buildings?

## How I Solved It

First thought was brute force — try every combination. But that gets messy fast.

Ended up using **recursion with memoization** to find the max earnings, then **backtracking** to trace all the optimal paths. The memo table prunes a lot of dead branches so it's not too slow.

## Running It

```bash
javac MaxProfit.java
java MaxProfit
