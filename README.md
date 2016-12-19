# JPX - Java GPX library

This is a Java library for creating, reading and writing GPS data in [GPX](http://www.topografix.com/GPX) format. It implements version [1.1](http://www.topografix.com/GPX/1/1/) of the GPX format. (*First version will be released soon.*)

Javadoc of the library can be [here](https://jenetics.github.io/jpx/javadoc/jpx/0.1/index.html).

## Requirements

### Runtime
*  **JRE 8**: Java runtime version 8 is needed for using the library.

### Build time
*  **JDK 8**: The Java [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) must be installed.
*  **Gradle 3.x**: [Gradle](http://www.gradle.org/) is used for building the library. (Gradle is download automatically, if you are using the Gradle Wrapper script `./gradlew`, located in the base directory, for building the library.)
*  **TestNG 6.9.13**: JPX uses [TestNG](http://testng.org/doc/index.html) framework for unit tests.

## Building JPX

For building the JPX library you have to check out the master branch from Github.

    $ git clone https://github.com/jenetics/jpx.git
    
*Executing the tests:*
    
    $ cd jpx
    $ ./gradle test

*Building the library:*

    $ ./gradle jar

## Examples

### Creating new GPX object with 3 track-points

```java
final GPX gpx = GPX.builder()
    .addTrack(track -> track
        .addSegment(segment -> segment
            .addPoint(p -> p.lat(48.2081743).lon(16.3738189).ele(160))
            .addPoint(p -> p.lat(48.2081743).lon(16.3738189).ele(161))
            .addPoint(p -> p.lat(48.2081743).lon(16.3738189).ele(162))))
    .build();
```

**Writing GPX object to a file**

```java
// Writing "pretty" GPX file.
GPX.write(gpx, "    ", "gpx.xml");
```

*GPX output*

```xml
<?xml version="1.0" encoding="UTF-8"?>
<gpx xmlns="http://www.topografix.com/GPX/1/1" version="1.1" creator="JPX - Java GPX library">
    <trk>
        <trkseg>
            <trkpt lat="48.2081743" lon="16.3738189">
                <ele>160.0</ele>
            </trkpt>
            <trkpt lat="48.2081743" lon="16.3738189">
                <ele>161.0</ele>
            </trkpt>
            <trkpt lat="48.2081743" lon="16.3738189">
                <ele>162.0</ele>
            </trkpt>
        </trkseg>
    </trk>
</gpx>

```

### Reading GPX object from file

This example writes a given `GPX` object to a file, reads it again and prints the `WayPoint`s of all tracks and all track-segments to the console.

```java
GPX.write(gpx, "gpx.xml");
GPX.read("gpx.xml").tracks()
    .flatMap(Track::segments)
    .flatMap(TrackSegment::points)
    .forEach(System.out::println);

```

*Console output*

```bash
$ [lat=48.2081743, lon=48.2081743]
$ [lat=48.2081743, lon=48.2081743]
$ [lat=48.2081743, lon=48.2081743]

```

### Geodetic calculations

#### Distance between two points

```java
final Point start = WayPoint.of(47.2692124, 11.4041024);
final Point end = WayPoint.of(47.3502, 11.70584);
final Length distance = Geoid.WGSC_84.distance(start, end);
System.out.println(distance);
```

*Console output*

```bash
$ 24528.356073554987 m
```


## License

The library is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

    Copyright 2016 Franz Wilhelmstötter

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
