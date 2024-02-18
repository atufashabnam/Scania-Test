# Scania-Test

##Assignment
We have a zoo, and want to know how much money we need to spend feeding the animals in
a day.
##Description
There are 2 types of food: meat and fruit.
Each of these will have a price per kg that will be provided in a text (.txt) file following the
next strict format (with strict format we mean that when processing the file, it is not needed
to protect the system from syntax errors):
=======================================
File: prices.txt
=======================================
Meat=12.56
Fruit=5.60
=======================================
The zoo has 3 different types of animals: carnivores, herbivores and omnivores. The first
only eat meat, the second, only fruit, and the third eat both.
Each animal eats an amount of food that depends on its weight. For each animal type, there
is a rate that determines how much food the animal needs.
The file animals.csv provides information about which animal species can exist in the zoo,
and their rates.
For the omnivore animals, this rate needs to be split into fruits and meat. A percentage is also
given to show how much of that rate must be covered with meat
=======================================
File: animals.csv (strict comma separated format)
=======================================
Lion;0.10;meat;
Tiger;0.09;meat;
Giraffe;0.08;fruit;
Zebra;0.08;fruit;
Wolf;0.07;both;90%
Piranha;0.5;both;50%
=======================================
A third file (zoo.xml) will tell us the content of our zoo
=======================================
File: zoo.xml
=======================================
<Zoo>
<Lions>
<Lion name='Simba' kg='160'/>
<Lion name='Nala' kg='172'/>

<Lion name='Mufasa' kg='190'/>
</Lions>
<Giraffes>
<Giraffe name='Hanna' kg='200'/>
<Giraffe name='Anna' kg='202'/>
<Giraffe name='Susanna' kg='199'/>
</Giraffes>
<Tigers>
<Tiger name='Dante' kg='150'/>
<Tiger name='Asimov' kg='142'/>
<Tiger name='Tolkien' kg='139'/>
</Tigers>
<Zebras>
<Zebra name='Chip' kg='100'/>
<Zebra name='Dale' kg='62'/>
</Zebras>
<Wolves>
<Wolf name='Pin' kg='78'/>
<Wolf name='Pon' kg='69'/>
</Wolves>
<Piranhas>
<Piranha name='Anastasia' kg='0.5'/>
</Piranhas>
</Zoo>
=======================================

##Additional notes
When solving the task please use Dependency Injection.

##Deliverables
Please send us a full solution, including unit tests. Exclude binaries since they might be
rejected by malware scanner.
