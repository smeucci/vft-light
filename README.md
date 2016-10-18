# vft-lite
Lite version of VFT based on [Vishal Sharma, Analisi Forense di Video File Container, 2015/16].


Usage
=====
```bash
java -jar vft.jar [-b | -d | -h | -p]  [-i <file>] [-o <folder>]
```

Examples
========

Parse a video file container into a xml file:
```bash
java -jar vft.jar -p  -i input.mp4 -o /output_folder
```

Batch parse a directory of videos. It also recreates the same subdirectory structure:
```bash
java -jar vft.jar -b  -i /input_folder -o /output_folder
```

Draw a tree from an input xml file:
```bash
java -jar vft.jar -d  -i input.xml
```

Print help message:
```bash
java -jar vft.jar -h
```
