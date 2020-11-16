Testing truncated label.

1. Starting QGIS Server (run from this directory):

   ```bash
   docker run --rm -d -p 8380:80 --volume=$PWD/qgis-server/:/etc/qgisserver --name c2c-countries camptocamp/qgis-server
   ```

2. Run the print example (run from the root directory):

   ```bash
   ./gradlew print -PprintArgs="-config ../examples/src/test/resources/examples/printwms_truncated_label/config.yaml -spec ../examples/src/test/resources/examples/printwms_truncated_label/requestData-bbox-wms1_1_1.json -output ./output.pdf"
   ```
