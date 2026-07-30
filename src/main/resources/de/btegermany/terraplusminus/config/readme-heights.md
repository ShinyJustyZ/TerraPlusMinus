# Custom terrain elevation datasets

You can add json files to this directory to add custom elevation dataset to TerraPlusMinus.

You can find the default elevation dataset configuration files
[here](https://github.com/SmylerMC/terraminusminus/blob/master/src/main/resources/net/buildtheearth/terraminusminus/dataset/scalar/heights.json5).

You custom dataset will be loaded alongside the default ones.
In order to override them, make sure to give your a higher priority.

For instructions on how to generate and configure your dataset,
you can refer to the [Terra++ wiki](https://github.com/BuildTheEarth/terraplusplus/wiki/Custom-terrain-dataset-guide).

## QET1 (Quantized Elevation Tile) format

TerraPlusMinus also supports the QET1 binary format, a delta-encoded
elevation tile format using Zstandard compression.
It reduces tile size by ~80% on average (up to 92%) compared to Terrarium
PNG and loads approximately 100x faster (estimated; direct binary read
bypasses ImageIO entirely).

Use `"blend": "LINEAR"` with QET1. The CUBIC blend mode causes spline
overshoot artifacts on quantized elevation data — see the QET1 benchmarks
for details.

Example configuration:
```json5
[
    {
        "dataset": {
            "urls": ["file:///C:/path/to/tiles/17/${x}/${z}.qet1"],
            "projection": {"web_mercator": {"zoom": 17}},
            "resolution": 256,
            "blend": "LINEAR",
            "parse": {"parse_qet1": {}}
        },
        "bounds": {"minX": -180.0, "maxX": 180.0, "minZ": -85.0, "maxZ": 85.0},
        "zooms": {"min": 0, "max": 3},
        "priority": 10.0
    }
]
```

To convert existing PNG or GeoTIFF tiles to QET1, use a conversion tool
that produces the QET1 binary format (10-byte header + Zstd-compressed
delta-encoded int16 elevations).
