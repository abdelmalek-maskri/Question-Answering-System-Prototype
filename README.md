# Question Answering System Prototype

This project implements a prototype Question Answering system that utilizes a Distributional Semantic Model (DSM) file, specifically "glove.6B.50d_Reduced.csv", to infer semantic relationships between words. The system can identify related concepts, provide associations based on context, and find semantically close words using cosine similarity.

## Introduction

The ability to understand word relationships is crucial in natural language processing. This project leverages the GloVe model, which represents words as high-dimensional vectors, allowing the system to compute semantic similarities and identify closest words based on context.

## Features

- Identify semantic relationships (e.g., capitals and countries).
- Provide associations for specific concepts (e.g., colors of fruits).
- Find the top 5 semantically close words for any input word.
- Utilize cosine similarity for measuring word closeness.


## Understanding GloVe and DSM
GloVe (Global Vectors for Word Representation) is a model for distributed word representation that captures semantic relationships between words in vector form. The DSM file contains word vectors which allow the system to calculate similarities and relationships efficiently.

## How It Works
The system reads the GloVe vectors from the CSV file, processes the input word to find its vector representation, and uses cosine similarity to identify related words. The top 5 semantically similar words are then presented as output.

## Usage
After running the program, you can input a word, and the system will return the top 5 semantically close words along with their cosine similarity values.

## Example:
```bash
Input: apple
Output:
1. fruit (0.85)
2. orange (0.82)
3. banana (0.80)
4. pear (0.78)
5. grape (0.75)
```
