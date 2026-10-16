![Jurassic Park Banner](screenshots/Jurassic_Park.png)
# Jurassic Park Java Simulation

A console-based object-oriented adventure game set in Jurassic Park.  
You are trapped with two dinosaurs — find the key and escape through the hatch before they catch you!

## Features

- Randomly generated park (size 5×5 to 9×9)
- Player movement (`u` `d` `l` `r`)
- Zap dinosaurs in the direction of your last move (`z`)
- Random teleport (`?`)
- Dinosaurs move randomly every step
- Key pickup and hatch escape
- Win / lose conditions with step counters
- Input validation

## How to Play

| Command | Action                          |
|---------|---------------------------------|
| `u`     | Move up                         |
| `d`     | Move down                       |
| `l`     | Move left                       |
| `r`     | Move right                      |
| `z`     | Zap in the direction of last move |
| `?`     | Random teleport                 |

**Goal:** Pick up the key (`$`) and reach the hatch (`#`) without being caught by a dinosaur (`X`).

## How to Run

```bash
# Compile
javac src/SharmaRaghavA4.java

# Run
java -cp src SharmaRaghavA4
```

## Project Structure

```
textjurassic-park-java/
├── src/
│   └── SharmaRaghavA4.java   # Main game logic
├── .gitignore
└── README.md
```

## Concepts Demonstrated

Object-oriented design
Procedural game loop
Input validation with regular expressions
Random number generation
State management (key, zapped dinosaurs, steps)

Author
Raghav Sharma

