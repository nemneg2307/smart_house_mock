# Smart house simulation

This project is a simulation of the physical smart house model which is used in course DA326A Software Engineering 2 @ HKR

----
# About project
The Smart house simulation will include just a few features that exist in the physical house model.

The following are the features that the physical house model includes (copied from the school document)

## Input signals

- [x] Fire alarm - a switch to simulate fire (Values 1 and 0)
- [x] Housebreaking alarm - house door (1 when closed, 0 when open)	           ← Bad naming
- [x] Water leakage - a switch to simulate water leakage (Values 1 and 0)
- [x] Temperature indoors - analog sensor LM35 (Outputs temp as double in format [dd.dd])
- [x] Temperature outdoors - digital sensor SMT160-30 (Outputs temp as int - will be converted to double value - not tested properly - maybe it gives the double value)
- [x] Stove - a switch to simulate the stove (Values 1 and 0)
- [x] Window - a switch to simulate window open/closed (Values 1 and 0)
- [x] Electricity consumption - analog sensor (Outputs int value of the voltage)
- [ ] Twilight automatic system - Light-to-voltage sensor (not tested)
- [ ] Power cut - digital sensor (1 when power is present, 0 when not)


## Output signals

- [ ] Timer T1 - a LED lamp marked T1 (can be used to show if alarm is armed)
- [ ] Timer T2 - a LED lamp marked T2 (can be used for something else)
- [x] Indoor light - One lamp inside the house
- [x] Outdoor light - One lamp outside the house
- [ ] Housebreaking alarm - a speaker
- [x] Fan (not MUX) - a fan mounted on the house’s loft (possible speed control-not checked)
- [x] Radiator - Four radiators mounted inside the house
- [x] Heating element wind - mounted close to the fan (untested)

----
To track the progres, 
- [x] means that the feature is implemeted or partially implemented
- [ ] means that the feature is not implemented

to be continued...