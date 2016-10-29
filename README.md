# vft-lite
Lite version of VFT based on [Vishal Sharma, Analisi Forense di Video File Container, 2015/16].

Javadoc
=======
[LINK](https://smeucci.github.io/vft-lite/ "vft-lite javadoc")

Usage
=====
```bash
 vft [-b | -d | -h | -m | -p | -u]   [-i <file|folder>] [-i2 <file>]  [-o <folder>]  [-wa]

```

Examples
========

Parse a video file container into a xml file:
```bash
java -jar vft.jar  -p  -i input.mp4  -o /output_folder
```

Batch parse a directory of videos. It also recreates the same subdirectory structure:
```bash
java -jar vft.jar  -b  -i /input_folder  -o /output_folder
```

Draw a tree from an input xml file:
```bash
java -jar vft.jar  -d  -i input.xml
```

Merge two xml files, with or without attributes:
```bash
java -jar vft.jar  -m  -wa  -i input.xml  -i2 input2.xml  -o /output_folder
```

Merge all xml files given a directory into a xml file saved in /output_folder, with or without attributes. It also consider subdirectories:
```bash
java -jar vft.jar  -u  -wa  -i /input_folder  -o /output_folder
```

Print help message:
```bash
java -jar vft.jar  -h
```
