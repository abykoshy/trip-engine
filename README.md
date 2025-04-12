# Engine to calculate trip details based on tap on/off

## How to Run

### Run the program using the following command:
    ```bash
    ./mvnw compile exec:java -Dexec.arguments="src/test/resources/taps.csv,target/trips-out.csv"
    ```
- The argument src/test/resources/taps.csv is the input file containing the taps
- The argument target/trips-out.csv is the output file