# Fluid Surface Simulation
This is a simple interactive fluid surface simulation based on Matthias
Müller-Fischer's presentation
["Fast Water Simulation for Games Using Height Fields"](http://matthias-mueller-fischer.ch/talks/GDC2008.pdf).

## Building the Project

### Prerequisites
JDK 8 or later is required to build the project, and the JDK bin directory
(where `javac` and `jar` are located) must be in the `PATH`. The program is a
simple Swing application and doesn't require any external libraries.

### Compiling the Code
Simply run `build.sh` in the project's root directory. This will compile the
code and package it into `WaterSim.jar`.

## Running the Simulation
Launch the program with:
```
java -jar WaterSim.jar
```

The simulation is controlled with the mouse:
* Left-click to add fluid to the simulation at the cursor
* Right-click to add "energy" to the simulation at the cursor. This results in
    larger waves and actually increases the fluid level.
* Middle-click to mark cells as obstacles. Waves will reflect off obstacles in
    the same way that they reflect off the sides of the simulation area.

## License
This project is licensed under the [MIT license](LICENSE.txt).
